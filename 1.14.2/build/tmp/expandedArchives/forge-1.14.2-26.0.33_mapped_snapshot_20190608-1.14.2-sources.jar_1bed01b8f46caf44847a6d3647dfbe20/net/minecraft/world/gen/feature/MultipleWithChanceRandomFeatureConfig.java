package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultipleWithChanceRandomFeatureConfig implements IFeatureConfig {
   public final List<ConfiguredFeature<?>> field_202454_a;
   public final int count;

   public MultipleWithChanceRandomFeatureConfig(List<ConfiguredFeature<?>> p_i51451_1_, int p_i51451_2_) {
      this.field_202454_a = p_i51451_1_;
      this.count = p_i51451_2_;
   }

   public MultipleWithChanceRandomFeatureConfig(Feature<?>[] p_i48670_1_, IFeatureConfig[] p_i48670_2_, int p_i48670_3_) {
      this(IntStream.range(0, p_i48670_1_.length).mapToObj((p_214655_2_) -> {
         return func_214656_a(p_i48670_1_[p_214655_2_], p_i48670_2_[p_214655_2_]);
      }).collect(Collectors.toList()), p_i48670_3_);
   }

   private static <FC extends IFeatureConfig> ConfiguredFeature<?> func_214656_a(Feature<FC> p_214656_0_, IFeatureConfig p_214656_1_) {
      return new ConfiguredFeature<>(p_214656_0_, (FC)p_214656_1_);
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("features"), p_214634_1_.createList(this.field_202454_a.stream().map((p_214654_1_) -> {
         return p_214654_1_.serialize(p_214634_1_).getValue();
      })), p_214634_1_.createString("count"), p_214634_1_.createInt(this.count))));
   }

   public static <T> MultipleWithChanceRandomFeatureConfig func_214653_a(Dynamic<T> p_214653_0_) {
      List<ConfiguredFeature<?>> list = p_214653_0_.get("features").asList(ConfiguredFeature::deserialize);
      int i = p_214653_0_.get("count").asInt(0);
      return new MultipleWithChanceRandomFeatureConfig(list, i);
   }
}