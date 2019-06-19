package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VillagerModel<T extends Entity> extends EntityModel<T> implements IHasHead, IHeadToggle {
   protected final RendererModel field_78191_a;
   protected RendererModel field_217151_b;
   protected final RendererModel field_217152_f;
   protected final RendererModel field_78189_b;
   protected final RendererModel field_217153_h;
   protected final RendererModel field_78190_c;
   protected final RendererModel field_78187_d;
   protected final RendererModel field_78188_e;
   protected final RendererModel field_82898_f;

   public VillagerModel(float scale) {
      this(scale, 64, 64);
   }

   public VillagerModel(float p_i51059_1_, int p_i51059_2_, int p_i51059_3_) {
      float f = 0.5F;
      this.field_78191_a = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_78191_a.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_78191_a.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_);
      this.field_217151_b = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_217151_b.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_217151_b.setTextureOffset(32, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51059_1_ + 0.5F);
      this.field_78191_a.addChild(this.field_217151_b);
      this.field_217152_f = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_217152_f.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_217152_f.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16, 16, 1, p_i51059_1_);
      this.field_217152_f.rotateAngleX = (-(float)Math.PI / 2F);
      this.field_217151_b.addChild(this.field_217152_f);
      this.field_82898_f = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_82898_f.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.field_82898_f.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i51059_1_);
      this.field_78191_a.addChild(this.field_82898_f);
      this.field_78189_b = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_78189_b.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_78189_b.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i51059_1_);
      this.field_217153_h = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_217153_h.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_217153_h.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i51059_1_ + 0.5F);
      this.field_78189_b.addChild(this.field_217153_h);
      this.field_78190_c = (new RendererModel(this)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_78190_c.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.field_78190_c.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_);
      this.field_78190_c.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i51059_1_, true);
      this.field_78190_c.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i51059_1_);
      this.field_78187_d = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_78187_d.setRotationPoint(-2.0F, 12.0F, 0.0F);
      this.field_78187_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
      this.field_78188_e = (new RendererModel(this, 0, 22)).setTextureSize(p_i51059_2_, p_i51059_3_);
      this.field_78188_e.mirror = true;
      this.field_78188_e.setRotationPoint(2.0F, 12.0F, 0.0F);
      this.field_78188_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51059_1_);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_78191_a.render(scale);
      this.field_78189_b.render(scale);
      this.field_78187_d.render(scale);
      this.field_78188_e.render(scale);
      this.field_78190_c.render(scale);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      boolean flag = false;
      if (p_212844_1_ instanceof AbstractVillagerEntity) {
         flag = ((AbstractVillagerEntity)p_212844_1_).getShakeHeadTicks() > 0;
      }

      this.field_78191_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_78191_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      if (flag) {
         this.field_78191_a.rotateAngleZ = 0.3F * MathHelper.sin(0.45F * p_212844_4_);
         this.field_78191_a.rotateAngleX = 0.4F;
      } else {
         this.field_78191_a.rotateAngleZ = 0.0F;
      }

      this.field_78190_c.rotationPointY = 3.0F;
      this.field_78190_c.rotationPointZ = -1.0F;
      this.field_78190_c.rotateAngleX = -0.75F;
      this.field_78187_d.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F) * 1.4F * p_212844_3_ * 0.5F;
      this.field_78188_e.rotateAngleX = MathHelper.cos(p_212844_2_ * 0.6662F + (float)Math.PI) * 1.4F * p_212844_3_ * 0.5F;
      this.field_78187_d.rotateAngleY = 0.0F;
      this.field_78188_e.rotateAngleY = 0.0F;
   }

   public RendererModel func_205072_a() {
      return this.field_78191_a;
   }

   public void func_217146_a(boolean p_217146_1_) {
      this.field_78191_a.showModel = p_217146_1_;
      this.field_217151_b.showModel = p_217146_1_;
      this.field_217152_f.showModel = p_217146_1_;
   }
}