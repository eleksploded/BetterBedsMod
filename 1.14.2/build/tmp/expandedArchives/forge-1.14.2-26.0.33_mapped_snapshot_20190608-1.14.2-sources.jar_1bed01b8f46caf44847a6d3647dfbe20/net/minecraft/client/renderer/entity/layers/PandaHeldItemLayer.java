package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PandaHeldItemLayer extends LayerRenderer<PandaEntity, PandaModel<PandaEntity>> {
   public PandaHeldItemLayer(IEntityRenderer<PandaEntity, PandaModel<PandaEntity>> p_i50930_1_) {
      super(p_i50930_1_);
   }

   public void func_212842_a_(PandaEntity p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      ItemStack itemstack = p_212842_1_.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
      if (p_212842_1_.func_213556_dX() && !itemstack.isEmpty() && !p_212842_1_.func_213566_eo()) {
         float f = -0.6F;
         float f1 = 1.4F;
         if (p_212842_1_.func_213578_dZ()) {
            f -= 0.2F * MathHelper.sin(p_212842_5_ * 0.6F) + 0.2F;
            f1 -= 0.09F * MathHelper.sin(p_212842_5_ * 0.6F);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translatef(0.1F, f1, f);
         Minecraft.getInstance().getItemRenderer().renderItem(itemstack, p_212842_1_, ItemCameraTransforms.TransformType.GROUND, false);
         GlStateManager.popMatrix();
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}