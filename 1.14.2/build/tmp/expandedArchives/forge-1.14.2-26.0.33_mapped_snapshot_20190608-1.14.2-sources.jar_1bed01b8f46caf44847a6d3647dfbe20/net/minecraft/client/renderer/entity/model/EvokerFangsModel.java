package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EvokerFangsModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_191213_a = new RendererModel(this, 0, 0);
   private final RendererModel field_191214_b;
   private final RendererModel field_191215_c;

   public EvokerFangsModel() {
      this.field_191213_a.setRotationPoint(-5.0F, 22.0F, -5.0F);
      this.field_191213_a.addBox(0.0F, 0.0F, 0.0F, 10, 12, 10);
      this.field_191214_b = new RendererModel(this, 40, 0);
      this.field_191214_b.setRotationPoint(1.5F, 22.0F, -4.0F);
      this.field_191214_b.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
      this.field_191215_c = new RendererModel(this, 40, 0);
      this.field_191215_c.setRotationPoint(-1.5F, 22.0F, 4.0F);
      this.field_191215_c.addBox(0.0F, 0.0F, 0.0F, 4, 14, 8);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      float f = limbSwing * 2.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      f = 1.0F - f * f * f;
      this.field_191214_b.rotateAngleZ = (float)Math.PI - f * 0.35F * (float)Math.PI;
      this.field_191215_c.rotateAngleZ = (float)Math.PI + f * 0.35F * (float)Math.PI;
      this.field_191215_c.rotateAngleY = (float)Math.PI;
      float f1 = (limbSwing + MathHelper.sin(limbSwing * 2.7F)) * 0.6F * 12.0F;
      this.field_191214_b.rotationPointY = 24.0F - f1;
      this.field_191215_c.rotationPointY = this.field_191214_b.rotationPointY;
      this.field_191213_a.rotationPointY = this.field_191214_b.rotationPointY;
      this.field_191213_a.render(scale);
      this.field_191214_b.render(scale);
      this.field_191215_c.render(scale);
   }
}