package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PigModel;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SaddleLayer extends LayerRenderer<PigEntity, PigModel<PigEntity>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
   private final PigModel<PigEntity> field_177157_c = new PigModel<>(0.5F);

   public SaddleLayer(IEntityRenderer<PigEntity, PigModel<PigEntity>> p_i50927_1_) {
      super(p_i50927_1_);
   }

   public void func_212842_a_(PigEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (p_212842_1_.getSaddled()) {
         this.func_215333_a(TEXTURE);
         this.func_215332_c().func_217111_a(this.field_177157_c);
         this.field_177157_c.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}