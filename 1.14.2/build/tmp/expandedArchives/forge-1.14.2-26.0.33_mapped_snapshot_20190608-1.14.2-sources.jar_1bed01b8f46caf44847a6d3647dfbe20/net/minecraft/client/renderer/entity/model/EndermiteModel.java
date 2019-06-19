package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermiteModel<T extends Entity> extends EntityModel<T> {
   private static final int[][] BODY_SIZES = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] BODY_TEXS = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int BODY_COUNT = BODY_SIZES.length;
   private final RendererModel[] field_178713_d = new RendererModel[BODY_COUNT];

   public EndermiteModel() {
      float f = -3.5F;

      for(int i = 0; i < this.field_178713_d.length; ++i) {
         this.field_178713_d[i] = new RendererModel(this, BODY_TEXS[i][0], BODY_TEXS[i][1]);
         this.field_178713_d[i].addBox((float)BODY_SIZES[i][0] * -0.5F, 0.0F, (float)BODY_SIZES[i][2] * -0.5F, BODY_SIZES[i][0], BODY_SIZES[i][1], BODY_SIZES[i][2]);
         this.field_178713_d[i].setRotationPoint(0.0F, (float)(24 - BODY_SIZES[i][1]), f);
         if (i < this.field_178713_d.length - 1) {
            f += (float)(BODY_SIZES[i][2] + BODY_SIZES[i + 1][2]) * 0.5F;
         }
      }

   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

      for(RendererModel renderermodel : this.field_178713_d) {
         renderermodel.render(scale);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      for(int i = 0; i < this.field_178713_d.length; ++i) {
         this.field_178713_d[i].rotateAngleY = MathHelper.cos(p_212844_4_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.01F * (float)(1 + Math.abs(i - 2));
         this.field_178713_d[i].rotationPointX = MathHelper.sin(p_212844_4_ * 0.9F + (float)i * 0.15F * (float)Math.PI) * (float)Math.PI * 0.1F * (float)Math.abs(i - 2);
      }

   }
}