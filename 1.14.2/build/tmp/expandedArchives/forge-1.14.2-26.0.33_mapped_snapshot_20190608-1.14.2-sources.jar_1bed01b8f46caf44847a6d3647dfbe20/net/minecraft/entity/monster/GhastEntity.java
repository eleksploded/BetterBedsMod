package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhastEntity extends FlyingEntity implements IMob {
   private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(GhastEntity.class, DataSerializers.field_187198_h);
   private int explosionStrength = 1;

   public GhastEntity(EntityType<? extends GhastEntity> p_i50206_1_, World p_i50206_2_) {
      super(p_i50206_1_, p_i50206_2_);
      this.experienceValue = 5;
      this.field_70765_h = new GhastEntity.MoveHelperController(this);
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(5, new GhastEntity.RandomFlyGoal(this));
      this.field_70714_bg.addTask(7, new GhastEntity.LookAroundGoal(this));
      this.field_70714_bg.addTask(7, new GhastEntity.FireballAttackGoal(this));
      this.field_70715_bh.addTask(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
         return Math.abs(p_213812_1_.posY - this.posY) <= 4.0D;
      }));
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isAttacking() {
      return this.dataManager.get(ATTACKING);
   }

   public void setAttacking(boolean attacking) {
      this.dataManager.set(ATTACKING, attacking);
   }

   public int getFireballStrength() {
      return this.explosionStrength;
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (!this.world.isRemote && this.world.getDifficulty() == Difficulty.PEACEFUL) {
         this.remove();
      }

   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else if (source.getImmediateSource() instanceof FireballEntity && source.getTrueSource() instanceof PlayerEntity) {
         super.attackEntityFrom(source, 1000.0F);
         return true;
      } else {
         return super.attackEntityFrom(source, amount);
      }
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(ATTACKING, false);
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
   }

   public SoundCategory getSoundCategory() {
      return SoundCategory.HOSTILE;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_GHAST_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_GHAST_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_GHAST_DEATH;
   }

   /**
    * Returns the volume for the sounds this mob makes.
    */
   protected float getSoundVolume() {
      return 10.0F;
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return this.rand.nextInt(20) == 0 && super.canSpawn(p_213380_1_, p_213380_2_) && p_213380_1_.getDifficulty() != Difficulty.PEACEFUL;
   }

   /**
    * Will return how many at most can spawn in a chunk at once.
    */
   public int getMaxSpawnedInChunk() {
      return 1;
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putInt("ExplosionPower", this.explosionStrength);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      if (compound.contains("ExplosionPower", 99)) {
         this.explosionStrength = compound.getInt("ExplosionPower");
      }

   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return 2.6F;
   }

   static class FireballAttackGoal extends Goal {
      private final GhastEntity field_179470_b;
      public int attackTimer;

      public FireballAttackGoal(GhastEntity ghast) {
         this.field_179470_b = ghast;
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return this.field_179470_b.getAttackTarget() != null;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.attackTimer = 0;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_179470_b.setAttacking(false);
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         LivingEntity livingentity = this.field_179470_b.getAttackTarget();
         double d0 = 64.0D;
         if (livingentity.getDistanceSq(this.field_179470_b) < 4096.0D && this.field_179470_b.canEntityBeSeen(livingentity)) {
            World world = this.field_179470_b.world;
            ++this.attackTimer;
            if (this.attackTimer == 10) {
               world.playEvent((PlayerEntity)null, 1015, new BlockPos(this.field_179470_b), 0);
            }

            if (this.attackTimer == 20) {
               double d1 = 4.0D;
               Vec3d vec3d = this.field_179470_b.getLook(1.0F);
               double d2 = livingentity.posX - (this.field_179470_b.posX + vec3d.x * 4.0D);
               double d3 = livingentity.getBoundingBox().minY + (double)(livingentity.getHeight() / 2.0F) - (0.5D + this.field_179470_b.posY + (double)(this.field_179470_b.getHeight() / 2.0F));
               double d4 = livingentity.posZ - (this.field_179470_b.posZ + vec3d.z * 4.0D);
               world.playEvent((PlayerEntity)null, 1016, new BlockPos(this.field_179470_b), 0);
               FireballEntity fireballentity = new FireballEntity(world, this.field_179470_b, d2, d3, d4);
               fireballentity.explosionPower = this.field_179470_b.getFireballStrength();
               fireballentity.posX = this.field_179470_b.posX + vec3d.x * 4.0D;
               fireballentity.posY = this.field_179470_b.posY + (double)(this.field_179470_b.getHeight() / 2.0F) + 0.5D;
               fireballentity.posZ = this.field_179470_b.posZ + vec3d.z * 4.0D;
               world.func_217376_c(fireballentity);
               this.attackTimer = -40;
            }
         } else if (this.attackTimer > 0) {
            --this.attackTimer;
         }

         this.field_179470_b.setAttacking(this.attackTimer > 10);
      }
   }

   static class LookAroundGoal extends Goal {
      private final GhastEntity field_179472_a;

      public LookAroundGoal(GhastEntity ghast) {
         this.field_179472_a = ghast;
         this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return true;
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         if (this.field_179472_a.getAttackTarget() == null) {
            Vec3d vec3d = this.field_179472_a.getMotion();
            this.field_179472_a.rotationYaw = -((float)MathHelper.atan2(vec3d.x, vec3d.z)) * (180F / (float)Math.PI);
            this.field_179472_a.renderYawOffset = this.field_179472_a.rotationYaw;
         } else {
            LivingEntity livingentity = this.field_179472_a.getAttackTarget();
            double d0 = 64.0D;
            if (livingentity.getDistanceSq(this.field_179472_a) < 4096.0D) {
               double d1 = livingentity.posX - this.field_179472_a.posX;
               double d2 = livingentity.posZ - this.field_179472_a.posZ;
               this.field_179472_a.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
               this.field_179472_a.renderYawOffset = this.field_179472_a.rotationYaw;
            }
         }

      }
   }

   static class MoveHelperController extends MovementController {
      private final GhastEntity field_179927_g;
      private int courseChangeCooldown;

      public MoveHelperController(GhastEntity ghast) {
         super(ghast);
         this.field_179927_g = ghast;
      }

      public void tick() {
         if (this.field_188491_h == MovementController.Action.MOVE_TO) {
            if (this.courseChangeCooldown-- <= 0) {
               this.courseChangeCooldown += this.field_179927_g.getRNG().nextInt(5) + 2;
               Vec3d vec3d = new Vec3d(this.posX - this.field_179927_g.posX, this.posY - this.field_179927_g.posY, this.posZ - this.field_179927_g.posZ);
               double d0 = vec3d.length();
               vec3d = vec3d.normalize();
               if (this.func_220673_a(vec3d, MathHelper.ceil(d0))) {
                  this.field_179927_g.setMotion(this.field_179927_g.getMotion().add(vec3d.scale(0.1D)));
               } else {
                  this.field_188491_h = MovementController.Action.WAIT;
               }
            }

         }
      }

      private boolean func_220673_a(Vec3d p_220673_1_, int p_220673_2_) {
         AxisAlignedBB axisalignedbb = this.field_179927_g.getBoundingBox();

         for(int i = 1; i < p_220673_2_; ++i) {
            axisalignedbb = axisalignedbb.offset(p_220673_1_);
            if (!this.field_179927_g.world.isCollisionBoxesEmpty(this.field_179927_g, axisalignedbb)) {
               return false;
            }
         }

         return true;
      }
   }

   static class RandomFlyGoal extends Goal {
      private final GhastEntity field_179454_a;

      public RandomFlyGoal(GhastEntity ghast) {
         this.field_179454_a = ghast;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         MovementController movementcontroller = this.field_179454_a.getMoveHelper();
         if (!movementcontroller.isUpdating()) {
            return true;
         } else {
            double d0 = movementcontroller.getX() - this.field_179454_a.posX;
            double d1 = movementcontroller.getY() - this.field_179454_a.posY;
            double d2 = movementcontroller.getZ() - this.field_179454_a.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
         }
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return false;
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         Random random = this.field_179454_a.getRNG();
         double d0 = this.field_179454_a.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d1 = this.field_179454_a.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d2 = this.field_179454_a.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.field_179454_a.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
      }
   }
}