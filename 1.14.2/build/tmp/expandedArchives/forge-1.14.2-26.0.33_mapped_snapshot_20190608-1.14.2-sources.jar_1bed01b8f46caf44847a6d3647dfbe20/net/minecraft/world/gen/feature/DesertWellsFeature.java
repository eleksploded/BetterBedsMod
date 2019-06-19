package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;

public class DesertWellsFeature extends Feature<NoFeatureConfig> {
   private static final BlockStateMatcher field_175913_a = BlockStateMatcher.forBlock(Blocks.SAND);
   private final BlockState field_175911_b = Blocks.SANDSTONE_SLAB.getDefaultState();
   private final BlockState field_175912_c = Blocks.SANDSTONE.getDefaultState();
   private final BlockState field_175910_d = Blocks.WATER.getDefaultState();

   public DesertWellsFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49887_1_) {
      super(p_i49887_1_);
   }

   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      for(pos = pos.up(); worldIn.isAirBlock(pos) && pos.getY() > 2; pos = pos.down()) {
         ;
      }

      if (!field_175913_a.test(worldIn.getBlockState(pos))) {
         return false;
      } else {
         for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
               if (worldIn.isAirBlock(pos.add(i, -1, j)) && worldIn.isAirBlock(pos.add(i, -2, j))) {
                  return false;
               }
            }
         }

         for(int l = -1; l <= 0; ++l) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               for(int k = -2; k <= 2; ++k) {
                  worldIn.setBlockState(pos.add(l1, l, k), this.field_175912_c, 2);
               }
            }
         }

         worldIn.setBlockState(pos, this.field_175910_d, 2);

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            worldIn.setBlockState(pos.offset(direction), this.field_175910_d, 2);
         }

         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int i2 = -2; i2 <= 2; ++i2) {
               if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                  worldIn.setBlockState(pos.add(i1, 1, i2), this.field_175912_c, 2);
               }
            }
         }

         worldIn.setBlockState(pos.add(2, 1, 0), this.field_175911_b, 2);
         worldIn.setBlockState(pos.add(-2, 1, 0), this.field_175911_b, 2);
         worldIn.setBlockState(pos.add(0, 1, 2), this.field_175911_b, 2);
         worldIn.setBlockState(pos.add(0, 1, -2), this.field_175911_b, 2);

         for(int j1 = -1; j1 <= 1; ++j1) {
            for(int j2 = -1; j2 <= 1; ++j2) {
               if (j1 == 0 && j2 == 0) {
                  worldIn.setBlockState(pos.add(j1, 4, j2), this.field_175912_c, 2);
               } else {
                  worldIn.setBlockState(pos.add(j1, 4, j2), this.field_175911_b, 2);
               }
            }
         }

         for(int k1 = 1; k1 <= 3; ++k1) {
            worldIn.setBlockState(pos.add(-1, k1, -1), this.field_175912_c, 2);
            worldIn.setBlockState(pos.add(-1, k1, 1), this.field_175912_c, 2);
            worldIn.setBlockState(pos.add(1, k1, -1), this.field_175912_c, 2);
            worldIn.setBlockState(pos.add(1, k1, 1), this.field_175912_c, 2);
         }

         return true;
      }
   }
}