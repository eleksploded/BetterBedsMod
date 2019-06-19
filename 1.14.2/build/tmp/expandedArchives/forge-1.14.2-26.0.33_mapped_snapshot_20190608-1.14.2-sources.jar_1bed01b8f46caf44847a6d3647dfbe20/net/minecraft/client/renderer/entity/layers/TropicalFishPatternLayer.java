package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.TropicalFishAModel;
import net.minecraft.client.renderer.entity.model.TropicalFishBModel;
import net.minecraft.entity.passive.fish.TropicalFishEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TropicalFishPatternLayer extends LayerRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> {
   private final TropicalFishAModel<TropicalFishEntity> field_204251_b = new TropicalFishAModel<>(0.008F);
   private final TropicalFishBModel<TropicalFishEntity> field_204252_c = new TropicalFishBModel<>(0.008F);

   public TropicalFishPatternLayer(IEntityRenderer<TropicalFishEntity, EntityModel<TropicalFishEntity>> p_i50918_1_) {
      super(p_i50918_1_);
   }

   public void func_212842_a_(TropicalFishEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (!p_212842_1_.isInvisible()) {
         EntityModel<TropicalFishEntity> entitymodel = (EntityModel<TropicalFishEntity>)(p_212842_1_.getSize() == 0 ? this.field_204251_b : this.field_204252_c);
         this.func_215333_a(p_212842_1_.getPatternTexture());
         float[] afloat = p_212842_1_.func_204222_dD();
         GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
         this.func_215332_c().func_217111_a(entitymodel);
         entitymodel.func_212843_a_(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_4_);
         entitymodel.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}