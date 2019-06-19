package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.VexEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VexModel extends BipedModel<VexEntity> {
   private final RendererModel field_191229_a;
   private final RendererModel field_191230_b;

   public VexModel() {
      this(0.0F);
   }

   public VexModel(float p_i47224_1_) {
      super(p_i47224_1_, 0.0F, 64, 64);
      this.field_178722_k.showModel = false;
      this.field_178720_f.showModel = false;
      this.field_178721_j = new RendererModel(this, 32, 0);
      this.field_178721_j.addBox(-1.0F, -1.0F, -2.0F, 6, 10, 4, 0.0F);
      this.field_178721_j.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.field_191230_b = new RendererModel(this, 0, 32);
      this.field_191230_b.addBox(-20.0F, 0.0F, 0.0F, 20, 12, 1);
      this.field_191229_a = new RendererModel(this, 0, 32);
      this.field_191229_a.mirror = true;
      this.field_191229_a.addBox(0.0F, 0.0F, 0.0F, 20, 12, 1);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(VexEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_191230_b.render(scale);
      this.field_191229_a.render(scale);
   }

   public void func_212844_a_(VexEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      if (p_212844_1_.isCharging()) {
         if (p_212844_1_.getPrimaryHand() == HandSide.RIGHT) {
            this.field_178723_h.rotateAngleX = 3.7699115F;
         } else {
            this.field_178724_i.rotateAngleX = 3.7699115F;
         }
      }

      this.field_178721_j.rotateAngleX += ((float)Math.PI / 5F);
      this.field_191230_b.rotationPointZ = 2.0F;
      this.field_191229_a.rotationPointZ = 2.0F;
      this.field_191230_b.rotationPointY = 1.0F;
      this.field_191229_a.rotationPointY = 1.0F;
      this.field_191230_b.rotateAngleY = 0.47123894F + MathHelper.cos(p_212844_4_ * 0.8F) * (float)Math.PI * 0.05F;
      this.field_191229_a.rotateAngleY = -this.field_191230_b.rotateAngleY;
      this.field_191229_a.rotateAngleZ = -0.47123894F;
      this.field_191229_a.rotateAngleX = 0.47123894F;
      this.field_191230_b.rotateAngleX = 0.47123894F;
      this.field_191230_b.rotateAngleZ = 0.47123894F;
   }
}