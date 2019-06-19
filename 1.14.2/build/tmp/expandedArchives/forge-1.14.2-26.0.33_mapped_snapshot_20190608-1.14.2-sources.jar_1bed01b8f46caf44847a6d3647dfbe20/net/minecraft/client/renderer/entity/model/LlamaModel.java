package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaModel<T extends AbstractChestedHorseEntity> extends QuadrupedModel<T> {
   private final RendererModel field_191226_i;
   private final RendererModel field_191227_j;

   public LlamaModel(float p_i47226_1_) {
      super(15, p_i47226_1_);
      this.textureWidth = 128;
      this.textureHeight = 64;
      this.field_78150_a = new RendererModel(this, 0, 0);
      this.field_78150_a.addBox(-2.0F, -14.0F, -10.0F, 4, 4, 9, p_i47226_1_);
      this.field_78150_a.setRotationPoint(0.0F, 7.0F, -6.0F);
      this.field_78150_a.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8, 18, 6, p_i47226_1_);
      this.field_78150_a.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
      this.field_78150_a.setTextureOffset(17, 0).addBox(1.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
      this.field_78148_b = new RendererModel(this, 29, 0);
      this.field_78148_b.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, p_i47226_1_);
      this.field_78148_b.setRotationPoint(0.0F, 5.0F, 2.0F);
      this.field_191226_i = new RendererModel(this, 45, 28);
      this.field_191226_i.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
      this.field_191226_i.setRotationPoint(-8.5F, 3.0F, 3.0F);
      this.field_191226_i.rotateAngleY = ((float)Math.PI / 2F);
      this.field_191227_j = new RendererModel(this, 45, 41);
      this.field_191227_j.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
      this.field_191227_j.setRotationPoint(5.5F, 3.0F, 3.0F);
      this.field_191227_j.rotateAngleY = ((float)Math.PI / 2F);
      int i = 4;
      int j = 14;
      this.field_78149_c = new RendererModel(this, 29, 29);
      this.field_78149_c.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
      this.field_78149_c.setRotationPoint(-2.5F, 10.0F, 6.0F);
      this.field_78146_d = new RendererModel(this, 29, 29);
      this.field_78146_d.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
      this.field_78146_d.setRotationPoint(2.5F, 10.0F, 6.0F);
      this.field_78147_e = new RendererModel(this, 29, 29);
      this.field_78147_e.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
      this.field_78147_e.setRotationPoint(-2.5F, 10.0F, -4.0F);
      this.field_78144_f = new RendererModel(this, 29, 29);
      this.field_78144_f.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
      this.field_78144_f.setRotationPoint(2.5F, 10.0F, -4.0F);
      --this.field_78149_c.rotationPointX;
      ++this.field_78146_d.rotationPointX;
      this.field_78149_c.rotationPointZ += 0.0F;
      this.field_78146_d.rotationPointZ += 0.0F;
      --this.field_78147_e.rotationPointX;
      ++this.field_78144_f.rotationPointX;
      --this.field_78147_e.rotationPointZ;
      --this.field_78144_f.rotationPointZ;
      this.childZOffset += 2.0F;
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      boolean flag = !entityIn.isChild() && entityIn.hasChest();
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.0F, this.childYOffset * scale, this.childZOffset * scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         float f1 = 0.7F;
         GlStateManager.scalef(0.71428573F, 0.64935064F, 0.7936508F);
         GlStateManager.translatef(0.0F, 21.0F * scale, 0.22F);
         this.field_78150_a.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         float f2 = 1.1F;
         GlStateManager.scalef(0.625F, 0.45454544F, 0.45454544F);
         GlStateManager.translatef(0.0F, 33.0F * scale, 0.0F);
         this.field_78148_b.render(scale);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         GlStateManager.scalef(0.45454544F, 0.41322312F, 0.45454544F);
         GlStateManager.translatef(0.0F, 33.0F * scale, 0.0F);
         this.field_78149_c.render(scale);
         this.field_78146_d.render(scale);
         this.field_78147_e.render(scale);
         this.field_78144_f.render(scale);
         GlStateManager.popMatrix();
      } else {
         this.field_78150_a.render(scale);
         this.field_78148_b.render(scale);
         this.field_78149_c.render(scale);
         this.field_78146_d.render(scale);
         this.field_78147_e.render(scale);
         this.field_78144_f.render(scale);
      }

      if (flag) {
         this.field_191226_i.render(scale);
         this.field_191227_j.render(scale);
      }

   }
}