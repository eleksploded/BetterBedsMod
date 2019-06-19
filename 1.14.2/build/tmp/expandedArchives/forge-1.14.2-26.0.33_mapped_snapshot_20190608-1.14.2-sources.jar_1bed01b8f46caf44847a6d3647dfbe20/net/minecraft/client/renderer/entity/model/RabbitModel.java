package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RabbitModel<T extends RabbitEntity> extends EntityModel<T> {
   private final RendererModel field_178698_a = new RendererModel(this, 26, 24);
   private final RendererModel field_178696_b;
   private final RendererModel field_178697_c;
   private final RendererModel field_178694_d;
   private final RendererModel field_178695_e;
   private final RendererModel field_178692_f;
   private final RendererModel field_178693_g;
   private final RendererModel field_178704_h;
   private final RendererModel field_178705_i;
   private final RendererModel field_178702_j;
   private final RendererModel field_178703_k;
   private final RendererModel field_178700_l;
   private float jumpRotation;

   public RabbitModel() {
      this.field_178698_a.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.field_178698_a.setRotationPoint(3.0F, 17.5F, 3.7F);
      this.field_178698_a.mirror = true;
      this.setRotationOffset(this.field_178698_a, 0.0F, 0.0F, 0.0F);
      this.field_178696_b = new RendererModel(this, 8, 24);
      this.field_178696_b.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
      this.field_178696_b.setRotationPoint(-3.0F, 17.5F, 3.7F);
      this.field_178696_b.mirror = true;
      this.setRotationOffset(this.field_178696_b, 0.0F, 0.0F, 0.0F);
      this.field_178697_c = new RendererModel(this, 30, 15);
      this.field_178697_c.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.field_178697_c.setRotationPoint(3.0F, 17.5F, 3.7F);
      this.field_178697_c.mirror = true;
      this.setRotationOffset(this.field_178697_c, -0.34906584F, 0.0F, 0.0F);
      this.field_178694_d = new RendererModel(this, 16, 15);
      this.field_178694_d.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
      this.field_178694_d.setRotationPoint(-3.0F, 17.5F, 3.7F);
      this.field_178694_d.mirror = true;
      this.setRotationOffset(this.field_178694_d, -0.34906584F, 0.0F, 0.0F);
      this.field_178695_e = new RendererModel(this, 0, 0);
      this.field_178695_e.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
      this.field_178695_e.setRotationPoint(0.0F, 19.0F, 8.0F);
      this.field_178695_e.mirror = true;
      this.setRotationOffset(this.field_178695_e, -0.34906584F, 0.0F, 0.0F);
      this.field_178692_f = new RendererModel(this, 8, 15);
      this.field_178692_f.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.field_178692_f.setRotationPoint(3.0F, 17.0F, -1.0F);
      this.field_178692_f.mirror = true;
      this.setRotationOffset(this.field_178692_f, -0.17453292F, 0.0F, 0.0F);
      this.field_178693_g = new RendererModel(this, 0, 15);
      this.field_178693_g.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
      this.field_178693_g.setRotationPoint(-3.0F, 17.0F, -1.0F);
      this.field_178693_g.mirror = true;
      this.setRotationOffset(this.field_178693_g, -0.17453292F, 0.0F, 0.0F);
      this.field_178704_h = new RendererModel(this, 32, 0);
      this.field_178704_h.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
      this.field_178704_h.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.field_178704_h.mirror = true;
      this.setRotationOffset(this.field_178704_h, 0.0F, 0.0F, 0.0F);
      this.field_178705_i = new RendererModel(this, 52, 0);
      this.field_178705_i.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
      this.field_178705_i.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.field_178705_i.mirror = true;
      this.setRotationOffset(this.field_178705_i, 0.0F, -0.2617994F, 0.0F);
      this.field_178702_j = new RendererModel(this, 58, 0);
      this.field_178702_j.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
      this.field_178702_j.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.field_178702_j.mirror = true;
      this.setRotationOffset(this.field_178702_j, 0.0F, 0.2617994F, 0.0F);
      this.field_178703_k = new RendererModel(this, 52, 6);
      this.field_178703_k.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
      this.field_178703_k.setRotationPoint(0.0F, 20.0F, 7.0F);
      this.field_178703_k.mirror = true;
      this.setRotationOffset(this.field_178703_k, -0.3490659F, 0.0F, 0.0F);
      this.field_178700_l = new RendererModel(this, 32, 9);
      this.field_178700_l.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
      this.field_178700_l.setRotationPoint(0.0F, 16.0F, -1.0F);
      this.field_178700_l.mirror = true;
      this.setRotationOffset(this.field_178700_l, 0.0F, 0.0F, 0.0F);
   }

   private void setRotationOffset(RendererModel renderer, float x, float y, float z) {
      renderer.rotateAngleX = x;
      renderer.rotateAngleY = y;
      renderer.rotateAngleZ = z;
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.field_217114_e) {
         float f = 1.5F;
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.56666666F, 0.56666666F, 0.56666666F);
         GlStateManager.translatef(0.0F, 22.0F * scale, 2.0F * scale);
         this.field_178704_h.render(scale);
         this.field_178702_j.render(scale);
         this.field_178705_i.render(scale);
         this.field_178700_l.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.4F, 0.4F, 0.4F);
         GlStateManager.translatef(0.0F, 36.0F * scale, 0.0F);
         this.field_178698_a.render(scale);
         this.field_178696_b.render(scale);
         this.field_178697_c.render(scale);
         this.field_178694_d.render(scale);
         this.field_178695_e.render(scale);
         this.field_178692_f.render(scale);
         this.field_178693_g.render(scale);
         this.field_178703_k.render(scale);
         GlStateManager.popMatrix();
      } else {
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.6F, 0.6F, 0.6F);
         GlStateManager.translatef(0.0F, 16.0F * scale, 0.0F);
         this.field_178698_a.render(scale);
         this.field_178696_b.render(scale);
         this.field_178697_c.render(scale);
         this.field_178694_d.render(scale);
         this.field_178695_e.render(scale);
         this.field_178692_f.render(scale);
         this.field_178693_g.render(scale);
         this.field_178704_h.render(scale);
         this.field_178705_i.render(scale);
         this.field_178702_j.render(scale);
         this.field_178703_k.render(scale);
         this.field_178700_l.render(scale);
         GlStateManager.popMatrix();
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      float f = p_212844_4_ - (float)p_212844_1_.ticksExisted;
      this.field_178700_l.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_178704_h.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_178705_i.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_178702_j.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_178700_l.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_178704_h.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_178705_i.rotateAngleY = this.field_178700_l.rotateAngleY - 0.2617994F;
      this.field_178702_j.rotateAngleY = this.field_178700_l.rotateAngleY + 0.2617994F;
      this.jumpRotation = MathHelper.sin(p_212844_1_.getJumpCompletion(f) * (float)Math.PI);
      this.field_178697_c.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
      this.field_178694_d.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * ((float)Math.PI / 180F);
      this.field_178698_a.rotateAngleX = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
      this.field_178696_b.rotateAngleX = this.jumpRotation * 50.0F * ((float)Math.PI / 180F);
      this.field_178692_f.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
      this.field_178693_g.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * ((float)Math.PI / 180F);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      super.func_212843_a_(p_212843_1_, p_212843_2_, p_212843_3_, p_212843_4_);
      this.jumpRotation = MathHelper.sin(p_212843_1_.getJumpCompletion(p_212843_4_) * (float)Math.PI);
   }
}