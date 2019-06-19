package net.minecraft.entity.passive;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.entity.ai.goal.BreatheAirGoal;
import net.minecraft.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.FollowBoatGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathType;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DolphinEntity extends WaterMobEntity {
   private static final DataParameter<BlockPos> TREASURE_POS = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.field_187200_j);
   private static final DataParameter<Boolean> GOT_FISH = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> MOISTNESS = EntityDataManager.createKey(DolphinEntity.class, DataSerializers.field_187192_b);
   private static final EntityPredicate field_213810_bA = (new EntityPredicate()).func_221013_a(10.0D).func_221011_b().func_221008_a();
   public static final Predicate<ItemEntity> ITEM_SELECTOR = (p_205023_0_) -> {
      return !p_205023_0_.cannotPickup() && p_205023_0_.isAlive() && p_205023_0_.isInWater();
   };

   public DolphinEntity(EntityType<? extends DolphinEntity> p_i50275_1_, World p_i50275_2_) {
      super(p_i50275_1_, p_i50275_2_);
      this.field_70765_h = new DolphinEntity.MoveHelperController(this);
      this.field_70749_g = new DolphinLookController(this, 10);
      this.setCanPickUpLoot(true);
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      this.setAir(this.getMaxAir());
      this.rotationPitch = 0.0F;
      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   public boolean canBreatheUnderwater() {
      return false;
   }

   protected void updateAir(int p_209207_1_) {
   }

   public void setTreasurePos(BlockPos p_208012_1_) {
      this.dataManager.set(TREASURE_POS, p_208012_1_);
   }

   public BlockPos getTreasurePos() {
      return this.dataManager.get(TREASURE_POS);
   }

   public boolean hasGotFish() {
      return this.dataManager.get(GOT_FISH);
   }

   public void setGotFish(boolean p_208008_1_) {
      this.dataManager.set(GOT_FISH, p_208008_1_);
   }

   public int getMoistness() {
      return this.dataManager.get(MOISTNESS);
   }

   public void setMoistness(int p_211137_1_) {
      this.dataManager.set(MOISTNESS, p_211137_1_);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(TREASURE_POS, BlockPos.ZERO);
      this.dataManager.register(GOT_FISH, false);
      this.dataManager.register(MOISTNESS, 2400);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("TreasurePosX", this.getTreasurePos().getX());
      p_213281_1_.putInt("TreasurePosY", this.getTreasurePos().getY());
      p_213281_1_.putInt("TreasurePosZ", this.getTreasurePos().getZ());
      p_213281_1_.putBoolean("GotFish", this.hasGotFish());
      p_213281_1_.putInt("Moistness", this.getMoistness());
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      int i = compound.getInt("TreasurePosX");
      int j = compound.getInt("TreasurePosY");
      int k = compound.getInt("TreasurePosZ");
      this.setTreasurePos(new BlockPos(i, j, k));
      super.readAdditional(compound);
      this.setGotFish(compound.getBoolean("GotFish"));
      this.setMoistness(compound.getInt("Moistness"));
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(0, new BreatheAirGoal(this));
      this.field_70714_bg.addTask(0, new FindWaterGoal(this));
      this.field_70714_bg.addTask(1, new DolphinEntity.SwimToTreasureGoal(this));
      this.field_70714_bg.addTask(2, new DolphinEntity.SwimWithPlayerGoal(this, 4.0D));
      this.field_70714_bg.addTask(4, new RandomSwimmingGoal(this, 1.0D, 10));
      this.field_70714_bg.addTask(4, new LookRandomlyGoal(this));
      this.field_70714_bg.addTask(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
      this.field_70714_bg.addTask(5, new DolphinJumpGoal(this, 10));
      this.field_70714_bg.addTask(6, new MeleeAttackGoal(this, (double)1.2F, true));
      this.field_70714_bg.addTask(8, new DolphinEntity.PlayWithItemsGoal());
      this.field_70714_bg.addTask(8, new FollowBoatGoal(this));
      this.field_70714_bg.addTask(9, new AvoidEntityGoal<>(this, GuardianEntity.class, 8.0F, 1.0D, 1.0D));
      this.field_70715_bh.addTask(1, (new HurtByTargetGoal(this, GuardianEntity.class)).func_220794_a());
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)1.2F);
      this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new SwimmerPathNavigator(this, worldIn);
   }

   public boolean attackEntityAsMob(Entity entityIn) {
      boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue()));
      if (flag) {
         this.applyEnchantments(this, entityIn);
         this.playSound(SoundEvents.ENTITY_DOLPHIN_ATTACK, 1.0F, 1.0F);
      }

      return flag;
   }

   public int getMaxAir() {
      return 4800;
   }

   protected int determineNextAir(int currentAir) {
      return this.getMaxAir();
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return 0.3F;
   }

   /**
    * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
    * use in wolves.
    */
   public int getVerticalFaceSpeed() {
      return 1;
   }

   public int getHorizontalFaceSpeed() {
      return 1;
   }

   protected boolean canBeRidden(Entity entityIn) {
      return true;
   }

   public boolean func_213365_e(ItemStack p_213365_1_) {
      EquipmentSlotType equipmentslottype = MobEntity.getSlotForItemStack(p_213365_1_);
      if (!this.getItemStackFromSlot(equipmentslottype).isEmpty()) {
         return false;
      } else {
         return equipmentslottype == EquipmentSlotType.MAINHAND && super.func_213365_e(p_213365_1_);
      }
   }

   /**
    * Tests if this entity should pickup a weapon or an armor. Entity drops current weapon or armor if the new one is
    * better.
    */
   protected void updateEquipmentIfNeeded(ItemEntity itemEntity) {
      if (this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty()) {
         ItemStack itemstack = itemEntity.getItem();
         if (this.canEquipItem(itemstack)) {
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, itemstack);
            this.inventoryHandsDropChances[EquipmentSlotType.MAINHAND.getIndex()] = 2.0F;
            this.onItemPickup(itemEntity, itemstack.getCount());
            itemEntity.remove();
         }
      }

   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (!this.isAIDisabled()) {
         if (this.isInWaterRainOrBubbleColumn()) {
            this.setMoistness(2400);
         } else {
            this.setMoistness(this.getMoistness() - 1);
            if (this.getMoistness() <= 0) {
               this.attackEntityFrom(DamageSource.DRYOUT, 1.0F);
            }

            if (this.onGround) {
               this.setMotion(this.getMotion().add((double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.2F)));
               this.rotationYaw = this.rand.nextFloat() * 360.0F;
               this.onGround = false;
               this.isAirBorne = true;
            }
         }

         if (this.world.isRemote && this.isInWater() && this.getMotion().lengthSquared() > 0.03D) {
            Vec3d vec3d = this.getLook(0.0F);
            float f = MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * 0.3F;
            float f1 = MathHelper.sin(this.rotationYaw * ((float)Math.PI / 180F)) * 0.3F;
            float f2 = 1.2F - this.rand.nextFloat() * 0.7F;

            for(int i = 0; i < 2; ++i) {
               this.world.addParticle(ParticleTypes.DOLPHIN, this.posX - vec3d.x * (double)f2 + (double)f, this.posY - vec3d.y, this.posZ - vec3d.z * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
               this.world.addParticle(ParticleTypes.DOLPHIN, this.posX - vec3d.x * (double)f2 - (double)f, this.posY - vec3d.y, this.posZ - vec3d.z * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
            }
         }

      }
   }

   /**
    * Handler for {@link World#setEntityState}
    */
   @OnlyIn(Dist.CLIENT)
   public void handleStatusUpdate(byte id) {
      if (id == 38) {
         this.func_208401_a(ParticleTypes.HAPPY_VILLAGER);
      } else {
         super.handleStatusUpdate(id);
      }

   }

   @OnlyIn(Dist.CLIENT)
   private void func_208401_a(IParticleData p_208401_1_) {
      for(int i = 0; i < 7; ++i) {
         double d0 = this.rand.nextGaussian() * 0.01D;
         double d1 = this.rand.nextGaussian() * 0.01D;
         double d2 = this.rand.nextGaussian() * 0.01D;
         this.world.addParticle(p_208401_1_, this.posX + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), this.posY + (double)0.2F + (double)(this.rand.nextFloat() * this.getHeight()), this.posZ + (double)(this.rand.nextFloat() * this.getWidth() * 2.0F) - (double)this.getWidth(), d0, d1, d2);
      }

   }

   protected boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (!itemstack.isEmpty() && itemstack.getItem().isIn(ItemTags.FISHES)) {
         if (!this.world.isRemote) {
            this.playSound(SoundEvents.ENTITY_DOLPHIN_EAT, 1.0F, 1.0F);
         }

         this.setGotFish(true);
         if (!player.playerAbilities.isCreativeMode) {
            itemstack.shrink(1);
         }

         return true;
      } else {
         return super.processInteract(player, hand);
      }
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return this.posY > 45.0D && this.posY < (double)p_213380_1_.getSeaLevel() && p_213380_1_.getBiome(new BlockPos(this)) != Biomes.OCEAN || p_213380_1_.getBiome(new BlockPos(this)) != Biomes.DEEP_OCEAN && super.canSpawn(p_213380_1_, p_213380_2_);
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_DOLPHIN_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_DOLPHIN_DEATH;
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return this.isInWater() ? SoundEvents.ENTITY_DOLPHIN_AMBIENT_WATER : SoundEvents.ENTITY_DOLPHIN_AMBIENT;
   }

   protected SoundEvent getSplashSound() {
      return SoundEvents.ENTITY_DOLPHIN_SPLASH;
   }

   protected SoundEvent getSwimSound() {
      return SoundEvents.ENTITY_DOLPHIN_SWIM;
   }

   protected boolean closeToTarget() {
      BlockPos blockpos = this.getNavigator().getTargetPos();
      return blockpos != null ? blockpos.func_218137_a(this.getPositionVec(), 12.0D) : false;
   }

   public void travel(Vec3d p_213352_1_) {
      if (this.isServerWorld() && this.isInWater()) {
         this.moveRelative(this.getAIMoveSpeed(), p_213352_1_);
         this.move(MoverType.SELF, this.getMotion());
         this.setMotion(this.getMotion().scale(0.9D));
         if (this.getAttackTarget() == null) {
            this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.travel(p_213352_1_);
      }

   }

   public boolean canBeLeashedTo(PlayerEntity player) {
      return true;
   }

   static class MoveHelperController extends MovementController {
      private final DolphinEntity field_205138_i;

      public MoveHelperController(DolphinEntity p_i48945_1_) {
         super(p_i48945_1_);
         this.field_205138_i = p_i48945_1_;
      }

      public void tick() {
         if (this.field_205138_i.isInWater()) {
            this.field_205138_i.setMotion(this.field_205138_i.getMotion().add(0.0D, 0.005D, 0.0D));
         }

         if (this.field_188491_h == MovementController.Action.MOVE_TO && !this.field_205138_i.getNavigator().noPath()) {
            double d0 = this.posX - this.field_205138_i.posX;
            double d1 = this.posY - this.field_205138_i.posY;
            double d2 = this.posZ - this.field_205138_i.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < (double)2.5000003E-7F) {
               this.field_75648_a.setMoveForward(0.0F);
            } else {
               float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
               this.field_205138_i.rotationYaw = this.limitAngle(this.field_205138_i.rotationYaw, f, 10.0F);
               this.field_205138_i.renderYawOffset = this.field_205138_i.rotationYaw;
               this.field_205138_i.rotationYawHead = this.field_205138_i.rotationYaw;
               float f1 = (float)(this.speed * this.field_205138_i.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
               if (this.field_205138_i.isInWater()) {
                  this.field_205138_i.setAIMoveSpeed(f1 * 0.02F);
                  float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                  f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                  this.field_205138_i.rotationPitch = this.limitAngle(this.field_205138_i.rotationPitch, f2, 5.0F);
                  float f3 = MathHelper.cos(this.field_205138_i.rotationPitch * ((float)Math.PI / 180F));
                  float f4 = MathHelper.sin(this.field_205138_i.rotationPitch * ((float)Math.PI / 180F));
                  this.field_205138_i.moveForward = f3 * f1;
                  this.field_205138_i.moveVertical = -f4 * f1;
               } else {
                  this.field_205138_i.setAIMoveSpeed(f1 * 0.1F);
               }

            }
         } else {
            this.field_205138_i.setAIMoveSpeed(0.0F);
            this.field_205138_i.setMoveStrafing(0.0F);
            this.field_205138_i.setMoveVertical(0.0F);
            this.field_205138_i.setMoveForward(0.0F);
         }
      }
   }

   class PlayWithItemsGoal extends Goal {
      private int field_205154_b;

      private PlayWithItemsGoal() {
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (this.field_205154_b > DolphinEntity.this.ticksExisted) {
            return false;
         } else {
            List<ItemEntity> list = DolphinEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, DolphinEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), DolphinEntity.ITEM_SELECTOR);
            return !list.isEmpty() || !DolphinEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND).isEmpty();
         }
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         List<ItemEntity> list = DolphinEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, DolphinEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), DolphinEntity.ITEM_SELECTOR);
         if (!list.isEmpty()) {
            DolphinEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
            DolphinEntity.this.playSound(SoundEvents.ENTITY_DOLPHIN_PLAY, 1.0F, 1.0F);
         }

         this.field_205154_b = 0;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         ItemStack itemstack = DolphinEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (!itemstack.isEmpty()) {
            this.func_220810_a(itemstack);
            DolphinEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
            this.field_205154_b = DolphinEntity.this.ticksExisted + DolphinEntity.this.rand.nextInt(100);
         }

      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         List<ItemEntity> list = DolphinEntity.this.world.getEntitiesWithinAABB(ItemEntity.class, DolphinEntity.this.getBoundingBox().grow(8.0D, 8.0D, 8.0D), DolphinEntity.ITEM_SELECTOR);
         ItemStack itemstack = DolphinEntity.this.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
         if (!itemstack.isEmpty()) {
            this.func_220810_a(itemstack);
            DolphinEntity.this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
         } else if (!list.isEmpty()) {
            DolphinEntity.this.getNavigator().tryMoveToEntityLiving(list.get(0), (double)1.2F);
         }

      }

      private void func_220810_a(ItemStack p_220810_1_) {
         if (!p_220810_1_.isEmpty()) {
            double d0 = DolphinEntity.this.posY - (double)0.3F + (double)DolphinEntity.this.getEyeHeight();
            ItemEntity itementity = new ItemEntity(DolphinEntity.this.world, DolphinEntity.this.posX, d0, DolphinEntity.this.posZ, p_220810_1_);
            itementity.setPickupDelay(40);
            itementity.setThrowerId(DolphinEntity.this.getUniqueID());
            float f = 0.3F;
            float f1 = DolphinEntity.this.rand.nextFloat() * ((float)Math.PI * 2F);
            float f2 = 0.02F * DolphinEntity.this.rand.nextFloat();
            itementity.setMotion((double)(0.3F * -MathHelper.sin(DolphinEntity.this.rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(DolphinEntity.this.rotationPitch * ((float)Math.PI / 180F)) + MathHelper.cos(f1) * f2), (double)(0.3F * MathHelper.sin(DolphinEntity.this.rotationPitch * ((float)Math.PI / 180F)) * 1.5F), (double)(0.3F * MathHelper.cos(DolphinEntity.this.rotationYaw * ((float)Math.PI / 180F)) * MathHelper.cos(DolphinEntity.this.rotationPitch * ((float)Math.PI / 180F)) + MathHelper.sin(f1) * f2));
            DolphinEntity.this.world.func_217376_c(itementity);
         }
      }
   }

   static class SwimToTreasureGoal extends Goal {
      private final DolphinEntity field_208057_a;
      private boolean field_208058_b;

      SwimToTreasureGoal(DolphinEntity p_i49344_1_) {
         this.field_208057_a = p_i49344_1_;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean isPreemptible() {
         return false;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_208057_a.hasGotFish() && this.field_208057_a.getAir() >= 100;
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         BlockPos blockpos = this.field_208057_a.getTreasurePos();
         return !(new BlockPos((double)blockpos.getX(), this.field_208057_a.posY, (double)blockpos.getZ())).func_218137_a(this.field_208057_a.getPositionVec(), 4.0D) && !this.field_208058_b && this.field_208057_a.getAir() >= 100;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_208058_b = false;
         this.field_208057_a.getNavigator().clearPath();
         World world = this.field_208057_a.world;
         BlockPos blockpos = new BlockPos(this.field_208057_a);
         String s = (double)world.rand.nextFloat() >= 0.5D ? "Ocean_Ruin" : "Shipwreck";
         BlockPos blockpos1 = world.findNearestStructure(s, blockpos, 50, false);
         if (blockpos1 == null) {
            BlockPos blockpos2 = world.findNearestStructure(s.equals("Ocean_Ruin") ? "Shipwreck" : "Ocean_Ruin", blockpos, 50, false);
            if (blockpos2 == null) {
               this.field_208058_b = true;
               return;
            }

            this.field_208057_a.setTreasurePos(blockpos2);
         } else {
            this.field_208057_a.setTreasurePos(blockpos1);
         }

         world.setEntityState(this.field_208057_a, (byte)38);
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         BlockPos blockpos = this.field_208057_a.getTreasurePos();
         if ((new BlockPos((double)blockpos.getX(), this.field_208057_a.posY, (double)blockpos.getZ())).func_218137_a(this.field_208057_a.getPositionVec(), 4.0D) || this.field_208058_b) {
            this.field_208057_a.setGotFish(false);
         }

      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         BlockPos blockpos = this.field_208057_a.getTreasurePos();
         World world = this.field_208057_a.world;
         if (this.field_208057_a.closeToTarget() || this.field_208057_a.getNavigator().noPath()) {
            Vec3d vec3d = RandomPositionGenerator.findRandomTargetTowardsScaled(this.field_208057_a, 16, 1, new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()), (double)((float)Math.PI / 8F));
            if (vec3d == null) {
               vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.field_208057_a, 8, 4, new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()));
            }

            if (vec3d != null) {
               BlockPos blockpos1 = new BlockPos(vec3d);
               if (!world.getFluidState(blockpos1).isTagged(FluidTags.WATER) || !world.getBlockState(blockpos1).allowsMovement(world, blockpos1, PathType.WATER)) {
                  vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.field_208057_a, 8, 5, new Vec3d((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ()));
               }
            }

            if (vec3d == null) {
               this.field_208058_b = true;
               return;
            }

            this.field_208057_a.getLookHelper().setLookPosition(vec3d.x, vec3d.y, vec3d.z, (float)(this.field_208057_a.getHorizontalFaceSpeed() + 20), (float)this.field_208057_a.getVerticalFaceSpeed());
            this.field_208057_a.getNavigator().tryMoveToXYZ(vec3d.x, vec3d.y, vec3d.z, 1.3D);
            if (world.rand.nextInt(80) == 0) {
               world.setEntityState(this.field_208057_a, (byte)38);
            }
         }

      }
   }

   static class SwimWithPlayerGoal extends Goal {
      private final DolphinEntity field_206834_a;
      private final double speed;
      private PlayerEntity field_206836_c;

      SwimWithPlayerGoal(DolphinEntity p_i48994_1_, double p_i48994_2_) {
         this.field_206834_a = p_i48994_1_;
         this.speed = p_i48994_2_;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         this.field_206836_c = this.field_206834_a.world.func_217370_a(DolphinEntity.field_213810_bA, this.field_206834_a);
         return this.field_206836_c == null ? false : this.field_206836_c.isSwimming();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return this.field_206836_c != null && this.field_206836_c.isSwimming() && this.field_206834_a.getDistanceSq(this.field_206836_c) < 256.0D;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.field_206836_c.addPotionEffect(new EffectInstance(Effects.field_206827_D, 100));
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_206836_c = null;
         this.field_206834_a.getNavigator().clearPath();
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         this.field_206834_a.getLookHelper().setLookPositionWithEntity(this.field_206836_c, (float)(this.field_206834_a.getHorizontalFaceSpeed() + 20), (float)this.field_206834_a.getVerticalFaceSpeed());
         if (this.field_206834_a.getDistanceSq(this.field_206836_c) < 6.25D) {
            this.field_206834_a.getNavigator().clearPath();
         } else {
            this.field_206834_a.getNavigator().tryMoveToEntityLiving(this.field_206836_c, this.speed);
         }

         if (this.field_206836_c.isSwimming() && this.field_206836_c.world.rand.nextInt(6) == 0) {
            this.field_206836_c.addPotionEffect(new EffectInstance(Effects.field_206827_D, 100));
         }

      }
   }
}