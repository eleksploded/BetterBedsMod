package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.Util;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CatEntity extends TameableEntity {
   private static final Ingredient field_213426_bE = Ingredient.fromItems(Items.COD, Items.SALMON);
   private static final DataParameter<Integer> field_213427_bF = EntityDataManager.createKey(CatEntity.class, DataSerializers.field_187192_b);
   private static final DataParameter<Boolean> field_213428_bG = EntityDataManager.createKey(CatEntity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Boolean> field_213429_bH = EntityDataManager.createKey(CatEntity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> field_213430_bI = EntityDataManager.createKey(CatEntity.class, DataSerializers.field_187192_b);
   public static final Map<Integer, ResourceLocation> field_213425_bD = Util.make(Maps.newHashMap(), (p_213410_0_) -> {
      p_213410_0_.put(0, new ResourceLocation("textures/entity/cat/tabby.png"));
      p_213410_0_.put(1, new ResourceLocation("textures/entity/cat/black.png"));
      p_213410_0_.put(2, new ResourceLocation("textures/entity/cat/red.png"));
      p_213410_0_.put(3, new ResourceLocation("textures/entity/cat/siamese.png"));
      p_213410_0_.put(4, new ResourceLocation("textures/entity/cat/british_shorthair.png"));
      p_213410_0_.put(5, new ResourceLocation("textures/entity/cat/calico.png"));
      p_213410_0_.put(6, new ResourceLocation("textures/entity/cat/persian.png"));
      p_213410_0_.put(7, new ResourceLocation("textures/entity/cat/ragdoll.png"));
      p_213410_0_.put(8, new ResourceLocation("textures/entity/cat/white.png"));
      p_213410_0_.put(9, new ResourceLocation("textures/entity/cat/jellie.png"));
      p_213410_0_.put(10, new ResourceLocation("textures/entity/cat/all_black.png"));
   });
   private CatEntity.AvoidPlayerGoal<PlayerEntity> field_213431_bJ;
   private net.minecraft.entity.ai.goal.TemptGoal field_213432_bK;
   private float field_213433_bL;
   private float field_213434_bM;
   private float field_213435_bN;
   private float field_213436_bO;
   private float field_213437_bP;
   private float field_213438_bQ;

   public CatEntity(EntityType<? extends CatEntity> p_i50284_1_, World p_i50284_2_) {
      super(p_i50284_1_, p_i50284_2_);
   }

   public ResourceLocation func_213423_ee() {
      return field_213425_bD.get(this.func_213413_ef());
   }

   protected void initEntityAI() {
      this.field_70911_d = new SitGoal(this);
      this.field_213432_bK = new CatEntity.TemptGoal(this, 0.6D, field_213426_bE, true);
      this.field_70714_bg.addTask(1, new SwimGoal(this));
      this.field_70714_bg.addTask(1, new CatEntity.MorningGiftGoal(this));
      this.field_70714_bg.addTask(2, this.field_70911_d);
      this.field_70714_bg.addTask(3, this.field_213432_bK);
      this.field_70714_bg.addTask(5, new CatLieOnBedGoal(this, 1.1D, 8));
      this.field_70714_bg.addTask(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F));
      this.field_70714_bg.addTask(7, new CatSitOnBlockGoal(this, 0.8D));
      this.field_70714_bg.addTask(8, new LeapAtTargetGoal(this, 0.3F));
      this.field_70714_bg.addTask(9, new OcelotAttackGoal(this));
      this.field_70714_bg.addTask(10, new BreedGoal(this, 0.8D));
      this.field_70714_bg.addTask(11, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
      this.field_70714_bg.addTask(12, new LookAtGoal(this, PlayerEntity.class, 10.0F));
      this.field_70715_bh.addTask(1, new NonTamedTargetGoal<>(this, RabbitEntity.class, false, (Predicate<LivingEntity>)null));
      this.field_70715_bh.addTask(1, new NonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
   }

   public int func_213413_ef() {
      return this.dataManager.get(field_213427_bF);
   }

   public void func_213422_r(int p_213422_1_) {
      if (p_213422_1_ < 0 || p_213422_1_ >= 11) {
         p_213422_1_ = this.rand.nextInt(10);
      }

      this.dataManager.set(field_213427_bF, p_213422_1_);
   }

   public void func_213419_u(boolean p_213419_1_) {
      this.dataManager.set(field_213428_bG, p_213419_1_);
   }

   public boolean func_213416_eg() {
      return this.dataManager.get(field_213428_bG);
   }

   public void func_213415_v(boolean p_213415_1_) {
      this.dataManager.set(field_213429_bH, p_213415_1_);
   }

   public boolean func_213409_eh() {
      return this.dataManager.get(field_213429_bH);
   }

   public DyeColor func_213414_ei() {
      return DyeColor.byId(this.dataManager.get(field_213430_bI));
   }

   public void func_213417_a(DyeColor p_213417_1_) {
      this.dataManager.set(field_213430_bI, p_213417_1_.getId());
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213427_bF, 1);
      this.dataManager.register(field_213428_bG, false);
      this.dataManager.register(field_213429_bH, false);
      this.dataManager.register(field_213430_bI, DyeColor.RED.getId());
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("CatType", this.func_213413_ef());
      p_213281_1_.putByte("CollarColor", (byte)this.func_213414_ei().getId());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.func_213422_r(compound.getInt("CatType"));
      if (compound.contains("CollarColor", 99)) {
         this.func_213417_a(DyeColor.byId(compound.getInt("CollarColor")));
      }

   }

   public void updateAITasks() {
      if (this.getMoveHelper().isUpdating()) {
         double d0 = this.getMoveHelper().getSpeed();
         if (d0 == 0.6D) {
            this.setSneaking(true);
            this.setSprinting(false);
         } else if (d0 == 1.33D) {
            this.setSneaking(false);
            this.setSprinting(true);
         } else {
            this.setSneaking(false);
            this.setSprinting(false);
         }
      } else {
         this.setSneaking(false);
         this.setSprinting(false);
      }

   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      if (this.isTamed()) {
         if (this.isInLove()) {
            return SoundEvents.ENTITY_CAT_PURR;
         } else {
            return this.rand.nextInt(4) == 0 ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT;
         }
      } else {
         return SoundEvents.field_219606_aE;
      }
   }

   /**
    * Get number of ticks, at least during which the living entity will be silent.
    */
   public int getTalkInterval() {
      return 120;
   }

   public void func_213420_ej() {
      this.playSound(SoundEvents.ENTITY_CAT_HISS, this.getSoundVolume(), this.getSoundPitch());
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_CAT_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_CAT_DEATH;
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   /**
    * Decreases ItemStack size by one
    */
   protected void consumeItemFromStack(PlayerEntity player, ItemStack stack) {
      if (this.isBreedingItem(stack)) {
         this.playSound(SoundEvents.field_219607_aG, 1.0F, 1.0F);
      }

      super.consumeItemFromStack(player, stack);
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (this.field_213432_bK != null && this.field_213432_bK.isRunning() && !this.isTamed() && this.ticksExisted % 100 == 0) {
         this.playSound(SoundEvents.field_219608_aI, 1.0F, 1.0F);
      }

      this.func_213412_ek();
   }

   private void func_213412_ek() {
      if ((this.func_213416_eg() || this.func_213409_eh()) && this.ticksExisted % 5 == 0) {
         this.playSound(SoundEvents.ENTITY_CAT_PURR, 0.6F + 0.4F * (this.rand.nextFloat() - this.rand.nextFloat()), 1.0F);
      }

      this.func_213418_el();
      this.func_213411_em();
   }

   private void func_213418_el() {
      this.field_213434_bM = this.field_213433_bL;
      this.field_213436_bO = this.field_213435_bN;
      if (this.func_213416_eg()) {
         this.field_213433_bL = Math.min(1.0F, this.field_213433_bL + 0.15F);
         this.field_213435_bN = Math.min(1.0F, this.field_213435_bN + 0.08F);
      } else {
         this.field_213433_bL = Math.max(0.0F, this.field_213433_bL - 0.22F);
         this.field_213435_bN = Math.max(0.0F, this.field_213435_bN - 0.13F);
      }

   }

   private void func_213411_em() {
      this.field_213438_bQ = this.field_213437_bP;
      if (this.func_213409_eh()) {
         this.field_213437_bP = Math.min(1.0F, this.field_213437_bP + 0.1F);
      } else {
         this.field_213437_bP = Math.max(0.0F, this.field_213437_bP - 0.13F);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public float func_213408_v(float p_213408_1_) {
      return MathHelper.func_219799_g(p_213408_1_, this.field_213434_bM, this.field_213433_bL);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213421_w(float p_213421_1_) {
      return MathHelper.func_219799_g(p_213421_1_, this.field_213436_bO, this.field_213435_bN);
   }

   @OnlyIn(Dist.CLIENT)
   public float func_213424_x(float p_213424_1_) {
      return MathHelper.func_219799_g(p_213424_1_, this.field_213438_bQ, this.field_213437_bP);
   }

   public CatEntity createChild(AgeableEntity ageable) {
      CatEntity catentity = EntityType.field_220360_g.create(this.world);
      if (ageable instanceof CatEntity) {
         if (this.rand.nextBoolean()) {
            catentity.func_213422_r(this.func_213413_ef());
         } else {
            catentity.func_213422_r(((CatEntity)ageable).func_213413_ef());
         }

         if (this.isTamed()) {
            catentity.setOwnerId(this.getOwnerId());
            catentity.setTamed(true);
            if (this.rand.nextBoolean()) {
               catentity.func_213417_a(this.func_213414_ei());
            } else {
               catentity.func_213417_a(((CatEntity)ageable).func_213414_ei());
            }
         }
      }

      return catentity;
   }

   /**
    * Returns true if the mob is currently able to mate with the specified mob.
    */
   public boolean canMateWith(AnimalEntity otherAnimal) {
      if (!this.isTamed()) {
         return false;
      } else if (!(otherAnimal instanceof CatEntity)) {
         return false;
      } else {
         CatEntity catentity = (CatEntity)otherAnimal;
         return catentity.isTamed() && super.canMateWith(otherAnimal);
      }
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
      if (p_213386_1_.getCurrentMoonPhaseFactor() > 0.9F) {
         this.func_213422_r(this.rand.nextInt(11));
      } else {
         this.func_213422_r(this.rand.nextInt(10));
      }

      if (Feature.field_202334_l.isPositionInsideStructure(p_213386_1_, new BlockPos(this))) {
         this.func_213422_r(10);
         this.enablePersistence();
      }

      return p_213386_4_;
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      Item item = itemstack.getItem();
      if (this.isTamed()) {
         if (this.isOwner(player)) {
            if (item instanceof DyeItem) {
               DyeColor dyecolor = ((DyeItem)item).getDyeColor();
               if (dyecolor != this.func_213414_ei()) {
                  this.func_213417_a(dyecolor);
                  if (!player.playerAbilities.isCreativeMode) {
                     itemstack.shrink(1);
                  }

                  this.enablePersistence();
                  return true;
               }
            } else if (this.isBreedingItem(itemstack)) {
               if (this.getHealth() < this.getMaxHealth() && item.func_219971_r()) {
                  this.consumeItemFromStack(player, itemstack);
                  this.heal((float)item.func_219967_s().func_221466_a());
                  return true;
               }
            } else if (!this.world.isRemote) {
               this.field_70911_d.setSitting(!this.isSitting());
            }
         }
      } else if (this.isBreedingItem(itemstack)) {
         this.consumeItemFromStack(player, itemstack);
         if (!this.world.isRemote) {
            if (this.rand.nextInt(3) == 0) {
               this.setTamedBy(player);
               this.playTameEffect(true);
               this.field_70911_d.setSitting(true);
               this.world.setEntityState(this, (byte)7);
            } else {
               this.playTameEffect(false);
               this.world.setEntityState(this, (byte)6);
            }
         }

         this.enablePersistence();
         return true;
      }

      boolean flag = super.processInteract(player, hand);
      if (flag) {
         this.enablePersistence();
      }

      return flag;
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return field_213426_bE.test(stack);
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return p_213348_2_.height * 0.5F;
   }

   public boolean func_213397_c(double p_213397_1_) {
      return !this.isTamed() && this.ticksExisted > 2400;
   }

   protected void setupTamedAI() {
      if (this.field_213431_bJ == null) {
         this.field_213431_bJ = new CatEntity.AvoidPlayerGoal<>(this, PlayerEntity.class, 16.0F, 0.8D, 1.33D);
      }

      this.field_70714_bg.removeTask(this.field_213431_bJ);
      if (!this.isTamed()) {
         this.field_70714_bg.addTask(4, this.field_213431_bJ);
      }

   }

   static class AvoidPlayerGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final CatEntity field_220873_i;

      public AvoidPlayerGoal(CatEntity p_i50440_1_, Class<T> p_i50440_2_, float p_i50440_3_, double p_i50440_4_, double p_i50440_6_) {
         super(p_i50440_1_, p_i50440_2_, p_i50440_3_, p_i50440_4_, p_i50440_6_, EntityPredicates.CAN_AI_TARGET::test);
         this.field_220873_i = p_i50440_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !this.field_220873_i.isTamed() && super.shouldExecute();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return !this.field_220873_i.isTamed() && super.shouldContinueExecuting();
      }
   }

   static class MorningGiftGoal extends Goal {
      private final CatEntity field_220806_a;
      private PlayerEntity field_220807_b;
      private BlockPos field_220808_c;
      private int field_220809_d;

      public MorningGiftGoal(CatEntity p_i50439_1_) {
         this.field_220806_a = p_i50439_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (!this.field_220806_a.isTamed()) {
            return false;
         } else if (this.field_220806_a.isSitting()) {
            return false;
         } else {
            LivingEntity livingentity = this.field_220806_a.getOwner();
            if (livingentity instanceof PlayerEntity) {
               this.field_220807_b = (PlayerEntity)livingentity;
               if (!livingentity.isPlayerSleeping()) {
                  return false;
               }

               if (this.field_220806_a.getDistanceSq(this.field_220807_b) > 100.0D) {
                  return false;
               }

               BlockPos blockpos = new BlockPos(this.field_220807_b);
               BlockState blockstate = this.field_220806_a.world.getBlockState(blockpos);
               if (blockstate.getBlock().isIn(BlockTags.field_219747_F)) {
                  Direction direction = blockstate.get(BedBlock.HORIZONTAL_FACING);
                  this.field_220808_c = new BlockPos(blockpos.getX() - direction.getXOffset(), blockpos.getY(), blockpos.getZ() - direction.getZOffset());
                  return !this.func_220805_g();
               }
            }

            return false;
         }
      }

      private boolean func_220805_g() {
         for(CatEntity catentity : this.field_220806_a.world.getEntitiesWithinAABB(CatEntity.class, (new AxisAlignedBB(this.field_220808_c)).grow(2.0D))) {
            if (catentity != this.field_220806_a && (catentity.func_213416_eg() || catentity.func_213409_eh())) {
               return true;
            }
         }

         return false;
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return this.field_220806_a.isTamed() && !this.field_220806_a.isSitting() && this.field_220807_b != null && this.field_220807_b.isPlayerSleeping() && this.field_220808_c != null && !this.func_220805_g();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         if (this.field_220808_c != null) {
            this.field_220806_a.getAISit().setSitting(false);
            this.field_220806_a.getNavigator().tryMoveToXYZ((double)this.field_220808_c.getX(), (double)this.field_220808_c.getY(), (double)this.field_220808_c.getZ(), (double)1.1F);
         }

      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_220806_a.func_213419_u(false);
         float f = this.field_220806_a.world.getCelestialAngle(1.0F);
         if (this.field_220807_b.getSleepTimer() >= 100 && (double)f > 0.77D && (double)f < 0.8D && (double)this.field_220806_a.world.getRandom().nextFloat() < 0.7D) {
            this.func_220804_h();
         }

         this.field_220809_d = 0;
         this.field_220806_a.func_213415_v(false);
         this.field_220806_a.getNavigator().clearPath();
      }

      private void func_220804_h() {
         Random random = this.field_220806_a.getRNG();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         blockpos$mutableblockpos.setPos(this.field_220806_a);
         this.field_220806_a.func_213373_a((double)(blockpos$mutableblockpos.getX() + random.nextInt(11) - 5), (double)(blockpos$mutableblockpos.getY() + random.nextInt(5) - 2), (double)(blockpos$mutableblockpos.getZ() + random.nextInt(11) - 5), false);
         blockpos$mutableblockpos.setPos(this.field_220806_a);
         LootTable loottable = this.field_220806_a.world.getServer().getLootTableManager().getLootTableFromLocation(LootTables.field_215797_af);
         LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)this.field_220806_a.world)).withParameter(LootParameters.field_216286_f, blockpos$mutableblockpos).withParameter(LootParameters.field_216281_a, this.field_220806_a).withRandom(random);

         for(ItemStack itemstack : loottable.func_216113_a(lootcontext$builder.build(LootParameterSets.field_216264_e))) {
            this.field_220806_a.world.func_217376_c(new ItemEntity(this.field_220806_a.world, (double)((float)blockpos$mutableblockpos.getX() - MathHelper.sin(this.field_220806_a.renderYawOffset * ((float)Math.PI / 180F))), (double)blockpos$mutableblockpos.getY(), (double)((float)blockpos$mutableblockpos.getZ() + MathHelper.cos(this.field_220806_a.renderYawOffset * ((float)Math.PI / 180F))), itemstack));
         }

      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         if (this.field_220807_b != null && this.field_220808_c != null) {
            this.field_220806_a.getAISit().setSitting(false);
            this.field_220806_a.getNavigator().tryMoveToXYZ((double)this.field_220808_c.getX(), (double)this.field_220808_c.getY(), (double)this.field_220808_c.getZ(), (double)1.1F);
            if (this.field_220806_a.getDistanceSq(this.field_220807_b) < 2.5D) {
               ++this.field_220809_d;
               if (this.field_220809_d > 16) {
                  this.field_220806_a.func_213419_u(true);
                  this.field_220806_a.func_213415_v(false);
               } else {
                  this.field_220806_a.faceEntity(this.field_220807_b, 45.0F, 45.0F);
                  this.field_220806_a.func_213415_v(true);
               }
            } else {
               this.field_220806_a.func_213419_u(false);
            }
         }

      }
   }

   static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
      @Nullable
      private PlayerEntity field_220763_c;
      private final CatEntity field_220764_d;

      public TemptGoal(CatEntity p_i50438_1_, double p_i50438_2_, Ingredient p_i50438_4_, boolean p_i50438_5_) {
         super(p_i50438_1_, p_i50438_2_, p_i50438_4_, p_i50438_5_);
         this.field_220764_d = p_i50438_1_;
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         super.tick();
         if (this.field_220763_c == null && this.field_75284_a.getRNG().nextInt(600) == 0) {
            this.field_220763_c = this.field_75289_h;
         } else if (this.field_75284_a.getRNG().nextInt(500) == 0) {
            this.field_220763_c = null;
         }

      }

      protected boolean func_220761_g() {
         return this.field_220763_c != null && this.field_220763_c.equals(this.field_75289_h) ? false : super.func_220761_g();
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return super.shouldExecute() && !this.field_220764_d.isTamed();
      }
   }
}