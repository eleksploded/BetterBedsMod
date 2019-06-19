package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OcelotModel<T extends Entity> extends EntityModel<T> {
   protected final RendererModel field_78161_a;
   protected final RendererModel field_78159_b;
   protected final RendererModel field_78160_c;
   protected final RendererModel field_78157_d;
   protected final RendererModel field_78158_e;
   protected final RendererModel field_78155_f;
   protected final RendererModel field_78156_g;
   protected final RendererModel field_78162_h;
   protected int state = 1;

   public OcelotModel(float p_i51064_1_) {
      this.field_78156_g = new RendererModel(this, "head");
      this.field_78156_g.func_217178_a("main", -2.5F, -2.0F, -3.0F, 5, 4, 5, p_i51064_1_, 0, 0);
      this.field_78156_g.func_217178_a("nose", -1.5F, 0.0F, -4.0F, 3, 2, 2, p_i51064_1_, 0, 24);
      this.field_78156_g.func_217178_a("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, p_i51064_1_, 0, 10);
      this.field_78156_g.func_217178_a("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, p_i51064_1_, 6, 10);
      this.field_78156_g.setRotationPoint(0.0F, 15.0F, -9.0F);
      this.field_78162_h = new RendererModel(this, 20, 0);
      this.field_78162_h.addBox(-2.0F, 3.0F, -8.0F, 4, 16, 6, p_i51064_1_);
      this.field_78162_h.setRotationPoint(0.0F, 12.0F, -10.0F);
      this.field_78158_e = new RendererModel(this, 0, 15);
      this.field_78158_e.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1, p_i51064_1_);
      this.field_78158_e.rotateAngleX = 0.9F;
      this.field_78158_e.setRotationPoint(0.0F, 15.0F, 8.0F);
      this.field_78155_f = new RendererModel(this, 4, 15);
      this.field_78155_f.addBox(-0.5F, 0.0F, 0.0F, 1, 8, 1, p_i51064_1_);
      this.field_78155_f.setRotationPoint(0.0F, 20.0F, 14.0F);
      this.field_78161_a = new RendererModel(this, 8, 13);
      this.field_78161_a.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2, p_i51064_1_);
      this.field_78161_a.setRotationPoint(1.1F, 18.0F, 5.0F);
      this.field_78159_b = new RendererModel(this, 8, 13);
      this.field_78159_b.addBox(-1.0F, 0.0F, 1.0F, 2, 6, 2, p_i51064_1_);
      this.field_78159_b.setRotationPoint(-1.1F, 18.0F, 5.0F);
      this.field_78160_c = new RendererModel(this, 40, 0);
      this.field_78160_c.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, p_i51064_1_);
      this.field_78160_c.setRotationPoint(1.2F, 13.8F, -5.0F);
      this.field_78157_d = new RendererModel(this, 40, 0);
      this.field_78157_d.addBox(-1.0F, 0.0F, 0.0F, 2, 10, 2, p_i51064_1_);
      this.field_78157_d.setRotationPoint(-1.2F, 13.8F, -5.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.75F, 0.75F, 0.75F);
         GlStateManager.translatef(0.0F, 10.0F * scale, 4.0F * scale);
         this.field_78156_g.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_78162_h.render(scale);
         this.field_78161_a.render(scale);
         this.field_78159_b.render(scale);
         this.field_78160_c.render(scale);
         this.field_78157_d.render(scale);
         this.field_78158_e.render(scale);
         this.field_78155_f.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.field_78156_g.render(scale);
         this.field_78162_h.render(scale);
         this.field_78158_e.render(scale);
         this.field_78155_f.render(scale);
         this.field_78161_a.render(scale);
         this.field_78159_b.render(scale);
         this.field_78160_c.render(scale);
         this.field_78157_d.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.field_78156_g.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_78156_g.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      if (this.state != 3) {
         this.field_78162_h.rotateAngleX = ((float)Math.PI / 2F);
         if (this.state == 2) {
            this.field_78161_a.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * p_212844_3_;
            this.field_78159_b.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + 0.3F) * p_212844_3_;
            this.field_78160_c.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI + 0.3F) * p_212844_3_;
            this.field_78157_d.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * p_212844_3_;
            this.field_78155_f.rotateAngleX = 1.7278761F + ((float)Math.PI / 10F) * MathHelper.cos(p_212844_2_) * p_212844_3_;
         } else {
            this.field_78161_a.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * p_212844_3_;
            this.field_78159_b.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * p_212844_3_;
            this.field_78160_c.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * p_212844_3_;
            this.field_78157_d.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * p_212844_3_;
            if (this.state == 1) {
               this.field_78155_f.rotateAngleX = 1.7278761F + ((float)Math.PI / 4F) * MathHelper.cos(p_212844_2_) * p_212844_3_;
            } else {
               this.field_78155_f.rotateAngleX = 1.7278761F + 0.47123894F * MathHelper.cos(p_212844_2_) * p_212844_3_;
            }
         }
      }

   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_78162_h.rotationPointY = 12.0F;
      this.field_78162_h.rotationPointZ = -10.0F;
      this.field_78156_g.rotationPointY = 15.0F;
      this.field_78156_g.rotationPointZ = -9.0F;
      this.field_78158_e.rotationPointY = 15.0F;
      this.field_78158_e.rotationPointZ = 8.0F;
      this.field_78155_f.rotationPointY = 20.0F;
      this.field_78155_f.rotationPointZ = 14.0F;
      this.field_78160_c.rotationPointY = 13.8F;
      this.field_78160_c.rotationPointZ = -5.0F;
      this.field_78157_d.rotationPointY = 13.8F;
      this.field_78157_d.rotationPointZ = -5.0F;
      this.field_78161_a.rotationPointY = 18.0F;
      this.field_78161_a.rotationPointZ = 5.0F;
      this.field_78159_b.rotationPointY = 18.0F;
      this.field_78159_b.rotationPointZ = 5.0F;
      this.field_78158_e.rotateAngleX = 0.9F;
      if (p_212843_1_.isSneaking()) {
         ++this.field_78162_h.rotationPointY;
         this.field_78156_g.rotationPointY += 2.0F;
         ++this.field_78158_e.rotationPointY;
         this.field_78155_f.rotationPointY += -4.0F;
         this.field_78155_f.rotationPointZ += 2.0F;
         this.field_78158_e.rotateAngleX = ((float)Math.PI / 2F);
         this.field_78155_f.rotateAngleX = ((float)Math.PI / 2F);
         this.state = 0;
      } else if (p_212843_1_.isSprinting()) {
         this.field_78155_f.rotationPointY = this.field_78158_e.rotationPointY;
         this.field_78155_f.rotationPointZ += 2.0F;
         this.field_78158_e.rotateAngleX = ((float)Math.PI / 2F);
         this.field_78155_f.rotateAngleX = ((float)Math.PI / 2F);
         this.state = 2;
      } else {
         this.state = 1;
      }

   }
}