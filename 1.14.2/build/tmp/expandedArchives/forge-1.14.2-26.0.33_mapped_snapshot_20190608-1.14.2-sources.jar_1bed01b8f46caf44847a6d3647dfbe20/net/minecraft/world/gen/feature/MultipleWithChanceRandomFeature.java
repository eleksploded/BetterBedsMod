package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class MultipleWithChanceRandomFeature extends Feature<MultipleRandomFeatureConfig> {
   public MultipleWithChanceRandomFeature(Function<Dynamic<?>, ? extends MultipleRandomFeatureConfig> p_i51447_1_) {
      super(p_i51447_1_);
   }

   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, MultipleRandomFeatureConfig config) {
      for(ConfiguredRandomFeatureList<?> configuredrandomfeaturelist : config.field_202449_a) {
         if (rand.nextFloat() < configuredrandomfeaturelist.field_214844_c) {
            return configuredrandomfeaturelist.func_214839_a(worldIn, generator, rand, pos);
         }
      }

      return config.field_202452_d.place(worldIn, generator, rand, pos);
   }
}