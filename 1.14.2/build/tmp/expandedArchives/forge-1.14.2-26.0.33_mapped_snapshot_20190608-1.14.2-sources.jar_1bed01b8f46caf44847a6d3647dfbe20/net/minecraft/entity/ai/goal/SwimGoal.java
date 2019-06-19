package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.MobEntity;

public class SwimGoal extends Goal {
   private final MobEntity field_75373_a;

   public SwimGoal(MobEntity entityIn) {
      this.field_75373_a = entityIn;
      this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP));
      entityIn.getNavigator().setCanSwim(true);
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      double d0 = (double)this.field_75373_a.getEyeHeight() < 0.4D ? 0.2D : 0.4D;
      return this.field_75373_a.isInWater() && this.field_75373_a.getSubmergedHeight() > d0 || this.field_75373_a.isInLava();
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (this.field_75373_a.getRNG().nextFloat() < 0.8F) {
         this.field_75373_a.getJumpHelper().setJumping();
      }

   }
}