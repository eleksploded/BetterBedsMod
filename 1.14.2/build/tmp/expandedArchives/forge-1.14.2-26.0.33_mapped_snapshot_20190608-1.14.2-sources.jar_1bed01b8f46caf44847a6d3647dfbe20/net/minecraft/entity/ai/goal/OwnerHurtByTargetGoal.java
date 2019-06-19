package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class OwnerHurtByTargetGoal extends TargetGoal {
   private final TameableEntity field_75316_a;
   private LivingEntity field_75315_b;
   private int timestamp;

   public OwnerHurtByTargetGoal(TameableEntity theDefendingTameableIn) {
      super(theDefendingTameableIn, false);
      this.field_75316_a = theDefendingTameableIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_75316_a.isTamed() && !this.field_75316_a.isSitting()) {
         LivingEntity livingentity = this.field_75316_a.getOwner();
         if (livingentity == null) {
            return false;
         } else {
            this.field_75315_b = livingentity.getRevengeTarget();
            int i = livingentity.getRevengeTimer();
            return i != this.timestamp && this.func_220777_a(this.field_75315_b, EntityPredicate.DEFAULT) && this.field_75316_a.shouldAttackEntity(this.field_75315_b, livingentity);
         }
      } else {
         return false;
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75299_d.setAttackTarget(this.field_75315_b);
      LivingEntity livingentity = this.field_75316_a.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getRevengeTimer();
      }

      super.startExecuting();
   }
}