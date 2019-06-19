package net.minecraft.item.crafting;

import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CampfireCookingRecipe extends AbstractCookingRecipe {
   public CampfireCookingRecipe(ResourceLocation p_i50030_1_, String p_i50030_2_, Ingredient p_i50030_3_, ItemStack p_i50030_4_, float p_i50030_5_, int p_i50030_6_) {
      super(IRecipeType.field_222153_e, p_i50030_1_, p_i50030_2_, p_i50030_3_, p_i50030_4_, p_i50030_5_, p_i50030_6_);
   }

   public ItemStack func_222128_h() {
      return new ItemStack(Blocks.field_222433_lV);
   }

   public IRecipeSerializer<?> getSerializer() {
      return IRecipeSerializer.field_222174_r;
   }
}