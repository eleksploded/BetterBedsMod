package net.minecraft.entity.ai.goal;

import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class LandOnOwnersShoulderGoal extends Goal {
   private final ShoulderRidingEntity field_192382_a;
   private ServerPlayerEntity field_192383_b;
   private boolean isSittingOnShoulder;

   public LandOnOwnersShoulderGoal(ShoulderRidingEntity entityIn) {
      this.field_192382_a = entityIn;
   }

   /**
    * Returns whether the EntityAIBase should begin execution.
    */
   public boolean shouldExecute() {
      ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)this.field_192382_a.getOwner();
      boolean flag = serverplayerentity != null && !serverplayerentity.isSpectator() && !serverplayerentity.playerAbilities.isFlying && !serverplayerentity.isInWater();
      return !this.field_192382_a.isSitting() && flag && this.field_192382_a.canSitOnShoulder();
   }

   public boolean isPreemptible() {
      return !this.isSittingOnShoulder;
   }

   /**
    * Execute a one shot task or start executing a continuous task
    */
   public void startExecuting() {
      this.field_192383_b = (ServerPlayerEntity)this.field_192382_a.getOwner();
      this.isSittingOnShoulder = false;
   }

   /**
    * Keep ticking a continuous task that has already been started
    */
   public void tick() {
      if (!this.isSittingOnShoulder && !this.field_192382_a.isSitting() && !this.field_192382_a.getLeashed()) {
         if (this.field_192382_a.getBoundingBox().intersects(this.field_192383_b.getBoundingBox())) {
            this.isSittingOnShoulder = this.field_192382_a.func_213439_d(this.field_192383_b);
         }

      }
   }
}