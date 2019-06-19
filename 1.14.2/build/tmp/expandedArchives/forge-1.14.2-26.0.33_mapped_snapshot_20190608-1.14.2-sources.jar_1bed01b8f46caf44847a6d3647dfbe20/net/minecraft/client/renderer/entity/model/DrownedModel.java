package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DrownedModel<T extends ZombieEntity> extends ZombieModel<T> {
   public DrownedModel(float p_i48915_1_, float p_i48915_2_, int p_i48915_3_, int p_i48915_4_) {
      super(p_i48915_1_, p_i48915_2_, p_i48915_3_, p_i48915_4_);
      this.field_178723_h = new RendererModel(this, 32, 48);
      this.field_178723_h.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i48915_1_);
      this.field_178723_h.setRotationPoint(-5.0F, 2.0F + p_i48915_2_, 0.0F);
      this.field_178721_j = new RendererModel(this, 16, 48);
      this.field_178721_j.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i48915_1_);
      this.field_178721_j.setRotationPoint(-1.9F, 12.0F + p_i48915_2_, 0.0F);
   }

   public DrownedModel(float p_i49398_1_, boolean p_i49398_2_) {
      super(p_i49398_1_, 0.0F, 64, p_i49398_2_ ? 32 : 64);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_187076_m = BipedModel.ArmPose.EMPTY;
      this.field_187075_l = BipedModel.ArmPose.EMPTY;
      ItemStack itemstack = p_212843_1_.getHeldItem(Hand.MAIN_HAND);
      if (itemstack.getItem() == Items.TRIDENT && p_212843_1_.func_213398_dR()) {
         if (p_212843_1_.getPrimaryHand() == HandSide.RIGHT) {
            this.field_187076_m = BipedModel.ArmPose.THROW_SPEAR;
         } else {
            this.field_187075_l = BipedModel.ArmPose.THROW_SPEAR;
         }
      }

      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      if (this.field_187075_l == BipedModel.ArmPose.THROW_SPEAR) {
         this.field_178724_i.rotateAngleX = this.field_178724_i.rotateAngleX * 0.5F - (float)Math.PI;
         this.field_178724_i.rotateAngleY = 0.0F;
      }

      if (this.field_187076_m == BipedModel.ArmPose.THROW_SPEAR) {
         this.field_178723_h.rotateAngleX = this.field_178723_h.rotateAngleX * 0.5F - (float)Math.PI;
         this.field_178723_h.rotateAngleY = 0.0F;
      }

      if (this.field_205061_a > 0.0F) {
         this.field_178723_h.rotateAngleX = this.func_205060_a(this.field_178723_h.rotateAngleX, -2.5132742F, this.field_205061_a) + this.field_205061_a * 0.35F * MathHelper.sin(0.1F * p_212844_4_);
         this.field_178724_i.rotateAngleX = this.func_205060_a(this.field_178724_i.rotateAngleX, -2.5132742F, this.field_205061_a) - this.field_205061_a * 0.35F * MathHelper.sin(0.1F * p_212844_4_);
         this.field_178723_h.rotateAngleZ = this.func_205060_a(this.field_178723_h.rotateAngleZ, -0.15F, this.field_205061_a);
         this.field_178724_i.rotateAngleZ = this.func_205060_a(this.field_178724_i.rotateAngleZ, 0.15F, this.field_205061_a);
         this.field_178722_k.rotateAngleX -= this.field_205061_a * 0.55F * MathHelper.sin(0.1F * p_212844_4_);
         this.field_178721_j.rotateAngleX += this.field_205061_a * 0.55F * MathHelper.sin(0.1F * p_212844_4_);
         this.field_78116_c.rotateAngleX = 0.0F;
      }

   }
}