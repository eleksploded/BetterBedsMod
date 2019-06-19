package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShulkerModel<T extends ShulkerEntity> extends EntityModel<T> {
   private final RendererModel field_187067_b;
   private final RendererModel field_187068_c;
   private final RendererModel field_187066_a;

   public ShulkerModel() {
      this.textureHeight = 64;
      this.textureWidth = 64;
      this.field_187068_c = new RendererModel(this);
      this.field_187067_b = new RendererModel(this);
      this.field_187066_a = new RendererModel(this);
      this.field_187068_c.setTextureOffset(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16, 12, 16);
      this.field_187068_c.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.field_187067_b.setTextureOffset(0, 28).addBox(-8.0F, -8.0F, -8.0F, 16, 8, 16);
      this.field_187067_b.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.field_187066_a.setTextureOffset(0, 52).addBox(-3.0F, 0.0F, -3.0F, 6, 6, 6);
      this.field_187066_a.setRotationPoint(0.0F, 12.0F, 0.0F);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      float f = p_212844_4_ - (float)p_212844_1_.ticksExisted;
      float f1 = (0.5F + p_212844_1_.getClientPeekAmount(f)) * (float)Math.PI;
      float f2 = -1.0F + MathHelper.sin(f1);
      float f3 = 0.0F;
      if (f1 > (float)Math.PI) {
         f3 = MathHelper.sin(p_212844_4_ * 0.1F) * 0.7F;
      }

      this.field_187068_c.setRotationPoint(0.0F, 16.0F + MathHelper.sin(f1) * 8.0F + f3, 0.0F);
      if (p_212844_1_.getClientPeekAmount(f) > 0.3F) {
         this.field_187068_c.rotateAngleY = f2 * f2 * f2 * f2 * (float)Math.PI * 0.125F;
      } else {
         this.field_187068_c.rotateAngleY = 0.0F;
      }

      this.field_187066_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      this.field_187066_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.field_187067_b.render(scale);
      this.field_187068_c.render(scale);
   }

   public RendererModel getBase() {
      return this.field_187067_b;
   }

   public RendererModel getLid() {
      return this.field_187068_c;
   }

   public RendererModel getHead() {
      return this.field_187066_a;
   }
}