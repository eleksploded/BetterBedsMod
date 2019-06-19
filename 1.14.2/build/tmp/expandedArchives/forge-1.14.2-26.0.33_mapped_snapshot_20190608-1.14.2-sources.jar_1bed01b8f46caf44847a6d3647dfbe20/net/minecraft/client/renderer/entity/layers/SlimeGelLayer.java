package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SlimeModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SlimeGelLayer<T extends Entity> extends LayerRenderer<T, SlimeModel<T>> {
   private final EntityModel<T> field_177160_b = new SlimeModel<>(0);

   public SlimeGelLayer(IEntityRenderer<T, SlimeModel<T>> p_i50923_1_) {
      super(p_i50923_1_);
   }

   public void func_212842_a_(T p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (!p_212842_1_.isInvisible()) {
         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableNormalize();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
         this.func_215332_c().func_217111_a(this.field_177160_b);
         this.field_177160_b.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
         GlStateManager.disableBlend();
         GlStateManager.disableNormalize();
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}