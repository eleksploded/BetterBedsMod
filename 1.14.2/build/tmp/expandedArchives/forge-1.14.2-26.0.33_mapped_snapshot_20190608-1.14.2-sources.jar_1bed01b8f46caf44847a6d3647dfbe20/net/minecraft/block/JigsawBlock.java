package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.JigsawTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.template.Template;

public class JigsawBlock extends DirectionalBlock implements ITileEntityProvider {
   protected JigsawBlock(Block.Properties p_i49981_1_) {
      super(p_i49981_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP));
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(FACING);
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   public BlockState rotate(BlockState state, Rotation rot) {
      return state.with(FACING, rot.rotate(state.get(FACING)));
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.getDefaultState().with(FACING, context.getFace());
   }

   @Nullable
   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new JigsawTileEntity();
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      TileEntity tileentity = p_220051_2_.getTileEntity(p_220051_3_);
      if (tileentity instanceof JigsawTileEntity && p_220051_4_.canUseCommandBlock()) {
         p_220051_4_.func_213826_a((JigsawTileEntity)tileentity);
         return true;
      } else {
         return false;
      }
   }

   public static boolean func_220171_a(Template.BlockInfo p_220171_0_, Template.BlockInfo p_220171_1_) {
      return p_220171_0_.blockState.get(FACING) == p_220171_1_.blockState.get(FACING).getOpposite() && p_220171_0_.tileentityData.getString("attachement_type").equals(p_220171_1_.tileentityData.getString("attachement_type"));
   }
}