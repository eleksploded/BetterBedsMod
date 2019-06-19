package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class SingleItemRecipe implements IRecipe<IInventory> {
   protected final Ingredient field_222131_a;
   protected final ItemStack field_222132_b;
   private final IRecipeType<?> field_222135_e;
   private final IRecipeSerializer<?> field_222136_f;
   protected final ResourceLocation field_222133_c;
   protected final String field_222134_d;

   public SingleItemRecipe(IRecipeType<?> p_i50023_1_, IRecipeSerializer<?> p_i50023_2_, ResourceLocation p_i50023_3_, String p_i50023_4_, Ingredient p_i50023_5_, ItemStack p_i50023_6_) {
      this.field_222135_e = p_i50023_1_;
      this.field_222136_f = p_i50023_2_;
      this.field_222133_c = p_i50023_3_;
      this.field_222134_d = p_i50023_4_;
      this.field_222131_a = p_i50023_5_;
      this.field_222132_b = p_i50023_6_;
   }

   public IRecipeType<?> func_222127_g() {
      return this.field_222135_e;
   }

   public IRecipeSerializer<?> getSerializer() {
      return this.field_222136_f;
   }

   public ResourceLocation getId() {
      return this.field_222133_c;
   }

   /**
    * Recipes with equal group are combined into one button in the recipe book
    */
   @OnlyIn(Dist.CLIENT)
   public String getGroup() {
      return this.field_222134_d;
   }

   /**
    * Get the result of this recipe, usually for display purposes (e.g. recipe book). If your recipe has more than one
    * possible result (e.g. it's dynamic and depends on its inputs), then return an empty stack.
    */
   public ItemStack getRecipeOutput() {
      return this.field_222132_b;
   }

   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> nonnulllist = NonNullList.create();
      nonnulllist.add(this.field_222131_a);
      return nonnulllist;
   }

   /**
    * Used to determine if this recipe can fit in a grid of the given width/height
    */
   @OnlyIn(Dist.CLIENT)
   public boolean canFit(int width, int height) {
      return true;
   }

   /**
    * Returns an Item that is the result of this recipe
    */
   public ItemStack getCraftingResult(IInventory inv) {
      return this.field_222132_b.copy();
   }

   public static class Serializer<T extends SingleItemRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
      final SingleItemRecipe.Serializer.IRecipeFactory<T> field_222180_t;

      protected Serializer(SingleItemRecipe.Serializer.IRecipeFactory<T> p_i50146_1_) {
         this.field_222180_t = p_i50146_1_;
      }

      public T read(ResourceLocation recipeId, JsonObject json) {
         String s = JSONUtils.getString(json, "group", "");
         Ingredient ingredient;
         if (JSONUtils.isJsonArray(json, "ingredient")) {
            ingredient = Ingredient.deserialize(JSONUtils.getJsonArray(json, "ingredient"));
         } else {
            ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
         }

         String s1 = JSONUtils.getString(json, "result");
         int i = JSONUtils.getInt(json, "count");
         ItemStack itemstack = new ItemStack(Registry.field_212630_s.getOrDefault(new ResourceLocation(s1)), i);
         return this.field_222180_t.create(recipeId, s, ingredient, itemstack);
      }

      public T read(ResourceLocation recipeId, PacketBuffer buffer) {
         String s = buffer.readString(32767);
         Ingredient ingredient = Ingredient.read(buffer);
         ItemStack itemstack = buffer.readItemStack();
         return this.field_222180_t.create(recipeId, s, ingredient, itemstack);
      }

      public void write(PacketBuffer buffer, T recipe) {
         buffer.writeString(recipe.field_222134_d);
         recipe.field_222131_a.write(buffer);
         buffer.writeItemStack(recipe.field_222132_b);
      }

      interface IRecipeFactory<T extends SingleItemRecipe> {
         T create(ResourceLocation p_create_1_, String p_create_2_, Ingredient p_create_3_, ItemStack p_create_4_);
      }
   }
}