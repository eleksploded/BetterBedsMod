package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SingleRandomFeature implements IFeatureConfig {
   public final List<ConfiguredFeature<?>> field_204628_a;

   public SingleRandomFeature(List<ConfiguredFeature<?>> p_i51437_1_) {
      this.field_204628_a = p_i51437_1_;
   }

   public SingleRandomFeature(Feature<?>[] featuresIn, IFeatureConfig[] configsIn) {
      this(IntStream.range(0, featuresIn.length).mapToObj((p_214666_2_) -> {
         return func_214667_a(featuresIn[p_214666_2_], configsIn[p_214666_2_]);
      }).collect(Collectors.toList()));
   }

   private static <FC extends IFeatureConfig> ConfiguredFeature<FC> func_214667_a(Feature<FC> p_214667_0_, IFeatureConfig p_214667_1_) {
      return new ConfiguredFeature<>(p_214667_0_, (FC)p_214667_1_);
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("features"), p_214634_1_.createList(this.field_204628_a.stream().map((p_214665_1_) -> {
         return p_214665_1_.serialize(p_214634_1_).getValue();
      })))));
   }

   public static <T> SingleRandomFeature func_214664_a(Dynamic<T> p_214664_0_) {
      List<ConfiguredFeature<?>> list = p_214664_0_.get("features").asList(ConfiguredFeature::deserialize);
      return new SingleRandomFeature(list);
   }
}