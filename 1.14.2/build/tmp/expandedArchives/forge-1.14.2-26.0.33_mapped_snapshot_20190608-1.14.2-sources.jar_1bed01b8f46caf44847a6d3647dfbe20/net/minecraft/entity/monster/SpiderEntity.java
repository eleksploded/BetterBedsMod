package net.minecraft.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class SpiderEntity extends MonsterEntity {
   private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(SpiderEntity.class, DataSerializers.field_187191_a);

   public SpiderEntity(EntityType<? extends SpiderEntity> type, World p_i48550_2_) {
      super(type, p_i48550_2_);
   }

   protected void initEntityAI() {
      this.field_70714_bg.addTask(1, new SwimGoal(this));
      this.field_70714_bg.addTask(3, new LeapAtTargetGoal(this, 0.4F));
      this.field_70714_bg.addTask(4, new SpiderEntity.AttackGoal(this));
      this.field_70714_bg.addTask(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
      this.field_70714_bg.addTask(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
      this.field_70714_bg.addTask(6, new LookRandomlyGoal(this));
      this.field_70715_bh.addTask(1, new HurtByTargetGoal(this));
      this.field_70715_bh.addTask(2, new SpiderEntity.TargetGoal<>(this, PlayerEntity.class));
      this.field_70715_bh.addTask(3, new SpiderEntity.TargetGoal<>(this, IronGolemEntity.class));
   }

   /**
    * Returns the Y offset from the entity's position for any entity riding this one.
    */
   public double getMountedYOffset() {
      return (double)(this.getHeight() * 0.5F);
   }

   /**
    * Returns new PathNavigateGround instance
    */
   protected PathNavigator createNavigator(World worldIn) {
      return new ClimberPathNavigator(this, worldIn);
   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(CLIMBING, (byte)0);
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      super.tick();
      if (!this.world.isRemote) {
         this.setBesideClimbableBlock(this.collidedHorizontally);
      }

   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SPIDER_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.ENTITY_SPIDER_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SPIDER_DEATH;
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
   }

   /**
    * Returns true if this entity should move as if it were on a ladder (either because it's actually on a ladder, or
    * for AI reasons)
    */
   public boolean isOnLadder() {
      return this.isBesideClimbableBlock();
   }

   public void setMotionMultiplier(BlockState p_213295_1_, Vec3d p_213295_2_) {
      if (p_213295_1_.getBlock() != Blocks.COBWEB) {
         super.setMotionMultiplier(p_213295_1_, p_213295_2_);
      }

   }

   public CreatureAttribute getCreatureAttribute() {
      return CreatureAttribute.ARTHROPOD;
   }

   public boolean isPotionApplicable(EffectInstance potioneffectIn) {
      if (potioneffectIn.getPotion() == Effects.field_76436_u) {
         net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
         return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
      }
      return super.isPotionApplicable(potioneffectIn);
   }

   /**
    * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
    * setBesideClimableBlock.
    */
   public boolean isBesideClimbableBlock() {
      return (this.dataManager.get(CLIMBING) & 1) != 0;
   }

   /**
    * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
    * false.
    */
   public void setBesideClimbableBlock(boolean climbing) {
      byte b0 = this.dataManager.get(CLIMBING);
      if (climbing) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.dataManager.set(CLIMBING, b0);
   }

   @Nullable
   public ILivingEntityData onInitialSpawn(IWorld p_213386_1_, DifficultyInstance p_213386_2_, SpawnReason p_213386_3_, @Nullable ILivingEntityData p_213386_4_, @Nullable CompoundNBT p_213386_5_) {
      p_213386_4_ = super.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, p_213386_4_, p_213386_5_);
      if (p_213386_1_.getRandom().nextInt(100) == 0) {
         SkeletonEntity skeletonentity = EntityType.SKELETON.create(this.world);
         skeletonentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
         skeletonentity.onInitialSpawn(p_213386_1_, p_213386_2_, p_213386_3_, (ILivingEntityData)null, (CompoundNBT)null);
         p_213386_1_.func_217376_c(skeletonentity);
         skeletonentity.startRiding(this);
      }

      if (p_213386_4_ == null) {
         p_213386_4_ = new SpiderEntity.GroupData();
         if (p_213386_1_.getDifficulty() == Difficulty.HARD && p_213386_1_.getRandom().nextFloat() < 0.1F * p_213386_2_.getClampedAdditionalDifficulty()) {
            ((SpiderEntity.GroupData)p_213386_4_).setRandomEffect(p_213386_1_.getRandom());
         }
      }

      if (p_213386_4_ instanceof SpiderEntity.GroupData) {
         Effect effect = ((SpiderEntity.GroupData)p_213386_4_).field_188478_a;
         if (effect != null) {
            this.addPotionEffect(new EffectInstance(effect, Integer.MAX_VALUE));
         }
      }

      return p_213386_4_;
   }

   protected float getStandingEyeHeight(Pose p_213348_1_, EntitySize p_213348_2_) {
      return 0.65F;
   }

   static class AttackGoal extends MeleeAttackGoal {
      public AttackGoal(SpiderEntity spider) {
         super(spider, 1.0D, true);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         return super.shouldExecute() && !this.field_75441_b.isBeingRidden();
      }

      /**
       * Returns whether an in-progress EntityAIBase should continue executing
       */
      public boolean shouldContinueExecuting() {
         float f = this.field_75441_b.getBrightness();
         if (f >= 0.5F && this.field_75441_b.getRNG().nextInt(100) == 0) {
            this.field_75441_b.setAttackTarget((LivingEntity)null);
            return false;
         } else {
            return super.shouldContinueExecuting();
         }
      }

      protected double getAttackReachSqr(LivingEntity attackTarget) {
         return (double)(4.0F + attackTarget.getWidth());
      }
   }

   public static class GroupData implements ILivingEntityData {
      public Effect field_188478_a;

      public void setRandomEffect(Random rand) {
         int i = rand.nextInt(5);
         if (i <= 1) {
            this.field_188478_a = Effects.field_76424_c;
         } else if (i <= 2) {
            this.field_188478_a = Effects.field_76420_g;
         } else if (i <= 3) {
            this.field_188478_a = Effects.field_76428_l;
         } else if (i <= 4) {
            this.field_188478_a = Effects.field_76441_p;
         }

      }
   }

   static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
      public TargetGoal(SpiderEntity spider, Class<T> classTarget) {
         super(spider, classTarget, true);
      }

      /**
       * Returns whether the EntityAIBase should begin execution.
       */
      public boolean shouldExecute() {
         float f = this.field_75299_d.getBrightness();
         return f >= 0.5F ? false : super.shouldExecute();
      }
   }
}