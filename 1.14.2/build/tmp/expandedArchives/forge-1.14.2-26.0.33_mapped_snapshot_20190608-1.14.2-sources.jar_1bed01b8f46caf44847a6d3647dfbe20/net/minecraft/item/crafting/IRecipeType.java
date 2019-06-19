package net.minecraft.item.crafting;

import java.util.Optional;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface IRecipeType<T extends IRecipe<?>> {
   IRecipeType<ICraftingRecipe> field_222149_a = func_222147_a("crafting");
   IRecipeType<FurnaceRecipe> field_222150_b = func_222147_a("smelting");
   IRecipeType<BlastingRecipe> field_222151_c = func_222147_a("blasting");
   IRecipeType<SmokingRecipe> field_222152_d = func_222147_a("smoking");
   IRecipeType<CampfireCookingRecipe> field_222153_e = func_222147_a("campfire_cooking");
   IRecipeType<StonecuttingRecipe> field_222154_f = func_222147_a("stonecutting");

   static <T extends IRecipe<?>> IRecipeType<T> func_222147_a(final String p_222147_0_) {
      return Registry.register(Registry.field_218367_H, new ResourceLocation(p_222147_0_), new IRecipeType<T>() {
         public String toString() {
            return p_222147_0_;
         }
      });
   }

   default <C extends IInventory> Optional<T> func_222148_a(IRecipe<C> p_222148_1_, World p_222148_2_, C p_222148_3_) {
      return p_222148_1_.matches(p_222148_3_, p_222148_2_) ? Optional.of((T)p_222148_1_) : Optional.empty();
   }
}