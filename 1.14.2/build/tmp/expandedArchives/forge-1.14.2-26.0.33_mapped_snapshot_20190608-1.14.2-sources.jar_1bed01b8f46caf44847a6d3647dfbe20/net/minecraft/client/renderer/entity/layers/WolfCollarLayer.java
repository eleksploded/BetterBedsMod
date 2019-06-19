package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WolfCollarLayer extends LayerRenderer<WolfEntity, WolfModel<WolfEntity>> {
   private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

   public WolfCollarLayer(IEntityRenderer<WolfEntity, WolfModel<WolfEntity>> p_i50914_1_) {
      super(p_i50914_1_);
   }

   public void func_212842_a_(WolfEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (p_212842_1_.isTamed() && !p_212842_1_.isInvisible()) {
         this.func_215333_a(WOLF_COLLAR);
         float[] afloat = p_212842_1_.getCollarColor().getColorComponentValues();
         GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
         this.func_215332_c().render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}