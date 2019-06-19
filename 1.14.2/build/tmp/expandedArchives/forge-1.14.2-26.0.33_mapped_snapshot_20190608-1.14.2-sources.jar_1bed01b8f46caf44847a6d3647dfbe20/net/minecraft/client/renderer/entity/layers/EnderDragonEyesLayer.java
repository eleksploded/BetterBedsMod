package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EnderDragonModel;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderDragonEyesLayer extends LayerRenderer<EnderDragonEntity, EnderDragonModel> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");

   public EnderDragonEyesLayer(IEntityRenderer<EnderDragonEntity, EnderDragonModel> p_i50940_1_) {
      super(p_i50940_1_);
   }

   public void func_212842_a_(EnderDragonEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      this.func_215333_a(TEXTURE);
      GlStateManager.enableBlend();
      GlStateManager.disableAlphaTest();
      GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
      GlStateManager.disableLighting();
      GlStateManager.depthFunc(514);
      int i = 61680;
      int j = 61680;
      int k = 0;
      GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, 61680.0F, 0.0F);
      GlStateManager.enableLighting();
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
      gamerenderer.setupFogColor(true);
      this.func_215332_c().render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      gamerenderer.setupFogColor(false);
      this.func_215334_a(p_212842_1_);
      GlStateManager.disableBlend();
      GlStateManager.enableAlphaTest();
      GlStateManager.depthFunc(515);
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}