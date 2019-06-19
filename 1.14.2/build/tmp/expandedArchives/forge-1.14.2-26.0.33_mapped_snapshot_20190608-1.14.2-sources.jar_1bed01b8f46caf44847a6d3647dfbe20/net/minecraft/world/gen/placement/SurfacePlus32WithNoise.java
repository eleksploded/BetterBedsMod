package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;

public class SurfacePlus32WithNoise extends Placement<NoiseDependant> {
   public SurfacePlus32WithNoise(Function<Dynamic<?>, ? extends NoiseDependant> p_i51365_1_) {
      super(p_i51365_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, NoiseDependant p_212848_4_, BlockPos p_212848_5_) {
      double d0 = Biome.field_180281_af.getValue((double)p_212848_5_.getX() / 200.0D, (double)p_212848_5_.getZ() / 200.0D);
      int i = d0 < p_212848_4_.noiseThreshold ? p_212848_4_.lowNoiseCount : p_212848_4_.highNoiseCount;
      return IntStream.range(0, i).mapToObj((p_215054_3_) -> {
         int j = p_212848_3_.nextInt(16);
         int k = p_212848_3_.nextInt(16);
         int l = p_212848_1_.getHeight(Heightmap.Type.MOTION_BLOCKING, p_212848_5_.add(j, 0, k)).getY() + 32;
         if (l <= 0) {
            return null;
         } else {
            int i1 = p_212848_3_.nextInt(l);
            return p_212848_5_.add(j, i1, k);
         }
      }).filter(Objects::nonNull);
   }
}