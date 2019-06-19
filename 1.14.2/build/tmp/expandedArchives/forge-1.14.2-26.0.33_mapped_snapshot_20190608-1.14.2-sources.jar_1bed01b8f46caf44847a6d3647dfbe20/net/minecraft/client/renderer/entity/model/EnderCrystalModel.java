package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderCrystalModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_78230_a;
   private final RendererModel field_78228_b = new RendererModel(this, "glass");
   private final RendererModel field_78229_c;

   public EnderCrystalModel(float p_i1170_1_, boolean renderBase) {
      this.field_78228_b.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      this.field_78230_a = new RendererModel(this, "cube");
      this.field_78230_a.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      if (renderBase) {
         this.field_78229_c = new RendererModel(this, "base");
         this.field_78229_c.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
      } else {
         this.field_78229_c = null;
      }

   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      GlStateManager.pushMatrix();
      GlStateManager.scalef(2.0F, 2.0F, 2.0F);
      GlStateManager.translatef(0.0F, -0.5F, 0.0F);
      if (this.field_78229_c != null) {
         this.field_78229_c.render(scale);
      }

      GlStateManager.rotatef(limbSwingAmount, 0.0F, 1.0F, 0.0F);
      GlStateManager.translatef(0.0F, 0.8F + ageInTicks, 0.0F);
      GlStateManager.rotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
      this.field_78228_b.render(scale);
      float f = 0.875F;
      GlStateManager.scalef(0.875F, 0.875F, 0.875F);
      GlStateManager.rotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.rotatef(limbSwingAmount, 0.0F, 1.0F, 0.0F);
      this.field_78228_b.render(scale);
      GlStateManager.scalef(0.875F, 0.875F, 0.875F);
      GlStateManager.rotatef(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.rotatef(limbSwingAmount, 0.0F, 1.0F, 0.0F);
      this.field_78230_a.render(scale);
      GlStateManager.popMatrix();
   }
}