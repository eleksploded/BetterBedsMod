package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParrotModel extends EntityModel<ParrotEntity> {
   private final RendererModel field_192764_a;
   private final RendererModel field_192765_b;
   private final RendererModel field_192766_c;
   private final RendererModel field_192767_d;
   private final RendererModel field_192768_e;
   private final RendererModel field_192769_f;
   private final RendererModel field_192770_g;
   private final RendererModel field_192771_h;
   private final RendererModel field_192772_i;
   private final RendererModel field_192773_j;
   private final RendererModel field_192774_k;

   public ParrotModel() {
      this.textureWidth = 32;
      this.textureHeight = 32;
      this.field_192764_a = new RendererModel(this, 2, 8);
      this.field_192764_a.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3);
      this.field_192764_a.setRotationPoint(0.0F, 16.5F, -3.0F);
      this.field_192765_b = new RendererModel(this, 22, 1);
      this.field_192765_b.addBox(-1.5F, -1.0F, -1.0F, 3, 4, 1);
      this.field_192765_b.setRotationPoint(0.0F, 21.07F, 1.16F);
      this.field_192766_c = new RendererModel(this, 19, 8);
      this.field_192766_c.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192766_c.setRotationPoint(1.5F, 16.94F, -2.76F);
      this.field_192767_d = new RendererModel(this, 19, 8);
      this.field_192767_d.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3);
      this.field_192767_d.setRotationPoint(-1.5F, 16.94F, -2.76F);
      this.field_192768_e = new RendererModel(this, 2, 2);
      this.field_192768_e.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2);
      this.field_192768_e.setRotationPoint(0.0F, 15.69F, -2.76F);
      this.field_192769_f = new RendererModel(this, 10, 0);
      this.field_192769_f.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4);
      this.field_192769_f.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.field_192768_e.addChild(this.field_192769_f);
      this.field_192770_g = new RendererModel(this, 11, 7);
      this.field_192770_g.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1);
      this.field_192770_g.setRotationPoint(0.0F, -0.5F, -1.5F);
      this.field_192768_e.addChild(this.field_192770_g);
      this.field_192771_h = new RendererModel(this, 16, 7);
      this.field_192771_h.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192771_h.setRotationPoint(0.0F, -1.75F, -2.45F);
      this.field_192768_e.addChild(this.field_192771_h);
      this.field_192772_i = new RendererModel(this, 2, 18);
      this.field_192772_i.addBox(0.0F, -4.0F, -2.0F, 0, 5, 4);
      this.field_192772_i.setRotationPoint(0.0F, -2.15F, 0.15F);
      this.field_192768_e.addChild(this.field_192772_i);
      this.field_192773_j = new RendererModel(this, 14, 18);
      this.field_192773_j.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192773_j.setRotationPoint(1.0F, 22.0F, -1.05F);
      this.field_192774_k = new RendererModel(this, 14, 18);
      this.field_192774_k.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
      this.field_192774_k.setRotationPoint(-1.0F, 22.0F, -1.05F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(ParrotEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_217159_a(scale);
   }

   public void func_212844_a_(ParrotEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.func_217162_a(func_217158_a(p_212844_1_), p_212844_1_.ticksExisted, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_);
   }

   public void func_212843_a_(ParrotEntity p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.func_217160_a(func_217158_a(p_212843_1_));
   }

   public void func_217161_a(float p_217161_1_, float p_217161_2_, float p_217161_3_, float p_217161_4_, float p_217161_5_, int p_217161_6_) {
      this.func_217160_a(ParrotModel.State.ON_SHOULDER);
      this.func_217162_a(ParrotModel.State.ON_SHOULDER, p_217161_6_, p_217161_1_, p_217161_2_, 0.0F, p_217161_3_, p_217161_4_);
      this.func_217159_a(p_217161_5_);
   }

   private void func_217159_a(float p_217159_1_) {
      this.field_192764_a.render(p_217159_1_);
      this.field_192766_c.render(p_217159_1_);
      this.field_192767_d.render(p_217159_1_);
      this.field_192765_b.render(p_217159_1_);
      this.field_192768_e.render(p_217159_1_);
      this.field_192773_j.render(p_217159_1_);
      this.field_192774_k.render(p_217159_1_);
   }

   private void func_217162_a(ParrotModel.State p_217162_1_, int p_217162_2_, float p_217162_3_, float p_217162_4_, float p_217162_5_, float p_217162_6_, float p_217162_7_) {
      this.field_192768_e.rotateAngleX = p_217162_7_ * ((float)Math.PI / 180F);
      this.field_192768_e.rotateAngleY = p_217162_6_ * ((float)Math.PI / 180F);
      this.field_192768_e.rotateAngleZ = 0.0F;
      this.field_192768_e.rotationPointX = 0.0F;
      this.field_192764_a.rotationPointX = 0.0F;
      this.field_192765_b.rotationPointX = 0.0F;
      this.field_192767_d.rotationPointX = -1.5F;
      this.field_192766_c.rotationPointX = 1.5F;
      switch(p_217162_1_) {
      case SITTING:
         break;
      case PARTY:
         float f = MathHelper.cos((float)p_217162_2_);
         float f1 = MathHelper.sin((float)p_217162_2_);
         this.field_192768_e.rotationPointX = f;
         this.field_192768_e.rotationPointY = 15.69F + f1;
         this.field_192768_e.rotateAngleX = 0.0F;
         this.field_192768_e.rotateAngleY = 0.0F;
         this.field_192768_e.rotateAngleZ = MathHelper.sin((float)p_217162_2_) * 0.4F;
         this.field_192764_a.rotationPointX = f;
         this.field_192764_a.rotationPointY = 16.5F + f1;
         this.field_192766_c.rotateAngleZ = -0.0873F - p_217162_5_;
         this.field_192766_c.rotationPointX = 1.5F + f;
         this.field_192766_c.rotationPointY = 16.94F + f1;
         this.field_192767_d.rotateAngleZ = 0.0873F + p_217162_5_;
         this.field_192767_d.rotationPointX = -1.5F + f;
         this.field_192767_d.rotationPointY = 16.94F + f1;
         this.field_192765_b.rotationPointX = f;
         this.field_192765_b.rotationPointY = 21.07F + f1;
         break;
      case STANDING:
         this.field_192773_j.rotateAngleX += MathHelper.cos(p_217162_3_ * 0.6662F) * 1.4F * p_217162_4_;
         this.field_192774_k.rotateAngleX += MathHelper.cos(p_217162_3_ * 0.6662F + (float)Math.PI) * 1.4F * p_217162_4_;
      case FLYING:
      case ON_SHOULDER:
      default:
         float f2 = p_217162_5_ * 0.3F;
         this.field_192768_e.rotationPointY = 15.69F + f2;
         this.field_192765_b.rotateAngleX = 1.015F + MathHelper.cos(p_217162_3_ * 0.6662F) * 0.3F * p_217162_4_;
         this.field_192765_b.rotationPointY = 21.07F + f2;
         this.field_192764_a.rotationPointY = 16.5F + f2;
         this.field_192766_c.rotateAngleZ = -0.0873F - p_217162_5_;
         this.field_192766_c.rotationPointY = 16.94F + f2;
         this.field_192767_d.rotateAngleZ = 0.0873F + p_217162_5_;
         this.field_192767_d.rotationPointY = 16.94F + f2;
         this.field_192773_j.rotationPointY = 22.0F + f2;
         this.field_192774_k.rotationPointY = 22.0F + f2;
      }

   }

   private void func_217160_a(ParrotModel.State p_217160_1_) {
      this.field_192772_i.rotateAngleX = -0.2214F;
      this.field_192764_a.rotateAngleX = 0.4937F;
      this.field_192766_c.rotateAngleX = -0.6981F;
      this.field_192766_c.rotateAngleY = -(float)Math.PI;
      this.field_192767_d.rotateAngleX = -0.6981F;
      this.field_192767_d.rotateAngleY = -(float)Math.PI;
      this.field_192773_j.rotateAngleX = -0.0299F;
      this.field_192774_k.rotateAngleX = -0.0299F;
      this.field_192773_j.rotationPointY = 22.0F;
      this.field_192774_k.rotationPointY = 22.0F;
      this.field_192773_j.rotateAngleZ = 0.0F;
      this.field_192774_k.rotateAngleZ = 0.0F;
      switch(p_217160_1_) {
      case SITTING:
         float f = 1.9F;
         this.field_192768_e.rotationPointY = 17.59F;
         this.field_192765_b.rotateAngleX = 1.5388988F;
         this.field_192765_b.rotationPointY = 22.97F;
         this.field_192764_a.rotationPointY = 18.4F;
         this.field_192766_c.rotateAngleZ = -0.0873F;
         this.field_192766_c.rotationPointY = 18.84F;
         this.field_192767_d.rotateAngleZ = 0.0873F;
         this.field_192767_d.rotationPointY = 18.84F;
         ++this.field_192773_j.rotationPointY;
         ++this.field_192774_k.rotationPointY;
         ++this.field_192773_j.rotateAngleX;
         ++this.field_192774_k.rotateAngleX;
         break;
      case PARTY:
         this.field_192773_j.rotateAngleZ = -0.34906584F;
         this.field_192774_k.rotateAngleZ = 0.34906584F;
      case STANDING:
      case ON_SHOULDER:
      default:
         break;
      case FLYING:
         this.field_192773_j.rotateAngleX += 0.6981317F;
         this.field_192774_k.rotateAngleX += 0.6981317F;
      }

   }

   private static ParrotModel.State func_217158_a(ParrotEntity p_217158_0_) {
      if (p_217158_0_.isPartying()) {
         return ParrotModel.State.PARTY;
      } else if (p_217158_0_.isSitting()) {
         return ParrotModel.State.SITTING;
      } else {
         return p_217158_0_.isFlying() ? ParrotModel.State.FLYING : ParrotModel.State.STANDING;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum State {
      FLYING,
      STANDING,
      SITTING,
      PARTY,
      ON_SHOULDER;
   }
}