package net.minecraft.world.storage.loot;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;

public class LootParameterSets {
   private static final BiMap<ResourceLocation, LootParameterSet> field_216268_i = HashBiMap.create();
   public static final LootParameterSet field_216260_a = func_216253_a("empty", (p_216249_0_) -> {
   });
   public static final LootParameterSet field_216261_b = func_216253_a("chest", (p_216259_0_) -> {
      p_216259_0_.required(LootParameters.field_216286_f).optional(LootParameters.field_216281_a);
      p_216259_0_.optional(LootParameters.field_216284_d); //Forge: Chest Minecarts can have killers.
   });
   public static final LootParameterSet field_216262_c = func_216253_a("fishing", (p_216250_0_) -> {
      p_216250_0_.required(LootParameters.field_216286_f).required(LootParameters.field_216289_i);
      p_216250_0_.optional(LootParameters.field_216284_d).optional(LootParameters.field_216281_a); //Forge: Allow fisher, and bobber
   });
   public static final LootParameterSet field_216263_d = func_216253_a("entity", (p_216254_0_) -> {
      p_216254_0_.required(LootParameters.field_216281_a).required(LootParameters.field_216286_f).required(LootParameters.field_216283_c).optional(LootParameters.field_216284_d).optional(LootParameters.field_216285_e).optional(LootParameters.field_216282_b);
   });
   public static final LootParameterSet field_216264_e = func_216253_a("gift", (p_216258_0_) -> {
      p_216258_0_.required(LootParameters.field_216286_f).required(LootParameters.field_216281_a);
   });
   public static final LootParameterSet field_216265_f = func_216253_a("advancement_reward", (p_216251_0_) -> {
      p_216251_0_.required(LootParameters.field_216281_a).required(LootParameters.field_216286_f);
   });
   public static final LootParameterSet field_216266_g = func_216253_a("generic", (p_216255_0_) -> {
      p_216255_0_.required(LootParameters.field_216281_a).required(LootParameters.field_216282_b).required(LootParameters.field_216283_c).required(LootParameters.field_216284_d).required(LootParameters.field_216285_e).required(LootParameters.field_216286_f).required(LootParameters.field_216287_g).required(LootParameters.field_216288_h).required(LootParameters.field_216289_i).required(LootParameters.field_216290_j);
   });
   public static final LootParameterSet field_216267_h = func_216253_a("block", (p_216252_0_) -> {
      p_216252_0_.required(LootParameters.field_216287_g).required(LootParameters.field_216286_f).required(LootParameters.field_216289_i).optional(LootParameters.field_216281_a).optional(LootParameters.field_216288_h).optional(LootParameters.field_216290_j);
   });

   private static LootParameterSet func_216253_a(String p_216253_0_, Consumer<LootParameterSet.Builder> p_216253_1_) {
      LootParameterSet.Builder lootparameterset$builder = new LootParameterSet.Builder();
      p_216253_1_.accept(lootparameterset$builder);
      LootParameterSet lootparameterset = lootparameterset$builder.build();
      ResourceLocation resourcelocation = new ResourceLocation(p_216253_0_);
      LootParameterSet lootparameterset1 = field_216268_i.put(resourcelocation, lootparameterset);
      if (lootparameterset1 != null) {
         throw new IllegalStateException("Loot table parameter set " + resourcelocation + " is already registered");
      } else {
         return lootparameterset;
      }
   }

   @Nullable
   public static LootParameterSet func_216256_a(ResourceLocation p_216256_0_) {
      return field_216268_i.get(p_216256_0_);
   }

   @Nullable
   public static ResourceLocation func_216257_a(LootParameterSet p_216257_0_) {
      return field_216268_i.inverse().get(p_216257_0_);
   }
}