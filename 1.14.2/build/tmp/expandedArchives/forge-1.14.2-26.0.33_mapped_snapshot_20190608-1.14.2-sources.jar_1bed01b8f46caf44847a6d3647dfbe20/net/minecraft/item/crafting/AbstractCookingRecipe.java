package net.minecraft.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class AbstractCookingRecipe implements IRecipe<IInventory> {
   protected final IRecipeType<?> field_222139_a;
   protected final ResourceLocation field_222140_b;
   protected final String field_222141_c;
   protected final Ingredient field_222142_d;
   protected final ItemStack field_222143_e;
   protected final float field_222144_f;
   protected final int field_222145_g;

   public AbstractCookingRecipe(IRecipeType<?> p_i50032_1_, ResourceLocation p_i50032_2_, String p_i50032_3_, Ingredient p_i50032_4_, ItemStack p_i50032_5_, float p_i50032_6_, int p_i50032_7_) {
      this.field_222139_a = p_i50032_1_;
      this.field_222140_b = p_i50032_2_;
      this.field_222141_c = p_i50032_3_;
      this.field_222142_d = p_i50032_4_;
      this.field_222143_e = p_i50032_5_;
      this.field_222144_f = p_i50032_6_;
      this.field_222145_g = p_i50032_7_;
   }

   /**
    * Used to check if a recipe matches current crafting inventory
    */
   public boolean matches(IInventory inv, World worldIn) {
      return this.field_222142_d.test(inv.getStackInSlot(0));
   }

   /**
    * Returns an Item that is the result of this recipe
    */
   public ItemStack getCraftingResult(IInventory inv) {
      return this.field_222143_e.copy();
   }

   /**
    * Used to determine if this recipe can fit in a grid of the given width/height
    */
   public boolean canFit(int width, int height) {
      return true;
   }

   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();
      nonnulllist.add(this.field_222142_d);
      return nonnulllist;
   }

   public float func_222138_b() {
      return this.field_222144_f;
   }

   /**
    * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
    * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
    */
   public ItemStack getRecipeOutput() {
      return this.field_222143_e;
   }

   /**
    * Recipes with equal group are combined into one button in the recipe book
    */
   public String getGroup() {
      return this.field_222141_c;
   }

   public int func_222137_e() {
      return this.field_222145_g;
   }

   public ResourceLocation getId() {
      return this.field_222140_b;
   }

   public IRecipeType<?> func_222127_g() {
      return this.field_222139_a;
   }
}