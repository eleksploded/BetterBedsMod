package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IllagerModel<T extends AbstractIllagerEntity> extends EntityModel<T> implements IHasArm, IHasHead {
   protected final RendererModel field_191217_a;
   private final RendererModel field_193775_b;
   protected final RendererModel field_191218_b;
   protected final RendererModel field_191219_c;
   protected final RendererModel field_217143_g;
   protected final RendererModel field_217144_h;
   private final RendererModel field_191222_f;
   protected final RendererModel field_191223_g;
   protected final RendererModel field_191224_h;
   private float field_217145_m;

   public IllagerModel(float scaleFactor, float p_i47227_2_, int textureWidthIn, int textureHeightIn) {
      this.field_191217_a = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191217_a.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.field_191217_a.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scaleFactor);
      this.field_193775_b = (new RendererModel(this, 32, 0)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_193775_b.addBox(-4.0F, -10.0F, -4.0F, 8, 12, 8, scaleFactor + 0.45F);
      this.field_191217_a.addChild(this.field_193775_b);
      this.field_193775_b.showModel = false;
      this.field_191222_f = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191222_f.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
      this.field_191222_f.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scaleFactor);
      this.field_191217_a.addChild(this.field_191222_f);
      this.field_191218_b = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191218_b.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
      this.field_191218_b.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scaleFactor);
      this.field_191218_b.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scaleFactor + 0.5F);
      this.field_191219_c = (new RendererModel(this)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191219_c.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
      this.field_191219_c.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
      RendererModel renderermodel = (new RendererModel(this, 44, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      renderermodel.mirror = true;
      renderermodel.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, scaleFactor);
      this.field_191219_c.addChild(renderermodel);
      this.field_191219_c.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scaleFactor);
      this.field_217143_g = (new RendererModel(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_217143_g.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_217143_g.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
      this.field_217144_h = (new RendererModel(this, 0, 22)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_217144_h.mirror = true;
      this.field_217144_h.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
      this.field_217144_h.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scaleFactor);
      this.field_191223_g = (new RendererModel(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191223_g.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
      this.field_191223_g.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
      this.field_191224_h = (new RendererModel(this, 40, 46)).setTextureSize(textureWidthIn, textureHeightIn);
      this.field_191224_h.mirror = true;
      this.field_191224_h.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, scaleFactor);
      this.field_191224_h.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_191217_a.render(scale);
      this.field_191218_b.render(scale);
      this.field_217143_g.render(scale);
      this.field_217144_h.render(scale);
      if (entityIn.getArmPose() == AbstractIllagerEntity.ArmPose.CROSSED) {
         this.field_191219_c.render(scale);
      } else {
         this.field_191223_g.render(scale);
         this.field_191224_h.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      this.field_191217_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_191217_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_191219_c.rotationPointY = 3.0F;
      this.field_191219_c.rotationPointZ = -1.0F;
      this.field_191219_c.rotateAngleX = -0.75F;
      if (this.field_217113_d) {
         this.field_191223_g.rotateAngleX = (-(float)Math.PI / 5F);
         this.field_191223_g.rotateAngleY = 0.0F;
         this.field_191223_g.rotateAngleZ = 0.0F;
         this.field_191224_h.rotateAngleX = (-(float)Math.PI / 5F);
         this.field_191224_h.rotateAngleY = 0.0F;
         this.field_191224_h.rotateAngleZ = 0.0F;
         this.field_217143_g.rotateAngleX = -1.4137167F;
         this.field_217143_g.rotateAngleY = ((float)Math.PI / 10F);
         this.field_217143_g.rotateAngleZ = 0.07853982F;
         this.field_217144_h.rotateAngleX = -1.4137167F;
         this.field_217144_h.rotateAngleY = (-(float)Math.PI / 10F);
         this.field_217144_h.rotateAngleZ = -0.07853982F;
      } else {
         this.field_191223_g.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 2.0F * p_212844_3_ * 0.5F;
         this.field_191223_g.rotateAngleY = 0.0F;
         this.field_191223_g.rotateAngleZ = 0.0F;
         this.field_191224_h.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 2.0F * p_212844_3_ * 0.5F;
         this.field_191224_h.rotateAngleY = 0.0F;
         this.field_191224_h.rotateAngleZ = 0.0F;
         this.field_217143_g.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_ * 0.5F;
         this.field_217143_g.rotateAngleY = 0.0F;
         this.field_217143_g.rotateAngleZ = 0.0F;
         this.field_217144_h.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_ * 0.5F;
         this.field_217144_h.rotateAngleY = 0.0F;
         this.field_217144_h.rotateAngleZ = 0.0F;
      }

      AbstractIllagerEntity.ArmPose abstractillagerentity$armpose = p_212844_1_.getArmPose();
      if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.ATTACKING) {
         float f = MathHelper.sin(this.field_217112_c * (float)Math.PI);
         float f1 = MathHelper.sin((1.0F - (1.0F - this.field_217112_c) * (1.0F - this.field_217112_c)) * (float)Math.PI);
         this.field_191223_g.rotateAngleZ = 0.0F;
         this.field_191224_h.rotateAngleZ = 0.0F;
         this.field_191223_g.rotateAngleY = 0.15707964F;
         this.field_191224_h.rotateAngleY = -0.15707964F;
         if (p_212844_1_.getPrimaryHand() == HandSide.RIGHT) {
            this.field_191223_g.rotateAngleX = -1.8849558F + MathHelper.cos(p_212844_4_ * 0.09F) * 0.15F;
            this.field_191224_h.rotateAngleX = -0.0F + MathHelper.cos(p_212844_4_ * 0.19F) * 0.5F;
            this.field_191223_g.rotateAngleX += f * 2.2F - f1 * 0.4F;
            this.field_191224_h.rotateAngleX += f * 1.2F - f1 * 0.4F;
         } else {
            this.field_191223_g.rotateAngleX = -0.0F + MathHelper.cos(p_212844_4_ * 0.19F) * 0.5F;
            this.field_191224_h.rotateAngleX = -1.8849558F + MathHelper.cos(p_212844_4_ * 0.09F) * 0.15F;
            this.field_191223_g.rotateAngleX += f * 1.2F - f1 * 0.4F;
            this.field_191224_h.rotateAngleX += f * 2.2F - f1 * 0.4F;
         }

         this.field_191223_g.rotateAngleZ += MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
         this.field_191224_h.rotateAngleZ -= MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
         this.field_191223_g.rotateAngleX += MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
         this.field_191224_h.rotateAngleX -= MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.SPELLCASTING) {
         this.field_191223_g.rotationPointZ = 0.0F;
         this.field_191223_g.rotationPointX = -5.0F;
         this.field_191224_h.rotationPointZ = 0.0F;
         this.field_191224_h.rotationPointX = 5.0F;
         this.field_191223_g.rotateAngleX = MathHelper.cos(p_212844_4_ * 0.6662F) * 0.25F;
         this.field_191224_h.rotateAngleX = MathHelper.cos(p_212844_4_ * 0.6662F) * 0.25F;
         this.field_191223_g.rotateAngleZ = 2.3561945F;
         this.field_191224_h.rotateAngleZ = -2.3561945F;
         this.field_191223_g.rotateAngleY = 0.0F;
         this.field_191224_h.rotateAngleY = 0.0F;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.BOW_AND_ARROW) {
         this.field_191223_g.rotateAngleY = -0.1F + this.field_191217_a.rotateAngleY;
         this.field_191223_g.rotateAngleX = (-(float)Math.PI / 2F) + this.field_191217_a.rotateAngleX;
         this.field_191224_h.rotateAngleX = -0.9424779F + this.field_191217_a.rotateAngleX;
         this.field_191224_h.rotateAngleY = this.field_191217_a.rotateAngleY - 0.4F;
         this.field_191224_h.rotateAngleZ = ((float)Math.PI / 2F);
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD) {
         this.field_191223_g.rotateAngleY = -0.3F + this.field_191217_a.rotateAngleY;
         this.field_191224_h.rotateAngleY = 0.6F + this.field_191217_a.rotateAngleY;
         this.field_191223_g.rotateAngleX = (-(float)Math.PI / 2F) + this.field_191217_a.rotateAngleX + 0.1F;
         this.field_191224_h.rotateAngleX = -1.5F + this.field_191217_a.rotateAngleX;
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE) {
         this.field_191223_g.rotateAngleY = -0.8F;
         this.field_191223_g.rotateAngleX = -0.97079635F;
         this.field_191224_h.rotateAngleX = -0.97079635F;
         float f2 = MathHelper.clamp(this.field_217145_m, 0.0F, 25.0F);
         this.field_191224_h.rotateAngleY = MathHelper.func_219799_g(f2 / 25.0F, 0.4F, 0.85F);
         this.field_191224_h.rotateAngleX = MathHelper.func_219799_g(f2 / 25.0F, this.field_191224_h.rotateAngleX, (-(float)Math.PI / 2F));
      } else if (abstractillagerentity$armpose == AbstractIllagerEntity.ArmPose.CELEBRATING) {
         this.field_191223_g.rotationPointZ = 0.0F;
         this.field_191223_g.rotationPointX = -5.0F;
         this.field_191223_g.rotateAngleX = MathHelper.cos(p_212844_4_ * 0.6662F) * 0.05F;
         this.field_191223_g.rotateAngleZ = 2.670354F;
         this.field_191223_g.rotateAngleY = 0.0F;
         this.field_191224_h.rotationPointZ = 0.0F;
         this.field_191224_h.rotationPointX = 5.0F;
         this.field_191224_h.rotateAngleX = MathHelper.cos(p_212844_4_ * 0.6662F) * 0.05F;
         this.field_191224_h.rotateAngleZ = -2.3561945F;
         this.field_191224_h.rotateAngleY = 0.0F;
      }

   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_217145_m = (float)p_212843_1_.getItemInUseMaxCount();
      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
   }

   private RendererModel getArm(HandSide p_191216_1_) {
      return p_191216_1_ == HandSide.LEFT ? this.field_191224_h : this.field_191223_g;
   }

   public RendererModel func_205062_a() {
      return this.field_193775_b;
   }

   public RendererModel func_205072_a() {
      return this.field_191217_a;
   }

   public void postRenderArm(float scale, HandSide side) {
      this.getArm(side).postRender(0.0625F);
   }
}