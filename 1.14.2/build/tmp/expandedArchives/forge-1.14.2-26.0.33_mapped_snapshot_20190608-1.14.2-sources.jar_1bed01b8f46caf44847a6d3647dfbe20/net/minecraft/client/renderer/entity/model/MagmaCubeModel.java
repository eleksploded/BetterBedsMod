package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MagmaCubeModel<T extends SlimeEntity> extends EntityModel<T> {
   private final RendererModel[] field_78109_a = new RendererModel[8];
   private final RendererModel field_78108_b;

   public MagmaCubeModel() {
      for(int i = 0; i < this.field_78109_a.length; ++i) {
         int j = 0;
         int k = i;
         if (i == 2) {
            j = 24;
            k = 10;
         } else if (i == 3) {
            j = 24;
            k = 19;
         }

         this.field_78109_a[i] = new RendererModel(this, j, k);
         this.field_78109_a[i].addBox(-4.0F, (float)(16 + i), -4.0F, 8, 1, 8);
      }

      this.field_78108_b = new RendererModel(this, 0, 16);
      this.field_78108_b.addBox(-2.0F, 18.0F, -2.0F, 4, 4, 4);
   }

   public void func_212843_a_(T p_212843_1_, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
      float f = MathHelper.func_219799_g(p_212843_4_, p_212843_1_.prevSquishFactor, p_212843_1_.squishFactor);
      if (f < 0.0F) {
         f = 0.0F;
      }

      for(int i = 0; i < this.field_78109_a.length; ++i) {
         this.field_78109_a[i].rotationPointY = (float)(-(4 - i)) * f * 1.7F;
      }

   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_78108_b.render(scale);

      for(RendererModel renderermodel : this.field_78109_a) {
         renderermodel.render(scale);
      }

   }
}