package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkeletonModel<T extends MobEntity & IRangedAttackMob> extends BipedModel<T> {
   public SkeletonModel() {
      this(0.0F, false);
   }

   public SkeletonModel(float modelSize, boolean p_i46303_2_) {
      super(modelSize, 0.0F, 64, 32);
      if (!p_i46303_2_) {
         this.field_178723_h = new RendererModel(this, 40, 16);
         this.field_178723_h.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
         this.field_178723_h.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.field_178724_i = new RendererModel(this, 40, 16);
         this.field_178724_i.mirror = true;
         this.field_178724_i.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
         this.field_178724_i.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178721_j = new RendererModel(this, 0, 16);
         this.field_178721_j.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
         this.field_178721_j.setRotationPoint(-2.0F, 12.0F, 0.0F);
         this.field_178722_k = new RendererModel(this, 0, 16);
         this.field_178722_k.mirror = true;
         this.field_178722_k.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
         this.field_178722_k.setRotationPoint(2.0F, 12.0F, 0.0F);
      }

   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_187076_m = BipedModel.ArmPose.EMPTY;
      this.field_187075_l = BipedModel.ArmPose.EMPTY;
      ItemStack itemstack = p_212843_1_.getHeldItem(Hand.MAIN_HAND);
      if (itemstack.getItem() instanceof net.minecraft.item.BowItem && p_212843_1_.func_213398_dR()) {
         if (p_212843_1_.getPrimaryHand() == HandSide.RIGHT) {
            this.field_187076_m = BipedModel.ArmPose.BOW_AND_ARROW;
         } else {
            this.field_187075_l = BipedModel.ArmPose.BOW_AND_ARROW;
         }
      }

      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      ItemStack itemstack = p_212844_1_.getHeldItemMainhand();
      if (p_212844_1_.func_213398_dR() && (itemstack.isEmpty() || !(itemstack.getItem() instanceof net.minecraft.item.BowItem))) {
         float f = MathHelper.sin(this.field_217112_c * (float)Math.PI);
         float f1 = MathHelper.sin((1.0F - (1.0F - this.field_217112_c) * (1.0F - this.field_217112_c)) * (float)Math.PI);
         this.field_178723_h.rotateAngleZ = 0.0F;
         this.field_178724_i.rotateAngleZ = 0.0F;
         this.field_178723_h.rotateAngleY = -(0.1F - f * 0.6F);
         this.field_178724_i.rotateAngleY = 0.1F - f * 0.6F;
         this.field_178723_h.rotateAngleX = (-(float)Math.PI / 2F);
         this.field_178724_i.rotateAngleX = (-(float)Math.PI / 2F);
         this.field_178723_h.rotateAngleX -= f * 1.2F - f1 * 0.4F;
         this.field_178724_i.rotateAngleX -= f * 1.2F - f1 * 0.4F;
         this.field_178723_h.rotateAngleZ += MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
         this.field_178724_i.rotateAngleZ -= MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
         this.field_178723_h.rotateAngleX += MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
         this.field_178724_i.rotateAngleX -= MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      }

   }

   public void postRenderArm(float scale, HandSide side) {
      float f = side == HandSide.RIGHT ? 1.0F : -1.0F;
      RendererModel renderermodel = this.getArmForSide(side);
      renderermodel.rotationPointX += f;
      renderermodel.postRender(scale);
      renderermodel.rotationPointX -= f;
   }
}