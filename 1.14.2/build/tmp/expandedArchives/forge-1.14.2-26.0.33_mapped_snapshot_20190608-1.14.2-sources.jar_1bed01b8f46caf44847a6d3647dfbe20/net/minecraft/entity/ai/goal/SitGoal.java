package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class SitGoal extends Goal {
   private final TameableEntity field_75272_a;
   private boolean isSitting;

   public SitGoal(TameableEntity entityIn) {
      this.field_75272_a = entityIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.isSitting;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (!this.field_75272_a.isTamed()) {
         return false;
      } else if (this.field_75272_a.isInWaterOrBubbleColumn()) {
         return false;
      } else if (!this.field_75272_a.onGround) {
         return false;
      } else {
         LivingEntity livingentity = this.field_75272_a.getOwner();
         if (livingentity == null) {
            return true;
         } else {
            return this.field_75272_a.getDistanceSq(livingentity) < 144.0D && livingentity.getRevengeTarget() != null ? false : this.isSitting;
         }
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75272_a.getNavigator().clearPath();
      this.field_75272_a.setSitting(true);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75272_a.setSitting(false);
   }

   /**
    * Sets the sitting flag.
    */
   public void setSitting(boolean sitting) {
      this.isSitting = sitting;
   }
}