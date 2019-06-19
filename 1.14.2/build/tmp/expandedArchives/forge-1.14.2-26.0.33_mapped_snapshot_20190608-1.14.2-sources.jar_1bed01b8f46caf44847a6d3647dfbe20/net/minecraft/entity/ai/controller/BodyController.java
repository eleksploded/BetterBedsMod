package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;

public class BodyController {
   private final MobEntity field_75668_a;
   private int rotationTickCounter;
   private float prevRenderYawHead;

   public BodyController(MobEntity p_i50334_1_) {
      this.field_75668_a = p_i50334_1_;
   }

   /**
    * Update the Head and Body rendenring angles
    */
   public void updateRenderAngles() {
      if (this.func_220662_f()) {
         this.field_75668_a.renderYawOffset = this.field_75668_a.rotationYaw;
         this.func_220664_c();
         this.prevRenderYawHead = this.field_75668_a.rotationYawHead;
         this.rotationTickCounter = 0;
      } else {
         if (this.func_220661_e()) {
            if (Math.abs(this.field_75668_a.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
               this.rotationTickCounter = 0;
               this.prevRenderYawHead = this.field_75668_a.rotationYawHead;
               this.func_220663_b();
            } else {
               ++this.rotationTickCounter;
               if (this.rotationTickCounter > 10) {
                  this.func_220665_d();
               }
            }
         }

      }
   }

   private void func_220663_b() {
      this.field_75668_a.renderYawOffset = MathHelper.func_219800_b(this.field_75668_a.renderYawOffset, this.field_75668_a.rotationYawHead, (float)this.field_75668_a.getHorizontalFaceSpeed());
   }

   private void func_220664_c() {
      this.field_75668_a.rotationYawHead = MathHelper.func_219800_b(this.field_75668_a.rotationYawHead, this.field_75668_a.renderYawOffset, (float)this.field_75668_a.getHorizontalFaceSpeed());
   }

   private void func_220665_d() {
      int i = this.rotationTickCounter - 10;
      float f = MathHelper.clamp((float)i / 10.0F, 0.0F, 1.0F);
      float f1 = (float)this.field_75668_a.getHorizontalFaceSpeed() * (1.0F - f);
      this.field_75668_a.renderYawOffset = MathHelper.func_219800_b(this.field_75668_a.renderYawOffset, this.field_75668_a.rotationYawHead, f1);
   }

   private boolean func_220661_e() {
      return this.field_75668_a.getPassengers().isEmpty() || !(this.field_75668_a.getPassengers().get(0) instanceof MobEntity);
   }

   private boolean func_220662_f() {
      double d0 = this.field_75668_a.posX - this.field_75668_a.prevPosX;
      double d1 = this.field_75668_a.posZ - this.field_75668_a.prevPosZ;
      return d0 * d0 + d1 * d1 > (double)2.5000003E-7F;
   }
}