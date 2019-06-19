package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.SheepModel;
import net.minecraft.client.renderer.entity.model.SheepWoolModel;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SheepWoolLayer extends LayerRenderer<SheepEntity, SheepModel<SheepEntity>> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
   private final SheepWoolModel<SheepEntity> field_177164_c = new SheepWoolModel<>();

   public SheepWoolLayer(IEntityRenderer<SheepEntity, SheepModel<SheepEntity>> p_i50925_1_) {
      super(p_i50925_1_);
   }

   public void func_212842_a_(SheepEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      if (!p_212842_1_.getSheared() && !p_212842_1_.isInvisible()) {
         this.func_215333_a(TEXTURE);
         if (p_212842_1_.hasCustomName() && "jeb_".equals(p_212842_1_.getName().getUnformattedComponentText())) {
            int i1 = 25;
            int i = p_212842_1_.ticksExisted / 25 + p_212842_1_.getEntityId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f = ((float)(p_212842_1_.ticksExisted % 25) + p_212842_4_) / 25.0F;
            float[] afloat1 = SheepEntity.getDyeRgb(DyeColor.byId(k));
            float[] afloat2 = SheepEntity.getDyeRgb(DyeColor.byId(l));
            GlStateManager.color3f(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
         } else {
            float[] afloat = SheepEntity.getDyeRgb(p_212842_1_.getFleeceColor());
            GlStateManager.color3f(afloat[0], afloat[1], afloat[2]);
         }

         this.func_215332_c().func_217111_a(this.field_177164_c);
         this.field_177164_c.func_212843_a_(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_4_);
         this.field_177164_c.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }
   }

   public boolean shouldCombineTextures() {
      return true;
   }
}