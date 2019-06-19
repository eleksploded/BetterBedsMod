package net.minecraft.world.gen.feature.template;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.BlockState;

public class AlwaysTrueRuleTest extends RuleTest {
   public static final AlwaysTrueRuleTest field_215190_a = new AlwaysTrueRuleTest();

   public boolean test(BlockState p_215181_1_, Random p_215181_2_) {
      return true;
   }

   protected IRuleTestType getType() {
      return IRuleTestType.field_214911_b;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215182_1_) {
      return new Dynamic<>(p_215182_1_, p_215182_1_.emptyMap());
   }
}