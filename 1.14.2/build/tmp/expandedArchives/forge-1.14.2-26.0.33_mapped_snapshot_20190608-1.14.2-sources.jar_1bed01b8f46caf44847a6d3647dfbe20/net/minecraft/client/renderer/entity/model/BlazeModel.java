package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlazeModel<T extends Entity> extends EntityModel<T> {
   private final RendererModel[] field_78106_a = new RendererModel[12];
   private final RendererModel field_78105_b;

   public BlazeModel() {
      for(int i = 0; i < this.field_78106_a.length; ++i) {
         this.field_78106_a[i] = new RendererModel(this, 0, 16);
         this.field_78106_a[i].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.field_78105_b = new RendererModel(this, 0, 0);
      this.field_78105_b.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_78105_b.render(scale);

      for(RendererModel renderermodel : this.field_78106_a) {
         renderermodel.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      float f = p_212844_4_ * (float)Math.PI * -0.1F;

      for(int i = 0; i < 4; ++i) {
         this.field_78106_a[i].rotationPointY = -2.0F + MathHelper.cos(((float)(i * 2) + p_212844_4_) * 0.25F);
         this.field_78106_a[i].rotationPointX = MathHelper.cos(f) * 9.0F;
         this.field_78106_a[i].rotationPointZ = MathHelper.sin(f) * 9.0F;
         ++f;
      }

      f = ((float)Math.PI / 4F) + p_212844_4_ * (float)Math.PI * 0.03F;

      for(int j = 4; j < 8; ++j) {
         this.field_78106_a[j].rotationPointY = 2.0F + MathHelper.cos(((float)(j * 2) + p_212844_4_) * 0.25F);
         this.field_78106_a[j].rotationPointX = MathHelper.cos(f) * 7.0F;
         this.field_78106_a[j].rotationPointZ = MathHelper.sin(f) * 7.0F;
         ++f;
      }

      f = 0.47123894F + p_212844_4_ * (float)Math.PI * -0.05F;

      for(int k = 8; k < 12; ++k) {
         this.field_78106_a[k].rotationPointY = 11.0F + MathHelper.cos(((float)k * 1.5F + p_212844_4_) * 0.5F);
         this.field_78106_a[k].rotationPointX = MathHelper.cos(f) * 5.0F;
         this.field_78106_a[k].rotationPointZ = MathHelper.sin(f) * 5.0F;
         ++f;
      }

      this.field_78105_b.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_78105_b.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
   }
}