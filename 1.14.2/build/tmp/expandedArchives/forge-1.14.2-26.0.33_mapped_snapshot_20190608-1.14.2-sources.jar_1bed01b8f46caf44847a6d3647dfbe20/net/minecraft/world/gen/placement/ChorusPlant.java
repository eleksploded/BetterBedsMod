package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Objects;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;

public class ChorusPlant extends Placement<NoPlacementConfig> {
   public ChorusPlant(Function<Dynamic<?>, ? extends NoPlacementConfig> p_i51391_1_) {
      super(p_i51391_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, NoPlacementConfig p_212848_4_, BlockPos p_212848_5_) {
      int i = p_212848_3_.nextInt(5);
      return IntStream.range(0, i).mapToObj((p_215041_3_) -> {
         int j = p_212848_3_.nextInt(16);
         int k = p_212848_3_.nextInt(16);
         int l = p_212848_1_.getHeight(Heightmap.Type.MOTION_BLOCKING, p_212848_5_.add(j, 0, k)).getY();
         if (l > 0) {
            int i1 = l - 1;
            return new BlockPos(p_212848_5_.getX() + j, i1, p_212848_5_.getZ() + k);
         } else {
            return null;
         }
      }).filter(Objects::nonNull);
   }
}