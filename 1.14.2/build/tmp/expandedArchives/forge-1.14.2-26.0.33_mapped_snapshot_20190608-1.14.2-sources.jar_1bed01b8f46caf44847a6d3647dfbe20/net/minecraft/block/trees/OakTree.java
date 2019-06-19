package net.minecraft.block.trees;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BigTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;

public class OakTree extends Tree {
   @Nullable
   protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
      return (AbstractTreeFeature<NoFeatureConfig>)(random.nextInt(10) == 0 ? new BigTreeFeature(NoFeatureConfig::func_214639_a, true) : new TreeFeature(NoFeatureConfig::func_214639_a, true));
   }
}