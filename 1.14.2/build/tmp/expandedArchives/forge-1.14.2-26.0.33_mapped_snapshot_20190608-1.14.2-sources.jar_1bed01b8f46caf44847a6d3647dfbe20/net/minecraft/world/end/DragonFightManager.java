package net.minecraft.world.end;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Unit;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.BossInfo;
import net.minecraft.world.ServerBossInfo;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkHolder;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.chunk.TicketType;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.EndGatewayConfig;
import net.minecraft.world.gen.feature.EndPodiumFeature;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DragonFightManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Predicate<Entity> VALID_PLAYER = EntityPredicates.IS_ALIVE.and(EntityPredicates.withinRange(0.0D, 128.0D, 0.0D, 192.0D));
   private final ServerBossInfo field_186109_c = (ServerBossInfo)(new ServerBossInfo(new TranslationTextComponent("entity.minecraft.ender_dragon"), BossInfo.Color.PINK, BossInfo.Overlay.PROGRESS)).setPlayEndBossMusic(true).setCreateFog(true);
   private final ServerWorld field_186110_d;
   private final List<Integer> gateways = Lists.newArrayList();
   private final BlockPattern field_186112_f;
   private int ticksSinceDragonSeen;
   private int aliveCrystals;
   private int ticksSinceCrystalsScanned;
   private int ticksSinceLastPlayerScan;
   private boolean dragonKilled;
   private boolean previouslyKilled;
   private UUID dragonUniqueId;
   private boolean scanForLegacyFight = true;
   private BlockPos exitPortalLocation;
   private DragonSpawnState respawnState;
   private int respawnStateTicks;
   private List<EnderCrystalEntity> crystals;

   public DragonFightManager(ServerWorld worldIn, CompoundNBT compound, net.minecraft.world.dimension.EndDimension dim) {
      this.field_186110_d = worldIn;
      if (compound.contains("DragonKilled", 99)) {
         if (compound.hasUniqueId("DragonUUID")) {
            this.dragonUniqueId = compound.getUniqueId("DragonUUID");
         }

         this.dragonKilled = compound.getBoolean("DragonKilled");
         this.previouslyKilled = compound.getBoolean("PreviouslyKilled");
         this.scanForLegacyFight = !compound.getBoolean("LegacyScanPerformed"); // Forge: fix MC-105080
         if (compound.getBoolean("IsRespawning")) {
            this.respawnState = DragonSpawnState.START;
         }

         if (compound.contains("ExitPortalLocation", 10)) {
            this.exitPortalLocation = NBTUtil.readBlockPos(compound.getCompound("ExitPortalLocation"));
         }
      } else {
         this.dragonKilled = true;
         this.previouslyKilled = true;
      }

      if (compound.contains("Gateways", 9)) {
         ListNBT listnbt = compound.getList("Gateways", 3);

         for(int i = 0; i < listnbt.size(); ++i) {
            this.gateways.add(listnbt.getInt(i));
         }
      } else {
         this.gateways.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
         Collections.shuffle(this.gateways, new Random(dim.getSeed()));
      }

      this.field_186112_f = BlockPatternBuilder.start().aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").aisle("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").aisle("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").where('#', CachedBlockInfo.hasState(BlockMatcher.forBlock(Blocks.BEDROCK))).build();
   }

   public CompoundNBT write() {
      CompoundNBT compoundnbt = new CompoundNBT();
      if (this.dragonUniqueId != null) {
         compoundnbt.putUniqueId("DragonUUID", this.dragonUniqueId);
      }

      compoundnbt.putBoolean("DragonKilled", this.dragonKilled);
      compoundnbt.putBoolean("PreviouslyKilled", this.previouslyKilled);
      compoundnbt.putBoolean("LegacyScanPerformed", !this.scanForLegacyFight); // Forge: fix MC-105080
      if (this.exitPortalLocation != null) {
         compoundnbt.put("ExitPortalLocation", NBTUtil.writeBlockPos(this.exitPortalLocation));
      }

      ListNBT listnbt = new ListNBT();

      for(int i : this.gateways) {
         listnbt.add(new IntNBT(i));
      }

      compoundnbt.put("Gateways", listnbt);
      return compoundnbt;
   }

   public void tick() {
      this.field_186109_c.setVisible(!this.dragonKilled);
      if (++this.ticksSinceLastPlayerScan >= 20) {
         this.updatePlayers();
         this.ticksSinceLastPlayerScan = 0;
      }

      if (!this.field_186109_c.getPlayers().isEmpty()) {
         this.field_186110_d.getChunkProvider().func_217228_a(TicketType.field_219489_b, new ChunkPos(0, 0), 9, Unit.INSTANCE);
         boolean flag = this.func_222670_k();
         if (this.scanForLegacyFight && flag) {
            this.func_210827_g();
            this.scanForLegacyFight = false;
         }

         if (this.respawnState != null) {
            if (this.crystals == null && flag) {
               this.respawnState = null;
               this.tryRespawnDragon();
            }

            this.respawnState.process(this.field_186110_d, this, this.crystals, this.respawnStateTicks++, this.exitPortalLocation);
         }

         if (!this.dragonKilled) {
            if ((this.dragonUniqueId == null || ++this.ticksSinceDragonSeen >= 1200) && flag) {
               this.func_210828_h();
               this.ticksSinceDragonSeen = 0;
            }

            if (++this.ticksSinceCrystalsScanned >= 100 && flag) {
               this.findAliveCrystals();
               this.ticksSinceCrystalsScanned = 0;
            }
         }
      } else {
         this.field_186110_d.getChunkProvider().func_217222_b(TicketType.field_219489_b, new ChunkPos(0, 0), 9, Unit.INSTANCE);
      }

   }

   private void func_210827_g() {
      LOGGER.info("Scanning for legacy world dragon fight...");
      boolean flag = this.hasDragonBeenKilled();
      if (flag) {
         LOGGER.info("Found that the dragon has been killed in this world already.");
         this.previouslyKilled = true;
      } else {
         LOGGER.info("Found that the dragon has not yet been killed in this world.");
         this.previouslyKilled = false;
         this.generatePortal(false);
      }

      List<EnderDragonEntity> list = this.field_186110_d.getDragons();
      if (list.isEmpty()) {
         this.dragonKilled = true;
      } else {
         EnderDragonEntity enderdragonentity = list.get(0);
         this.dragonUniqueId = enderdragonentity.getUniqueID();
         LOGGER.info("Found that there's a dragon still alive ({})", (Object)enderdragonentity);
         this.dragonKilled = false;
         if (!flag) {
            LOGGER.info("But we didn't have a portal, let's remove it.");
            enderdragonentity.remove();
            this.dragonUniqueId = null;
         }
      }

      if (!this.previouslyKilled && this.dragonKilled) {
         this.dragonKilled = false;
      }

   }

   private void func_210828_h() {
      List<EnderDragonEntity> list = this.field_186110_d.getDragons();
      if (list.isEmpty()) {
         LOGGER.debug("Haven't seen the dragon, respawning it");
         this.createNewDragon();
      } else {
         LOGGER.debug("Haven't seen our dragon, but found another one to use.");
         this.dragonUniqueId = list.get(0).getUniqueID();
      }

   }

   protected void setRespawnState(DragonSpawnState state) {
      if (this.respawnState == null) {
         throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
      } else {
         this.respawnStateTicks = 0;
         if (state == DragonSpawnState.END) {
            this.respawnState = null;
            this.dragonKilled = false;
            EnderDragonEntity enderdragonentity = this.createNewDragon();

            for(ServerPlayerEntity serverplayerentity : this.field_186109_c.getPlayers()) {
               CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity, enderdragonentity);
            }
         } else {
            this.respawnState = state;
         }

      }
   }

   private boolean hasDragonBeenKilled() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            Chunk chunk = this.field_186110_d.getChunk(i, j);

            for(TileEntity tileentity : chunk.getTileEntityMap().values()) {
               if (tileentity instanceof EndPortalTileEntity) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   @Nullable
   private BlockPattern.PatternHelper findExitPortal() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = -8; j <= 8; ++j) {
            Chunk chunk = this.field_186110_d.getChunk(i, j);

            for(TileEntity tileentity : chunk.getTileEntityMap().values()) {
               if (tileentity instanceof EndPortalTileEntity) {
                  BlockPattern.PatternHelper blockpattern$patternhelper = this.field_186112_f.match(this.field_186110_d, tileentity.getPos());
                  if (blockpattern$patternhelper != null) {
                     BlockPos blockpos = blockpattern$patternhelper.translateOffset(3, 3, 3).getPos();
                     if (this.exitPortalLocation == null && blockpos.getX() == 0 && blockpos.getZ() == 0) {
                        this.exitPortalLocation = blockpos;
                     }

                     return blockpattern$patternhelper;
                  }
               }
            }
         }
      }

      int k = this.field_186110_d.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION).getY();

      for(int l = k; l >= 0; --l) {
         BlockPattern.PatternHelper blockpattern$patternhelper1 = this.field_186112_f.match(this.field_186110_d, new BlockPos(EndPodiumFeature.END_PODIUM_LOCATION.getX(), l, EndPodiumFeature.END_PODIUM_LOCATION.getZ()));
         if (blockpattern$patternhelper1 != null) {
            if (this.exitPortalLocation == null) {
               this.exitPortalLocation = blockpattern$patternhelper1.translateOffset(3, 3, 3).getPos();
            }

            return blockpattern$patternhelper1;
         }
      }

      return null;
   }

   private boolean func_222670_k() {
      for(int i = -8; i <= 8; ++i) {
         for(int j = 8; j <= 8; ++j) {
            IChunk ichunk = this.field_186110_d.getChunk(i, j, ChunkStatus.FULL, false);
            if (!(ichunk instanceof Chunk)) {
               return false;
            }

            ChunkHolder.LocationType chunkholder$locationtype = ((Chunk)ichunk).func_217321_u();
            if (!chunkholder$locationtype.func_219065_a(ChunkHolder.LocationType.TICKING)) {
               return false;
            }
         }
      }

      return true;
   }

   private void updatePlayers() {
      Set<ServerPlayerEntity> set = Sets.newHashSet();

      for(ServerPlayerEntity serverplayerentity : this.field_186110_d.getPlayers(VALID_PLAYER)) {
         this.field_186109_c.addPlayer(serverplayerentity);
         set.add(serverplayerentity);
      }

      Set<ServerPlayerEntity> set1 = Sets.newHashSet(this.field_186109_c.getPlayers());
      set1.removeAll(set);

      for(ServerPlayerEntity serverplayerentity1 : set1) {
         this.field_186109_c.removePlayer(serverplayerentity1);
      }

   }

   private void findAliveCrystals() {
      this.ticksSinceCrystalsScanned = 0;
      this.aliveCrystals = 0;

      for(EndSpikeFeature.EndSpike endspikefeature$endspike : EndSpikeFeature.func_214554_a(this.field_186110_d)) {
         this.aliveCrystals += this.field_186110_d.getEntitiesWithinAABB(EnderCrystalEntity.class, endspikefeature$endspike.getTopBoundingBox()).size();
      }

      LOGGER.debug("Found {} end crystals still alive", (int)this.aliveCrystals);
   }

   public void processDragonDeath(EnderDragonEntity dragon) {
      if (dragon.getUniqueID().equals(this.dragonUniqueId)) {
         this.field_186109_c.setPercent(0.0F);
         this.field_186109_c.setVisible(false);
         this.generatePortal(true);
         this.spawnNewGateway();
         if (!this.previouslyKilled) {
            this.field_186110_d.setBlockState(this.field_186110_d.getHeight(Heightmap.Type.MOTION_BLOCKING, EndPodiumFeature.END_PODIUM_LOCATION), Blocks.DRAGON_EGG.getDefaultState());
         }

         this.previouslyKilled = true;
         this.dragonKilled = true;
      }

   }

   private void spawnNewGateway() {
      if (!this.gateways.isEmpty()) {
         int i = this.gateways.remove(this.gateways.size() - 1);
         int j = MathHelper.floor(96.0D * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
         int k = MathHelper.floor(96.0D * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double)i)));
         this.generateGateway(new BlockPos(j, 75, k));
      }
   }

   private void generateGateway(BlockPos pos) {
      this.field_186110_d.playEvent(3000, pos, 0);
      Feature.END_GATEWAY.place(this.field_186110_d, this.field_186110_d.getChunkProvider().getChunkGenerator(), new Random(), pos, EndGatewayConfig.func_214698_a());
   }

   private void generatePortal(boolean active) {
      EndPodiumFeature endpodiumfeature = new EndPodiumFeature(active);
      if (this.exitPortalLocation == null) {
         for(this.exitPortalLocation = this.field_186110_d.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.END_PODIUM_LOCATION).down(); this.field_186110_d.getBlockState(this.exitPortalLocation).getBlock() == Blocks.BEDROCK && this.exitPortalLocation.getY() > this.field_186110_d.getSeaLevel(); this.exitPortalLocation = this.exitPortalLocation.down()) {
            ;
         }
      }

      endpodiumfeature.place(this.field_186110_d, this.field_186110_d.getChunkProvider().getChunkGenerator(), new Random(), this.exitPortalLocation, IFeatureConfig.NO_FEATURE_CONFIG);
   }

   private EnderDragonEntity createNewDragon() {
      this.field_186110_d.getChunk(new BlockPos(0, 128, 0));
      EnderDragonEntity enderdragonentity = EntityType.ENDER_DRAGON.create(this.field_186110_d);
      enderdragonentity.getPhaseManager().setPhase(PhaseType.HOLDING_PATTERN);
      enderdragonentity.setLocationAndAngles(0.0D, 128.0D, 0.0D, this.field_186110_d.rand.nextFloat() * 360.0F, 0.0F);
      this.field_186110_d.func_217376_c(enderdragonentity);
      this.dragonUniqueId = enderdragonentity.getUniqueID();
      return enderdragonentity;
   }

   public void dragonUpdate(EnderDragonEntity dragonIn) {
      if (dragonIn.getUniqueID().equals(this.dragonUniqueId)) {
         this.field_186109_c.setPercent(dragonIn.getHealth() / dragonIn.getMaxHealth());
         this.ticksSinceDragonSeen = 0;
         if (dragonIn.hasCustomName()) {
            this.field_186109_c.setName(dragonIn.getDisplayName());
         }
      }

   }

   public int getNumAliveCrystals() {
      return this.aliveCrystals;
   }

   public void onCrystalDestroyed(EnderCrystalEntity crystal, DamageSource dmgSrc) {
      if (this.respawnState != null && this.crystals.contains(crystal)) {
         LOGGER.debug("Aborting respawn sequence");
         this.respawnState = null;
         this.respawnStateTicks = 0;
         this.resetSpikeCrystals();
         this.generatePortal(true);
      } else {
         this.findAliveCrystals();
         Entity entity = this.field_186110_d.func_217461_a(this.dragonUniqueId);
         if (entity instanceof EnderDragonEntity) {
            ((EnderDragonEntity)entity).onCrystalDestroyed(crystal, new BlockPos(crystal), dmgSrc);
         }
      }

   }

   public boolean hasPreviouslyKilledDragon() {
      return this.previouslyKilled;
   }

   public void tryRespawnDragon() {
      if (this.dragonKilled && this.respawnState == null) {
         BlockPos blockpos = this.exitPortalLocation;
         if (blockpos == null) {
            LOGGER.debug("Tried to respawn, but need to find the portal first.");
            BlockPattern.PatternHelper blockpattern$patternhelper = this.findExitPortal();
            if (blockpattern$patternhelper == null) {
               LOGGER.debug("Couldn't find a portal, so we made one.");
               this.generatePortal(true);
            } else {
               LOGGER.debug("Found the exit portal & temporarily using it.");
            }

            blockpos = this.exitPortalLocation;
         }

         List<EnderCrystalEntity> list1 = Lists.newArrayList();
         BlockPos blockpos1 = blockpos.up(1);

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            List<EnderCrystalEntity> list = this.field_186110_d.getEntitiesWithinAABB(EnderCrystalEntity.class, new AxisAlignedBB(blockpos1.offset(direction, 2)));
            if (list.isEmpty()) {
               return;
            }

            list1.addAll(list);
         }

         LOGGER.debug("Found all crystals, respawning dragon.");
         this.respawnDragon(list1);
      }

   }

   private void respawnDragon(List<EnderCrystalEntity> crystalsIn) {
      if (this.dragonKilled && this.respawnState == null) {
         for(BlockPattern.PatternHelper blockpattern$patternhelper = this.findExitPortal(); blockpattern$patternhelper != null; blockpattern$patternhelper = this.findExitPortal()) {
            for(int i = 0; i < this.field_186112_f.getPalmLength(); ++i) {
               for(int j = 0; j < this.field_186112_f.getThumbLength(); ++j) {
                  for(int k = 0; k < this.field_186112_f.getFingerLength(); ++k) {
                     CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.translateOffset(i, j, k);
                     if (cachedblockinfo.getBlockState().getBlock() == Blocks.BEDROCK || cachedblockinfo.getBlockState().getBlock() == Blocks.END_PORTAL) {
                        this.field_186110_d.setBlockState(cachedblockinfo.getPos(), Blocks.END_STONE.getDefaultState());
                     }
                  }
               }
            }
         }

         this.respawnState = DragonSpawnState.START;
         this.respawnStateTicks = 0;
         this.generatePortal(false);
         this.crystals = crystalsIn;
      }

   }

   public void resetSpikeCrystals() {
      for(EndSpikeFeature.EndSpike endspikefeature$endspike : EndSpikeFeature.func_214554_a(this.field_186110_d)) {
         for(EnderCrystalEntity endercrystalentity : this.field_186110_d.getEntitiesWithinAABB(EnderCrystalEntity.class, endspikefeature$endspike.getTopBoundingBox())) {
            endercrystalentity.setInvulnerable(false);
            endercrystalentity.setBeamTarget((BlockPos)null);
         }
      }
   }

   public void addPlayer(ServerPlayerEntity player) {
      this.field_186109_c.addPlayer(player);
   }

   public void removePlayer(ServerPlayerEntity player) {
      this.field_186109_c.removePlayer(player);
   }
}