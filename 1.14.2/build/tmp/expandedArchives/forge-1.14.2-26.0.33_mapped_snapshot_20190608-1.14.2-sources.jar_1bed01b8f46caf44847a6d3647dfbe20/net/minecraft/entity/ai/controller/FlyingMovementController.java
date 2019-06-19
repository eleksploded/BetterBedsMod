package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;

public class FlyingMovementController extends MovementController {
   public FlyingMovementController(MobEntity p_i47418_1_) {
      super(p_i47418_1_);
   }

   public void tick() {
      if (this.field_188491_h == MovementController.Action.MOVE_TO) {
         this.field_188491_h = MovementController.Action.WAIT;
         this.field_75648_a.setNoGravity(true);
         double d0 = this.posX - this.field_75648_a.posX;
         double d1 = this.posY - this.field_75648_a.posY;
         double d2 = this.posZ - this.field_75648_a.posZ;
         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
         if (d3 < (double)2.5000003E-7F) {
            this.field_75648_a.setMoveVertical(0.0F);
            this.field_75648_a.setMoveForward(0.0F);
            return;
         }

         float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
         this.field_75648_a.rotationYaw = this.limitAngle(this.field_75648_a.rotationYaw, f, 10.0F);
         float f1;
         if (this.field_75648_a.onGround) {
            f1 = (float)(this.speed * this.field_75648_a.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
         } else {
            f1 = (float)(this.speed * this.field_75648_a.getAttribute(SharedMonsterAttributes.FLYING_SPEED).getValue());
         }

         this.field_75648_a.setAIMoveSpeed(f1);
         double d4 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
         float f2 = (float)(-(MathHelper.atan2(d1, d4) * (double)(180F / (float)Math.PI)));
         this.field_75648_a.rotationPitch = this.limitAngle(this.field_75648_a.rotationPitch, f2, 10.0F);
         this.field_75648_a.setMoveVertical(d1 > 0.0D ? f1 : -f1);
      } else {
         this.field_75648_a.setNoGravity(false);
         this.field_75648_a.setMoveVertical(0.0F);
         this.field_75648_a.setMoveForward(0.0F);
      }

   }
}