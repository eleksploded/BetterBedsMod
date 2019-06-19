package net.minecraft.entity.ai.controller;

import net.minecraft.entity.MobEntity;
import net.minecraft.util.math.MathHelper;

public class DolphinLookController extends LookController {
   private final int field_205139_h;

   public DolphinLookController(MobEntity p_i48942_1_, int p_i48942_2_) {
      super(p_i48942_1_);
      this.field_205139_h = p_i48942_2_;
   }

   /**
    * Updates look
    */
   public void tick() {
      if (this.isLooking) {
         this.isLooking = false;
         this.field_75659_a.rotationYawHead = this.func_220675_a(this.field_75659_a.rotationYawHead, this.func_220678_h() + 20.0F, this.deltaLookYaw);
         this.field_75659_a.rotationPitch = this.func_220675_a(this.field_75659_a.rotationPitch, this.func_220677_g() + 10.0F, this.deltaLookPitch);
      } else {
         if (this.field_75659_a.getNavigator().noPath()) {
            this.field_75659_a.rotationPitch = this.func_220675_a(this.field_75659_a.rotationPitch, 0.0F, 5.0F);
         }

         this.field_75659_a.rotationYawHead = this.func_220675_a(this.field_75659_a.rotationYawHead, this.field_75659_a.renderYawOffset, this.deltaLookYaw);
      }

      float f = MathHelper.wrapDegrees(this.field_75659_a.rotationYawHead - this.field_75659_a.renderYawOffset);
      if (f < (float)(-this.field_205139_h)) {
         this.field_75659_a.renderYawOffset -= 4.0F;
      } else if (f > (float)this.field_205139_h) {
         this.field_75659_a.renderYawOffset += 4.0F;
      }

   }
}