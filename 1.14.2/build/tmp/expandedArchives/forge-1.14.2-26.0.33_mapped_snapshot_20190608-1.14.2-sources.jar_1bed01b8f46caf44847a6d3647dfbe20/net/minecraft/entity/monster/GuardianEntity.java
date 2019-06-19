package net.minecraft.entity.monster;

import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.LookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GuardianEntity extends MonsterEntity {
   private static final DataParameter<Boolean> MOVING = EntityDataManager.createKey(GuardianEntity.class, DataSerializers.field_187198_h);
   private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(GuardianEntity.class, DataSerializers.field_187192_b);
   protected float clientSideTailAnimation;
   protected float clientSideTailAnimationO;
   protected float clientSideTailAnimationSpeed;
   protected float clientSideSpikesAnimation;
   protected float clientSideSpikesAnimationO;
   private LivingEntity field_175478_bn;
   private int clientSideAttackTime;
   private boolean clientSideTouchedGround;
   protected RandomWalkingGoal field_175481_bq;

   public GuardianEntity(EntityType<? extends GuardianEntity> p_i48554_1_, World p_i48554_2_) {
      super(p_i48554_1_, p_i48554_2_);
      this.experienceValue = 10;
      this.field_70765_h = new GuardianEntity.MoveHelperController(this);
      this.clientSideTailAnimation = this.rand.nextFloat();
      this.clientSideTailAnimationO = this.clientSideTailAnimation;
   }

   protected void initEntityAI() {
      MoveTowardsRestrictionGoal movetowardsrestrictiongoal = new MoveTowardsRestrictionGoal(this, 1.0D);
      this.field_175481_bq = new RandomWalkingGoal(this, 1.0D, 80);
      this.field_70714_bg.addTask(4, new GuardianEntity.AttackGoal(this));
      this.field_70714_bg.addTask(5, movetowardsrestrictiongoal);
      this.field_70714_bg.addTask(7, this.field_175481_bq);
      this.field_70714_bg.addTask(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.field_70714_bg.addTask(8, new LookAtGoal(this, GuardianEntity.class, 12.0F, 0.01F));
      this.field_70714_bg.addTask(9, new LookRandomlyGoal(this));
      this.field_175481_bq.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      movetowardsrestrictiongoal.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      this.field_70715_bh.addTask(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new GuardianEntity.TargetPredicate(this)));
   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
      this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new SwimmerPathNavigator(this, worldIn);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(MOVING, false);
      this.dataManager.register(TARGET_ENTITY, 0);
   }

   public boolean canBreatheUnderwater() {
      return true;
   }

   public CreatureAttribute getCreatureAttribute() {
      return CreatureAttribute.WATER;
   }

   public boolean isMoving() {
      return this.dataManager.get(MOVING);
   }

   private void setMoving(boolean moving) {
      this.dataManager.set(MOVING, moving);
   }

   public int getAttackDuration() {
      return 80;
   }

   private void setTargetedEntity(int entityId) {
      this.dataManager.set(TARGET_ENTITY, entityId);
   }

   public boolean hasTargetedEntity() {
      return this.dataManager.get(TARGET_ENTITY) != 0;
   }

   @Nullable
   public LivingEntity getTargetedEntity() {
      if (!this.hasTargetedEntity()) {
         return null;
      } else if (this.world.isRemote) {
         if (this.field_175478_bn != null) {
            return this.field_175478_bn;
         } else {
            Entity entity = this.world.getEntityByID(this.dataManager.get(TARGET_ENTITY));
            if (entity instanceof LivingEntity) {
               this.field_175478_bn = (LivingEntity)entity;
               return this.field_175478_bn;
            } else {
               return null;
            }
         }
      } else {
         return this.getAttackTarget();
      }
   }

   public void notifyDataManagerChange(DataParameter<?> key) {
      super.notifyDataManagerChange(key);
      if (TARGET_ENTITY.equals(key)) {
         this.clientSideAttackTime = 0;
         this.field_175478_bn = null;
      }

   }

   /**
    * Get number of ticks, at least during which the living entity will be silent.
    */
   public int getTalkInterval() {
      return 160;
   }

   protected SoundEvent getAmbientSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
   }

   protected SoundEvent getDeathSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND;
   }

   /**
    * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
    * prevent them from trampling crops
    */
   protected boolean canTriggerWalking() {
      return false;
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return p_213348_2_.height * 0.5F;
   }

   public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
      return worldIn.getFluidState(pos).isTagged(FluidTags.WATER) ? 10.0F + worldIn.getBrightness(pos) - 0.5F : super.getBlockPathWeight(pos, worldIn);
   }

   /**
    * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
    * use this to react to sunlight and start to burn.
    */
   public void livingTick() {
      if (this.isAlive()) {
         if (this.world.isRemote) {
            this.clientSideTailAnimationO = this.clientSideTailAnimation;
            if (!this.isInWater()) {
               this.clientSideTailAnimationSpeed = 2.0F;
               Vec3d vec3d = this.getMotion();
               if (vec3d.y > 0.0D && this.clientSideTouchedGround && !this.isSilent()) {
                  this.world.playSound(this.posX, this.posY, this.posZ, this.getFlopSound(), this.getSoundCategory(), 1.0F, 1.0F, false);
               }

               this.clientSideTouchedGround = vec3d.y < 0.0D && this.world.func_217400_a((new BlockPos(this)).down(), this);
            } else if (this.isMoving()) {
               if (this.clientSideTailAnimationSpeed < 0.5F) {
                  this.clientSideTailAnimationSpeed = 4.0F;
               } else {
                  this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
               }
            } else {
               this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
            }

            this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
            this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
            if (!this.isInWaterOrBubbleColumn()) {
               this.clientSideSpikesAnimation = this.rand.nextFloat();
            } else if (this.isMoving()) {
               this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
            } else {
               this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
            }

            if (this.isMoving() && this.isInWater()) {
               Vec3d vec3d1 = this.getLook(0.0F);

               for(int i = 0; i < 2; ++i) {
                  this.world.addParticle(ParticleTypes.BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth() - vec3d1.x * 1.5D, this.posY + this.rand.nextDouble() * (double)this.getHeight() - vec3d1.y * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.getWidth() - vec3d1.z * 1.5D, 0.0D, 0.0D, 0.0D);
               }
            }

            if (this.hasTargetedEntity()) {
               if (this.clientSideAttackTime < this.getAttackDuration()) {
                  ++this.clientSideAttackTime;
               }

               LivingEntity livingentity = this.getTargetedEntity();
               if (livingentity != null) {
                  this.getLookHelper().setLookPositionWithEntity(livingentity, 90.0F, 90.0F);
                  this.getLookHelper().tick();
                  double d5 = (double)this.getAttackAnimationScale(0.0F);
                  double d0 = livingentity.posX - this.posX;
                  double d1 = livingentity.posY + (double)(livingentity.getHeight() * 0.5F) - (this.posY + (double)this.getEyeHeight());
                  double d2 = livingentity.posZ - this.posZ;
                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                  d0 = d0 / d3;
                  d1 = d1 / d3;
                  d2 = d2 / d3;
                  double d4 = this.rand.nextDouble();

                  while(d4 < d3) {
                     d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
                     this.world.addParticle(ParticleTypes.BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + (double)this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D);
                  }
               }
            }
         }

         if (this.isInWaterOrBubbleColumn()) {
            this.setAir(300);
         } else if (this.onGround) {
            this.setMotion(this.getMotion().add((double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5D, (double)((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F)));
            this.rotationYaw = this.rand.nextFloat() * 360.0F;
            this.onGround = false;
            this.isAirBorne = true;
         }

         if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
         }
      }

      super.livingTick();
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.ENTITY_GUARDIAN_FLOP;
   }

   @OnlyIn(Dist.CLIENT)
   public float getTailAnimation(float p_175471_1_) {
      return MathHelper.func_219799_g(p_175471_1_, this.clientSideTailAnimationO, this.clientSideTailAnimation);
   }

   @OnlyIn(Dist.CLIENT)
   public float getSpikesAnimation(float p_175469_1_) {
      return MathHelper.func_219799_g(p_175469_1_, this.clientSideSpikesAnimationO, this.clientSideSpikesAnimation);
   }

   public float getAttackAnimationScale(float p_175477_1_) {
      return ((float)this.clientSideAttackTime + p_175477_1_) / (float)this.getAttackDuration();
   }

   /**
    * Checks to make sure the light is not too bright where the mob is spawning
    */
   protected boolean isValidLightLevel() {
      return true;
   }

   public boolean isNotColliding(IWorldReader worldIn) {
      return worldIn.func_217346_i(this);
   }

   public boolean canSpawn(IWorld p_213380_1_, SpawnReason p_213380_2_) {
      return (this.rand.nextInt(20) == 0 || !p_213380_1_.canBlockSeeSky(new BlockPos(this))) && super.canSpawn(p_213380_1_, p_213380_2_);
   }

   protected boolean canSpawn(IWorld p_213393_1_, SpawnReason p_213393_2_, BlockPos p_213393_3_) {
      return p_213393_1_.getFluidState(p_213393_3_).isTagged(FluidTags.WATER);
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (!this.isMoving() && !source.isMagicDamage() && source.getImmediateSource() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)source.getImmediateSource();
         if (!source.isExplosion()) {
            livingentity.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
         }
      }

      if (this.field_175481_bq != null) {
         this.field_175481_bq.makeUpdate();
      }

      return super.attackEntityFrom(source, amount);
   }

   /**
    * The speed it takes to move the entityliving's rotationPitch through the faceEntity method. This is only currently
    * use in wolves.
    */
   public int getVerticalFaceSpeed() {
      return 180;
   }

   public void travel(Vec3d p_213352_1_) {
      if (this.isServerWorld() && this.isInWater()) {
         this.moveRelative(0.1F, p_213352_1_);
         this.move(MoverType.SELF, this.getMotion());
         this.setMotion(this.getMotion().scale(0.9D));
         if (!this.isMoving() && this.getAttackTarget() == null) {
            this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.travel(p_213352_1_);
      }

   }

   static class AttackGoal extends Goal {
      private final GuardianEntity field_179456_a;
      private int tickCounter;
      private final boolean isElder;

      public AttackGoal(GuardianEntity guardian) {
         this.field_179456_a = guardian;
         this.isElder = guardian instanceof ElderGuardianEntity;
         this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         LivingEntity livingentity = this.field_179456_a.getAttackTarget();
         return livingentity != null && livingentity.isAlive();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         return super.shouldContinueExecuting() && (this.isElder || this.field_179456_a.getDistanceSq(this.field_179456_a.getAttackTarget()) > 9.0D);
      }

      /**
       * Execute a one shot task or start executing a continuous task
       */
      public void startExecuting() {
         this.tickCounter = -10;
         this.field_179456_a.getNavigator().clearPath();
         this.field_179456_a.getLookHelper().setLookPositionWithEntity(this.field_179456_a.getAttackTarget(), 90.0F, 90.0F);
         this.field_179456_a.isAirBorne = true;
      }

      /**
       * Reset the task's internal state. Called when this task is interrupted by another one
       */
      public void resetTask() {
         this.field_179456_a.setTargetedEntity(0);
         this.field_179456_a.setAttackTarget((LivingEntity)null);
         this.field_179456_a.field_175481_bq.makeUpdate();
      }

      /**
       * Keep ticking a continuous task that has already been started
       */
      public void tick() {
         LivingEntity livingentity = this.field_179456_a.getAttackTarget();
         this.field_179456_a.getNavigator().clearPath();
         this.field_179456_a.getLookHelper().setLookPositionWithEntity(livingentity, 90.0F, 90.0F);
         if (!this.field_179456_a.canEntityBeSeen(livingentity)) {
            this.field_179456_a.setAttackTarget((LivingEntity)null);
         } else {
            ++this.tickCounter;
            if (this.tickCounter == 0) {
               this.field_179456_a.setTargetedEntity(this.field_179456_a.getAttackTarget().getEntityId());
               this.field_179456_a.world.setEntityState(this.field_179456_a, (byte)21);
            } else if (this.tickCounter >= this.field_179456_a.getAttackDuration()) {
               float f = 1.0F;
               if (this.field_179456_a.world.getDifficulty() == Difficulty.HARD) {
                  f += 2.0F;
               }

               if (this.isElder) {
                  f += 2.0F;
               }

               livingentity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.field_179456_a, this.field_179456_a), f);
               livingentity.attackEntityFrom(DamageSource.causeMobDamage(this.field_179456_a), (float)this.field_179456_a.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue());
               this.field_179456_a.setAttackTarget((LivingEntity)null);
            }

            super.tick();
         }
      }
   }

   static class MoveHelperController extends MovementController {
      private final GuardianEntity field_179930_g;

      public MoveHelperController(GuardianEntity guardian) {
         super(guardian);
         this.field_179930_g = guardian;
      }

      public void tick() {
         if (this.field_188491_h == MovementController.Action.MOVE_TO && !this.field_179930_g.getNavigator().noPath()) {
            Vec3d vec3d = new Vec3d(this.posX - this.field_179930_g.posX, this.posY - this.field_179930_g.posY, this.posZ - this.field_179930_g.posZ);
            double d0 = vec3d.length();
            double d1 = vec3d.x / d0;
            double d2 = vec3d.y / d0;
            double d3 = vec3d.z / d0;
            float f = (float)(MathHelper.atan2(vec3d.z, vec3d.x) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.field_179930_g.rotationYaw = this.limitAngle(this.field_179930_g.rotationYaw, f, 90.0F);
            this.field_179930_g.renderYawOffset = this.field_179930_g.rotationYaw;
            float f1 = (float)(this.speed * this.field_179930_g.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
            float f2 = MathHelper.func_219799_g(0.125F, this.field_179930_g.getAIMoveSpeed(), f1);
            this.field_179930_g.setAIMoveSpeed(f2);
            double d4 = Math.sin((double)(this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.5D) * 0.05D;
            double d5 = Math.cos((double)(this.field_179930_g.rotationYaw * ((float)Math.PI / 180F)));
            double d6 = Math.sin((double)(this.field_179930_g.rotationYaw * ((float)Math.PI / 180F)));
            double d7 = Math.sin((double)(this.field_179930_g.ticksExisted + this.field_179930_g.getEntityId()) * 0.75D) * 0.05D;
            this.field_179930_g.setMotion(this.field_179930_g.getMotion().add(d4 * d5, d7 * (d6 + d5) * 0.25D + (double)f2 * d2 * 0.1D, d4 * d6));
            LookController lookcontroller = this.field_179930_g.getLookHelper();
            double d8 = this.field_179930_g.posX + d1 * 2.0D;
            double d9 = (double)this.field_179930_g.getEyeHeight() + this.field_179930_g.posY + d2 / d0;
            double d10 = this.field_179930_g.posZ + d3 * 2.0D;
            double d11 = lookcontroller.getLookPosX();
            double d12 = lookcontroller.getLookPosY();
            double d13 = lookcontroller.getLookPosZ();
            if (!lookcontroller.getIsLooking()) {
               d11 = d8;
               d12 = d9;
               d13 = d10;
            }

            this.field_179930_g.getLookHelper().setLookPosition(MathHelper.func_219803_d(0.125D, d11, d8), MathHelper.func_219803_d(0.125D, d12, d9), MathHelper.func_219803_d(0.125D, d13, d10), 10.0F, 40.0F);
            this.field_179930_g.setMoving(true);
         } else {
            this.field_179930_g.setAIMoveSpeed(0.0F);
            this.field_179930_g.setMoving(false);
         }
      }
   }

   static class TargetPredicate implements Predicate<LivingEntity> {
      private final GuardianEntity field_179916_a;

      public TargetPredicate(GuardianEntity guardian) {
         this.field_179916_a = guardian;
      }

      public boolean test(@Nullable LivingEntity p_test_1_) {
         return (p_test_1_ instanceof PlayerEntity || p_test_1_ instanceof SquidEntity) && p_test_1_.getDistanceSq(this.field_179916_a) > 9.0D;
      }
   }
}