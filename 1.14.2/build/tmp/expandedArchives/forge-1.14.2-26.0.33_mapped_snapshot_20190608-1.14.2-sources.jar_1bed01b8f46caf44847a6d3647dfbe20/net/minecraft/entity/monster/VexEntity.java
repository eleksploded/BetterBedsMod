package net.minecraft.entity.monster;

import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class VexEntity extends MonsterEntity {
   protected static final DataParameter<Byte> VEX_FLAGS = EntityDataManager.createKey(VexEntity.class, DataSerializers.field_187191_a);
   private MobEntity field_190665_b;
   @Nullable
   private BlockPos boundOrigin;
   private boolean limitedLifespan;
   private int limitedLifeTicks;

   public VexEntity(EntityType<? extends VexEntity> p_i50190_1_, World p_i50190_2_) {
      super(p_i50190_1_, p_i50190_2_);
      this.field_70765_h = new VexEntity.MoveHelperController(this);
      this.experienceValue = 3;
   }

   public void move(MoverType p_213315_1_, Vec3d p_213315_2_) {
      super.move(p_213315_1_, p_213315_2_);
      this.doBlockCollisions();
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      this.noClip = true;
      super.tick();
      this.noClip = false;
      this.setNoGravity(true);
      if (this.limitedLifespan && --this.limitedLifeTicks <= 0) {
         this.limitedLifeTicks = 20;
         this.attackEntityFrom(DamageSource.STARVE, 1.0F);
      }

   }

   protected void initEntityAI() {
      super.initEntityAI();
      this.field_70714_bg.addTask(0, new SwimGoal(this));
      this.field_70714_bg.addTask(4, new VexEntity.ChargeAttackGoal());
      this.field_70714_bg.addTask(8, new VexEntity.MoveRandomGoal());
      this.field_70714_bg.addTask(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
      this.field_70714_bg.addTask(10, new LookAtGoal(this, MobEntity.class, 8.0F));
      this.field_70715_bh.addTask(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).func_220794_a());
      this.field_70715_bh.addTask(2, new VexEntity.CopyOwnerTargetGoal(this));
      this.field_70715_bh.addTask(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(VEX_FLAGS, (byte)0);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("BoundX")) {
         this.boundOrigin = new BlockPos(compound.getInt("BoundX"), compound.getInt("BoundY"), compound.getInt("BoundZ"));
      }

      if (compound.contains("LifeTicks")) {
         this.setLimitedLife(compound.getInt("LifeTicks"));
      }

   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      if (this.boundOrigin != null) {
         p_213281_1_.putInt("BoundX", this.boundOrigin.getX());
         p_213281_1_.putInt("BoundY", this.boundOrigin.getY());
         p_213281_1_.putInt("BoundZ", this.boundOrigin.getZ());
      }

      if (this.limitedLifespan) {
         p_213281_1_.putInt("LifeTicks", this.limitedLifeTicks);
      }

   }

   public MobEntity getOwner() {
      return this.field_190665_b;
   }

   @Nullable
   public BlockPos getBoundOrigin() {
      return this.boundOrigin;
   }

   public void setBoundOrigin(@Nullable BlockPos boundOriginIn) {
      this.boundOrigin = boundOriginIn;
   }

   private boolean getVexFlag(int mask) {
      int i = this.dataManager.get(VEX_FLAGS);
      return (i & mask) != 0;
   }

   private void setVexFlag(int mask, boolean value) {
      int i = this.dataManager.get(VEX_FLAGS);
      if (value) {
         i = i | mask;
      } else {
         i = i & ~mask;
      }

      this.dataManager.set(VEX_FLAGS, (byte)(i & 255));
   }

   public boolean isCharging() {
      return this.getVexFlag(1);
   }

   public void setCharging(boolean charging) {
      this.setVexFlag(1, charging);
   }

   public void setOwner(MobEntity ownerIn) {
      this.field_190665_b = ownerIn;
   }

   public void setLimitedLife(int limitedLifeTicksIn) {
      this.limitedLifespan = true;
      this.limitedLifeTicks = limitedLifeTicksIn;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_VEX_AMBIENT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_VEX_DEATH;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_VEX_HURT;
   }

   @OnlyIn(Dist.CLIENT)
   public int getBrightnessForRender() {
      return 15728880;
   }

   /**
    * Gets how bright this entity is.
    */
   public float getBrightness() {
      return 1.0F;
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      this.setEquipmentBasedOnDifficulty(p_213386_2_);
      this.setEnchantmentBasedOnDifficulty(p_213386_2_);
      return super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
   }

   /**
    * Gives armor or weapon for entity based on given DifficultyInstance
    */
   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
      this.setItemStackToSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
      this.setDropChance(EquipmentSlotType.MAINHAND, 0.0F);
   }

   class ChargeAttackGoal extends Goal {
      public ChargeAttackGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         if (VexEntity.this.getAttackTarget() != null && !VexEntity.this.getMoveHelper().isUpdating() && VexEntity.this.rand.nextInt(7) == 0) {
            return VexEntity.this.getDistanceSq(VexEntity.this.getAttackTarget()) > 4.0D;
         } else {
            return false;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return VexEntity.this.getMoveHelper().isUpdating() && VexEntity.this.isCharging() && VexEntity.this.getAttackTarget() != null && VexEntity.this.getAttackTarget().isAlive();
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         LivingEntity livingentity = VexEntity.this.getAttackTarget();
         Vec3d vec3d = livingentity.getEyePosition(1.0F);
         VexEntity.this.field_70765_h.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
         VexEntity.this.setCharging(true);
         VexEntity.this.playSound(SoundEvents.ENTITY_VEX_CHARGE, 1.0F, 1.0F);
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         VexEntity.this.setCharging(false);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         LivingEntity livingentity = VexEntity.this.getAttackTarget();
         if (VexEntity.this.getBoundingBox().intersects(livingentity.getBoundingBox())) {
            VexEntity.this.attackEntityAsMob(livingentity);
            VexEntity.this.setCharging(false);
         } else {
            double d0 = VexEntity.this.getDistanceSq(livingentity);
            if (d0 < 9.0D) {
               Vec3d vec3d = livingentity.getEyePosition(1.0F);
               VexEntity.this.field_70765_h.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.0D);
            }
         }

      }
   }

   class CopyOwnerTargetGoal extends TargetGoal {
      private final EntityPredicate field_220803_b = (new EntityPredicate()).func_221014_c().func_221010_e();

      public CopyOwnerTargetGoal(CreatureEntity creature) {
         super(creature, false);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return VexEntity.this.field_190665_b != null && VexEntity.this.field_190665_b.getAttackTarget() != null && this.func_220777_a(VexEntity.this.field_190665_b.getAttackTarget(), this.field_220803_b);
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         VexEntity.this.setAttackTarget(VexEntity.this.field_190665_b.getAttackTarget());
         super.startExecuting();
      }
   }

   class MoveHelperController extends MovementController {
      public MoveHelperController(VexEntity vex) {
         super(vex);
      }

      public void tick() {
         if (this.field_188491_h == MovementController.Action.MOVE_TO) {
            Vec3d vec3d = new Vec3d(this.posX - VexEntity.this.posX, this.posY - VexEntity.this.posY, this.posZ - VexEntity.this.posZ);
            double d0 = vec3d.length();
            if (d0 < VexEntity.this.getBoundingBox().getAverageEdgeLength()) {
               this.field_188491_h = MovementController.Action.WAIT;
               VexEntity.this.setMotion(VexEntity.this.getMotion().scale(0.5D));
            } else {
               VexEntity.this.setMotion(VexEntity.this.getMotion().add(vec3d.scale(this.speed * 0.05D / d0)));
               if (VexEntity.this.getAttackTarget() == null) {
                  Vec3d vec3d1 = VexEntity.this.getMotion();
                  VexEntity.this.rotationYaw = -((float)MathHelper.atan2(vec3d1.x, vec3d1.z)) * (180F / (float)Math.PI);
                  VexEntity.this.renderYawOffset = VexEntity.this.rotationYaw;
               } else {
                  double d2 = VexEntity.this.getAttackTarget().posX - VexEntity.this.posX;
                  double d1 = VexEntity.this.getAttackTarget().posZ - VexEntity.this.posZ;
                  VexEntity.this.rotationYaw = -((float)MathHelper.atan2(d2, d1)) * (180F / (float)Math.PI);
                  VexEntity.this.renderYawOffset = VexEntity.this.rotationYaw;
               }
            }

         }
      }
   }

   class MoveRandomGoal extends Goal {
      public MoveRandomGoal() {
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return !VexEntity.this.getMoveHelper().isUpdating() && VexEntity.this.rand.nextInt(7) == 0;
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return false;
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         BlockPos blockpos = VexEntity.this.getBoundOrigin();
         if (blockpos == null) {
            blockpos = new BlockPos(VexEntity.this);
         }

         for(int i = 0; i < 3; ++i) {
            BlockPos blockpos1 = blockpos.add(VexEntity.this.rand.nextInt(15) - 7, VexEntity.this.rand.nextInt(11) - 5, VexEntity.this.rand.nextInt(15) - 7);
            if (VexEntity.this.world.isAirBlock(blockpos1)) {
               VexEntity.this.field_70765_h.setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 0.25D);
               if (VexEntity.this.getAttackTarget() == null) {
                  VexEntity.this.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
               }
               break;
            }
         }

      }
   }
}