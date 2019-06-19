package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SnowManModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_78196_a;
   private final RendererModel field_78194_b;
   private final RendererModel field_78195_c;
   private final RendererModel field_78192_d;
   private final RendererModel field_78193_e;

   public SnowManModel() {
      float f = 4.0F;
      float f1 = 0.0F;
      this.field_78195_c = (new RendererModel(this, 0, 0)).setTextureSize(64, 64);
      this.field_78195_c.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
      this.field_78195_c.setRotationPoint(0.0F, 4.0F, 0.0F);
      this.field_78192_d = (new RendererModel(this, 32, 0)).setTextureSize(64, 64);
      this.field_78192_d.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78192_d.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.field_78193_e = (new RendererModel(this, 32, 0)).setTextureSize(64, 64);
      this.field_78193_e.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
      this.field_78193_e.setRotationPoint(0.0F, 6.0F, 0.0F);
      this.field_78196_a = (new RendererModel(this, 0, 16)).setTextureSize(64, 64);
      this.field_78196_a.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
      this.field_78196_a.setRotationPoint(0.0F, 13.0F, 0.0F);
      this.field_78194_b = (new RendererModel(this, 0, 36)).setTextureSize(64, 64);
      this.field_78194_b.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
      this.field_78194_b.setRotationPoint(0.0F, 24.0F, 0.0F);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_78195_c.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_78195_c.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_78196_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F) * 0.25F;
      float f = MathHelper.sin(this.field_78196_a.rotateAngleY);
      float f1 = MathHelper.cos(this.field_78196_a.rotateAngleY);
      this.field_78192_d.rotateAngleZ = 1.0F;
      this.field_78193_e.rotateAngleZ = -1.0F;
      this.field_78192_d.rotateAngleY = 0.0F + this.field_78196_a.rotateAngleY;
      this.field_78193_e.rotateAngleY = (float)Math.PI + this.field_78196_a.rotateAngleY;
      this.field_78192_d.rotationPointX = f1 * 5.0F;
      this.field_78192_d.rotationPointZ = -f * 5.0F;
      this.field_78193_e.rotationPointX = -f1 * 5.0F;
      this.field_78193_e.rotationPointZ = f * 5.0F;
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_78196_a.render(scale);
      this.field_78194_b.render(scale);
      this.field_78195_c.render(scale);
      this.field_78192_d.render(scale);
      this.field_78193_e.render(scale);
   }

   public RendererModel func_205070_a() {
      return this.field_78195_c;
   }
}