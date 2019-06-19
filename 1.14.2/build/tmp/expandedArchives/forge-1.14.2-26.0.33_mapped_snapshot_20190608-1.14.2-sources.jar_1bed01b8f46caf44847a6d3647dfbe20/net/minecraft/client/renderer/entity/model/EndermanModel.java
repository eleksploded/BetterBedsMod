package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermanModel<T extends LivingEntity> extends BipedModel<T> {
   public boolean isCarrying;
   public boolean isAttacking;

   public EndermanModel(float scale) {
      super(0.0F, -14.0F, 64, 32);
      float f = -14.0F;
      this.field_178720_f = new RendererModel(this, 0, 16);
      this.field_178720_f.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, scale - 0.5F);
      this.field_178720_f.setRotationPoint(0.0F, -14.0F, 0.0F);
      this.field_78115_e = new RendererModel(this, 32, 16);
      this.field_78115_e.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, scale);
      this.field_78115_e.setRotationPoint(0.0F, -14.0F, 0.0F);
      this.field_178723_h = new RendererModel(this, 56, 0);
      this.field_178723_h.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, scale);
      this.field_178723_h.setRotationPoint(-3.0F, -12.0F, 0.0F);
      this.field_178724_i = new RendererModel(this, 56, 0);
      this.field_178724_i.mirror = true;
      this.field_178724_i.addBox(-1.0F, -2.0F, -1.0F, 2, 30, 2, scale);
      this.field_178724_i.setRotationPoint(5.0F, -12.0F, 0.0F);
      this.field_178721_j = new RendererModel(this, 56, 0);
      this.field_178721_j.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, scale);
      this.field_178721_j.setRotationPoint(-2.0F, -2.0F, 0.0F);
      this.field_178722_k = new RendererModel(this, 56, 0);
      this.field_178722_k.mirror = true;
      this.field_178722_k.addBox(-1.0F, 0.0F, -1.0F, 2, 30, 2, scale);
      this.field_178722_k.setRotationPoint(2.0F, -2.0F, 0.0F);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_78116_c.showModel = true;
      float f = -14.0F;
      this.field_78115_e.rotateAngleX = 0.0F;
      this.field_78115_e.rotationPointY = -14.0F;
      this.field_78115_e.rotationPointZ = -0.0F;
      this.field_178721_j.rotateAngleX -= 0.0F;
      this.field_178722_k.rotateAngleX -= 0.0F;
      this.field_178723_h.rotateAngleX = (float)((double)this.field_178723_h.rotateAngleX * 0.5D);
      this.field_178724_i.rotateAngleX = (float)((double)this.field_178724_i.rotateAngleX * 0.5D);
      this.field_178721_j.rotateAngleX = (float)((double)this.field_178721_j.rotateAngleX * 0.5D);
      this.field_178722_k.rotateAngleX = (float)((double)this.field_178722_k.rotateAngleX * 0.5D);
      float f1 = 0.4F;
      if (this.field_178723_h.rotateAngleX > 0.4F) {
         this.field_178723_h.rotateAngleX = 0.4F;
      }

      if (this.field_178724_i.rotateAngleX > 0.4F) {
         this.field_178724_i.rotateAngleX = 0.4F;
      }

      if (this.field_178723_h.rotateAngleX < -0.4F) {
         this.field_178723_h.rotateAngleX = -0.4F;
      }

      if (this.field_178724_i.rotateAngleX < -0.4F) {
         this.field_178724_i.rotateAngleX = -0.4F;
      }

      if (this.field_178721_j.rotateAngleX > 0.4F) {
         this.field_178721_j.rotateAngleX = 0.4F;
      }

      if (this.field_178722_k.rotateAngleX > 0.4F) {
         this.field_178722_k.rotateAngleX = 0.4F;
      }

      if (this.field_178721_j.rotateAngleX < -0.4F) {
         this.field_178721_j.rotateAngleX = -0.4F;
      }

      if (this.field_178722_k.rotateAngleX < -0.4F) {
         this.field_178722_k.rotateAngleX = -0.4F;
      }

      if (this.isCarrying) {
         this.field_178723_h.rotateAngleX = -0.5F;
         this.field_178724_i.rotateAngleX = -0.5F;
         this.field_178723_h.rotateAngleZ = 0.05F;
         this.field_178724_i.rotateAngleZ = -0.05F;
      }

      this.field_178723_h.rotationPointZ = 0.0F;
      this.field_178724_i.rotationPointZ = 0.0F;
      this.field_178721_j.rotationPointZ = 0.0F;
      this.field_178722_k.rotationPointZ = 0.0F;
      this.field_178721_j.rotationPointY = -5.0F;
      this.field_178722_k.rotationPointY = -5.0F;
      this.field_78116_c.rotationPointZ = -0.0F;
      this.field_78116_c.rotationPointY = -13.0F;
      this.field_178720_f.rotationPointX = this.field_78116_c.rotationPointX;
      this.field_178720_f.rotationPointY = this.field_78116_c.rotationPointY;
      this.field_178720_f.rotationPointZ = this.field_78116_c.rotationPointZ;
      this.field_178720_f.rotateAngleX = this.field_78116_c.rotateAngleX;
      this.field_178720_f.rotateAngleY = this.field_78116_c.rotateAngleY;
      this.field_178720_f.rotateAngleZ = this.field_78116_c.rotateAngleZ;
      if (this.isAttacking) {
         float f2 = 1.0F;
         this.field_78116_c.rotationPointY -= 5.0F;
      }

   }
}