package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class LakeWater extends Placement<LakeChanceConfig> {
   public LakeWater(Function<Dynamic<?>, ? extends LakeChanceConfig> p_i51367_1_) {
      super(p_i51367_1_);
   }

   public Stream<BlockPos> getPositions(IWorld p_212848_1_, ChunkGenerator<? extends GenerationSettings> p_212848_2_, Random p_212848_3_, LakeChanceConfig p_212848_4_, BlockPos p_212848_5_) {
      if (p_212848_3_.nextInt(p_212848_4_.chance) == 0) {
         int i = p_212848_3_.nextInt(16);
         int j = p_212848_3_.nextInt(p_212848_2_.getMaxHeight());
         int k = p_212848_3_.nextInt(16);
         return Stream.of(p_212848_5_.add(i, j, k));
      } else {
         return Stream.empty();
      }
   }
}