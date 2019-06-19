package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.passive.CatEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatModel<T extends CatEntity> extends OcelotModel<T> {
   private float field_217155_m;
   private float field_217156_n;
   private float field_217157_o;

   public CatModel(float p_i51069_1_) {
      super(p_i51069_1_);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      this.field_217155_m = p_212843_1_.func_213408_v(p_212843_4_);
      this.field_217156_n = p_212843_1_.func_213421_w(p_212843_4_);
      this.field_217157_o = p_212843_1_.func_213424_x(p_212843_4_);
      if (this.field_217155_m <= 0.0F) {
         this.field_78156_g.rotateAngleX = 0.0F;
         this.field_78156_g.rotateAngleZ = 0.0F;
         this.field_78160_c.rotateAngleX = 0.0F;
         this.field_78160_c.rotateAngleZ = 0.0F;
         this.field_78157_d.rotateAngleX = 0.0F;
         this.field_78157_d.rotateAngleZ = 0.0F;
         this.field_78157_d.rotationPointX = -1.2F;
         this.field_78161_a.rotateAngleX = 0.0F;
         this.field_78159_b.rotateAngleX = 0.0F;
         this.field_78159_b.rotateAngleZ = 0.0F;
         this.field_78159_b.rotationPointX = -1.1F;
         this.field_78159_b.rotationPointY = 18.0F;
      }

      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
      if (p_212843_1_.isSitting()) {
         this.field_78162_h.rotateAngleX = ((float)Math.PI / 4F);
         this.field_78162_h.rotationPointY += -4.0F;
         this.field_78162_h.rotationPointZ += 5.0F;
         this.field_78156_g.rotationPointY += -3.3F;
         ++this.field_78156_g.rotationPointZ;
         this.field_78158_e.rotationPointY += 8.0F;
         this.field_78158_e.rotationPointZ += -2.0F;
         this.field_78155_f.rotationPointY += 2.0F;
         this.field_78155_f.rotationPointZ += -0.8F;
         this.field_78158_e.rotateAngleX = 1.7278761F;
         this.field_78155_f.rotateAngleX = 2.670354F;
         this.field_78160_c.rotateAngleX = -0.15707964F;
         this.field_78160_c.rotationPointY = 15.8F;
         this.field_78160_c.rotationPointZ = -7.0F;
         this.field_78157_d.rotateAngleX = -0.15707964F;
         this.field_78157_d.rotationPointY = 15.8F;
         this.field_78157_d.rotationPointZ = -7.0F;
         this.field_78161_a.rotateAngleX = (-(float)Math.PI / 2F);
         this.field_78161_a.rotationPointY = 21.0F;
         this.field_78161_a.rotationPointZ = 1.0F;
         this.field_78159_b.rotateAngleX = (-(float)Math.PI / 2F);
         this.field_78159_b.rotationPointY = 21.0F;
         this.field_78159_b.rotationPointZ = 1.0F;
         this.state = 3;
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      if (this.field_217155_m > 0.0F) {
         this.field_78156_g.rotateAngleZ = this.func_217154_a(this.field_78156_g.rotateAngleZ, -1.2707963F, this.field_217155_m);
         this.field_78156_g.rotateAngleY = this.func_217154_a(this.field_78156_g.rotateAngleY, 1.2707963F, this.field_217155_m);
         this.field_78160_c.rotateAngleX = -1.2707963F;
         this.field_78157_d.rotateAngleX = -0.47079635F;
         this.field_78157_d.rotateAngleZ = -0.2F;
         this.field_78157_d.rotationPointX = -0.2F;
         this.field_78161_a.rotateAngleX = -0.4F;
         this.field_78159_b.rotateAngleX = 0.5F;
         this.field_78159_b.rotateAngleZ = -0.5F;
         this.field_78159_b.rotationPointX = -0.3F;
         this.field_78159_b.rotationPointY = 20.0F;
         this.field_78158_e.rotateAngleX = this.func_217154_a(this.field_78158_e.rotateAngleX, 0.8F, this.field_217156_n);
         this.field_78155_f.rotateAngleX = this.func_217154_a(this.field_78155_f.rotateAngleX, -0.4F, this.field_217156_n);
      }

      if (this.field_217157_o > 0.0F) {
         this.field_78156_g.rotateAngleX = this.func_217154_a(this.field_78156_g.rotateAngleX, -0.58177644F, this.field_217157_o);
      }

   }

   protected float func_217154_a(float p_217154_1_, float p_217154_2_, float p_217154_3_) {
      float f;
      for(f = p_217154_2_ - p_217154_1_; f < -(float)Math.PI; f += ((float)Math.PI * 2F)) {
         ;
      }

      while(f >= (float)Math.PI) {
         f -= ((float)Math.PI * 2F);
      }

      return p_217154_1_ + p_217154_3_ * f;
   }
}