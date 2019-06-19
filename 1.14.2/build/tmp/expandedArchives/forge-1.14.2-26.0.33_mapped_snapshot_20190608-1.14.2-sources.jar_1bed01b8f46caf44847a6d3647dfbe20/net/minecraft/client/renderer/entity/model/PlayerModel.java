package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PlayerModel<T extends LivingEntity> extends BipedModel<T> {
   public final RendererModel field_178734_a;
   public final RendererModel field_178732_b;
   public final RendererModel field_178733_c;
   public final RendererModel field_178731_d;
   public final RendererModel field_178730_v;
   private final RendererModel field_178729_w;
   private final RendererModel field_178736_x;
   private final boolean smallArms;

   public PlayerModel(float modelSize, boolean smallArmsIn) {
      super(modelSize, 0.0F, 64, 64);
      this.smallArms = smallArmsIn;
      this.field_178736_x = new RendererModel(this, 24, 0);
      this.field_178736_x.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
      this.field_178729_w = new RendererModel(this, 0, 0);
      this.field_178729_w.setTextureSize(64, 32);
      this.field_178729_w.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
      if (smallArmsIn) {
         this.field_178724_i = new RendererModel(this, 32, 48);
         this.field_178724_i.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
         this.field_178724_i.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.field_178723_h = new RendererModel(this, 40, 16);
         this.field_178723_h.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
         this.field_178723_h.setRotationPoint(-5.0F, 2.5F, 0.0F);
         this.field_178734_a = new RendererModel(this, 48, 48);
         this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
         this.field_178734_a.setRotationPoint(5.0F, 2.5F, 0.0F);
         this.field_178732_b = new RendererModel(this, 40, 32);
         this.field_178732_b.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
         this.field_178732_b.setRotationPoint(-5.0F, 2.5F, 10.0F);
      } else {
         this.field_178724_i = new RendererModel(this, 32, 48);
         this.field_178724_i.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
         this.field_178724_i.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178734_a = new RendererModel(this, 48, 48);
         this.field_178734_a.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
         this.field_178734_a.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178732_b = new RendererModel(this, 40, 32);
         this.field_178732_b.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
         this.field_178732_b.setRotationPoint(-5.0F, 2.0F, 10.0F);
      }

      this.field_178722_k = new RendererModel(this, 16, 48);
      this.field_178722_k.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
      this.field_178722_k.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178733_c = new RendererModel(this, 0, 48);
      this.field_178733_c.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
      this.field_178733_c.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178731_d = new RendererModel(this, 0, 32);
      this.field_178731_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
      this.field_178731_d.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.field_178730_v = new RendererModel(this, 16, 32);
      this.field_178730_v.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
      this.field_178730_v.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      GlStateManager.pushMatrix();
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_178733_c.render(scale);
         this.field_178731_d.render(scale);
         this.field_178734_a.render(scale);
         this.field_178732_b.render(scale);
         this.field_178730_v.render(scale);
      } else {
         if (entityIn.func_213287_bg()) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
         }

         this.field_178733_c.render(scale);
         this.field_178731_d.render(scale);
         this.field_178734_a.render(scale);
         this.field_178732_b.render(scale);
         this.field_178730_v.render(scale);
      }

      GlStateManager.popMatrix();
   }

   public void renderDeadmau5Head(float scale) {
      this.field_178736_x.func_217177_a(this.field_78116_c);
      this.field_178736_x.rotationPointX = 0.0F;
      this.field_178736_x.rotationPointY = 0.0F;
      this.field_178736_x.render(scale);
   }

   public void renderCape(float scale) {
      this.field_178729_w.render(scale);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_178733_c.func_217177_a(this.field_178722_k);
      this.field_178731_d.func_217177_a(this.field_178721_j);
      this.field_178734_a.func_217177_a(this.field_178724_i);
      this.field_178732_b.func_217177_a(this.field_178723_h);
      this.field_178730_v.func_217177_a(this.field_78115_e);
      if (p_212844_1_.func_213287_bg()) {
         this.field_178729_w.rotationPointY = 2.0F;
      } else {
         this.field_178729_w.rotationPointY = 0.0F;
      }

   }

   public void setVisible(boolean visible) {
      super.setVisible(visible);
      this.field_178734_a.showModel = visible;
      this.field_178732_b.showModel = visible;
      this.field_178733_c.showModel = visible;
      this.field_178731_d.showModel = visible;
      this.field_178730_v.showModel = visible;
      this.field_178729_w.showModel = visible;
      this.field_178736_x.showModel = visible;
   }

   public void postRenderArm(float scale, HandSide side) {
      RendererModel renderermodel = this.getArmForSide(side);
      if (this.smallArms) {
         float f = 0.5F * (float)(side == HandSide.RIGHT ? 1 : -1);
         renderermodel.rotationPointX += f;
         renderermodel.postRender(scale);
         renderermodel.rotationPointX -= f;
      } else {
         renderermodel.postRender(scale);
      }

   }
}