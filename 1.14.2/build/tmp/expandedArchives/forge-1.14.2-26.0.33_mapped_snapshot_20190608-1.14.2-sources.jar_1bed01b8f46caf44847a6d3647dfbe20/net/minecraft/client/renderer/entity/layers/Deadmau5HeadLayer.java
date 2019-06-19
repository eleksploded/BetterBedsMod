package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Deadmau5HeadLayer extends LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {
   public Deadmau5HeadLayer(IEntityRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> p_i50945_1_) {
      super(p_i50945_1_);
   }

   public void func_212842_a_(AbstractClientPlayerEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if ("deadmau5".equals(p_212842_1_.getName().getString()) && p_212842_1_.hasSkin() && !p_212842_1_.isInvisible()) {
         this.func_215333_a(p_212842_1_.getLocationSkin());

         for(int i = 0; i < 2; ++i) {
            float f = MathHelper.func_219799_g(p_212842_4_, p_212842_1_.prevRotationYaw, p_212842_1_.rotationYaw) - MathHelper.func_219799_g(p_212842_4_, p_212842_1_.prevRenderYawOffset, p_212842_1_.renderYawOffset);
            float f1 = MathHelper.func_219799_g(p_212842_4_, p_212842_1_.prevRotationPitch, p_212842_1_.rotationPitch);
            GlStateManager.pushMatrix();
            GlStateManager.rotatef(f, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotatef(f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.translatef(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
            GlStateManager.translatef(0.0F, -0.375F, 0.0F);
            GlStateManager.rotatef(-f1, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotatef(-f, 0.0F, 1.0F, 0.0F);
            float f2 = 1.3333334F;
            GlStateManager.scalef(1.3333334F, 1.3333334F, 1.3333334F);
            this.func_215332_c().renderDeadmau5Head(0.0625F);
            GlStateManager.popMatrix();
         }

      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}