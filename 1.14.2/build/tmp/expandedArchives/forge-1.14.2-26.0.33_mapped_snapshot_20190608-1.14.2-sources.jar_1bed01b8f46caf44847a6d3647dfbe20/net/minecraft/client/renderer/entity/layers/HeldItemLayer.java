package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeldItemLayer<T extends LivingEntity, M extends EntityModel<T> & IHasArm> extends LayerRenderer<T, M> {
   public HeldItemLayer(IEntityRenderer<T, M> p_i50934_1_) {
      super(p_i50934_1_);
   }

   public void func_212842_a_(T p_212842_1_, float p_212842_2_, float p_212842_3_, float p_212842_4_, float p_212842_5_, float p_212842_6_, float p_212842_7_, float p_212842_8_) {
      boolean flag = p_212842_1_.getPrimaryHand() == HandSide.RIGHT;
      ItemStack itemstack = flag ? p_212842_1_.getHeldItemOffhand() : p_212842_1_.getHeldItemMainhand();
      ItemStack itemstack1 = flag ? p_212842_1_.getHeldItemMainhand() : p_212842_1_.getHeldItemOffhand();
      if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
         GlStateManager.pushMatrix();
         if (this.func_215332_c().field_217114_e) {
            float f = 0.5F;
            GlStateManager.translatef(0.0F, 0.75F, 0.0F);
            GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         }

         this.renderHeldItem(p_212842_1_, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, HandSide.RIGHT);
         this.renderHeldItem(p_212842_1_, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, HandSide.LEFT);
         GlStateManager.popMatrix();
      }
   }

   private void renderHeldItem(LivingEntity p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, HandSide handSide) {
      if (!p_188358_2_.isEmpty()) {
         GlStateManager.pushMatrix();
         if (p_188358_1_.func_213287_bg()) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
         }

         // Forge: moved this call down, fixes incorrect offset while sneaking.
         this.translateToHand(handSide);
         GlStateManager.rotatef(-90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
         boolean flag = handSide == HandSide.LEFT;
         GlStateManager.translatef((float)(flag ? -1 : 1) / 16.0F, 0.125F, -0.625F);
         Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
         GlStateManager.popMatrix();
      }
   }

   protected void translateToHand(HandSide p_191361_1_) {
      ((IHasArm)this.func_215332_c()).postRenderArm(0.0625F, p_191361_1_);
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}