package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlowerPotBlock extends Block {
   private static final Map<Block, Block> field_196451_b = Maps.newHashMap();  //TODO: Delegates
   protected static final VoxelShape SHAPE = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
   private final Block field_196452_c;

   public FlowerPotBlock(Block p_i48395_1_, Block.Properties p_i48395_2_) {
      super(p_i48395_2_);
      this.field_196452_c = p_i48395_1_;
      field_196451_b.put(p_i48395_1_, this);
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return SHAPE;
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      ItemStack itemstack = p_220051_4_.getHeldItem(p_220051_5_);
      Item item = itemstack.getItem();
      Block block = item instanceof BlockItem ? field_196451_b.getOrDefault(((BlockItem)item).getBlock(), Blocks.AIR) : Blocks.AIR;
      boolean flag = block == Blocks.AIR;
      boolean flag1 = this.field_196452_c == Blocks.AIR;
      if (flag != flag1) {
         if (flag1) {
            p_220051_2_.setBlockState(p_220051_3_, block.getDefaultState(), 3);
            p_220051_4_.addStat(Stats.POT_FLOWER);
            if (!p_220051_4_.playerAbilities.isCreativeMode) {
               itemstack.shrink(1);
            }
         } else {
            ItemStack itemstack1 = new ItemStack(this.field_196452_c);
            if (itemstack.isEmpty()) {
               p_220051_4_.setHeldItem(p_220051_5_, itemstack1);
            } else if (!p_220051_4_.addItemStackToInventory(itemstack1)) {
               p_220051_4_.dropItem(itemstack1, false);
            }

            p_220051_2_.setBlockState(p_220051_3_, Blocks.FLOWER_POT.getDefaultState(), 3);
         }
      }

      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return this.field_196452_c == Blocks.AIR ? super.getItem(worldIn, pos, state) : new ItemStack(this.field_196452_c);
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return facing == Direction.DOWN && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   /**
    * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
    * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
    */
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public Block func_220276_d() {
      return this.field_196452_c;
   }
}