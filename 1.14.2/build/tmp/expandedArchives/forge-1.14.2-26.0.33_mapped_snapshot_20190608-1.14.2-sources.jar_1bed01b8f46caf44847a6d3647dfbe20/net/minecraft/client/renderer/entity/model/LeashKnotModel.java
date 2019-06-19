package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeashKnotModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel field_110723_a;

   public LeashKnotModel() {
      this(0, 0, 32, 32);
   }

   public LeashKnotModel(int p_i46365_1_, int p_i46365_2_, int p_i46365_3_, int p_i46365_4_) {
      this.textureWidth = p_i46365_3_;
      this.textureHeight = p_i46365_4_;
      this.field_110723_a = new RendererModel(this, p_i46365_1_, p_i46365_2_);
      this.field_110723_a.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
      this.field_110723_a.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_110723_a.render(scale);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_110723_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_110723_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
   }
}