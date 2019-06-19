package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.BowItem;
import net.minecraft.item.Items;

public class RangedBowAttackGoal<T extends MonsterEntity & IRangedAttackMob> extends Goal {
   private final T field_188499_a;
   private final double moveSpeedAmp;
   private int attackCooldown;
   private final float maxAttackDistance;
   private int attackTime = -1;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;

   public RangedBowAttackGoal(T mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn) {
      this.field_188499_a = mob;
      this.moveSpeedAmp = moveSpeedAmpIn;
      this.attackCooldown = attackCooldownIn;
      this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public void setAttackCooldown(int p_189428_1_) {
      this.attackCooldown = p_189428_1_;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      return this.field_188499_a.getAttackTarget() == null ? false : this.isBowInMainhand();
   }

   protected boolean isBowInMainhand() {
      net.minecraft.item.ItemStack main = this.field_188499_a.getHeldItemMainhand();
      net.minecraft.item.ItemStack off  = this.field_188499_a.getHeldItemOffhand();
      return main.getItem() instanceof BowItem || off.getItem() instanceof BowItem;
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return (this.shouldExecute() || !this.field_188499_a.getNavigator().noPath()) && this.isBowInMainhand();
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      super.startExecuting();
      this.field_188499_a.func_213395_q(true);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      super.resetTask();
      this.field_188499_a.func_213395_q(false);
      this.seeTime = 0;
      this.attackTime = -1;
      this.field_188499_a.resetActiveHand();
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      LivingEntity livingentity = this.field_188499_a.getAttackTarget();
      if (livingentity != null) {
         double d0 = this.field_188499_a.getDistanceSq(livingentity.posX, livingentity.getBoundingBox().minY, livingentity.posZ);
         boolean flag = this.field_188499_a.getEntitySenses().canSee(livingentity);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
            ++this.seeTime;
         } else {
            --this.seeTime;
         }

         if (!(d0 > (double)this.maxAttackDistance) && this.seeTime >= 20) {
            this.field_188499_a.getNavigator().clearPath();
            ++this.strafingTime;
         } else {
            this.field_188499_a.getNavigator().tryMoveToEntityLiving(livingentity, this.moveSpeedAmp);
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if ((double)this.field_188499_a.getRNG().nextFloat() < 0.3D) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double)this.field_188499_a.getRNG().nextFloat() < 0.3D) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (d0 > (double)(this.maxAttackDistance * 0.75F)) {
               this.strafingBackwards = false;
            } else if (d0 < (double)(this.maxAttackDistance * 0.25F)) {
               this.strafingBackwards = true;
            }

            this.field_188499_a.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.field_188499_a.faceEntity(livingentity, 30.0F, 30.0F);
         } else {
            this.field_188499_a.getLookHelper().setLookPositionWithEntity(livingentity, 30.0F, 30.0F);
         }

         if (this.field_188499_a.isHandActive()) {
            if (!flag && this.seeTime < -60) {
               this.field_188499_a.resetActiveHand();
            } else if (flag) {
               int i = this.field_188499_a.getItemInUseMaxCount();
               if (i >= 20) {
                  this.field_188499_a.resetActiveHand();
                  ((IRangedAttackMob)this.field_188499_a).attackEntityWithRangedAttack(livingentity, BowItem.getArrowVelocity(i));
                  this.attackTime = this.attackCooldown;
               }
            }
         } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
            this.field_188499_a.setActiveHand(ProjectileHelper.func_221274_a(this.field_188499_a, Items.BOW));
         }

      }
   }
}