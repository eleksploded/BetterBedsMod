package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;

public class AtSurfaceWithChanceMultiple extends Placement<HeightWithChanceConfig> {
   public AtSurfaceWithChanceMultiple(Function<Dynamic<?>, ? extends HeightWithChanceConfig> p_i51387_1_) {
      super(p_i51387_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, HeightWithChanceConfig p_212848_4_, BlockPos p_212848_5_) {
      return IntStream.range(0, p_212848_4_.height).filter((p_215043_2_) -> {
         return p_212848_3_.nextFloat() < p_212848_4_.chance;
      }).mapToObj((p_215042_3_) -> {
         int i = p_212848_3_.nextInt(16);
         int j = p_212848_3_.nextInt(16);
         return p_212848_1_.getHeight(Heightmap.Type.MOTION_BLOCKING, p_212848_5_.add(i, 0, j));
      });
   }
}