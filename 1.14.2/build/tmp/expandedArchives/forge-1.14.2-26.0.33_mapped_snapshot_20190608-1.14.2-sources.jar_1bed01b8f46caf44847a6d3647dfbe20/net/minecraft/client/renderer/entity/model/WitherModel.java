package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitherModel<T extends WitherEntity> extends EntityModel<T> {
   private final RendererModel[] field_82905_a;
   private final RendererModel[] field_82904_b;

   public WitherModel(float p_i46302_1_) {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_82905_a = new RendererModel[3];
      this.field_82905_a[0] = new RendererModel(this, 0, 16);
      this.field_82905_a[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, p_i46302_1_);
      this.field_82905_a[1] = (new RendererModel(this)).setTextureSize(this.textureWidth, this.textureHeight);
      this.field_82905_a[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
      this.field_82905_a[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, p_i46302_1_);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, p_i46302_1_);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, p_i46302_1_);
      this.field_82905_a[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, p_i46302_1_);
      this.field_82905_a[2] = new RendererModel(this, 12, 22);
      this.field_82905_a[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, p_i46302_1_);
      this.field_82904_b = new RendererModel[3];
      this.field_82904_b[0] = new RendererModel(this, 0, 0);
      this.field_82904_b[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p_i46302_1_);
      this.field_82904_b[1] = new RendererModel(this, 32, 0);
      this.field_82904_b[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
      this.field_82904_b[1].rotationPointX = -8.0F;
      this.field_82904_b[1].rotationPointY = 4.0F;
      this.field_82904_b[2] = new RendererModel(this, 32, 0);
      this.field_82904_b[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
      this.field_82904_b[2].rotationPointX = 10.0F;
      this.field_82904_b[2].rotationPointY = 4.0F;
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

      for(RendererModel renderermodel : this.field_82904_b) {
         renderermodel.render(scale);
      }

      for(RendererModel renderermodel1 : this.field_82905_a) {
         renderermodel1.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      float f = MathHelper.cos(p_212844_4_ * 0.1F);
      this.field_82905_a[1].rotateAngleX = (0.065F + 0.05F * f) * (float)Math.PI;
      this.field_82905_a[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos(this.field_82905_a[1].rotateAngleX) * 10.0F, -0.5F + MathHelper.sin(this.field_82905_a[1].rotateAngleX) * 10.0F);
      this.field_82905_a[2].rotateAngleX = (0.265F + 0.1F * f) * (float)Math.PI;
      this.field_82904_b[0].rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_82904_b[0].rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      for(int i = 1; i < 3; ++i) {
         this.field_82904_b[i].rotateAngleY = (p_212843_1_.getHeadYRotation(i - 1) - p_212843_1_.renderYawOffset) * ((float)Math.PI / 180F);
         this.field_82904_b[i].rotateAngleX = p_212843_1_.getHeadXRotation(i - 1) * ((float)Math.PI / 180F);
      }

   }
}