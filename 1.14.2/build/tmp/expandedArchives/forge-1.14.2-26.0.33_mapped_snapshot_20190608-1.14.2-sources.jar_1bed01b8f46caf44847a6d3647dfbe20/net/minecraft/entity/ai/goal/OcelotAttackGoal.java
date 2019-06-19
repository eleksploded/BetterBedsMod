package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.IBlockReader;

public class OcelotAttackGoal extends Goal {
   private final IBlockReader world;
   private final MobEntity field_75409_b;
   private LivingEntity field_75410_c;
   private int attackCountdown;

   public OcelotAttackGoal(MobEntity theEntityIn) {
      this.field_75409_b = theEntityIn;
      this.world = theEntityIn.world;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      LivingEntity livingentity = this.field_75409_b.getAttackTarget();
      if (livingentity == null) {
         return false;
      } else {
         this.field_75410_c = livingentity;
         return true;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      if (!this.field_75410_c.isAlive()) {
         return false;
      } else if (this.field_75409_b.getDistanceSq(this.field_75410_c) > 225.0D) {
         return false;
      } else {
         return !this.field_75409_b.getNavigator().noPath() || this.shouldExecute();
      }
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75410_c = null;
      this.field_75409_b.getNavigator().clearPath();
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75409_b.getLookHelper().setLookPositionWithEntity(this.field_75410_c, 30.0F, 30.0F);
      double d0 = (double)(this.field_75409_b.getWidth() * 2.0F * this.field_75409_b.getWidth() * 2.0F);
      double d1 = this.field_75409_b.getDistanceSq(this.field_75410_c.posX, this.field_75410_c.getBoundingBox().minY, this.field_75410_c.posZ);
      double d2 = 0.8D;
      if (d1 > d0 && d1 < 16.0D) {
         d2 = 1.33D;
      } else if (d1 < 225.0D) {
         d2 = 0.6D;
      }

      this.field_75409_b.getNavigator().tryMoveToEntityLiving(this.field_75410_c, d2);
      this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
      if (!(d1 > d0)) {
         if (this.attackCountdown <= 0) {
            this.attackCountdown = 20;
            this.field_75409_b.attackEntityAsMob(this.field_75410_c);
         }
      }
   }
}