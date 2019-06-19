package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class ConfiguredFeature<FC extends IFeatureConfig> {
   public final Feature<FC> feature;
   public final FC config;

   public ConfiguredFeature(Feature<FC> p_i49900_1_, FC p_i49900_2_) {
      this.feature = p_i49900_1_;
      this.config = p_i49900_2_;
   }

   public ConfiguredFeature(Feature<FC> p_i49901_1_, Dynamic<?> p_i49901_2_) {
      this(p_i49901_1_, p_i49901_1_.createConfig(p_i49901_2_));
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> p_222735_1_) {
      return new Dynamic<>(p_222735_1_, p_222735_1_.createMap(ImmutableMap.of(p_222735_1_.createString("name"), p_222735_1_.createString(Registry.field_218379_q.getKey(this.feature).toString()), p_222735_1_.createString("config"), this.config.func_214634_a(p_222735_1_).getValue())));
   }

   public boolean place(IWorld p_222734_1_, ChunkGenerator<? extends GenerationSettings> p_222734_2_, Random p_222734_3_, BlockPos p_222734_4_) {
      return this.feature.place(p_222734_1_, p_222734_2_, p_222734_3_, p_222734_4_, this.config);
   }

   public static <T> ConfiguredFeature<?> deserialize(Dynamic<T> p_222736_0_) {
      Feature<? extends IFeatureConfig> feature = Registry.field_218379_q.getOrDefault(new ResourceLocation(p_222736_0_.get("name").asString("")));
      return new ConfiguredFeature<>(feature, p_222736_0_.get("config").orElseEmptyMap());
   }
}