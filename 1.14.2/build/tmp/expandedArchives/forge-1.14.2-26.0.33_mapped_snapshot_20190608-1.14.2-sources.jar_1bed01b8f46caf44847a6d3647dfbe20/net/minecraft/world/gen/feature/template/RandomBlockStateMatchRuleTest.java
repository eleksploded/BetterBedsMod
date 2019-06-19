package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.BlockState;

public class RandomBlockStateMatchRuleTest extends RuleTest {
   private final BlockState field_215187_a;
   private final float field_215188_b;

   public RandomBlockStateMatchRuleTest(BlockState p_i51322_1_, float p_i51322_2_) {
      this.field_215187_a = p_i51322_1_;
      this.field_215188_b = p_i51322_2_;
   }

   public <T> RandomBlockStateMatchRuleTest(Dynamic<T> p_i51323_1_) {
      this(BlockState.func_215698_a(p_i51323_1_.get("blockstate").orElseEmptyMap()), p_i51323_1_.get("probability").asFloat(1.0F));
   }

   public boolean test(BlockState p_215181_1_, Random p_215181_2_) {
      return p_215181_1_ == this.field_215187_a && p_215181_2_.nextFloat() < this.field_215188_b;
   }

   protected IRuleTestType getType() {
      return IRuleTestType.field_214916_g;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215182_1_) {
      return new Dynamic<>(p_215182_1_, p_215182_1_.createMap(ImmutableMap.of(p_215182_1_.createString("blockstate"), BlockState.func_215689_a(p_215182_1_, this.field_215187_a).getValue(), p_215182_1_.createString("probability"), p_215182_1_.createFloat(this.field_215188_b))));
   }
}