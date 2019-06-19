package net.minecraft.world.gen;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.ITickList;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.WorldGenTickList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenRegion implements IWorld {
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<IChunk> field_201684_a;
   private final int mainChunkX;
   private final int mainChunkZ;
   private final int field_217380_e;
   private final ServerWorld field_201689_f;
   private final long seed;
   private final int seaLevel;
   private final WorldInfo worldInfo;
   private final Random random;
   private final Dimension dimension;
   private final GenerationSettings field_201695_l;
   private final ITickList<Block> pendingBlockTickList = new WorldGenTickList<>((p_205335_1_) -> {
      return this.func_217349_x(p_205335_1_).getBlocksToBeTicked();
   });
   private final ITickList<Fluid> pendingFluidTickList = new WorldGenTickList<>((p_205334_1_) -> {
      return this.func_217349_x(p_205334_1_).getFluidsToBeTicked();
   });

   public WorldGenRegion(ServerWorld p_i50698_1_, List<IChunk> p_i50698_2_) {
      int i = MathHelper.floor(Math.sqrt((double)p_i50698_2_.size()));
      if (i * i != p_i50698_2_.size()) {
         throw new IllegalStateException("Cache size is not a square.");
      } else {
         ChunkPos chunkpos = p_i50698_2_.get(p_i50698_2_.size() / 2).getPos();
         this.field_201684_a = p_i50698_2_;
         this.mainChunkX = chunkpos.x;
         this.mainChunkZ = chunkpos.z;
         this.field_217380_e = i;
         this.field_201689_f = p_i50698_1_;
         this.seed = p_i50698_1_.getSeed();
         this.field_201695_l = p_i50698_1_.getChunkProvider().getChunkGenerator().getSettings();
         this.seaLevel = p_i50698_1_.getSeaLevel();
         this.worldInfo = p_i50698_1_.getWorldInfo();
         this.random = p_i50698_1_.getRandom();
         this.dimension = p_i50698_1_.getDimension();
      }
   }

   public int getMainChunkX() {
      return this.mainChunkX;
   }

   public int getMainChunkZ() {
      return this.mainChunkZ;
   }

   /**
    * Gets the chunk at the specified location.
    */
   public IChunk getChunk(int chunkX, int chunkZ) {
      return this.func_217348_a(chunkX, chunkZ, ChunkStatus.EMPTY);
   }

   @Nullable
   public IChunk getChunk(int p_217353_1_, int p_217353_2_, ChunkStatus p_217353_3_, boolean p_217353_4_) {
      IChunk ichunk;
      if (this.chunkExists(p_217353_1_, p_217353_2_)) {
         ChunkPos chunkpos = this.field_201684_a.get(0).getPos();
         int i = p_217353_1_ - chunkpos.x;
         int j = p_217353_2_ - chunkpos.z;
         ichunk = this.field_201684_a.get(i + j * this.field_217380_e);
         if (ichunk.getStatus().isAtLeast(p_217353_3_)) {
            return ichunk;
         }
      } else {
         ichunk = null;
      }

      if (!p_217353_4_) {
         return null;
      } else {
         IChunk ichunk1 = this.field_201684_a.get(0);
         IChunk ichunk2 = this.field_201684_a.get(this.field_201684_a.size() - 1);
         LOGGER.error("Requested chunk : {} {}", p_217353_1_, p_217353_2_);
         LOGGER.error("Region bounds : {} {} | {} {}", ichunk1.getPos().x, ichunk1.getPos().z, ichunk2.getPos().x, ichunk2.getPos().z);
         if (ichunk != null) {
            throw new RuntimeException(String.format("Chunk is not of correct status. Expecting %s, got %s | %s %s", p_217353_3_, ichunk.getStatus(), p_217353_1_, p_217353_2_));
         } else {
            throw new RuntimeException(String.format("We are asking a region for a chunk out of bound | %s %s", p_217353_1_, p_217353_2_));
         }
      }
   }

   public boolean chunkExists(int p_217354_1_, int p_217354_2_) {
      IChunk ichunk = this.field_201684_a.get(0);
      IChunk ichunk1 = this.field_201684_a.get(this.field_201684_a.size() - 1);
      return p_217354_1_ >= ichunk.getPos().x && p_217354_1_ <= ichunk1.getPos().x && p_217354_2_ >= ichunk.getPos().z && p_217354_2_ <= ichunk1.getPos().z;
   }

   public BlockState getBlockState(BlockPos pos) {
      return this.getChunk(pos.getX() >> 4, pos.getZ() >> 4).getBlockState(pos);
   }

   public IFluidState getFluidState(BlockPos pos) {
      return this.func_217349_x(pos).getFluidState(pos);
   }

   @Nullable
   public PlayerEntity getClosestPlayer(double x, double y, double z, double distance, Predicate<Entity> predicate) {
      return null;
   }

   public int getSkylightSubtracted() {
      return 0;
   }

   public Biome getBiome(BlockPos pos) {
      Biome biome = this.func_217349_x(pos).getBiomes()[pos.getX() & 15 | (pos.getZ() & 15) << 4];
      if (biome == null) {
         throw new RuntimeException(String.format("Biome is null @ %s", pos));
      } else {
         return biome;
      }
   }

   public int getLightFor(LightType type, BlockPos pos) {
      return this.getChunkProvider().getLightManager().func_215569_a(type).func_215611_b(pos);
   }

   public int getLightSubtracted(BlockPos pos, int amount) {
      return this.func_217349_x(pos).getLightSubtracted(pos, amount, this.getDimension().hasSkyLight());
   }

   /**
    * Sets a block to air, but also plays the sound and particles and can spawn drops
    */
   public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
      BlockState blockstate = this.getBlockState(pos);
      if (blockstate.isAir(this, pos)) {
         return false;
      } else {
         if (dropBlock) {
            TileEntity tileentity = blockstate.hasTileEntity() ? this.getTileEntity(pos) : null;
            Block.spawnDrops(blockstate, this.field_201689_f, pos, tileentity);
         }

         return this.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
      }
   }

   @Nullable
   public TileEntity getTileEntity(BlockPos pos) {
      IChunk ichunk = this.func_217349_x(pos);
      TileEntity tileentity = ichunk.getTileEntity(pos);
      if (tileentity != null) {
         return tileentity;
      } else {
         CompoundNBT compoundnbt = ichunk.getDeferredTileEntity(pos);
         if (compoundnbt != null) {
            if ("DUMMY".equals(compoundnbt.getString("id"))) {
               BlockState state = this.getBlockState(pos);
               if (!state.hasTileEntity()) {
                  return null;
               }

               tileentity = state.createTileEntity(this.field_201689_f);
            } else {
               tileentity = TileEntity.create(compoundnbt);
            }

            if (tileentity != null) {
               ichunk.addTileEntity(pos, tileentity);
               return tileentity;
            }
         }

         if (ichunk.getBlockState(pos).hasTileEntity()) {
            LOGGER.warn("Tried to access a block entity before it was created. {}", (Object)pos);
         }

         return null;
      }
   }

   /**
    * Sets a block state into this world.Flags are as follows:
    * 1 will cause a block update.
    * 2 will send the change to clients.
    * 4 will prevent the block from being re-rendered.
    * 8 will force any re-renders to run on the main thread instead
    * 16 will prevent neighbor reactions (e.g. fences connecting, observers pulsing).
    * 32 will prevent neighbor reactions from spawning drops.
    * 64 will signify the block is being moved.
    * Flags can be OR-ed
    */
   public boolean setBlockState(BlockPos pos, BlockState newState, int flags) {
      IChunk ichunk = this.func_217349_x(pos);
      BlockState blockstate = ichunk.setBlockState(pos, newState, false);
      if (blockstate != null) {
         this.field_201689_f.func_217393_a(pos, blockstate, newState);
      }

      Block block = newState.getBlock();
      if (newState.hasTileEntity()) {
         if (ichunk.getStatus().getType() == ChunkStatus.Type.LEVELCHUNK) {
            ichunk.addTileEntity(pos, newState.createTileEntity(this));
         } else {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putInt("x", pos.getX());
            compoundnbt.putInt("y", pos.getY());
            compoundnbt.putInt("z", pos.getZ());
            compoundnbt.putString("id", "DUMMY");
            ichunk.addTileEntity(compoundnbt);
         }
      } else if (blockstate != null && blockstate.hasTileEntity()) {
         ichunk.removeTileEntity(pos);
      }

      if (newState.blockNeedsPostProcessing(this, pos)) {
         this.markBlockForPostprocessing(pos);
      }

      return true;
   }

   private void markBlockForPostprocessing(BlockPos pos) {
      this.func_217349_x(pos).markBlockForPostprocessing(pos);
   }

   public boolean func_217376_c(Entity p_217376_1_) {
      int i = MathHelper.floor(p_217376_1_.posX / 16.0D);
      int j = MathHelper.floor(p_217376_1_.posZ / 16.0D);
      this.getChunk(i, j).addEntity(p_217376_1_);
      return true;
   }

   public boolean removeBlock(BlockPos p_217377_1_, boolean p_217377_2_) {
      return this.setBlockState(p_217377_1_, Blocks.AIR.getDefaultState(), 3);
   }

   public WorldBorder getWorldBorder() {
      return this.field_201689_f.getWorldBorder();
   }

   public boolean checkNoEntityCollision(@Nullable Entity entityIn, VoxelShape shape) {
      return true;
   }

   public boolean isRemote() {
      return false;
   }

   @Deprecated
   public ServerWorld getWorld() {
      return this.field_201689_f;
   }

   /**
    * Returns the world's WorldInfo object
    */
   public WorldInfo getWorldInfo() {
      return this.worldInfo;
   }

   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
      if (!this.chunkExists(pos.getX() >> 4, pos.getZ() >> 4)) {
         throw new RuntimeException("We are asking a region for a chunk out of bound");
      } else {
         return new DifficultyInstance(this.field_201689_f.getDifficulty(), this.field_201689_f.getDayTime(), 0L, this.field_201689_f.getCurrentMoonPhaseFactor());
      }
   }

   /**
    * gets the world's chunk provider
    */
   public AbstractChunkProvider getChunkProvider() {
      return this.field_201689_f.getChunkProvider();
   }

   /**
    * gets the random world seed
    */
   public long getSeed() {
      return this.seed;
   }

   public ITickList<Block> getPendingBlockTicks() {
      return this.pendingBlockTickList;
   }

   public ITickList<Fluid> getPendingFluidTicks() {
      return this.pendingFluidTickList;
   }

   public int getSeaLevel() {
      return this.seaLevel;
   }

   public Random getRandom() {
      return this.random;
   }

   public void notifyNeighbors(BlockPos pos, Block blockIn) {
   }

   public int getHeight(Heightmap.Type heightmapType, int x, int z) {
      return this.getChunk(x >> 4, z >> 4).getTopBlockY(heightmapType, x & 15, z & 15) + 1;
   }

   /**
    * Plays the specified sound for a player at the center of the given block position.
    */
   public void playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
   }

   public void addParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
   }

   public void playEvent(@Nullable PlayerEntity p_217378_1_, int p_217378_2_, BlockPos p_217378_3_, int p_217378_4_) {
   }

   /**
    * Gets the spawn point in the world
    */
   @OnlyIn(Dist.CLIENT)
   public BlockPos getSpawnPoint() {
      return this.field_201689_f.getSpawnPoint();
   }

   public Dimension getDimension() {
      return this.dimension;
   }

   public boolean func_217375_a(BlockPos p_217375_1_, Predicate<BlockState> p_217375_2_) {
      return p_217375_2_.test(this.getBlockState(p_217375_1_));
   }

   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
      return Collections.emptyList();
   }

   /**
    * Gets all entities within the specified AABB excluding the one passed into it.
    */
   public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
      return Collections.emptyList();
   }

   public List<PlayerEntity> getPlayers() {
      return Collections.emptyList();
   }

   public BlockPos getHeight(Heightmap.Type heightmapType, BlockPos pos) {
      return new BlockPos(pos.getX(), this.getHeight(heightmapType, pos.getX(), pos.getZ()), pos.getZ());
   }
}