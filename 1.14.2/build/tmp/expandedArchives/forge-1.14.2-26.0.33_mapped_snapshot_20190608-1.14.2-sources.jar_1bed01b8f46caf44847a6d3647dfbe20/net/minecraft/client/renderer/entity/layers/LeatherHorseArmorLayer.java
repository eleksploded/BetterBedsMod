package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.HorseModel;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.item.DyeableHorseArmorItem;
import net.minecraft.item.HorseArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LeatherHorseArmorLayer extends LayerRenderer<HorseEntity, HorseModel<HorseEntity>> {
   private final HorseModel<HorseEntity> field_215341_a = new HorseModel<>(0.1F);

   public LeatherHorseArmorLayer(IEntityRenderer<HorseEntity, HorseModel<HorseEntity>> p_i50937_1_) {
      super(p_i50937_1_);
   }

   public void func_212842_a_(HorseEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      ItemStack itemstack = p_212842_1_.func_213803_dV();
      if (itemstack.getItem() instanceof HorseArmorItem) {
         HorseArmorItem horsearmoritem = (HorseArmorItem)itemstack.getItem();
         this.func_215332_c().func_217111_a(this.field_215341_a);
         this.field_215341_a.func_212843_a_(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_4_);
         this.func_215333_a(horsearmoritem.func_219976_d());
         if (horsearmoritem instanceof DyeableHorseArmorItem) {
            int i = ((DyeableHorseArmorItem)horsearmoritem).getColor(itemstack);
            float f = (float)(i >> 16 & 255) / 255.0F;
            float f1 = (float)(i >> 8 & 255) / 255.0F;
            float f2 = (float)(i & 255) / 255.0F;
            GlStateManager.color4f(f, f1, f2, 1.0F);
            this.field_215341_a.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
            return;
         }

         GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.field_215341_a.render(p_212842_1_, p_212842_2_, p_212842_3_, p_212842_5_, p_212842_6_, p_212842_7_, p_212842_8_);
      }

   }

   public boolean shouldCombineTextures() {
      return false;
   }
}