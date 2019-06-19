package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;

public class ShowVillagerFlowerGoal extends Goal {
   private static final EntityPredicate field_220738_a = (new EntityPredicate()).func_221013_a(6.0D).func_221011_b().func_221008_a();
   private final IronGolemEntity field_75397_a;
   private VillagerEntity field_75395_b;
   private int lookTime;

   public ShowVillagerFlowerGoal(IronGolemEntity ironGolemIn) {
      this.field_75397_a = ironGolemIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      if (!this.field_75397_a.world.isDaytime()) {
         return false;
      } else if (this.field_75397_a.getRNG().nextInt(8000) != 0) {
         return false;
      } else {
         this.field_75395_b = this.field_75397_a.world.func_217360_a(VillagerEntity.class, field_220738_a, this.field_75397_a, this.field_75397_a.posX, this.field_75397_a.posY, this.field_75397_a.posZ, this.field_75397_a.getBoundingBox().grow(6.0D, 2.0D, 6.0D));
         return this.field_75395_b != null;
      }
   }

   /**
    * Returns whether an in-progress EntityAIBase should continue executing
    */
   public boolean shouldContinueExecuting() {
      return this.lookTime > 0;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.lookTime = 400;
      this.field_75397_a.setHoldingRose(true);
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      this.field_75397_a.setHoldingRose(false);
      this.field_75395_b = null;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      this.field_75397_a.getLookHelper().setLookPositionWithEntity(this.field_75395_b, 30.0F, 30.0F);
      --this.lookTime;
   }
}