package net.minecraft.world.gen.feature.template;

import net.minecraft.util.IDynamicDeserializer;
import net.minecraft.util.registry.Registry;

public interface IRuleTestType extends IDynamicDeserializer<RuleTest> {
   IRuleTestType field_214911_b = func_214910_a("always_true", (p_214909_0_) -> {
      return AlwaysTrueRuleTest.field_215190_a;
   });
   IRuleTestType field_214912_c = func_214910_a("block_match", BlockMatchRuleTest::new);
   IRuleTestType field_214913_d = func_214910_a("blockstate_match", BlockStateMatchRuleTest::new);
   IRuleTestType field_214914_e = func_214910_a("tag_match", TagMatchRuleTest::new);
   IRuleTestType field_214915_f = func_214910_a("random_block_match", RandomBlockMatchRuleTest::new);
   IRuleTestType field_214916_g = func_214910_a("random_blockstate_match", RandomBlockStateMatchRuleTest::new);

   static IRuleTestType func_214910_a(String p_214910_0_, IRuleTestType p_214910_1_) {
      return Registry.register(Registry.field_218363_D, p_214910_0_, p_214910_1_);
   }
}