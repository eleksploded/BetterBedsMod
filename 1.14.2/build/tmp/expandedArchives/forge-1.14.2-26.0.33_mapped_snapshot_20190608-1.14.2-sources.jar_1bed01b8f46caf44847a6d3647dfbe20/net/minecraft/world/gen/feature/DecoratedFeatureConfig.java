package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class DecoratedFeatureConfig implements IFeatureConfig {
   public final ConfiguredFeature<?> feature;
   public final ConfiguredPlacement<?> placement;

   public DecoratedFeatureConfig(ConfiguredFeature<?> p_i49891_1_, ConfiguredPlacement<?> p_i49891_2_) {
      this.feature = p_i49891_1_;
      this.placement = p_i49891_2_;
   }

   public <F extends IFeatureConfig, D extends IPlacementConfig> DecoratedFeatureConfig(Feature<F> p_i49892_1_, F p_i49892_2_, Placement<D> p_i49892_3_, D p_i49892_4_) {
      this(new ConfiguredFeature<>(p_i49892_1_, p_i49892_2_), new ConfiguredPlacement<>(p_i49892_3_, p_i49892_4_));
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("feature"), this.feature.serialize(p_214634_1_).getValue(), p_214634_1_.createString("decorator"), this.placement.func_215094_a(p_214634_1_).getValue())));
   }

   public String toString() {
      return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.field_218379_q.getKey(this.feature.feature), Registry.field_218380_r.getKey(this.placement.placement));
   }

   public static <T> DecoratedFeatureConfig func_214688_a(Dynamic<T> p_214688_0_) {
      ConfiguredFeature<?> configuredfeature = ConfiguredFeature.deserialize(p_214688_0_.get("feature").orElseEmptyMap());
      ConfiguredPlacement<?> configuredplacement = ConfiguredPlacement.func_215095_a(p_214688_0_.get("decorator").orElseEmptyMap());
      return new DecoratedFeatureConfig(configuredfeature, configuredplacement);
   }
}