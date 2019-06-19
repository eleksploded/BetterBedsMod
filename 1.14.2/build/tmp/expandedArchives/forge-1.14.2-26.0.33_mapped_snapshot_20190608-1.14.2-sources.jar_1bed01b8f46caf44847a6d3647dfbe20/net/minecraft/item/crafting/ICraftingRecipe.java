package net.minecraft.item.crafting;

import net.minecraft.inventory.CraftingInventory;

public interface ICraftingRecipe extends IRecipe<CraftingInventory> {
   default IRecipeType<?> func_222127_g() {
      return IRecipeType.field_222149_a;
   }
}