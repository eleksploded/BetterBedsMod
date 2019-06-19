package net.minecraft.world.gen.feature.jigsaw;

import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.registry.Registry;

public interface IJigsawDeserializer extends IDynamicDeserializer<JigsawPiece> {
   IJigsawDeserializer field_214928_b = func_214926_a("single_pool_element", SingleJigsawPiece::new);
   IJigsawDeserializer field_214929_c = func_214926_a("list_pool_element", ListJigsawPiece::new);
   IJigsawDeserializer field_214930_d = func_214926_a("feature_pool_element", FeatureJigsawPiece::new);
   IJigsawDeserializer field_214931_e = func_214926_a("empty_pool_element", (p_214927_0_) -> {
      return EmptyJigsawPiece.field_214856_a;
   });

   static IJigsawDeserializer func_214926_a(String p_214926_0_, IJigsawDeserializer p_214926_1_) {
      return Registry.register(Registry.field_218365_F, p_214926_0_, p_214926_1_);
   }
}