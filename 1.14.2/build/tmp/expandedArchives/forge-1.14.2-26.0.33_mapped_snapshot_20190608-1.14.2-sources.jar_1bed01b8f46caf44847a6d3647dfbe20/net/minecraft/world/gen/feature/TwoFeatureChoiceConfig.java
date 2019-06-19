package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public class TwoFeatureChoiceConfig implements IFeatureConfig {
   public final ConfiguredFeature<?> field_202445_a;
   public final ConfiguredFeature<?> field_202447_c;

   public TwoFeatureChoiceConfig(ConfiguredFeature<?> p_i51459_1_, ConfiguredFeature<?> p_i51459_2_) {
      this.field_202445_a = p_i51459_1_;
      this.field_202447_c = p_i51459_2_;
   }

   public TwoFeatureChoiceConfig(Feature<?> trueFeature, IFeatureConfig trueConfig, Feature<?> falseFeature, IFeatureConfig falseConfig) {
      this(func_214646_a(trueFeature, trueConfig), func_214646_a(falseFeature, falseConfig));
   }

   private static <FC extends IFeatureConfig> ConfiguredFeature<FC> func_214646_a(Feature<FC> p_214646_0_, IFeatureConfig p_214646_1_) {
      return new ConfiguredFeature<>(p_214646_0_, (FC)p_214646_1_);
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("feature_true"), this.field_202445_a.serialize(p_214634_1_).getValue(), p_214634_1_.createString("feature_false"), this.field_202447_c.serialize(p_214634_1_).getValue())));
   }

   public static <T> TwoFeatureChoiceConfig func_214647_a(Dynamic<T> p_214647_0_) {
      ConfiguredFeature<?> configuredfeature = ConfiguredFeature.deserialize(p_214647_0_.get("feature_true").orElseEmptyMap());
      ConfiguredFeature<?> configuredfeature1 = ConfiguredFeature.deserialize(p_214647_0_.get("feature_false").orElseEmptyMap());
      return new TwoFeatureChoiceConfig(configuredfeature, configuredfeature1);
   }
}