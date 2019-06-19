package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TradeWithPlayerGoal extends Goal {
   private final AbstractVillagerEntity field_75276_a;

   public TradeWithPlayerGoal(AbstractVillagerEntity p_i50320_1_) {
      this.field_75276_a = p_i50320_1_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (!this.field_75276_a.isAlive()) {
         return false;
      } else if (this.field_75276_a.isInWater()) {
         return false;
      } else if (!this.field_75276_a.onGround) {
         return false;
      } else if (this.field_75276_a.velocityChanged) {
         return false;
      } else {
         PlayerEntity playerentity = this.field_75276_a.getCustomer();
         if (playerentity == null) {
            return false;
         } else if (this.field_75276_a.getDistanceSq(playerentity) > 16.0D) {
            return false;
         } else {
            return playerentity.openContainer != null;
         }
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75276_a.getNavigator().clearPath();
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75276_a.setCustomer((PlayerEntity)null);
   }
}