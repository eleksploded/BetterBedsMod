package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerBulletModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_187069_a;

   public ShulkerBulletModel() {
      this.textureWidth = 64;
      this.textureHeight = 32;
      this.field_187069_a = new RendererModel(this);
      this.field_187069_a.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8, 8, 2, 0.0F);
      this.field_187069_a.setTextureOffset(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2, 8, 8, 0.0F);
      this.field_187069_a.setTextureOffset(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8, 2, 8, 0.0F);
      this.field_187069_a.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_187069_a.render(scale);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_187069_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_187069_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
   }
}