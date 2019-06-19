package net.minecraft.block;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.ComparatorMode;
import net.minecraft.tileentity.ComparatorTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class ComparatorBlock extends RedstoneDiodeBlock implements ITileEntityProvider {
   public static final EnumProperty<ComparatorMode> MODE = BlockStateProperties.COMPARATOR_MODE;

   public ComparatorBlock(Block.Properties properties) {
      super(properties);
      this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, Boolean.valueOf(false)).with(MODE, ComparatorMode.COMPARE));
   }

   protected int getDelay(BlockState p_196346_1_) {
      return 2;
   }

   protected int getActiveSignal(IBlockReader worldIn, BlockPos pos, BlockState state) {
      TileEntity tileentity = worldIn.getTileEntity(pos);
      return tileentity instanceof ComparatorTileEntity ? ((ComparatorTileEntity)tileentity).getOutputSignal() : 0;
   }

   private int calculateOutput(World worldIn, BlockPos pos, BlockState state) {
      return state.get(MODE) == ComparatorMode.SUBTRACT ? Math.max(this.calculateInputStrength(worldIn, pos, state) - this.getPowerOnSides(worldIn, pos, state), 0) : this.calculateInputStrength(worldIn, pos, state);
   }

   protected boolean shouldBePowered(World worldIn, BlockPos pos, BlockState state) {
      int i = this.calculateInputStrength(worldIn, pos, state);
      if (i >= 15) {
         return true;
      } else if (i == 0) {
         return false;
      } else {
         return i >= this.getPowerOnSides(worldIn, pos, state);
      }
   }

   protected int calculateInputStrength(World worldIn, BlockPos pos, BlockState state) {
      int i = super.calculateInputStrength(worldIn, pos, state);
      Direction direction = state.get(HORIZONTAL_FACING);
      BlockPos blockpos = pos.offset(direction);
      BlockState blockstate = worldIn.getBlockState(blockpos);
      if (blockstate.hasComparatorInputOverride()) {
         i = blockstate.getComparatorInputOverride(worldIn, blockpos);
      } else if (i < 15 && blockstate.func_215686_e(worldIn, blockpos)) {
         blockpos = blockpos.offset(direction);
         blockstate = worldIn.getBlockState(blockpos);
         if (blockstate.hasComparatorInputOverride()) {
            i = blockstate.getComparatorInputOverride(worldIn, blockpos);
         } else if (blockstate.isAir(worldIn, blockpos)) {
            ItemFrameEntity itemframeentity = this.findItemFrame(worldIn, direction, blockpos);
            if (itemframeentity != null) {
               i = itemframeentity.getAnalogOutput();
            }
         }
      }

      return i;
   }

   @Nullable
   private ItemFrameEntity findItemFrame(World worldIn, Direction facing, BlockPos pos) {
      List<ItemFrameEntity> list = worldIn.getEntitiesWithinAABB(ItemFrameEntity.class, new AxisAlignedBB((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), (double)(pos.getX() + 1), (double)(pos.getY() + 1), (double)(pos.getZ() + 1)), (p_210304_1_) -> {
         return p_210304_1_ != null && p_210304_1_.getHorizontalFacing() == facing;
      });
      return list.size() == 1 ? list.get(0) : null;
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      if (!p_220051_4_.playerAbilities.allowEdit) {
         return false;
      } else {
         p_220051_1_ = p_220051_1_.cycle(MODE);
         float f = p_220051_1_.get(MODE) == ComparatorMode.SUBTRACT ? 0.55F : 0.5F;
         p_220051_2_.playSound(p_220051_4_, p_220051_3_, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, f);
         p_220051_2_.setBlockState(p_220051_3_, p_220051_1_, 2);
         this.onStateChange(p_220051_2_, p_220051_3_, p_220051_1_);
         return true;
      }
   }

   protected void updateState(World worldIn, BlockPos pos, BlockState state) {
      if (!worldIn.getPendingBlockTicks().isTickPending(pos, this)) {
         int i = this.calculateOutput(worldIn, pos, state);
         TileEntity tileentity = worldIn.getTileEntity(pos);
         int j = tileentity instanceof ComparatorTileEntity ? ((ComparatorTileEntity)tileentity).getOutputSignal() : 0;
         if (i != j || state.get(POWERED) != this.shouldBePowered(worldIn, pos, state)) {
            TickPriority tickpriority = this.isFacingTowardsRepeater(worldIn, pos, state) ? TickPriority.HIGH : TickPriority.NORMAL;
            worldIn.getPendingBlockTicks().scheduleTick(pos, this, 2, tickpriority);
         }

      }
   }

   private void onStateChange(World worldIn, BlockPos pos, BlockState state) {
      int i = this.calculateOutput(worldIn, pos, state);
      TileEntity tileentity = worldIn.getTileEntity(pos);
      int j = 0;
      if (tileentity instanceof ComparatorTileEntity) {
         ComparatorTileEntity comparatortileentity = (ComparatorTileEntity)tileentity;
         j = comparatortileentity.getOutputSignal();
         comparatortileentity.setOutputSignal(i);
      }

      if (j != i || state.get(MODE) == ComparatorMode.COMPARE) {
         boolean flag1 = this.shouldBePowered(worldIn, pos, state);
         boolean flag = state.get(POWERED);
         if (flag && !flag1) {
            worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(false)), 2);
         } else if (!flag && flag1) {
            worldIn.setBlockState(pos, state.with(POWERED, Boolean.valueOf(true)), 2);
         }

         this.notifyNeighbors(worldIn, pos, state);
      }

   }

   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
      this.onStateChange(worldIn, pos, state);
   }

   /**
    * Called on server when World#addBlockEvent is called. If server returns true, then also called on the client. On
    * the Server, this may perform additional changes to the world, like pistons replacing the block with an extended
    * base. On the client, the update may involve replacing tile entities or effects such as sounds or particles
    * @deprecated call via {@link IBlockState#onBlockEventReceived(World,BlockPos,int,int)} whenever possible.
    * Implementing/overriding is fine.
    */
   public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
      super.eventReceived(state, worldIn, pos, id, param);
      TileEntity tileentity = worldIn.getTileEntity(pos);
      return tileentity != null && tileentity.receiveClientEvent(id, param);
   }

   public TileEntity createNewTileEntity(IBlockReader worldIn) {
      return new ComparatorTileEntity();
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
      builder.add(HORIZONTAL_FACING, MODE, POWERED);
   }

   @Override
   public boolean getWeakChanges(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos) {
      return true;
   }

   @Override
   public void onNeighborChange(BlockState state, net.minecraft.world.IWorldReader world, BlockPos pos, BlockPos neighbor) {
      if (pos.getY() == neighbor.getY() && world instanceof World && !((World)world).isRemote()) {
         state.neighborChanged((World)world, pos, world.getBlockState(neighbor).getBlock(), neighbor, false);
      }
   }
}