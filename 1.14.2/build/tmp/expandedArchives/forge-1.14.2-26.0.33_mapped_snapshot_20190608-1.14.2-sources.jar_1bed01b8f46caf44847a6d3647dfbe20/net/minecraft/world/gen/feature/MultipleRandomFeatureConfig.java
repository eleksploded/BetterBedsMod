package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultipleRandomFeatureConfig implements IFeatureConfig {
   public final List<ConfiguredRandomFeatureList<?>> field_202449_a;
   public final ConfiguredFeature<?> field_202452_d;

   public MultipleRandomFeatureConfig(List<ConfiguredRandomFeatureList<?>> p_i51455_1_, ConfiguredFeature<?> p_i51455_2_) {
      this.field_202449_a = p_i51455_1_;
      this.field_202452_d = p_i51455_2_;
   }

   public MultipleRandomFeatureConfig(Feature<?>[] p_i48671_1_, IFeatureConfig[] p_i48671_2_, float[] p_i48671_3_, Feature<?> p_i48671_4_, IFeatureConfig p_i48671_5_) {
      this(IntStream.range(0, p_i48671_1_.length).mapToObj((p_214652_3_) -> {
         return func_214650_a(p_i48671_1_[p_214652_3_], p_i48671_2_[p_214652_3_], p_i48671_3_[p_214652_3_]);
      }).collect(Collectors.toList()), func_214651_a(p_i48671_4_, p_i48671_5_));
   }

   private static <FC extends IFeatureConfig> ConfiguredRandomFeatureList<FC> func_214650_a(Feature<FC> p_214650_0_, IFeatureConfig p_214650_1_, float p_214650_2_) {
      return new ConfiguredRandomFeatureList<>(p_214650_0_, (FC)p_214650_1_, Float.valueOf(p_214650_2_));
   }

   private static <FC extends IFeatureConfig> ConfiguredFeature<FC> func_214651_a(Feature<FC> p_214651_0_, IFeatureConfig p_214651_1_) {
      return new ConfiguredFeature<>(p_214651_0_, (FC)p_214651_1_);
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      T t = p_214634_1_.createList(this.field_202449_a.stream().map((p_214649_1_) -> {
         return p_214649_1_.func_214841_a(p_214634_1_).getValue();
      }));
      T t1 = this.field_202452_d.serialize(p_214634_1_).getValue();
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("features"), t, p_214634_1_.createString("default"), t1)));
   }

   public static <T> MultipleRandomFeatureConfig func_214648_a(Dynamic<T> p_214648_0_) {
      List<ConfiguredRandomFeatureList<?>> list = p_214648_0_.get("features").asList(ConfiguredRandomFeatureList::func_214840_a);
      ConfiguredFeature<?> configuredfeature = ConfiguredFeature.deserialize(p_214648_0_.get("default").orElseEmptyMap());
      return new MultipleRandomFeatureConfig(list, configuredfeature);
   }
}