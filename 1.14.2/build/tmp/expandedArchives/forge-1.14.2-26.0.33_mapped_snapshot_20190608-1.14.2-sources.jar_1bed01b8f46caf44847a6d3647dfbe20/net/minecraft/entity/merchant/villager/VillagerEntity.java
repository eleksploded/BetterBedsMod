package net.minecraft.entity.merchant.villager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.schedule.Activity;
import net.minecraft.entity.ai.brain.schedule.Schedule;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.VillagerTasks;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.IReputationTracking;
import net.minecraft.entity.merchant.IReputationType;
import net.minecraft.entity.monster.WitchEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.villager.IVillagerDataHolder;
import net.minecraft.entity.villager.IVillagerType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.village.GossipManager;
import net.minecraft.village.GossipType;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.raid.Raid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VillagerEntity extends AbstractVillagerEntity implements IReputationTracking, IVillagerDataHolder {
   private static final DataParameter<VillagerData> field_213775_bC = EntityDataManager.createKey(VillagerEntity.class, DataSerializers.field_218813_q);
   public static final Map<Item, Integer> field_213788_bA = ImmutableMap.of(Items.BREAD, 4, Items.POTATO, 1, Items.CARROT, 1, Items.BEETROOT, 1);
   private static final Set<Item> field_213776_bD = ImmutableSet.of(Items.BREAD, Items.POTATO, Items.CARROT, Items.WHEAT, Items.WHEAT_SEEDS, Items.BEETROOT, Items.BEETROOT_SEEDS);
   private int timeUntilReset;
   private boolean field_213777_bF;
   @Nullable
   private PlayerEntity field_213778_bG;
   @Nullable
   private UUID field_213779_bH;
   private long field_213780_bI = Long.MIN_VALUE;
   private byte field_213781_bL;
   private final GossipManager field_213782_bM = new GossipManager();
   private long field_213783_bN;
   private int field_213784_bO;
   private long field_213785_bP;
   private static final ImmutableList<MemoryModuleType<?>> field_213786_bQ = ImmutableList.of(MemoryModuleType.field_220941_b, MemoryModuleType.field_220942_c, MemoryModuleType.field_220943_d, MemoryModuleType.field_220945_f, MemoryModuleType.field_220946_g, MemoryModuleType.field_220947_h, MemoryModuleType.field_220948_i, MemoryModuleType.field_220949_j, MemoryModuleType.field_220950_k, MemoryModuleType.field_220951_l, MemoryModuleType.field_220952_m, MemoryModuleType.field_220953_n, MemoryModuleType.field_220954_o, MemoryModuleType.field_220955_p, MemoryModuleType.field_220956_q, MemoryModuleType.field_220957_r, MemoryModuleType.field_220958_s, MemoryModuleType.field_220959_t, MemoryModuleType.field_220944_e, MemoryModuleType.field_220960_u, MemoryModuleType.field_220961_v, MemoryModuleType.field_220962_w, MemoryModuleType.field_223021_x);
   private static final ImmutableList<SensorType<? extends Sensor<? super VillagerEntity>>> field_213787_bR = ImmutableList.of(SensorType.field_220998_b, SensorType.field_220999_c, SensorType.field_221000_d, SensorType.field_221001_e, SensorType.field_221002_f, SensorType.field_221003_g, SensorType.field_221004_h, SensorType.field_221005_i);
   public static final Map<MemoryModuleType<GlobalPos>, BiPredicate<VillagerEntity, PointOfInterestType>> field_213774_bB = ImmutableMap.of(MemoryModuleType.field_220941_b, (p_213769_0_, p_213769_1_) -> {
      return p_213769_1_ == PointOfInterestType.field_221069_q;
   }, MemoryModuleType.field_220942_c, (p_213771_0_, p_213771_1_) -> {
      return p_213771_0_.func_213700_eh().getProfession().func_221149_b() == p_213771_1_;
   }, MemoryModuleType.field_220943_d, (p_213772_0_, p_213772_1_) -> {
      return p_213772_1_ == PointOfInterestType.field_221070_r;
   });

   public VillagerEntity(EntityType<? extends VillagerEntity> p_i50182_1_, World p_i50182_2_) {
      this(p_i50182_1_, p_i50182_2_, IVillagerType.field_221175_c);
   }

   public VillagerEntity(EntityType<? extends VillagerEntity> p_i50183_1_, World p_i50183_2_, IVillagerType p_i50183_3_) {
      super(p_i50183_1_, p_i50183_2_);
      ((GroundPathNavigator)this.getNavigator()).setBreakDoors(true);
      this.getNavigator().setCanSwim(true);
      this.setCanPickUpLoot(true);
      this.func_213753_a(this.func_213700_eh().func_221134_a(p_i50183_3_).func_221126_a(VillagerProfession.field_221151_a));
      this.field_213378_br = this.func_213364_a(new Dynamic<>(NBTDynamicOps.INSTANCE, new CompoundNBT()));
   }

   public Brain<VillagerEntity> getBrain() {
      return (Brain<VillagerEntity>) super.getBrain();
   }

   protected Brain<?> func_213364_a(Dynamic<?> p_213364_1_) {
      Brain<VillagerEntity> brain = new Brain<>(field_213786_bQ, field_213787_bR, p_213364_1_);
      this.func_213744_a(brain);
      return brain;
   }

   public void func_213770_a(ServerWorld p_213770_1_) {
      Brain<VillagerEntity> brain = this.getBrain();
      brain.stopAllTasks(p_213770_1_, this);
      this.field_213378_br = brain.copy();
      this.func_213744_a(this.getBrain());
   }

   private void func_213744_a(Brain<VillagerEntity> p_213744_1_) {
      VillagerProfession villagerprofession = this.func_213700_eh().getProfession();
      float f = (float)this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
      if (this.isChild()) {
         p_213744_1_.setSchedule(Schedule.field_221385_c);
         p_213744_1_.registerActivity(Activity.PLAY, VillagerTasks.func_220645_a(f));
      } else {
         p_213744_1_.setSchedule(Schedule.field_221386_d);
         p_213744_1_.registerActivity(Activity.WORK, VillagerTasks.func_220639_b(villagerprofession, f), ImmutableSet.of(Pair.of(MemoryModuleType.field_220942_c, MemoryModuleStatus.VALUE_PRESENT)));
      }

      p_213744_1_.registerActivity(Activity.CORE, VillagerTasks.core(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.MEET, VillagerTasks.meet(villagerprofession, f), ImmutableSet.of(Pair.of(MemoryModuleType.field_220943_d, MemoryModuleStatus.VALUE_PRESENT)));
      p_213744_1_.registerActivity(Activity.REST, VillagerTasks.rest(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.IDLE, VillagerTasks.idle(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.PANIC, VillagerTasks.panic(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.PRE_RAID, VillagerTasks.func_220642_g(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.RAID, VillagerTasks.raid(villagerprofession, f));
      p_213744_1_.registerActivity(Activity.HIDE, VillagerTasks.hide(villagerprofession, f));
      p_213744_1_.setDefaultActivities(ImmutableSet.of(Activity.CORE));
      p_213744_1_.setFallbackActivity(Activity.IDLE);
      p_213744_1_.switchTo(Activity.IDLE);
      p_213744_1_.updateActivity(this.world.getDayTime(), this.world.getGameTime());
   }

   /**
    * This is called when Entity's growing age timer reaches 0 (negative values are considered as a child, positive as
    * an adult)
    */
   protected void onGrowingAdult() {
      super.onGrowingAdult();
      if (this.world instanceof ServerWorld) {
         this.func_213770_a((ServerWorld)this.world);
      }

   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
   }

   protected void updateAITasks() {
      this.world.getProfiler().startSection("brain");
      this.getBrain().tick((ServerWorld)this.world, this);
      this.world.getProfiler().endSection();
      if (!this.func_213716_dX() && this.timeUntilReset > 0) {
         --this.timeUntilReset;
         if (this.timeUntilReset <= 0) {
            if (this.field_213777_bF) {
               this.populateBuyingList();
               this.field_213777_bF = false;
            }

            this.addPotionEffect(new EffectInstance(Effects.field_76428_l, 200, 0));
         }
      }

      if (this.field_213778_bG != null && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).func_217489_a(IReputationType.field_221033_e, this.field_213778_bG, this);
         this.world.setEntityState(this, (byte)14);
         this.field_213778_bG = null;
      }

      if (!this.isAIDisabled() && this.rand.nextInt(100) == 0) {
         Raid raid = ((ServerWorld)this.world).findRaid(new BlockPos(this));
         if (raid != null && raid.func_221333_v() && !raid.func_221319_a()) {
            this.world.setEntityState(this, (byte)42);
         }
      }

      super.updateAITasks();
   }

   public void func_213750_eg() {
      this.setCustomer((PlayerEntity)null);
      this.func_213748_et();
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (this.getShakeHeadTicks() > 0) {
         this.setShakeHeadTicks(this.getShakeHeadTicks() - 1);
      }

   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      boolean flag = itemstack.getItem() == Items.NAME_TAG;
      if (flag) {
         itemstack.interactWithEntity(player, this, hand);
         return true;
      } else if (itemstack.getItem() != Items.VILLAGER_SPAWN_EGG && this.isAlive() && !this.func_213716_dX() && !this.isPlayerSleeping() && !player.isSneaking()) {
         if (this.isChild()) {
            this.shakeHead();
            return super.processInteract(player, hand);
         } else {
            boolean flag1 = this.func_213706_dY().isEmpty();
            if (hand == Hand.MAIN_HAND) {
               if (flag1 && !this.world.isRemote) {
                  this.shakeHead();
               }

               player.addStat(Stats.TALKED_TO_VILLAGER);
            }

            if (flag1) {
               return super.processInteract(player, hand);
            } else {
               if (!this.world.isRemote && !this.offers.isEmpty()) {
                  this.func_213740_f(player);
               }

               return true;
            }
         }
      } else {
         return super.processInteract(player, hand);
      }
   }

   private void shakeHead() {
      this.setShakeHeadTicks(40);
      if (!this.world.isRemote()) {
         this.playSound(SoundEvents.ENTITY_VILLAGER_NO, this.getSoundVolume(), this.getSoundPitch());
      }

   }

   private void func_213740_f(PlayerEntity p_213740_1_) {
      this.func_213762_g(p_213740_1_);
      this.setCustomer(p_213740_1_);
      this.func_213707_a(p_213740_1_, this.getDisplayName(), this.func_213700_eh().getLevel());
   }

   public void setCustomer(@Nullable PlayerEntity player) {
      boolean flag = this.getCustomer() != null && player == null;
      super.setCustomer(player);
      if (flag) {
         this.func_213750_eg();
      }

   }

   public void func_213766_ei() {
      for(MerchantOffer merchantoffer : this.func_213706_dY()) {
         merchantoffer.func_222222_e();
         merchantoffer.func_222203_h();
      }

      this.field_213785_bP = this.world.getDayTime() % 24000L;
   }

   private void func_213762_g(PlayerEntity p_213762_1_) {
      int i = this.field_213782_bM.func_220921_a(p_213762_1_.getUniqueID(), (p_213760_0_) -> {
         return p_213760_0_ != GossipType.GOLEM;
      });
      if (i != 0) {
         for(MerchantOffer merchantoffer : this.func_213706_dY()) {
            merchantoffer.func_222207_a(-MathHelper.floor((float)i * merchantoffer.func_222211_m()));
         }
      }

      if (p_213762_1_.isPotionActive(Effects.field_220310_F)) {
         EffectInstance effectinstance = p_213762_1_.getActivePotionEffect(Effects.field_220310_F);
         int k = effectinstance.getAmplifier();

         for(MerchantOffer merchantoffer1 : this.func_213706_dY()) {
            double d0 = 0.3D + 0.0625D * (double)k;
            int j = (int)Math.floor(d0 * (double)merchantoffer1.func_222218_a().getCount());
            merchantoffer1.func_222207_a(-Math.max(j, 1));
         }
      }

   }

   private void func_213748_et() {
      for(MerchantOffer merchantoffer : this.func_213706_dY()) {
         merchantoffer.func_222220_k();
      }

   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213775_bC, new VillagerData(IVillagerType.field_221175_c, VillagerProfession.field_221151_a, 1));
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.put("VillagerData", this.func_213700_eh().func_221131_a(NBTDynamicOps.INSTANCE));
      p_213281_1_.putByte("FoodLevel", this.field_213781_bL);
      p_213281_1_.put("Gossips", this.field_213782_bM.func_220914_a(NBTDynamicOps.INSTANCE).getValue());
      p_213281_1_.putInt("Xp", this.field_213784_bO);
      p_213281_1_.putLong("LastRestock", this.field_213785_bP);
      if (this.field_213779_bH != null) {
         p_213281_1_.putUniqueId("BuddyGolem", this.field_213779_bH);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("VillagerData", 10)) {
         this.func_213753_a(new VillagerData(new Dynamic<>(NBTDynamicOps.INSTANCE, compound.get("VillagerData"))));
      }

      if (compound.contains("Offers", 10)) {
         this.offers = new MerchantOffers(compound.getCompound("Offers"));
      }

      if (compound.contains("FoodLevel", 1)) {
         this.field_213781_bL = compound.getByte("FoodLevel");
      }

      ListNBT listnbt = compound.getList("Gossips", 10);
      this.field_213782_bM.func_220918_a(new Dynamic<>(NBTDynamicOps.INSTANCE, listnbt));
      if (compound.contains("Xp", 3)) {
         this.field_213784_bO = compound.getInt("Xp");
      }

      this.field_213785_bP = compound.getLong("LastRestock");
      if (compound.hasUniqueId("BuddyGolem")) {
         this.field_213779_bH = compound.getUniqueId("BuddyGolem");
      }

      this.setCanPickUpLoot(true);
      this.func_213770_a((ServerWorld)this.world);
   }

   public boolean func_213397_c(double p_213397_1_) {
      return false;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.isPlayerSleeping()) {
         return null;
      } else {
         return this.func_213716_dX() ? SoundEvents.ENTITY_VILLAGER_TRADE : SoundEvents.ENTITY_VILLAGER_AMBIENT;
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_VILLAGER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_VILLAGER_DEATH;
   }

   public void func_213767_ej() {
      SoundEvent soundevent = this.func_213700_eh().getProfession().func_221149_b().func_221048_d();
      if (soundevent != null) {
         this.playSound(soundevent, this.getSoundVolume(), this.getSoundPitch());
      }

   }

   public void func_213753_a(VillagerData p_213753_1_) {
      VillagerData villagerdata = this.func_213700_eh();
      if (villagerdata.getProfession() != p_213753_1_.getProfession()) {
         this.offers = null;
      }

      this.dataManager.set(field_213775_bC, p_213753_1_);
   }

   public VillagerData func_213700_eh() {
      return this.dataManager.get(field_213775_bC);
   }

   protected void func_213713_b(MerchantOffer p_213713_1_) {
      int i = 3 + this.rand.nextInt(4);
      this.field_213784_bO += p_213713_1_.func_222210_n();
      this.field_213778_bG = this.getCustomer();
      if (this.func_213741_eu()) {
         this.timeUntilReset = 40;
         this.field_213777_bF = true;
         i += 5;
      }

      if (p_213713_1_.func_222221_q()) {
         this.world.func_217376_c(new ExperienceOrbEntity(this.world, this.posX, this.posY + 0.5D, this.posZ, i));
      }

   }

   /**
    * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
    * change our actual active target (for example if we are currently busy attacking someone else)
    */
   public void setRevengeTarget(@Nullable LivingEntity livingBase) {
      if (livingBase != null && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).func_217489_a(IReputationType.field_221031_c, livingBase, this);
         if (this.isAlive() && livingBase instanceof PlayerEntity) {
            this.world.setEntityState(this, (byte)13);
         }
      }

      super.setRevengeTarget(livingBase);
   }

   /**
    * Called when the mob's health reaches 0.
    */
   public void onDeath(DamageSource cause) {
      this.func_213742_a(MemoryModuleType.field_220941_b);
      this.func_213742_a(MemoryModuleType.field_220942_c);
      this.func_213742_a(MemoryModuleType.field_220943_d);
      super.onDeath(cause);
   }

   public void func_213742_a(MemoryModuleType<GlobalPos> p_213742_1_) {
      if (this.world instanceof ServerWorld) {
         MinecraftServer minecraftserver = ((ServerWorld)this.world).getServer();
         this.field_213378_br.getMemory(p_213742_1_).ifPresent((p_213752_3_) -> {
            ServerWorld serverworld = minecraftserver.getWorld(p_213752_3_.func_218177_a());
            PointOfInterestManager pointofinterestmanager = serverworld.func_217443_B();
            Optional<PointOfInterestType> optional = pointofinterestmanager.func_219148_c(p_213752_3_.func_218180_b());
            BiPredicate<VillagerEntity, PointOfInterestType> bipredicate = field_213774_bB.get(p_213742_1_);
            if (optional.isPresent() && bipredicate.test(this, optional.get())) {
               pointofinterestmanager.func_219142_b(p_213752_3_.func_218180_b());
               DebugPacketSender.func_218801_c(serverworld, p_213752_3_.func_218180_b());
            }

         });
      }
   }

   public boolean func_213743_em() {
      return this.field_213781_bL + this.func_213751_ew() >= 12 && this.getGrowingAge() == 0;
   }

   public void func_213765_en() {
      if (this.field_213781_bL < 12 && this.func_213751_ew() != 0) {
         for(int i = 0; i < this.func_213715_ed().getSizeInventory(); ++i) {
            ItemStack itemstack = this.func_213715_ed().getStackInSlot(i);
            if (!itemstack.isEmpty()) {
               Integer integer = field_213788_bA.get(itemstack.getItem());
               if (integer != null) {
                  int j = itemstack.getCount();

                  for(int k = j; k > 0; --k) {
                     this.field_213781_bL = (byte)(this.field_213781_bL + integer);
                     this.func_213715_ed().decrStackSize(i, 1);
                     if (this.field_213781_bL >= 12) {
                        return;
                     }
                  }
               }
            }
         }

      }
   }

   public int func_223107_f(PlayerEntity p_223107_1_) {
      return this.field_213782_bM.func_220921_a(p_223107_1_.getUniqueID(), (p_223103_0_) -> {
         return p_223103_0_ != GossipType.GOLEM;
      });
   }

   public void func_213758_s(int p_213758_1_) {
      this.field_213781_bL = (byte)(this.field_213781_bL - p_213758_1_);
   }

   public void func_213768_b(MerchantOffers p_213768_1_) {
      this.offers = p_213768_1_;
   }

   private boolean func_213741_eu() {
      int i = this.func_213700_eh().getLevel();
      return VillagerData.func_221128_d(i) && this.field_213784_bO >= VillagerData.func_221127_c(i);
   }

   private void populateBuyingList() {
      this.func_213753_a(this.func_213700_eh().func_221135_a(this.func_213700_eh().getLevel() + 1));
      this.func_213712_ef();
   }

   public ITextComponent getDisplayName() {
      Team team = this.getTeam();
      ITextComponent itextcomponent = this.getCustomName();
      if (itextcomponent != null) {
         return ScorePlayerTeam.formatMemberName(team, itextcomponent).applyTextStyle((p_213755_1_) -> {
            p_213755_1_.setHoverEvent(this.getHoverEvent()).setInsertion(this.getCachedUniqueIdString());
         });
      } else {
         VillagerProfession villagerprofession = this.func_213700_eh().getProfession();
         ITextComponent itextcomponent1 = (new TranslationTextComponent(this.getType().getTranslationKey() + '.' + Registry.field_218370_L.getKey(villagerprofession).getPath())).applyTextStyle((p_213773_1_) -> {
            p_213773_1_.setHoverEvent(this.getHoverEvent()).setInsertion(this.getCachedUniqueIdString());
         });
         if (team != null) {
            itextcomponent1.applyTextStyle(team.getColor());
         }

         return itextcomponent1;
      }
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 12) {
         this.func_213718_a(ParticleTypes.HEART);
      } else if (id == 13) {
         this.func_213718_a(ParticleTypes.ANGRY_VILLAGER);
      } else if (id == 14) {
         this.func_213718_a(ParticleTypes.HAPPY_VILLAGER);
      } else if (id == 42) {
         this.func_213718_a(ParticleTypes.field_218422_X);
      } else {
         super.handleStatusUpdate(id);
      }

   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      if (p_213386_3_ == SpawnReason.BREEDING) {
         this.func_213753_a(this.func_213700_eh().func_221126_a(VillagerProfession.field_221151_a));
      }

      if (p_213386_3_ == SpawnReason.COMMAND || p_213386_3_ == SpawnReason.SPAWN_EGG || p_213386_3_ == SpawnReason.SPAWNER) {
         this.func_213753_a(this.func_213700_eh().func_221134_a(IVillagerType.func_221170_a(p_213386_1_.getBiome(new BlockPos(this)))));
      }

      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   public VillagerEntity createChild(AgeableEntity ageable) {
      double d0 = this.rand.nextDouble();
      IVillagerType ivillagertype;
      if (d0 < 0.5D) {
         ivillagertype = IVillagerType.func_221170_a(this.world.getBiome(new BlockPos(this)));
      } else if (d0 < 0.75D) {
         ivillagertype = this.func_213700_eh().getType();
      } else {
         ivillagertype = ((VillagerEntity)ageable).func_213700_eh().getType();
      }

      VillagerEntity villagerentity = new VillagerEntity(EntityType.VILLAGER, this.world, ivillagertype);
      villagerentity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(villagerentity)), SpawnReason.BREEDING, (ILivingEntityData)null, (CompoundNBT)null);
      return villagerentity;
   }

   /**
    * Called when a lightning bolt hits the entity.
    */
   public void onStruckByLightning(LightningBoltEntity lightningBolt) {
      WitchEntity witchentity = EntityType.WITCH.create(this.world);
      witchentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      witchentity.onInitialSpawn(this.world, this.world.getDifficultyForLocation(new BlockPos(witchentity)), SpawnReason.CONVERSION, (ILivingEntityData)null, (CompoundNBT)null);
      witchentity.setNoAI(this.isAIDisabled());
      if (this.hasCustomName()) {
         witchentity.setCustomName(this.getCustomName());
         witchentity.setCustomNameVisible(this.isCustomNameVisible());
      }

      this.world.func_217376_c(witchentity);
      this.remove();
   }

   /**
    * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
    * better.
    */
   protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
      ItemStack itemstack = itemEntity.getItem();
      Item item = itemstack.getItem();
      VillagerProfession villagerprofession = this.func_213700_eh().getProfession();
      if (field_213776_bD.contains(item) || villagerprofession.func_221146_c().contains(item)) {
         if (villagerprofession == VillagerProfession.field_221156_f && item == Items.WHEAT) {
            int i = itemstack.getCount() / 3;
            if (i > 0) {
               ItemStack itemstack1 = this.func_213715_ed().addItem(new ItemStack(Items.BREAD, i));
               itemstack.shrink(i * 3);
               if (!itemstack1.isEmpty()) {
                  this.entityDropItem(itemstack1, 0.5F);
               }
            }
         }

         this.onItemPickup(itemEntity, itemstack.getCount());
         ItemStack itemstack2 = this.func_213715_ed().addItem(itemstack);
         if (itemstack2.isEmpty()) {
            itemEntity.remove();
         } else {
            itemstack.setCount(itemstack2.getCount());
         }
      }

   }

   /**
    * Used by {@link net.minecraft.entity.ai.EntityAIVillagerInteract EntityAIVillagerInteract} to check if the villager
    * can give some items from an inventory to another villager.
    */
   public boolean canAbondonItems() {
      return this.func_213751_ew() >= 24;
   }

   public boolean wantsMoreFood() {
      boolean flag = this.func_213700_eh().getProfession() == VillagerProfession.field_221156_f;
      int i = this.func_213751_ew();
      return flag ? i < 60 : i < 12;
   }

   private int func_213751_ew() {
      Inventory inventory = this.func_213715_ed();
      return field_213788_bA.entrySet().stream().mapToInt((p_213764_1_) -> {
         return inventory.count(p_213764_1_.getKey()) * p_213764_1_.getValue();
      }).sum();
   }

   /**
    * Returns true if villager has seeds, potatoes or carrots in inventory
    */
   public boolean isFarmItemInInventory() {
      Inventory inventory = this.func_213715_ed();
      return inventory.hasAny(ImmutableSet.of(Items.WHEAT_SEEDS, Items.POTATO, Items.CARROT, Items.BEETROOT_SEEDS));
   }

   protected void func_213712_ef() {
      VillagerData villagerdata = this.func_213700_eh();
      Int2ObjectMap<VillagerTrades.ITrade[]> int2objectmap = VillagerTrades.field_221239_a.get(villagerdata.getProfession());
      if (int2objectmap != null && !int2objectmap.isEmpty()) {
         VillagerTrades.ITrade[] avillagertrades$itrade = int2objectmap.get(villagerdata.getLevel());
         if (avillagertrades$itrade != null) {
            MerchantOffers merchantoffers = this.func_213706_dY();
            this.func_213717_a(merchantoffers, avillagertrades$itrade, 2);
         }
      }
   }

   public void func_213746_a(VillagerEntity p_213746_1_, long p_213746_2_) {
      if ((p_213746_2_ < this.field_213783_bN || p_213746_2_ >= this.field_213783_bN + 1200L) && (p_213746_2_ < p_213746_1_.field_213783_bN || p_213746_2_ >= p_213746_1_.field_213783_bN + 1200L)) {
         boolean flag = this.func_213754_a(p_213746_2_);
         if (this.func_213747_a(this) || flag) {
            this.field_213782_bM.func_220916_a(this.getUniqueID(), GossipType.GOLEM, GossipType.GOLEM.field_220933_i);
         }

         this.field_213782_bM.func_220912_a(p_213746_1_.field_213782_bM, this.rand, 10);
         this.field_213783_bN = p_213746_2_;
         p_213746_1_.field_213783_bN = p_213746_2_;
         if (flag) {
            this.func_213749_ex();
         }

      }
   }

   private void func_213749_ex() {
      VillagerData villagerdata = this.func_213700_eh();
      if (villagerdata.getProfession() != VillagerProfession.field_221151_a && villagerdata.getProfession() != VillagerProfession.field_221162_l) {
         Optional<VillagerEntity.GolemStatus> optional = this.getBrain().getMemory(MemoryModuleType.field_220960_u);
         if (optional.isPresent()) {
            if (optional.get().func_221140_c(this.world.getGameTime())) {
               boolean flag = this.field_213782_bM.func_220910_a(GossipType.GOLEM, (p_213757_0_) -> {
                  return p_213757_0_ > 30.0D;
               }) >= 5L;
               if (flag) {
                  AxisAlignedBB axisalignedbb = this.getBoundingBox().expand(80.0D, 80.0D, 80.0D);
                  List<VillagerEntity> list = this.world.getEntitiesWithinAABB(VillagerEntity.class, axisalignedbb, this::func_213747_a).stream().limit(5L).collect(Collectors.toList());
                  boolean flag1 = list.size() >= 5;
                  if (flag1) {
                     IronGolemEntity irongolementity = this.func_213759_ey();
                     if (irongolementity != null) {
                        UUID uuid = irongolementity.getUniqueID();

                        for(VillagerEntity villagerentity : list) {
                           for(VillagerEntity villagerentity1 : list) {
                              villagerentity.field_213782_bM.func_220916_a(villagerentity1.getUniqueID(), GossipType.GOLEM, -GossipType.GOLEM.field_220933_i);
                           }

                           villagerentity.field_213779_bH = uuid;
                        }

                     }
                  }
               }
            }
         }
      }
   }

   private boolean func_213747_a(Entity p_213747_1_) {
      return this.field_213782_bM.func_220921_a(p_213747_1_.getUniqueID(), (p_213745_0_) -> {
         return p_213745_0_ == GossipType.GOLEM;
      }) > 30;
   }

   private boolean func_213754_a(long p_213754_1_) {
      if (this.field_213779_bH == null) {
         return true;
      } else {
         if (this.field_213780_bI < p_213754_1_ + 1200L) {
            this.field_213780_bI = p_213754_1_ + 1200L;
            Entity entity = ((ServerWorld)this.world).func_217461_a(this.field_213779_bH);
            if (entity == null || !entity.isAlive() || this.getDistanceSq(entity) > 6400.0D) {
               this.field_213779_bH = null;
               return true;
            }
         }

         return false;
      }
   }

   @Nullable
   private IronGolemEntity func_213759_ey() {
      BlockPos blockpos = new BlockPos(this);

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.add(this.world.rand.nextInt(16) - 8, this.world.rand.nextInt(6) - 3, this.world.rand.nextInt(16) - 8);
         IronGolemEntity irongolementity = EntityType.IRON_GOLEM.func_220349_b(this.world, (CompoundNBT)null, (ITextComponent)null, (PlayerEntity)null, blockpos1, SpawnReason.MOB_SUMMONED, false, false);
         if (irongolementity != null) {
            if (irongolementity.canSpawn(this.world, SpawnReason.MOB_SUMMONED) && irongolementity.isNotColliding(this.world)) {
               this.world.func_217376_c(irongolementity);
               return irongolementity;
            }

            irongolementity.remove();
         }
      }

      return null;
   }

   public void func_213739_a(IReputationType p_213739_1_, Entity p_213739_2_) {
      if (p_213739_1_ == IReputationType.field_221029_a) {
         this.field_213782_bM.func_220916_a(p_213739_2_.getUniqueID(), GossipType.MAJOR_POSITIVE, 25);
      } else if (p_213739_1_ == IReputationType.field_221033_e) {
         this.field_213782_bM.func_220916_a(p_213739_2_.getUniqueID(), GossipType.TRADING, 2);
      } else if (p_213739_1_ == IReputationType.field_221031_c) {
         this.field_213782_bM.func_220916_a(p_213739_2_.getUniqueID(), GossipType.MINOR_NEGATIVE, 25);
      } else if (p_213739_1_ == IReputationType.field_221032_d) {
         this.field_213782_bM.func_220916_a(p_213739_2_.getUniqueID(), GossipType.MAJOR_NEGATIVE, 25);
      }

   }

   public int func_213708_dV() {
      return this.field_213784_bO;
   }

   public void func_213761_t(int p_213761_1_) {
      this.field_213784_bO = p_213761_1_;
   }

   public long func_213763_er() {
      return this.field_213785_bP;
   }

   protected void func_213387_K() {
      super.func_213387_K();
      DebugPacketSender.func_218798_a(this);
   }

   public void func_213342_e(BlockPos p_213342_1_) {
      super.func_213342_e(p_213342_1_);
      VillagerEntity.GolemStatus villagerentity$golemstatus = this.getBrain().getMemory(MemoryModuleType.field_220960_u).orElseGet(VillagerEntity.GolemStatus::new);
      villagerentity$golemstatus.func_221142_b(this.world.getGameTime());
      this.field_213378_br.setMemory(MemoryModuleType.field_220960_u, villagerentity$golemstatus);
   }

   public static final class GolemStatus {
      private long field_221144_a;
      private long field_221145_b;

      public void func_221143_a(long p_221143_1_) {
         this.field_221144_a = p_221143_1_;
      }

      public void func_221142_b(long p_221142_1_) {
         this.field_221145_b = p_221142_1_;
      }

      private boolean func_221140_c(long p_221140_1_) {
         return p_221140_1_ - this.field_221145_b < 24000L && p_221140_1_ - this.field_221144_a < 36000L;
      }
   }
}