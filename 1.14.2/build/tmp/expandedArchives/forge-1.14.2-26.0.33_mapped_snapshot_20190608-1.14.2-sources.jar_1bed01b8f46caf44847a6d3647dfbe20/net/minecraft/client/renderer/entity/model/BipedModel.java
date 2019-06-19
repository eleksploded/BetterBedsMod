package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BipedModel<T extends LivingEntity> extends EntityModel<T> implements IHasArm, IHasHead {
   public RendererModel field_78116_c;
   public RendererModel field_178720_f;
   public RendererModel field_78115_e;
   public RendererModel field_178723_h;
   public RendererModel field_178724_i;
   public RendererModel field_178721_j;
   public RendererModel field_178722_k;
   public BipedModel.ArmPose field_187075_l = BipedModel.ArmPose.EMPTY;
   public BipedModel.ArmPose field_187076_m = BipedModel.ArmPose.EMPTY;
   public boolean isSneak;
   public float field_205061_a;
   private float field_217149_a;

   public BipedModel() {
      this(0.0F);
   }

   public BipedModel(float modelSize) {
      this(modelSize, 0.0F, 64, 32);
   }

   public BipedModel(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
      this.textureWidth = textureWidthIn;
      this.textureHeight = textureHeightIn;
      this.field_78116_c = new RendererModel(this, 0, 0);
      this.field_78116_c.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
      this.field_78116_c.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_178720_f = new RendererModel(this, 32, 0);
      this.field_178720_f.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
      this.field_178720_f.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_78115_e = new RendererModel(this, 16, 16);
      this.field_78115_e.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
      this.field_78115_e.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
      this.field_178723_h = new RendererModel(this, 40, 16);
      this.field_178723_h.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
      this.field_178723_h.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.field_178724_i = new RendererModel(this, 40, 16);
      this.field_178724_i.mirror = true;
      this.field_178724_i.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
      this.field_178724_i.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
      this.field_178721_j = new RendererModel(this, 0, 16);
      this.field_178721_j.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
      this.field_178721_j.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
      this.field_178722_k = new RendererModel(this, 0, 16);
      this.field_178722_k.mirror = true;
      this.field_178722_k.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
      this.field_178722_k.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      GlStateManager.pushMatrix();
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.scalef(0.75F, 0.75F, 0.75F);
         GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
         this.field_78116_c.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_78115_e.render(scale);
         this.field_178723_h.render(scale);
         this.field_178724_i.render(scale);
         this.field_178721_j.render(scale);
         this.field_178722_k.render(scale);
         this.field_178720_f.render(scale);
      } else {
         if (entityIn.func_213287_bg()) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
         }

         this.field_78116_c.render(scale);
         this.field_78115_e.render(scale);
         this.field_178723_h.render(scale);
         this.field_178724_i.render(scale);
         this.field_178721_j.render(scale);
         this.field_178722_k.render(scale);
         this.field_178720_f.render(scale);
      }

      GlStateManager.popMatrix();
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_205061_a = p_212843_1_.getSwimAnimation(p_212843_4_);
      this.field_217149_a = (float)p_212843_1_.getItemInUseMaxCount();
      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      boolean flag = p_212844_1_.getTicksElytraFlying() > 4;
      boolean flag1 = p_212844_1_.func_213314_bj();
      this.field_78116_c.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      if (flag) {
         this.field_78116_c.rotateAngleX = (-(float)Math.PI / 4F);
      } else if (this.field_205061_a > 0.0F) {
         if (flag1) {
            this.field_78116_c.rotateAngleX = this.func_205060_a(this.field_78116_c.rotateAngleX, (-(float)Math.PI / 4F), this.field_205061_a);
         } else {
            this.field_78116_c.rotateAngleX = this.func_205060_a(this.field_78116_c.rotateAngleX, p_212844_6_ * ((float)Math.PI / 180F), this.field_205061_a);
         }
      } else {
         this.field_78116_c.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      }

      this.field_78115_e.rotateAngleY = 0.0F;
      this.field_178723_h.rotationPointZ = 0.0F;
      this.field_178723_h.rotationPointX = -5.0F;
      this.field_178724_i.rotationPointZ = 0.0F;
      this.field_178724_i.rotationPointX = 5.0F;
      float f = 1.0F;
      if (flag) {
         f = (float)p_212844_1_.getMotion().lengthSquared();
         f = f / 0.2F;
         f = f * f * f;
      }

      if (f < 1.0F) {
         f = 1.0F;
      }

      this.field_178723_h.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 2.0F * p_212844_3_ * 0.5F / f;
      this.field_178724_i.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 2.0F * p_212844_3_ * 0.5F / f;
      this.field_178723_h.rotateAngleZ = 0.0F;
      this.field_178724_i.rotateAngleZ = 0.0F;
      this.field_178721_j.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_ / f;
      this.field_178722_k.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_ / f;
      this.field_178721_j.rotateAngleY = 0.0F;
      this.field_178722_k.rotateAngleY = 0.0F;
      this.field_178721_j.rotateAngleZ = 0.0F;
      this.field_178722_k.rotateAngleZ = 0.0F;
      if (this.field_217113_d) {
         this.field_178723_h.rotateAngleX += (-(float)Math.PI / 5F);
         this.field_178724_i.rotateAngleX += (-(float)Math.PI / 5F);
         this.field_178721_j.rotateAngleX = -1.4137167F;
         this.field_178721_j.rotateAngleY = ((float)Math.PI / 10F);
         this.field_178721_j.rotateAngleZ = 0.07853982F;
         this.field_178722_k.rotateAngleX = -1.4137167F;
         this.field_178722_k.rotateAngleY = (-(float)Math.PI / 10F);
         this.field_178722_k.rotateAngleZ = -0.07853982F;
      }

      this.field_178723_h.rotateAngleY = 0.0F;
      this.field_178723_h.rotateAngleZ = 0.0F;
      switch(this.field_187075_l) {
      case EMPTY:
         this.field_178724_i.rotateAngleY = 0.0F;
         break;
      case BLOCK:
         this.field_178724_i.rotateAngleX = this.field_178724_i.rotateAngleX * 0.5F - 0.9424779F;
         this.field_178724_i.rotateAngleY = ((float)Math.PI / 6F);
         break;
      case ITEM:
         this.field_178724_i.rotateAngleX = this.field_178724_i.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
         this.field_178724_i.rotateAngleY = 0.0F;
      }

      switch(this.field_187076_m) {
      case EMPTY:
         this.field_178723_h.rotateAngleY = 0.0F;
         break;
      case BLOCK:
         this.field_178723_h.rotateAngleX = this.field_178723_h.rotateAngleX * 0.5F - 0.9424779F;
         this.field_178723_h.rotateAngleY = (-(float)Math.PI / 6F);
         break;
      case ITEM:
         this.field_178723_h.rotateAngleX = this.field_178723_h.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
         this.field_178723_h.rotateAngleY = 0.0F;
         break;
      case THROW_SPEAR:
         this.field_178723_h.rotateAngleX = this.field_178723_h.rotateAngleX * 0.5F - (float)Math.PI;
         this.field_178723_h.rotateAngleY = 0.0F;
      }

      if (this.field_187075_l == BipedModel.ArmPose.THROW_SPEAR && this.field_187076_m != BipedModel.ArmPose.BLOCK && this.field_187076_m != BipedModel.ArmPose.THROW_SPEAR && this.field_187076_m != BipedModel.ArmPose.BOW_AND_ARROW) {
         this.field_178724_i.rotateAngleX = this.field_178724_i.rotateAngleX * 0.5F - (float)Math.PI;
         this.field_178724_i.rotateAngleY = 0.0F;
      }

      if (this.field_217112_c > 0.0F) {
         HandSide handside = this.func_217147_a(p_212844_1_);
         RendererModel renderermodel = this.getArmForSide(handside);
         float f1 = this.field_217112_c;
         this.field_78115_e.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;
         if (handside == HandSide.LEFT) {
            this.field_78115_e.rotateAngleY *= -1.0F;
         }

         this.field_178723_h.rotationPointZ = MathHelper.sin(this.field_78115_e.rotateAngleY) * 5.0F;
         this.field_178723_h.rotationPointX = -MathHelper.cos(this.field_78115_e.rotateAngleY) * 5.0F;
         this.field_178724_i.rotationPointZ = -MathHelper.sin(this.field_78115_e.rotateAngleY) * 5.0F;
         this.field_178724_i.rotationPointX = MathHelper.cos(this.field_78115_e.rotateAngleY) * 5.0F;
         this.field_178723_h.rotateAngleY += this.field_78115_e.rotateAngleY;
         this.field_178724_i.rotateAngleY += this.field_78115_e.rotateAngleY;
         this.field_178724_i.rotateAngleX += this.field_78115_e.rotateAngleY;
         f1 = 1.0F - this.field_217112_c;
         f1 = f1 * f1;
         f1 = f1 * f1;
         f1 = 1.0F - f1;
         float f2 = MathHelper.sin(f1 * (float)Math.PI);
         float f3 = MathHelper.sin(this.field_217112_c * (float)Math.PI) * -(this.field_78116_c.rotateAngleX - 0.7F) * 0.75F;
         renderermodel.rotateAngleX = (float)((double)renderermodel.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
         renderermodel.rotateAngleY += this.field_78115_e.rotateAngleY * 2.0F;
         renderermodel.rotateAngleZ += MathHelper.sin(this.field_217112_c * (float)Math.PI) * -0.4F;
      }

      if (this.isSneak) {
         this.field_78115_e.rotateAngleX = 0.5F;
         this.field_178723_h.rotateAngleX += 0.4F;
         this.field_178724_i.rotateAngleX += 0.4F;
         this.field_178721_j.rotationPointZ = 4.0F;
         this.field_178722_k.rotationPointZ = 4.0F;
         this.field_178721_j.rotationPointY = 9.0F;
         this.field_178722_k.rotationPointY = 9.0F;
         this.field_78116_c.rotationPointY = 1.0F;
      } else {
         this.field_78115_e.rotateAngleX = 0.0F;
         this.field_178721_j.rotationPointZ = 0.1F;
         this.field_178722_k.rotationPointZ = 0.1F;
         this.field_178721_j.rotationPointY = 12.0F;
         this.field_178722_k.rotationPointY = 12.0F;
         this.field_78116_c.rotationPointY = 0.0F;
      }

      this.field_178723_h.rotateAngleZ += MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.rotateAngleZ -= MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.rotateAngleX += MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      this.field_178724_i.rotateAngleX -= MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      if (this.field_187076_m == BipedModel.ArmPose.BOW_AND_ARROW) {
         this.field_178723_h.rotateAngleY = -0.1F + this.field_78116_c.rotateAngleY;
         this.field_178724_i.rotateAngleY = 0.1F + this.field_78116_c.rotateAngleY + 0.4F;
         this.field_178723_h.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX;
         this.field_178724_i.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX;
      } else if (this.field_187075_l == BipedModel.ArmPose.BOW_AND_ARROW && this.field_187076_m != BipedModel.ArmPose.THROW_SPEAR && this.field_187076_m != BipedModel.ArmPose.BLOCK) {
         this.field_178723_h.rotateAngleY = -0.1F + this.field_78116_c.rotateAngleY - 0.4F;
         this.field_178724_i.rotateAngleY = 0.1F + this.field_78116_c.rotateAngleY;
         this.field_178723_h.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX;
         this.field_178724_i.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX;
      }

      float f4 = (float)CrossbowItem.func_220026_e(p_212844_1_.getActiveItemStack());
      if (this.field_187076_m == BipedModel.ArmPose.CROSSBOW_CHARGE) {
         this.field_178723_h.rotateAngleY = -0.8F;
         this.field_178723_h.rotateAngleX = -0.97079635F;
         this.field_178724_i.rotateAngleX = -0.97079635F;
         float f5 = MathHelper.clamp(this.field_217149_a, 0.0F, f4);
         this.field_178724_i.rotateAngleY = MathHelper.func_219799_g(f5 / f4, 0.4F, 0.85F);
         this.field_178724_i.rotateAngleX = MathHelper.func_219799_g(f5 / f4, this.field_178724_i.rotateAngleX, (-(float)Math.PI / 2F));
      } else if (this.field_187075_l == BipedModel.ArmPose.CROSSBOW_CHARGE) {
         this.field_178724_i.rotateAngleY = 0.8F;
         this.field_178723_h.rotateAngleX = -0.97079635F;
         this.field_178724_i.rotateAngleX = -0.97079635F;
         float f6 = MathHelper.clamp(this.field_217149_a, 0.0F, f4);
         this.field_178723_h.rotateAngleY = MathHelper.func_219799_g(f6 / f4, -0.4F, -0.85F);
         this.field_178723_h.rotateAngleX = MathHelper.func_219799_g(f6 / f4, this.field_178723_h.rotateAngleX, (-(float)Math.PI / 2F));
      }

      if (this.field_187076_m == BipedModel.ArmPose.CROSSBOW_HOLD && this.field_217112_c <= 0.0F) {
         this.field_178723_h.rotateAngleY = -0.3F + this.field_78116_c.rotateAngleY;
         this.field_178724_i.rotateAngleY = 0.6F + this.field_78116_c.rotateAngleY;
         this.field_178723_h.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX + 0.1F;
         this.field_178724_i.rotateAngleX = -1.5F + this.field_78116_c.rotateAngleX;
      } else if (this.field_187075_l == BipedModel.ArmPose.CROSSBOW_HOLD) {
         this.field_178723_h.rotateAngleY = -0.6F + this.field_78116_c.rotateAngleY;
         this.field_178724_i.rotateAngleY = 0.3F + this.field_78116_c.rotateAngleY;
         this.field_178723_h.rotateAngleX = -1.5F + this.field_78116_c.rotateAngleX;
         this.field_178724_i.rotateAngleX = (-(float)Math.PI / 2F) + this.field_78116_c.rotateAngleX + 0.1F;
      }

      if (this.field_205061_a > 0.0F) {
         float f7 = p_212844_2_ % 26.0F;
         float f8 = this.field_217112_c > 0.0F ? 0.0F : this.field_205061_a;
         if (f7 < 14.0F) {
            this.field_178724_i.rotateAngleX = this.func_205060_a(this.field_178724_i.rotateAngleX, 0.0F, this.field_205061_a);
            this.field_178723_h.rotateAngleX = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleX, 0.0F);
            this.field_178724_i.rotateAngleY = this.func_205060_a(this.field_178724_i.rotateAngleY, (float)Math.PI, this.field_205061_a);
            this.field_178723_h.rotateAngleY = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleY, (float)Math.PI);
            this.field_178724_i.rotateAngleZ = this.func_205060_a(this.field_178724_i.rotateAngleZ, (float)Math.PI + 1.8707964F * this.func_203068_a(f7) / this.func_203068_a(14.0F), this.field_205061_a);
            this.field_178723_h.rotateAngleZ = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleZ, (float)Math.PI - 1.8707964F * this.func_203068_a(f7) / this.func_203068_a(14.0F));
         } else if (f7 >= 14.0F && f7 < 22.0F) {
            float f10 = (f7 - 14.0F) / 8.0F;
            this.field_178724_i.rotateAngleX = this.func_205060_a(this.field_178724_i.rotateAngleX, ((float)Math.PI / 2F) * f10, this.field_205061_a);
            this.field_178723_h.rotateAngleX = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleX, ((float)Math.PI / 2F) * f10);
            this.field_178724_i.rotateAngleY = this.func_205060_a(this.field_178724_i.rotateAngleY, (float)Math.PI, this.field_205061_a);
            this.field_178723_h.rotateAngleY = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleY, (float)Math.PI);
            this.field_178724_i.rotateAngleZ = this.func_205060_a(this.field_178724_i.rotateAngleZ, 5.012389F - 1.8707964F * f10, this.field_205061_a);
            this.field_178723_h.rotateAngleZ = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleZ, 1.2707963F + 1.8707964F * f10);
         } else if (f7 >= 22.0F && f7 < 26.0F) {
            float f9 = (f7 - 22.0F) / 4.0F;
            this.field_178724_i.rotateAngleX = this.func_205060_a(this.field_178724_i.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f9, this.field_205061_a);
            this.field_178723_h.rotateAngleX = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleX, ((float)Math.PI / 2F) - ((float)Math.PI / 2F) * f9);
            this.field_178724_i.rotateAngleY = this.func_205060_a(this.field_178724_i.rotateAngleY, (float)Math.PI, this.field_205061_a);
            this.field_178723_h.rotateAngleY = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleY, (float)Math.PI);
            this.field_178724_i.rotateAngleZ = this.func_205060_a(this.field_178724_i.rotateAngleZ, (float)Math.PI, this.field_205061_a);
            this.field_178723_h.rotateAngleZ = MathHelper.func_219799_g(f8, this.field_178723_h.rotateAngleZ, (float)Math.PI);
         }

         float f11 = 0.3F;
         float f12 = 0.33333334F;
         this.field_178722_k.rotateAngleX = MathHelper.func_219799_g(this.field_205061_a, this.field_178722_k.rotateAngleX, 0.3F * MathHelper.cos(p_212844_2_ * 0.33333334F + (float)Math.PI));
         this.field_178721_j.rotateAngleX = MathHelper.func_219799_g(this.field_205061_a, this.field_178721_j.rotateAngleX, 0.3F * MathHelper.cos(p_212844_2_ * 0.33333334F));
      }

      this.field_178720_f.func_217177_a(this.field_78116_c);
   }

   protected float func_205060_a(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
      float f = (p_205060_2_ - p_205060_1_) % ((float)Math.PI * 2F);
      if (f < -(float)Math.PI) {
         f += ((float)Math.PI * 2F);
      }

      if (f >= (float)Math.PI) {
         f -= ((float)Math.PI * 2F);
      }

      return p_205060_1_ + p_205060_3_ * f;
   }

   private float func_203068_a(float p_203068_1_) {
      return -65.0F * p_203068_1_ + p_203068_1_ * p_203068_1_;
   }

   public void func_217148_a(BipedModel<T> p_217148_1_) {
      super.func_217111_a(p_217148_1_);
      p_217148_1_.field_187075_l = this.field_187075_l;
      p_217148_1_.field_187076_m = this.field_187076_m;
      p_217148_1_.isSneak = this.isSneak;
   }

   public void setVisible(boolean visible) {
      this.field_78116_c.showModel = visible;
      this.field_178720_f.showModel = visible;
      this.field_78115_e.showModel = visible;
      this.field_178723_h.showModel = visible;
      this.field_178724_i.showModel = visible;
      this.field_178721_j.showModel = visible;
      this.field_178722_k.showModel = visible;
   }

   public void postRenderArm(float scale, HandSide side) {
      this.getArmForSide(side).postRender(scale);
   }

   protected RendererModel getArmForSide(HandSide side) {
      return side == HandSide.LEFT ? this.field_178724_i : this.field_178723_h;
   }

   public RendererModel func_205072_a() {
      return this.field_78116_c;
   }

   protected HandSide func_217147_a(T p_217147_1_) {
      HandSide handside = p_217147_1_.getPrimaryHand();
      return p_217147_1_.field_184622_au == Hand.MAIN_HAND ? handside : handside.opposite();
   }

   @OnlyIn(Dist.CLIENT)
   public static enum ArmPose {
      EMPTY,
      ITEM,
      BLOCK,
      BOW_AND_ARROW,
      THROW_SPEAR,
      CROSSBOW_CHARGE,
      CROSSBOW_HOLD;
   }
}