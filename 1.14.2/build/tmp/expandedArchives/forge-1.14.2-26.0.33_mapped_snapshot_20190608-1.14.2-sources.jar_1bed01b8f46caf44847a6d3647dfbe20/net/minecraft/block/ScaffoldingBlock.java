package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class ScaffoldingBlock extends Block implements IWaterLoggable {
   private static final VoxelShape field_220121_d;
   private static final VoxelShape field_220122_e;
   private static final VoxelShape field_220123_f = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   private static final VoxelShape field_220124_g = VoxelShapes.fullCube().withOffset(0.0D, -1.0D, 0.0D);
   public static final IntegerProperty field_220118_a = BlockStateProperties.field_222510_au;
   public static final BooleanProperty field_220119_b = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty field_220120_c = BlockStateProperties.field_222513_b;

   protected ScaffoldingBlock(Block.Properties p_i49976_1_) {
      super(p_i49976_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_220118_a, Integer.valueOf(7)).with(field_220119_b, Boolean.valueOf(false)).with(field_220120_c, Boolean.valueOf(false)));
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_220118_a, field_220119_b, field_220120_c);
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      if (!p_220053_4_.hasItem(p_220053_1_.getBlock().asItem())) {
         return p_220053_1_.get(field_220120_c) ? field_220122_e : field_220121_d;
      } else {
         return VoxelShapes.fullCube();
      }
   }

   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return VoxelShapes.fullCube();
   }

   /**
    * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
    * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
    */
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return useContext.getItem().getItem() == this.asItem();
   }

   public BlockState getStateForPlacement(BlockItemUseContext context) {
      BlockPos blockpos = context.getPos();
      World world = context.getWorld();
      int i = func_220117_a(world, blockpos);
      return this.getDefaultState().with(field_220119_b, Boolean.valueOf(world.getFluidState(blockpos).getFluid() == Fluids.WATER)).with(field_220118_a, Integer.valueOf(i)).with(field_220120_c, Boolean.valueOf(this.func_220116_a(world, blockpos, i)));
   }

   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
      if (!p_220082_2_.isRemote) {
         p_220082_2_.getPendingBlockTicks().scheduleTick(p_220082_3_, this, 1);
      }

   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      if (stateIn.get(field_220119_b)) {
         worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
      }

      if (!worldIn.isRemote()) {
         worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
      }

      return stateIn;
   }

   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
      int i = func_220117_a(worldIn, pos);
      BlockState blockstate = state.with(field_220118_a, Integer.valueOf(i)).with(field_220120_c, Boolean.valueOf(this.func_220116_a(worldIn, pos, i)));
      if (blockstate.get(field_220118_a) == 7) {
         if (state.get(field_220118_a) == 7) {
            worldIn.func_217376_c(new FallingBlockEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, blockstate.with(field_220119_b, Boolean.valueOf(false))));
         } else {
            worldIn.destroyBlock(pos, true);
         }
      } else if (state != blockstate) {
         worldIn.setBlockState(pos, blockstate, 3);
      }

   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return func_220117_a(worldIn, pos) < 7;
   }

   public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
      if (p_220071_4_.func_216378_a(VoxelShapes.fullCube(), p_220071_3_, true) && !p_220071_4_.isSneaking()) {
         return field_220121_d;
      } else {
         return p_220071_1_.get(field_220118_a) != 0 && p_220071_1_.get(field_220120_c) && p_220071_4_.func_216378_a(field_220124_g, p_220071_3_, true) ? field_220123_f : VoxelShapes.empty();
      }
   }

   public IFluidState getFluidState(BlockState state) {
      return state.get(field_220119_b) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
   }

   private boolean func_220116_a(IBlockReader p_220116_1_, BlockPos p_220116_2_, int p_220116_3_) {
      return p_220116_3_ > 0 && p_220116_1_.getBlockState(p_220116_2_.down()).getBlock() != this;
   }

   public static int func_220117_a(IBlockReader p_220117_0_, BlockPos p_220117_1_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = (new BlockPos.MutableBlockPos(p_220117_1_)).move(Direction.DOWN);
      BlockState blockstate = p_220117_0_.getBlockState(blockpos$mutableblockpos);
      int i = 7;
      if (blockstate.getBlock() == Blocks.field_222420_lI) {
         i = blockstate.get(field_220118_a);
      } else if (Block.func_220056_d(blockstate, p_220117_0_, blockpos$mutableblockpos, Direction.UP)) {
         return 0;
      }

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockState blockstate1 = p_220117_0_.getBlockState(blockpos$mutableblockpos.setPos(p_220117_1_).move(direction));
         if (blockstate1.getBlock() == Blocks.field_222420_lI) {
            i = Math.min(i, blockstate1.get(field_220118_a) + 1);
            if (i == 1) {
               break;
            }
         }
      }

      return i;
   }

   static {
      VoxelShape voxelshape = Block.makeCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
      VoxelShape voxelshape1 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 2.0D);
      VoxelShape voxelshape2 = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
      VoxelShape voxelshape3 = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 2.0D, 16.0D, 16.0D);
      VoxelShape voxelshape4 = Block.makeCuboidShape(14.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
      field_220121_d = VoxelShapes.func_216384_a(voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
      VoxelShape voxelshape5 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 16.0D);
      VoxelShape voxelshape6 = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
      VoxelShape voxelshape7 = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 2.0D, 16.0D);
      VoxelShape voxelshape8 = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 2.0D);
      field_220122_e = VoxelShapes.func_216384_a(field_220123_f, field_220121_d, voxelshape6, voxelshape5, voxelshape8, voxelshape7);
   }
}