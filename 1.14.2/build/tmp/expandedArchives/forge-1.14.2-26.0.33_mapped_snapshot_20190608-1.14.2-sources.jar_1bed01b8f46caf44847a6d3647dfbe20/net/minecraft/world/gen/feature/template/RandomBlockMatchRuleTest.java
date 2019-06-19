package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class RandomBlockMatchRuleTest extends RuleTest {
   private final Block field_215185_a;
   private final float field_215186_b;

   public RandomBlockMatchRuleTest(Block p_i51324_1_, float p_i51324_2_) {
      this.field_215185_a = p_i51324_1_;
      this.field_215186_b = p_i51324_2_;
   }

   public <T> RandomBlockMatchRuleTest(Dynamic<T> p_i51325_1_) {
      this(Registry.field_212618_g.getOrDefault(new ResourceLocation(p_i51325_1_.get("block").asString(""))), p_i51325_1_.get("probability").asFloat(1.0F));
   }

   public boolean test(BlockState p_215181_1_, Random p_215181_2_) {
      return p_215181_1_.getBlock() == this.field_215185_a && p_215181_2_.nextFloat() < this.field_215186_b;
   }

   protected IRuleTestType getType() {
      return IRuleTestType.field_214915_f;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215182_1_) {
      return new Dynamic<>(p_215182_1_, p_215182_1_.createMap(ImmutableMap.of(p_215182_1_.createString("block"), p_215182_1_.createString(Registry.field_212618_g.getKey(this.field_215185_a).toString()), p_215182_1_.createString("probability"), p_215182_1_.createFloat(this.field_215186_b))));
   }
}