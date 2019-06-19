package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;

public class OwnerHurtTargetGoal extends TargetGoal {
   private final TameableEntity field_75314_a;
   private LivingEntity field_75313_b;
   private int timestamp;

   public OwnerHurtTargetGoal(TameableEntity theEntityTameableIn) {
      super(theEntityTameableIn, false);
      this.field_75314_a = theEntityTameableIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.TARGET));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (this.field_75314_a.isTamed() && !this.field_75314_a.isSitting()) {
         LivingEntity livingentity = this.field_75314_a.getOwner();
         if (livingentity == null) {
            return false;
         } else {
            this.field_75313_b = livingentity.getLastAttackedEntity();
            int i = livingentity.getLastAttackedEntityTime();
            return i != this.timestamp && this.func_220777_a(this.field_75313_b, EntityPredicate.DEFAULT) && this.field_75314_a.shouldAttackEntity(this.field_75313_b, livingentity);
         }
      } else {
         return false;
      }
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_75299_d.setAttackTarget(this.field_75313_b);
      LivingEntity livingentity = this.field_75314_a.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getLastAttackedEntityTime();
      }

      super.startExecuting();
   }
}