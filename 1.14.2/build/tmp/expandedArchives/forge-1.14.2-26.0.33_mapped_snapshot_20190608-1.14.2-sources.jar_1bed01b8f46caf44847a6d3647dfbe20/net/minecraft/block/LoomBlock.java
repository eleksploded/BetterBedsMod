package net.minecraft.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.LoomContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class LoomBlock extends HorizontalBlock {
   private static final TranslationTextComponent field_220255_a = new TranslationTextComponent("container.loom");

   protected LoomBlock(Block.Properties p_i49978_1_) {
      super(p_i49978_1_);
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      if (p_220051_2_.isRemote) {
         return true;
      } else {
         p_220051_4_.openContainer(p_220051_1_.getContainer(p_220051_2_, p_220051_3_));
         p_220051_4_.addStat(Stats.field_219738_av);
         return true;
      }
   }

   public INamedContainerProvider getContainer(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
      return new SimpleNamedContainerProvider((p_220254_2_, p_220254_3_, p_220254_4_) -> {
         return new LoomContainer(p_220254_2_, p_220254_3_, IWorldPosCallable.of(p_220052_2_, p_220052_3_));
      }, field_220255_a);
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(HORIZONTAL_FACING);
   }
}