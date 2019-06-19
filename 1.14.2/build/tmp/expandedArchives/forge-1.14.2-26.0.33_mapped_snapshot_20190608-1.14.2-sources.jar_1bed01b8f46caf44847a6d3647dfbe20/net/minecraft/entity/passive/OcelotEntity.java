package net.minecraft.entity.passive;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class OcelotEntity extends AnimalEntity {
   private static final Ingredient field_195402_bB = Ingredient.fromItems(Items.COD, Items.SALMON);
   private static final DataParameter<Boolean> field_213532_bA = EntityDataManager.createKey(OcelotEntity.class, DataSerializers.field_187198_h);
   private OcelotEntity.AvoidEntityGoal<PlayerEntity> field_213531_bB;
   private OcelotEntity.TemptGoal field_70914_e;

   public OcelotEntity(EntityType<? extends OcelotEntity> p_i50254_1_, World p_i50254_2_) {
      super(p_i50254_1_, p_i50254_2_);
      this.func_213529_dV();
   }

   private boolean func_213530_dX() {
      return this.dataManager.get(field_213532_bA);
   }

   private void func_213528_r(boolean p_213528_1_) {
      this.dataManager.set(field_213532_bA, p_213528_1_);
      this.func_213529_dV();
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putBoolean("Trusting", this.func_213530_dX());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.func_213528_r(compound.getBoolean("Trusting"));
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213532_bA, false);
   }

   protected void initEntityAI() {
      this.field_70914_e = new OcelotEntity.TemptGoal(this, 0.6D, field_195402_bB, true);
      this.field_70714_bg.addTask(1, new SwimGoal(this));
      this.field_70714_bg.addTask(3, this.field_70914_e);
      this.field_70714_bg.addTask(7, new LeapAtTargetGoal(this, 0.3F));
      this.field_70714_bg.addTask(8, new OcelotAttackGoal(this));
      this.field_70714_bg.addTask(9, new BreedGoal(this, 0.8D));
      this.field_70714_bg.addTask(10, new WaterAvoidingRandomWalkingGoal(this, 0.8D, 1.0000001E-5F));
      this.field_70714_bg.addTask(11, new LookAtGoal(this, PlayerEntity.class, 10.0F));
      this.field_70715_bh.addTask(1, new NearestAttackableTargetGoal<>(this, ChickenEntity.class, false));
      this.field_70715_bh.addTask(1, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, false, false, TurtleEntity.TARGET_DRY_BABY));
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

   public boolean func_213397_c(double p_213397_1_) {
      return !this.func_213530_dX() && this.ticksExisted > 2400;
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return SoundEvents.field_219667_hf;
   }

   /**
    * Get number of ticks, at least during which the living entity will be silent.
    */
   public int getTalkInterval() {
      return 900;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.field_219666_he;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.field_219668_hg;
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      return this.isInvulnerableTo(source) ? false : super.attackEntityFrom(source, amount);
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if ((this.field_70914_e == null || this.field_70914_e.isRunning()) && !this.func_213530_dX() && this.isBreedingItem(itemstack) && player.getDistanceSq(this) < 9.0D) {
         this.consumeItemFromStack(player, itemstack);
         if (!this.world.isRemote) {
            if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
               this.func_213528_r(true);
               this.func_213527_s(true);
               this.world.setEntityState(this, (byte)41);
            } else {
               this.func_213527_s(false);
               this.world.setEntityState(this, (byte)40);
            }
         }

         return true;
      } else {
         return super.processInteract(player, hand);
      }
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 41) {
         this.func_213527_s(true);
      } else if (id == 40) {
         this.func_213527_s(false);
      } else {
         super.handleStatusUpdate(id);
      }

   }

   private void func_213527_s(boolean p_213527_1_) {
      IParticleData iparticledata = ParticleTypes.HEART;
      if (!p_213527_1_) {
         iparticledata = ParticleTypes.SMOKE;
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.rand.nextGaussian() * 0.02D;
         double d1 = this.rand.nextGaussian() * 0.02D;
         double d2 = this.rand.nextGaussian() * 0.02D;
         this.world.addParticle(iparticledata, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + 0.5D + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
      }

   }

   protected void func_213529_dV() {
      if (this.field_213531_bB == null) {
         this.field_213531_bB = new OcelotEntity.AvoidEntityGoal<>(this, PlayerEntity.class, 16.0F, 0.8D, 1.33D);
      }

      this.field_70714_bg.removeTask(this.field_213531_bB);
      if (!this.func_213530_dX()) {
         this.field_70714_bg.addTask(4, this.field_213531_bB);
      }

   }

   public OcelotEntity createChild(AgeableEntity ageable) {
      return EntityType.OCELOT.create(this.world);
   }

   /**
    * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
    * the animal type)
    */
   public boolean isBreedingItem(ItemStack stack) {
      return field_195402_bB.test(stack);
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return this.rand.nextInt(3) != 0;
   }

   public boolean isNotColliding(IWorldReader worldIn) {
      if (worldIn.func_217346_i(this) && !worldIn.containsAnyLiquid(this.getBoundingBox())) {
         BlockPos blockpos = new BlockPos(this.posX, this.getBoundingBox().minY, this.posZ);
         if (blockpos.getY() < worldIn.getSeaLevel()) {
            return false;
         }

         BlockState blockstate = worldIn.getBlockState(blockpos.down());
         Block block = blockstate.getBlock();
         if (block == Blocks.GRASS_BLOCK || blockstate.isIn(BlockTags.LEAVES)) {
            return true;
         }
      }

      return false;
   }

   protected void func_213525_dW() {
      for(int i = 0; i < 2; ++i) {
         OcelotEntity ocelotentity = EntityType.OCELOT.create(this.world);
         ocelotentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
         ocelotentity.setGrowingAge(-24000);
         this.world.func_217376_c(ocelotentity);
      }

   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
      if (p_213386_1_.getRandom().nextInt(7) == 0) {
         this.func_213525_dW();
      }

      return p_213386_4_;
   }

   static class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
      private final OcelotEntity field_220874_i;

      public AvoidEntityGoal(OcelotEntity p_i50037_1_, Class<T> p_i50037_2_, float p_i50037_3_, double p_i50037_4_, double p_i50037_6_) {
         super(p_i50037_1_, p_i50037_2_, p_i50037_3_, p_i50037_4_, p_i50037_6_, EntityPredicates.CAN_AI_TARGET::test);
         this.field_220874_i = p_i50037_1_;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !this.field_220874_i.func_213530_dX() && super.shouldExecute();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return !this.field_220874_i.func_213530_dX() && super.shouldContinueExecuting();
      }
   }

   static class TemptGoal extends net.minecraft.entity.ai.goal.TemptGoal {
      private final OcelotEntity field_220765_c;

      public TemptGoal(OcelotEntity p_i50036_1_, double p_i50036_2_, Ingredient p_i50036_4_, boolean p_i50036_5_) {
         super(p_i50036_1_, p_i50036_2_, p_i50036_4_, p_i50036_5_);
         this.field_220765_c = p_i50036_1_;
      }

      protected boolean func_220761_g() {
         return super.func_220761_g() && !this.field_220765_c.func_213530_dX();
      }
   }
}