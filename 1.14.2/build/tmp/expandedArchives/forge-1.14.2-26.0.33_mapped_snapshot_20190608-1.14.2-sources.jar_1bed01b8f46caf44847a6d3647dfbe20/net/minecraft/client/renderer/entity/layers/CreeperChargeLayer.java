package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CreeperChargeLayer extends LayerRenderer<CreeperEntity, CreeperModel<CreeperEntity>> {
   private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final CreeperModel<CreeperEntity> field_177171_c = new CreeperModel<>(2.0F);

   public CreeperChargeLayer(IEntityRenderer<CreeperEntity, CreeperModel<CreeperEntity>> p_i50947_1_) {
      super(p_i50947_1_);
   }

   public void func_212842_a_(CreeperEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (p_212842_1_.getPowered()) {
         boolean flag = p_212842_1_.isInvisible();
         GlStateManager.depthMask(!flag);
         this.func_215333_a(LIGHTNING_TEXTURE);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         float f = (float)p_212842_1_.ticksExisted + p_212842_4_;
         GlStateManager.translatef(f * 0.01F, f * 0.01F, 0.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.enableBlend();
         float f1 = 0.5F;
         GlStateManager.color4f(0.5F, 0.5F, 0.5F, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
         this.func_215332_c().func_217111_a(this.field_177171_c);
         GameRenderer gamerenderer = Minecraft.getInstance().gameRenderer;
         gamerenderer.setupFogColor(true);
         this.field_177171_c.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
         gamerenderer.setupFogColor(false);
         GlStateManager.matrixMode(5890);
         GlStateManager.loadIdentity();
         GlStateManager.matrixMode(5888);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}