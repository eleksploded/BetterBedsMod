package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;

public class TwiceSurfaceWithChance extends Placement<ChanceConfig> {
   public TwiceSurfaceWithChance(Function<Dynamic<?>, ? extends ChanceConfig> p_i51394_1_) {
      super(p_i51394_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, ChanceConfig p_212848_4_, BlockPos p_212848_5_) {
      if (p_212848_3_.nextFloat() < 1.0F / (float)p_212848_4_.chance) {
         int i = p_212848_3_.nextInt(16);
         int j = p_212848_3_.nextInt(16);
         int k = p_212848_1_.getHeight(Heightmap.Type.MOTION_BLOCKING, p_212848_5_.add(i, 0, j)).getY() * 2;
         if (k <= 0) {
            return Stream.empty();
         } else {
            int l = p_212848_3_.nextInt(k);
            return Stream.of(p_212848_5_.add(i, l, j));
         }
      } else {
         return Stream.empty();
      }
   }
}