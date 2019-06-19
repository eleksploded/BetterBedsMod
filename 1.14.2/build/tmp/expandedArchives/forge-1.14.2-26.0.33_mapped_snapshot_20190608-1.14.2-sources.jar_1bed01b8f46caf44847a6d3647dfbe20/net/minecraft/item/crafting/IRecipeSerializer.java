package net.minecraft.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IRecipeSerializer<T extends IRecipe<?>> extends net.minecraftforge.registries.IForgeRegistryEntry<IRecipeSerializer<?>> {
   IRecipeSerializer<ShapedRecipe> field_222157_a = func_222156_a("crafting_shaped", new ShapedRecipe.Serializer());
   IRecipeSerializer<ShapelessRecipe> field_222158_b = func_222156_a("crafting_shapeless", new ShapelessRecipe.Serializer());
   SpecialRecipeSerializer<ArmorDyeRecipe> field_222159_c = func_222156_a("crafting_special_armordye", new SpecialRecipeSerializer<>(ArmorDyeRecipe::new));
   SpecialRecipeSerializer<BookCloningRecipe> field_222160_d = func_222156_a("crafting_special_bookcloning", new SpecialRecipeSerializer<>(BookCloningRecipe::new));
   SpecialRecipeSerializer<MapCloningRecipe> field_222161_e = func_222156_a("crafting_special_mapcloning", new SpecialRecipeSerializer<>(MapCloningRecipe::new));
   SpecialRecipeSerializer<MapExtendingRecipe> field_222162_f = func_222156_a("crafting_special_mapextending", new SpecialRecipeSerializer<>(MapExtendingRecipe::new));
   SpecialRecipeSerializer<FireworkRocketRecipe> field_222163_g = func_222156_a("crafting_special_firework_rocket", new SpecialRecipeSerializer<>(FireworkRocketRecipe::new));
   SpecialRecipeSerializer<FireworkStarRecipe> field_222164_h = func_222156_a("crafting_special_firework_star", new SpecialRecipeSerializer<>(FireworkStarRecipe::new));
   SpecialRecipeSerializer<FireworkStarFadeRecipe> field_222165_i = func_222156_a("crafting_special_firework_star_fade", new SpecialRecipeSerializer<>(FireworkStarFadeRecipe::new));
   SpecialRecipeSerializer<TippedArrowRecipe> field_222166_j = func_222156_a("crafting_special_tippedarrow", new SpecialRecipeSerializer<>(TippedArrowRecipe::new));
   SpecialRecipeSerializer<BannerDuplicateRecipe> field_222167_k = func_222156_a("crafting_special_bannerduplicate", new SpecialRecipeSerializer<>(BannerDuplicateRecipe::new));
   SpecialRecipeSerializer<ShieldRecipes> field_222168_l = func_222156_a("crafting_special_shielddecoration", new SpecialRecipeSerializer<>(ShieldRecipes::new));
   SpecialRecipeSerializer<ShulkerBoxColoringRecipe> field_222169_m = func_222156_a("crafting_special_shulkerboxcoloring", new SpecialRecipeSerializer<>(ShulkerBoxColoringRecipe::new));
   SpecialRecipeSerializer<SuspiciousStewRecipe> field_222170_n = func_222156_a("crafting_special_suspiciousstew", new SpecialRecipeSerializer<>(SuspiciousStewRecipe::new));
   CookingRecipeSerializer<FurnaceRecipe> field_222171_o = func_222156_a("smelting", new CookingRecipeSerializer<>(FurnaceRecipe::new, 200));
   CookingRecipeSerializer<BlastingRecipe> field_222172_p = func_222156_a("blasting", new CookingRecipeSerializer<>(BlastingRecipe::new, 100));
   CookingRecipeSerializer<SmokingRecipe> field_222173_q = func_222156_a("smoking", new CookingRecipeSerializer<>(SmokingRecipe::new, 100));
   CookingRecipeSerializer<CampfireCookingRecipe> field_222174_r = func_222156_a("campfire_cooking", new CookingRecipeSerializer<>(CampfireCookingRecipe::new, 100));
   IRecipeSerializer<StonecuttingRecipe> field_222175_s = func_222156_a("stonecutting", new SingleItemRecipe.Serializer<>(StonecuttingRecipe::new));

   T read(ResourceLocation recipeId, JsonObject json);

   T read(ResourceLocation recipeId, PacketBuffer buffer);

   void write(PacketBuffer buffer, T recipe);

   static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S func_222156_a(String p_222156_0_, S p_222156_1_) {
      return (S)(Registry.<IRecipeSerializer<?>>register(Registry.field_218368_I, p_222156_0_, p_222156_1_));
   }
}