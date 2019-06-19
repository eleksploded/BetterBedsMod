package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ElytraModel<T extends LivingEntity> extends EntityModel<T> {
   private final RendererModel field_187060_a;
   private final RendererModel field_187061_b = new RendererModel(this, 22, 0);

   public ElytraModel() {
      this.field_187061_b.addBox(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
      this.field_187060_a = new RendererModel(this, 22, 0);
      this.field_187060_a.mirror = true;
      this.field_187060_a.addBox(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      GlStateManager.disableRescaleNormal();
      GlStateManager.disableCull();
      if (entityIn.isChild()) {
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 1.5F, -0.1F);
         this.field_187061_b.render(scale);
         this.field_187060_a.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.field_187061_b.render(scale);
         this.field_187060_a.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      float f = 0.2617994F;
      float f1 = -0.2617994F;
      float f2 = 0.0F;
      float f3 = 0.0F;
      if (p_212844_1_.isElytraFlying()) {
         float f4 = 1.0F;
         Vec3d vec3d = p_212844_1_.getMotion();
         if (vec3d.y < 0.0D) {
            Vec3d vec3d1 = vec3d.normalize();
            f4 = 1.0F - (float)Math.pow(-vec3d1.y, 1.5D);
         }

         f = f4 * 0.34906584F + (1.0F - f4) * f;
         f1 = f4 * (-(float)Math.PI / 2F) + (1.0F - f4) * f1;
      } else if (p_212844_1_.func_213287_bg()) {
         f = 0.6981317F;
         f1 = (-(float)Math.PI / 4F);
         f2 = 3.0F;
         f3 = 0.08726646F;
      }

      this.field_187061_b.rotationPointX = 5.0F;
      this.field_187061_b.rotationPointY = f2;
      if (p_212844_1_ instanceof AbstractClientPlayerEntity) {
         AbstractClientPlayerEntity abstractclientplayerentity = (AbstractClientPlayerEntity)p_212844_1_;
         abstractclientplayerentity.rotateElytraX = (float)((double)abstractclientplayerentity.rotateElytraX + (double)(f - abstractclientplayerentity.rotateElytraX) * 0.1D);
         abstractclientplayerentity.rotateElytraY = (float)((double)abstractclientplayerentity.rotateElytraY + (double)(f3 - abstractclientplayerentity.rotateElytraY) * 0.1D);
         abstractclientplayerentity.rotateElytraZ = (float)((double)abstractclientplayerentity.rotateElytraZ + (double)(f1 - abstractclientplayerentity.rotateElytraZ) * 0.1D);
         this.field_187061_b.rotateAngleX = abstractclientplayerentity.rotateElytraX;
         this.field_187061_b.rotateAngleY = abstractclientplayerentity.rotateElytraY;
         this.field_187061_b.rotateAngleZ = abstractclientplayerentity.rotateElytraZ;
      } else {
         this.field_187061_b.rotateAngleX = f;
         this.field_187061_b.rotateAngleZ = f1;
         this.field_187061_b.rotateAngleY = f3;
      }

      this.field_187060_a.rotationPointX = -this.field_187061_b.rotationPointX;
      this.field_187060_a.rotateAngleY = -this.field_187061_b.rotateAngleY;
      this.field_187060_a.rotationPointY = this.field_187061_b.rotationPointY;
      this.field_187060_a.rotateAngleX = this.field_187061_b.rotateAngleX;
      this.field_187060_a.rotateAngleZ = -this.field_187061_b.rotateAngleZ;
   }
}