package net.minecraft.entity.ai.goal;

import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class LookAtCustomerGoal extends LookAtGoal {
   private final AbstractVillagerEntity field_75335_b;

   public LookAtCustomerGoal(AbstractVillagerEntity p_i50326_1_) {
      super(p_i50326_1_, PlayerEntity.class, 8.0F);
      this.field_75335_b = p_i50326_1_;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_75335_b.func_213716_dX()) {
         this.closestEntity = this.field_75335_b.getCustomer();
         return true;
      } else {
         return false;
      }
   }
}