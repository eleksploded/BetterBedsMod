package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.CatModel;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CatCollarLayer extends LayerRenderer<CatEntity, CatModel<CatEntity>> {
   private static final ResourceLocation field_215339_a = new ResourceLocation("textures/entity/cat/cat_collar.png");
   private final CatModel<CatEntity> field_215340_b = new CatModel<>(0.01F);

   public CatCollarLayer(IEntityRenderer<CatEntity, CatModel<CatEntity>> p_i50948_1_) {
      super(p_i50948_1_);
   }

   public void func_212842_a_(CatEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (p_212842_1_.isTamed() && !p_212842_1_.isInvisible()) {
         this.func_215333_a(field_215339_a);
         float[] afloat = p_212842_1_.func_213414_ei().getColorComponentValues();
         GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
         this.func_215332_c().func_217111_a(this.field_215340_b);
         this.field_215340_b.func_212843_a_(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_4_);
         this.field_215340_b.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}