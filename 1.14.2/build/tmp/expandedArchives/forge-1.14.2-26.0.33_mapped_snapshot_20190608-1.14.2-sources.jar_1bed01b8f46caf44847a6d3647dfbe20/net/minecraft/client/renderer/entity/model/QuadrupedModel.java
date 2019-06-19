package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class QuadrupedModel<T extends Entity> extends EntityModel<T> {
   protected RendererModel field_78150_a;
   protected RendererModel field_78148_b;
   protected RendererModel field_78149_c;
   protected RendererModel field_78146_d;
   protected RendererModel field_78147_e;
   protected RendererModel field_78144_f;
   protected float childYOffset = 8.0F;
   protected float childZOffset = 4.0F;

   public QuadrupedModel(int height, float scale) {
      this.field_78150_a = new RendererModel(this, 0, 0);
      this.field_78150_a.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, scale);
      this.field_78150_a.setRotationPoint(0.0F, (float)(18 - height), -6.0F);
      this.field_78148_b = new RendererModel(this, 28, 8);
      this.field_78148_b.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, scale);
      this.field_78148_b.setRotationPoint(0.0F, (float)(17 - height), 2.0F);
      this.field_78149_c = new RendererModel(this, 0, 16);
      this.field_78149_c.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.field_78149_c.setRotationPoint(-3.0F, (float)(24 - height), 7.0F);
      this.field_78146_d = new RendererModel(this, 0, 16);
      this.field_78146_d.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.field_78146_d.setRotationPoint(3.0F, (float)(24 - height), 7.0F);
      this.field_78147_e = new RendererModel(this, 0, 16);
      this.field_78147_e.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.field_78147_e.setRotationPoint(-3.0F, (float)(24 - height), -5.0F);
      this.field_78144_f = new RendererModel(this, 0, 16);
      this.field_78144_f.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
      this.field_78144_f.setRotationPoint(3.0F, (float)(24 - height), -5.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, this.childYOffset * scale, this.childZOffset * scale);
         this.field_78150_a.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_78148_b.render(scale);
         this.field_78149_c.render(scale);
         this.field_78146_d.render(scale);
         this.field_78147_e.render(scale);
         this.field_78144_f.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.field_78150_a.render(scale);
         this.field_78148_b.render(scale);
         this.field_78149_c.render(scale);
         this.field_78146_d.render(scale);
         this.field_78147_e.render(scale);
         this.field_78144_f.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.field_78150_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_78150_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_78148_b.rotateAngleX = ((float)Math.PI / 2F);
      this.field_78149_c.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_;
      this.field_78146_d.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_;
      this.field_78147_e.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_;
      this.field_78144_f.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_;
   }
}