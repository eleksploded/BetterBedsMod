package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

public interface IFeatureConfig {
   NoFeatureConfig NO_FEATURE_CONFIG = new NoFeatureConfig();

   <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_);
}