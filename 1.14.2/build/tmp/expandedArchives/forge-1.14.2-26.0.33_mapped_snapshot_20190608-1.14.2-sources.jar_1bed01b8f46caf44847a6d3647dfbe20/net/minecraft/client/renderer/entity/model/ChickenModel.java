package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_78142_a;
   private final RendererModel field_78140_b;
   private final RendererModel field_78141_c;
   private final RendererModel field_78138_d;
   private final RendererModel field_78139_e;
   private final RendererModel field_78136_f;
   private final RendererModel field_78137_g;
   private final RendererModel field_78143_h;

   public ChickenModel() {
      int i = 16;
      this.field_78142_a = new RendererModel(this, 0, 0);
      this.field_78142_a.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
      this.field_78142_a.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78137_g = new RendererModel(this, 14, 0);
      this.field_78137_g.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
      this.field_78137_g.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78143_h = new RendererModel(this, 14, 4);
      this.field_78143_h.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
      this.field_78143_h.setRotationPoint(0.0F, 15.0F, -4.0F);
      this.field_78140_b = new RendererModel(this, 0, 9);
      this.field_78140_b.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_78140_b.setRotationPoint(0.0F, 16.0F, 0.0F);
      this.field_78141_c = new RendererModel(this, 26, 0);
      this.field_78141_c.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78141_c.setRotationPoint(-2.0F, 19.0F, 1.0F);
      this.field_78138_d = new RendererModel(this, 26, 0);
      this.field_78138_d.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
      this.field_78138_d.setRotationPoint(1.0F, 19.0F, 1.0F);
      this.field_78139_e = new RendererModel(this, 24, 13);
      this.field_78139_e.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78139_e.setRotationPoint(-4.0F, 13.0F, 0.0F);
      this.field_78136_f = new RendererModel(this, 24, 13);
      this.field_78136_f.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
      this.field_78136_f.setRotationPoint(4.0F, 13.0F, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, 5.0F * scale, 2.0F * scale);
         this.field_78142_a.render(scale);
         this.field_78137_g.render(scale);
         this.field_78143_h.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_78140_b.render(scale);
         this.field_78141_c.render(scale);
         this.field_78138_d.render(scale);
         this.field_78139_e.render(scale);
         this.field_78136_f.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.field_78142_a.render(scale);
         this.field_78137_g.render(scale);
         this.field_78143_h.render(scale);
         this.field_78140_b.render(scale);
         this.field_78141_c.render(scale);
         this.field_78138_d.render(scale);
         this.field_78139_e.render(scale);
         this.field_78136_f.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.field_78142_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_78142_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_78137_g.rotateAngleX = this.field_78142_a.rotateAngleX;
      this.field_78137_g.rotateAngleY = this.field_78142_a.rotateAngleY;
      this.field_78143_h.rotateAngleX = this.field_78142_a.rotateAngleX;
      this.field_78143_h.rotateAngleY = this.field_78142_a.rotateAngleY;
      this.field_78140_b.rotateAngleX = ((float)Math.PI / 2F);
      this.field_78141_c.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_;
      this.field_78138_d.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_;
      this.field_78139_e.rotateAngleZ = p_212844_4_;
      this.field_78136_f.rotateAngleZ = -p_212844_4_;
   }
}