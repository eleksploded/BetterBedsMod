package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BatModel extends EntityModel<BatEntity> {
   private final RendererModel field_82895_a;
   private final RendererModel field_82893_b;
   private final RendererModel field_82894_c;
   private final RendererModel field_82891_d;
   private final RendererModel field_82892_e;
   private final RendererModel field_82890_f;

   public BatModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_82895_a = new RendererModel(this, 0, 0);
      this.field_82895_a.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
      RendererModel renderermodel = new RendererModel(this, 24, 0);
      renderermodel.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.addChild(renderermodel);
      RendererModel renderermodel1 = new RendererModel(this, 24, 0);
      renderermodel1.mirror = true;
      renderermodel1.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
      this.field_82895_a.addChild(renderermodel1);
      this.field_82893_b = new RendererModel(this, 0, 16);
      this.field_82893_b.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
      this.field_82893_b.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
      this.field_82894_c = new RendererModel(this, 42, 0);
      this.field_82894_c.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82892_e = new RendererModel(this, 24, 16);
      this.field_82892_e.setRotationPoint(-12.0F, 1.0F, 1.5F);
      this.field_82892_e.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82891_d = new RendererModel(this, 42, 0);
      this.field_82891_d.mirror = true;
      this.field_82891_d.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
      this.field_82890_f = new RendererModel(this, 24, 16);
      this.field_82890_f.mirror = true;
      this.field_82890_f.setRotationPoint(12.0F, 1.0F, 1.5F);
      this.field_82890_f.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
      this.field_82893_b.addChild(this.field_82894_c);
      this.field_82893_b.addChild(this.field_82891_d);
      this.field_82894_c.addChild(this.field_82892_e);
      this.field_82891_d.addChild(this.field_82890_f);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(BatEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_82895_a.render(scale);
      this.field_82893_b.render(scale);
   }

   public void func_212844_a_(BatEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      if (p_212844_1_.getIsBatHanging()) {
         this.field_82895_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
         this.field_82895_a.rotateAngleY = (float)Math.PI - p_212844_5_ * ((float)Math.PI / 180F);
         this.field_82895_a.rotateAngleZ = (float)Math.PI;
         this.field_82895_a.setRotationPoint(0.0F, -2.0F, 0.0F);
         this.field_82894_c.setRotationPoint(-3.0F, 0.0F, 3.0F);
         this.field_82891_d.setRotationPoint(3.0F, 0.0F, 3.0F);
         this.field_82893_b.rotateAngleX = (float)Math.PI;
         this.field_82894_c.rotateAngleX = -0.15707964F;
         this.field_82894_c.rotateAngleY = -1.2566371F;
         this.field_82892_e.rotateAngleY = -1.7278761F;
         this.field_82891_d.rotateAngleX = this.field_82894_c.rotateAngleX;
         this.field_82891_d.rotateAngleY = -this.field_82894_c.rotateAngleY;
         this.field_82890_f.rotateAngleY = -this.field_82892_e.rotateAngleY;
      } else {
         this.field_82895_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
         this.field_82895_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
         this.field_82895_a.rotateAngleZ = 0.0F;
         this.field_82895_a.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.field_82894_c.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.field_82891_d.setRotationPoint(0.0F, 0.0F, 0.0F);
         this.field_82893_b.rotateAngleX = ((float)Math.PI / 4F) + MathHelper.cos(p_212844_4_ * 0.1F) * 0.15F;
         this.field_82893_b.rotateAngleY = 0.0F;
         this.field_82894_c.rotateAngleY = MathHelper.cos(p_212844_4_ * 1.3F) * (float)Math.PI * 0.25F;
         this.field_82891_d.rotateAngleY = -this.field_82894_c.rotateAngleY;
         this.field_82892_e.rotateAngleY = this.field_82894_c.rotateAngleY * 0.5F;
         this.field_82890_f.rotateAngleY = -this.field_82894_c.rotateAngleY * 0.5F;
      }

   }
}