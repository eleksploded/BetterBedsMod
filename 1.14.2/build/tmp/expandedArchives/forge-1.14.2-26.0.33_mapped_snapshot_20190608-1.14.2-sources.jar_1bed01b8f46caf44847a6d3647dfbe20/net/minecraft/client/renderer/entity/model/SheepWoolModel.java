package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.passive.SheepEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepWoolModel<T extends SheepEntity> extends QuadrupedModel<T> {
   private float headRotationAngleX;

   public SheepWoolModel() {
      super(12, 0.0F);
      this.field_78150_a = new RendererModel(this, 0, 0);
      this.field_78150_a.addBox(-3.0F, -4.0F, -4.0F, 6, 6, 6, 0.6F);
      this.field_78150_a.setRotationPoint(0.0F, 6.0F, -8.0F);
      this.field_78148_b = new RendererModel(this, 28, 8);
      this.field_78148_b.addBox(-4.0F, -10.0F, -7.0F, 8, 16, 6, 1.75F);
      this.field_78148_b.setRotationPoint(0.0F, 5.0F, 2.0F);
      float f = 0.5F;
      this.field_78149_c = new RendererModel(this, 0, 16);
      this.field_78149_c.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78149_c.setRotationPoint(-3.0F, 12.0F, 7.0F);
      this.field_78146_d = new RendererModel(this, 0, 16);
      this.field_78146_d.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78146_d.setRotationPoint(3.0F, 12.0F, 7.0F);
      this.field_78147_e = new RendererModel(this, 0, 16);
      this.field_78147_e.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78147_e.setRotationPoint(-3.0F, 12.0F, -5.0F);
      this.field_78144_f = new RendererModel(this, 0, 16);
      this.field_78144_f.addBox(-2.0F, 0.0F, -2.0F, 4, 6, 4, 0.5F);
      this.field_78144_f.setRotationPoint(3.0F, 12.0F, -5.0F);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
      this.field_78150_a.rotationPointY = 6.0F + p_212843_1_.getHeadRotationPointY(p_212843_4_) * 9.0F;
      this.headRotationAngleX = p_212843_1_.getHeadRotationAngleX(p_212843_4_);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_78150_a.rotateAngleX = this.headRotationAngleX;
   }
}