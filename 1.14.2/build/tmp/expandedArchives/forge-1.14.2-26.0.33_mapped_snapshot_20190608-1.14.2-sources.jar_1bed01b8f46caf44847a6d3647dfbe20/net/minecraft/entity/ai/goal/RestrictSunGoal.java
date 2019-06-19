package net.minecraft.entity.ai.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.pathfinding.GroundPathNavigator;

public class RestrictSunGoal extends Goal {
   private final CreatureEntity field_75273_a;

   public RestrictSunGoal(CreatureEntity creature) {
      this.field_75273_a = creature;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      return this.field_75273_a.world.isDaytime() && this.field_75273_a.getItemStackFromSlot(EquipmentSlotType.HEAD).isEmpty() && this.field_75273_a.getNavigator() instanceof GroundPathNavigator;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      ((GroundPathNavigator)this.field_75273_a.getNavigator()).setAvoidSun(true);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      ((GroundPathNavigator)this.field_75273_a.getNavigator()).setAvoidSun(false);
   }
}