package net.minecraft.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IWorldGenerationReader;

public class ShrubFeature extends AbstractTreeFeature<NoFeatureConfig> {
   private final BlockState field_150528_a;
   private final BlockState field_150527_b;

   public ShrubFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49868_1_, BlockState p_i49868_2_, BlockState p_i49868_3_) {
      super(p_i49868_1_, false);
      this.field_150527_b = p_i49868_2_;
      this.field_150528_a = p_i49868_3_;
      setSapling((net.minecraftforge.common.IPlantable)net.minecraft.block.Blocks.JUNGLE_SAPLING);
   }

   public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
      position = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, position).down();
      if (isSoil(worldIn, position, getSapling())) {
         position = position.up();
         this.setLogState(changedBlocks, worldIn, position, this.field_150527_b, p_208519_5_);

         for(int i = position.getY(); i <= position.getY() + 2; ++i) {
            int j = i - position.getY();
            int k = 2 - j;

            for(int l = position.getX() - k; l <= position.getX() + k; ++l) {
               int i1 = l - position.getX();

               for(int j1 = position.getZ() - k; j1 <= position.getZ() + k; ++j1) {
                  int k1 = j1 - position.getZ();
                  if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0) {
                     BlockPos blockpos = new BlockPos(l, i, j1);
                     if (func_214572_g(worldIn, blockpos)) {
                        this.setLogState(changedBlocks, worldIn, blockpos, this.field_150528_a, p_208519_5_);
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}