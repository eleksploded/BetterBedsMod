package net.minecraft.entity.ai.goal;

import net.minecraft.entity.monster.ZombieEntity;

public class ZombieAttackGoal extends MeleeAttackGoal {
   private final ZombieEntity field_188494_h;
   private int raiseArmTicks;

   public ZombieAttackGoal(ZombieEntity zombieIn, double speedIn, boolean longMemoryIn) {
      super(zombieIn, speedIn, longMemoryIn);
      this.field_188494_h = zombieIn;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      super.startExecuting();
      this.raiseArmTicks = 0;
   }

   /**
    * Reset the task's internal state. Called when this task is interrupted by another one
    */
   public void resetTask() {
      super.resetTask();
      this.field_188494_h.func_213395_q(false);
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      super.tick();
      ++this.raiseArmTicks;
      if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
         this.field_188494_h.func_213395_q(true);
      } else {
         this.field_188494_h.func_213395_q(false);
      }

   }
}