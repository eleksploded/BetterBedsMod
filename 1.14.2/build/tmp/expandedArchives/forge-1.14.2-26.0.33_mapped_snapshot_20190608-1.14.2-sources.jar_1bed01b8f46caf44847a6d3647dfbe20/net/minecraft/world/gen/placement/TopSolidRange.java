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

public class TopSolidRange extends Placement<TopSolidRangeConfig> {
   public TopSolidRange(Function<Dynamic<?>, ? extends TopSolidRangeConfig> p_i51359_1_) {
      super(p_i51359_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, TopSolidRangeConfig p_212848_4_, BlockPos p_212848_5_) {
      int i = p_212848_3_.nextInt(p_212848_4_.maxCount - p_212848_4_.minCount) + p_212848_4_.minCount;
      return IntStream.range(0, i).mapToObj((p_215064_3_) -> {
         int j = p_212848_3_.nextInt(16);
         int k = p_212848_3_.nextInt(16);
         int l = p_212848_1_.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, p_212848_5_.getX() + j, p_212848_5_.getZ() + k);
         return new BlockPos(p_212848_5_.getX() + j, l, p_212848_5_.getZ() + k);
      });
   }
}