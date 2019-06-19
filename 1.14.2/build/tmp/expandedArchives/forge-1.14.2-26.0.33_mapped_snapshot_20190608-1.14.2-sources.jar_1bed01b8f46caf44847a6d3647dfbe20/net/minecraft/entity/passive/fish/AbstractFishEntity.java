package net.minecraft.entity.passive.fish;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public abstract class AbstractFishEntity extends WaterMobEntity {
   private static final DataParameter<Boolean> FROM_BUCKET = EntityDataManager.createKey(AbstractFishEntity.class, DataSerializers.field_187198_h);

   public AbstractFishEntity(EntityType<? extends AbstractFishEntity> type, World worldIn) {
      super(type, worldIn);
      this.field_70765_h = new AbstractFishEntity.MoveHelperController(this);
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return p_213348_2_.height * 0.65F;
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
   }

   public boolean func_213392_I() {
      return this.isFromBucket();
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      BlockPos blockpos = new BlockPos(this);
      return p_213380_1_.getBlockState(blockpos).getBlock() == Blocks.WATER && p_213380_1_.getBlockState(blockpos.up()).getBlock() == Blocks.WATER ? super.canSpawn(p_213380_1_, p_213380_2_) : false;
   }

   public boolean func_213397_c(double p_213397_1_) {
      return !this.isFromBucket() && !this.hasCustomName();
   }

   /**
    * Will return how many at most can spawn in a chunk at once.
    */
   public int getMaxSpawnedInChunk() {
      return 8;
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(FROM_BUCKET, false);
   }

   private boolean isFromBucket() {
      return this.dataManager.get(FROM_BUCKET);
   }

   public void setFromBucket(boolean p_203706_1_) {
      this.dataManager.set(FROM_BUCKET, p_203706_1_);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putBoolean("FromBucket", this.isFromBucket());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.setFromBucket(compound.getBoolean("FromBucket"));
   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(0, new PanicGoal(this, 1.25D));
      this.field_70714_bg.addTask(2, new AvoidEntityGoal<>(this, PlayerEntity.class, 8.0F, 1.6D, 1.4D, EntityPredicates.NOT_SPECTATING::test));
      this.field_70714_bg.addTask(4, new AbstractFishEntity.SwimGoal(this));
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new SwimmerPathNavigator(this, worldIn);
   }

   public void travel(Vec3d p_213352_1_) {
      if (this.isServerWorld() && this.isInWater()) {
         this.moveRelative(0.01F, p_213352_1_);
         this.move(MoverType.SELF, this.getMotion());
         this.setMotion(this.getMotion().scale(0.9D));
         if (this.getAttackTarget() == null) {
            this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.travel(p_213352_1_);
      }

   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (!this.isInWater() && this.onGround && this.collidedVertically) {
         this.setMotion(this.getMotion().add((double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F), (double)0.4F, (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.05F)));
         this.onGround = false;
         this.isAirBorne = true;
         this.playSound(this.getFlopSound(), this.getSoundVolume(), this.getSoundPitch());
      }

      super.livingTick();
   }

   protected boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (itemstack.getItem() == Items.WATER_BUCKET && this.isAlive()) {
         this.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
         itemstack.shrink(1);
         ItemStack itemstack1 = this.getFishBucket();
         this.setBucketData(itemstack1);
         if (!this.world.isRemote) {
            CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)player, itemstack1);
         }

         if (itemstack.isEmpty()) {
            player.setHeldItem(hand, itemstack1);
         } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
            player.dropItem(itemstack1, false);
         }

         this.remove();
         return true;
      } else {
         return super.processInteract(player, hand);
      }
   }

   /**
    * Add extra data to the bucket that just picked this fish up
    */
   protected void setBucketData(ItemStack bucket) {
      if (this.hasCustomName()) {
         bucket.setDisplayName(this.getCustomName());
      }

   }

   protected abstract ItemStack getFishBucket();

   protected boolean func_212800_dy() {
      return true;
   }

   protected abstract SoundEvent getFlopSound();

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_FISH_SWIM;
   }

   static class MoveHelperController extends MovementController {
      private final AbstractFishEntity field_203781_i;

      MoveHelperController(AbstractFishEntity fish) {
         super(fish);
         this.field_203781_i = fish;
      }

      public void tick() {
         if (this.field_203781_i.areEyesInFluid(FluidTags.WATER)) {
            this.field_203781_i.setMotion(this.field_203781_i.getMotion().add(0.0D, 0.005D, 0.0D));
         }

         if (this.field_188491_h == MovementController.Action.MOVE_TO && !this.field_203781_i.getNavigator().noPath()) {
            double d0 = this.posX - this.field_203781_i.posX;
            double d1 = this.posY - this.field_203781_i.posY;
            double d2 = this.posZ - this.field_203781_i.posZ;
            double d3 = (double)MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d1 = d1 / d3;
            float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.field_203781_i.rotationYaw = this.limitAngle(this.field_203781_i.rotationYaw, f, 90.0F);
            this.field_203781_i.renderYawOffset = this.field_203781_i.rotationYaw;
            float f1 = (float)(this.speed * this.field_203781_i.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
            this.field_203781_i.setAIMoveSpeed(MathHelper.func_219799_g(0.125F, this.field_203781_i.getAIMoveSpeed(), f1));
            this.field_203781_i.setMotion(this.field_203781_i.getMotion().add(0.0D, (double)this.field_203781_i.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
         } else {
            this.field_203781_i.setAIMoveSpeed(0.0F);
         }
      }
   }

   static class SwimGoal extends RandomSwimmingGoal {
      private final AbstractFishEntity field_203788_h;

      public SwimGoal(AbstractFishEntity fish) {
         super(fish, 1.0D, 40);
         this.field_203788_h = fish;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_203788_h.func_212800_dy() && super.shouldExecute();
      }
   }
}