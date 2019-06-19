package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BellAttachment;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.BellTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BellBlock extends ContainerBlock {
   public static final DirectionProperty field_220133_a = HorizontalBlock.HORIZONTAL_FACING;
   private static final EnumProperty<BellAttachment> field_220134_b = BlockStateProperties.field_222511_P;
   private static final VoxelShape field_220135_c = Block.makeCuboidShape(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
   private static final VoxelShape field_220136_d = Block.makeCuboidShape(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
   private static final VoxelShape field_220137_e = Block.makeCuboidShape(5.0D, 6.0D, 5.0D, 11.0D, 13.0D, 11.0D);
   private static final VoxelShape field_220138_f = Block.makeCuboidShape(4.0D, 4.0D, 4.0D, 12.0D, 6.0D, 12.0D);
   private static final VoxelShape field_220139_g = VoxelShapes.or(field_220138_f, field_220137_e);
   private static final VoxelShape field_220140_h = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 16.0D));
   private static final VoxelShape field_220141_i = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(0.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
   private static final VoxelShape field_220142_j = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(0.0D, 13.0D, 7.0D, 13.0D, 15.0D, 9.0D));
   private static final VoxelShape field_220143_k = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(3.0D, 13.0D, 7.0D, 16.0D, 15.0D, 9.0D));
   private static final VoxelShape field_220144_w = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(7.0D, 13.0D, 0.0D, 9.0D, 15.0D, 13.0D));
   private static final VoxelShape field_220145_x = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(7.0D, 13.0D, 3.0D, 9.0D, 15.0D, 16.0D));
   private static final VoxelShape field_220146_y = VoxelShapes.or(field_220139_g, Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D));

   public BellBlock(Block.Properties p_i49993_1_) {
      super(p_i49993_1_);
      this.setDefaultState(this.stateContainer.getBaseState().with(field_220133_a, Direction.NORTH).with(field_220134_b, BellAttachment.FLOOR));
   }

   public void onProjectileCollision(World p_220066_1_, BlockState p_220066_2_, BlockRayTraceResult p_220066_3_, Entity p_220066_4_) {
      if (p_220066_4_ instanceof AbstractArrowEntity) {
         Entity entity = ((AbstractArrowEntity)p_220066_4_).getShooter();
         PlayerEntity playerentity = entity instanceof PlayerEntity ? (PlayerEntity)entity : null;
         this.ring(p_220066_1_, p_220066_2_, p_220066_1_.getTileEntity(p_220066_3_.getPos()), p_220066_3_, playerentity, true);
      }

   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      return this.ring(p_220051_2_, p_220051_1_, p_220051_2_.getTileEntity(p_220051_3_), p_220051_6_, p_220051_4_, true);
   }

   public boolean ring(World p_220130_1_, BlockState p_220130_2_, @Nullable TileEntity p_220130_3_, BlockRayTraceResult p_220130_4_, @Nullable PlayerEntity p_220130_5_, boolean p_220130_6_) {
      Direction direction = p_220130_4_.getFace();
      BlockPos blockpos = p_220130_4_.getPos();
      boolean flag = !p_220130_6_ || this.func_220129_a(p_220130_2_, direction, p_220130_4_.getHitVec().y - (double)blockpos.getY());
      if (!p_220130_1_.isRemote && p_220130_3_ instanceof BellTileEntity && flag) {
         ((BellTileEntity)p_220130_3_).func_213939_a(direction);
         this.func_220132_a(p_220130_1_, blockpos);
         if (p_220130_5_ != null) {
            p_220130_5_.addStat(Stats.field_219740_ax);
         }

         return true;
      } else {
         return true;
      }
   }

   private boolean func_220129_a(BlockState p_220129_1_, Direction p_220129_2_, double p_220129_3_) {
      if (p_220129_2_.getAxis() != Direction.Axis.Y && !(p_220129_3_ > (double)0.8124F)) {
         Direction direction = p_220129_1_.get(field_220133_a);
         BellAttachment bellattachment = p_220129_1_.get(field_220134_b);
         switch(bellattachment) {
         case FLOOR:
            return direction.getAxis() == p_220129_2_.getAxis();
         case SINGLE_WALL:
         case DOUBLE_WALL:
            return direction.getAxis() != p_220129_2_.getAxis();
         case CEILING:
            return true;
         default:
            return false;
         }
      } else {
         return false;
      }
   }

   private void func_220132_a(World p_220132_1_, BlockPos p_220132_2_) {
      p_220132_1_.playSound((PlayerEntity)null, p_220132_2_, SoundEvents.field_219603_Y, SoundCategory.BLOCKS, 2.0F, 1.0F);
   }

   private VoxelShape func_220128_j(BlockState p_220128_1_) {
      Direction direction = p_220128_1_.get(field_220133_a);
      BellAttachment bellattachment = p_220128_1_.get(field_220134_b);
      if (bellattachment == BellAttachment.FLOOR) {
         return direction != Direction.NORTH && direction != Direction.SOUTH ? field_220136_d : field_220135_c;
      } else if (bellattachment == BellAttachment.CEILING) {
         return field_220146_y;
      } else if (bellattachment == BellAttachment.DOUBLE_WALL) {
         return direction != Direction.NORTH && direction != Direction.SOUTH ? field_220141_i : field_220140_h;
      } else if (direction == Direction.NORTH) {
         return field_220144_w;
      } else if (direction == Direction.SOUTH) {
         return field_220145_x;
      } else {
         return direction == Direction.EAST ? field_220143_k : field_220142_j;
      }
   }

   public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
      return this.func_220128_j(p_220071_1_);
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return this.func_220128_j(p_220053_1_);
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      Direction direction = context.getFace();
      BlockPos blockpos = context.getPos();
      World world = context.getWorld();
      Direction.Axis direction$axis = direction.getAxis();
      if (direction$axis == Direction.Axis.Y) {
         BlockState blockstate = this.getDefaultState().with(field_220134_b, direction == Direction.DOWN ? BellAttachment.CEILING : BellAttachment.FLOOR).with(field_220133_a, context.getPlacementHorizontalFacing());
         if (blockstate.isValidPosition(context.getWorld(), blockpos)) {
            return blockstate;
         }
      } else {
         boolean flag = direction$axis == Direction.Axis.X && func_220056_d(world.getBlockState(blockpos.west()), world, blockpos.west(), Direction.EAST) && func_220056_d(world.getBlockState(blockpos.east()), world, blockpos.east(), Direction.WEST) || direction$axis == Direction.Axis.Z && func_220056_d(world.getBlockState(blockpos.north()), world, blockpos.north(), Direction.SOUTH) && func_220056_d(world.getBlockState(blockpos.south()), world, blockpos.south(), Direction.NORTH);
         BlockState blockstate1 = this.getDefaultState().with(field_220133_a, direction.getOpposite()).with(field_220134_b, flag ? BellAttachment.DOUBLE_WALL : BellAttachment.SINGLE_WALL);
         if (blockstate1.isValidPosition(context.getWorld(), context.getPos())) {
            return blockstate1;
         }

         boolean flag1 = func_220056_d(world.getBlockState(blockpos.down()), world, blockpos.down(), Direction.UP);
         blockstate1 = blockstate1.with(field_220134_b, flag1 ? BellAttachment.FLOOR : BellAttachment.CEILING);
         if (blockstate1.isValidPosition(context.getWorld(), context.getPos())) {
            return blockstate1;
         }
      }

      return null;
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      BellAttachment bellattachment = stateIn.get(field_220134_b);
      Direction direction = func_220131_q(stateIn).getOpposite();
      if (direction == facing && !stateIn.isValidPosition(worldIn, currentPos) && bellattachment != BellAttachment.DOUBLE_WALL) {
         return Blocks.AIR.getDefaultState();
      } else {
         if (facing.getAxis() == stateIn.get(field_220133_a).getAxis()) {
            if (bellattachment == BellAttachment.DOUBLE_WALL && !func_220056_d(facingState, worldIn, facingPos, facing)) {
               return stateIn.with(field_220134_b, BellAttachment.SINGLE_WALL).with(field_220133_a, facing.getOpposite());
            }

            if (bellattachment == BellAttachment.SINGLE_WALL && direction.getOpposite() == facing && func_220056_d(facingState, worldIn, facingPos, stateIn.get(field_220133_a))) {
               return stateIn.with(field_220134_b, BellAttachment.DOUBLE_WALL);
            }
         }

         return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
      }
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return HorizontalFaceBlock.func_220185_b(worldIn, pos, func_220131_q(state).getOpposite());
   }

   private static Direction func_220131_q(BlockState p_220131_0_) {
      switch((BellAttachment)p_220131_0_.get(field_220134_b)) {
      case FLOOR:
         return Direction.UP;
      case CEILING:
         return Direction.DOWN;
      default:
         return p_220131_0_.get(field_220133_a).getOpposite();
      }
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   public PushReaction getPushReaction(BlockState state) {
      return PushReaction.DESTROY;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(field_220133_a, field_220134_b);
   }

   @Nullable
   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new BellTileEntity();
   }

   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      return false;
   }
}