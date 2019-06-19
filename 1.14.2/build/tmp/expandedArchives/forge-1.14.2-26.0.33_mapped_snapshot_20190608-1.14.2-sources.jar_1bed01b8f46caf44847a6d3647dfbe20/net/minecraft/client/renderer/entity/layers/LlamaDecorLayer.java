package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.LlamaModel;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LlamaDecorLayer extends LayerRenderer<LlamaEntity, LlamaModel<LlamaEntity>> {
   private static final ResourceLocation[] LLAMA_DECOR_TEXTURES = new ResourceLocation[]{new ResourceLocation("textures/entity/llama/decor/white.png"), new ResourceLocation("textures/entity/llama/decor/orange.png"), new ResourceLocation("textures/entity/llama/decor/magenta.png"), new ResourceLocation("textures/entity/llama/decor/light_blue.png"), new ResourceLocation("textures/entity/llama/decor/yellow.png"), new ResourceLocation("textures/entity/llama/decor/lime.png"), new ResourceLocation("textures/entity/llama/decor/pink.png"), new ResourceLocation("textures/entity/llama/decor/gray.png"), new ResourceLocation("textures/entity/llama/decor/light_gray.png"), new ResourceLocation("textures/entity/llama/decor/cyan.png"), new ResourceLocation("textures/entity/llama/decor/purple.png"), new ResourceLocation("textures/entity/llama/decor/blue.png"), new ResourceLocation("textures/entity/llama/decor/brown.png"), new ResourceLocation("textures/entity/llama/decor/green.png"), new ResourceLocation("textures/entity/llama/decor/red.png"), new ResourceLocation("textures/entity/llama/decor/black.png")};
   private static final ResourceLocation field_215342_b = new ResourceLocation("textures/entity/llama/decor/trader_llama.png");
   private final LlamaModel<LlamaEntity> field_191366_c = new LlamaModel<>(0.5F);

   public LlamaDecorLayer(IEntityRenderer<LlamaEntity, LlamaModel<LlamaEntity>> p_i50933_1_) {
      super(p_i50933_1_);
   }

   public void func_212842_a_(LlamaEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      DyeColor dyecolor = p_212842_1_.getColor();
      if (dyecolor != null) {
         this.func_215333_a(LLAMA_DECOR_TEXTURES[dyecolor.getId()]);
      } else {
         if (!p_212842_1_.func_213800_eB()) {
            return;
         }

         this.func_215333_a(field_215342_b);
      }

      this.func_215332_c().func_217111_a(this.field_191366_c);
      this.field_191366_c.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}