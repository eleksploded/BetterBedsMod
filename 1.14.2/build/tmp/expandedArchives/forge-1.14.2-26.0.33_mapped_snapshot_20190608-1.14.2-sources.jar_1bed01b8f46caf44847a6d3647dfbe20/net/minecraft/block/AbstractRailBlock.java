package net.minecraft.block;

import net.minecraft.block.material.PushReaction;
import net.minecraft.state.IProperty;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class AbstractRailBlock extends Block {
   protected static final VoxelShape FLAT_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   protected static final VoxelShape ASCENDING_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
   private final boolean disableCorners;

   public static boolean isRail(World p_208488_0_, BlockPos p_208488_1_) {
      return isRail(p_208488_0_.getBlockState(p_208488_1_));
   }

   public static boolean isRail(BlockState p_208487_0_) {
      return p_208487_0_.isIn(BlockTags.RAILS);
   }

   protected AbstractRailBlock(boolean p_i48444_1_, Block.Properties p_i48444_2_) {
      super(p_i48444_2_);
      this.disableCorners = p_i48444_1_;
   }

   public boolean areCornersDisabled() {
      return this.disableCorners;
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      RailShape railshape = p_220053_1_.getBlock() == this ? getRailDirection(p_220053_1_, p_220053_2_, p_220053_3_, null) : null;
      return railshape != null && railshape.isAscending() ? ASCENDING_AABB : FLAT_AABB;
   }

   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return func_220064_c(worldIn, pos.down());
   }

   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
      if (p_220082_4_.getBlock() != p_220082_1_.getBlock()) {
         if (!p_220082_2_.isRemote) {
            p_220082_1_ = this.getUpdatedState(p_220082_2_, p_220082_3_, p_220082_1_, true);
            if (this.disableCorners) {
               p_220082_1_.neighborChanged(p_220082_2_, p_220082_3_, this, p_220082_3_, p_220082_5_);
            }
         }

      }
   }

   public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
      if (!p_220069_2_.isRemote) {
         RailShape railshape = getRailDirection(p_220069_1_, p_220069_2_, p_220069_3_, null);
         boolean flag = false;
         BlockPos blockpos = p_220069_3_.down();
         if (!func_220064_c(p_220069_2_, blockpos)) {
            flag = true;
         }

         BlockPos blockpos1 = p_220069_3_.east();
         if (railshape == RailShape.ASCENDING_EAST && !func_220064_c(p_220069_2_, blockpos1)) {
            flag = true;
         } else {
            BlockPos blockpos2 = p_220069_3_.west();
            if (railshape == RailShape.ASCENDING_WEST && !func_220064_c(p_220069_2_, blockpos2)) {
               flag = true;
            } else {
               BlockPos blockpos3 = p_220069_3_.north();
               if (railshape == RailShape.ASCENDING_NORTH && !func_220064_c(p_220069_2_, blockpos3)) {
                  flag = true;
               } else {
                  BlockPos blockpos4 = p_220069_3_.south();
                  if (railshape == RailShape.ASCENDING_SOUTH && !func_220064_c(p_220069_2_, blockpos4)) {
                     flag = true;
                  }
               }
            }
         }

         if (flag && !p_220069_2_.isAirBlock(p_220069_3_)) {
            if (!p_220069_6_) {
               spawnDrops(p_220069_1_, p_220069_2_, p_220069_3_);
            }

            p_220069_2_.removeBlock(p_220069_3_, p_220069_6_);
         } else {
            this.updateState(p_220069_1_, p_220069_2_, p_220069_3_, p_220069_4_);
         }

      }
   }

   protected void updateState(BlockState state, World worldIn, BlockPos pos, Block blockIn) {
   }

   protected BlockState getUpdatedState(World p_208489_1_, BlockPos p_208489_2_, BlockState p_208489_3_, boolean placing) {
      return p_208489_1_.isRemote ? p_208489_3_ : (new RailState(p_208489_1_, p_208489_2_, p_208489_3_)).update(p_208489_1_.isBlockPowered(p_208489_2_), placing).getNewState();
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   public PushReaction getPushReaction(BlockState state) {
      return PushReaction.NORMAL;
   }

   /**
    * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
    * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
    */
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!isMoving) {
         super.onReplaced(state, worldIn, pos, newState, isMoving);
         if (getRailDirection(state, worldIn, pos, null).isAscending()) {
            worldIn.notifyNeighborsOfStateChange(pos.up(), this);
         }

         if (this.disableCorners) {
            worldIn.notifyNeighborsOfStateChange(pos, this);
            worldIn.notifyNeighborsOfStateChange(pos.down(), this);
         }

      }
   }

   //Forge: Use getRailDirection(IBlockAccess, BlockPos, IBlockState, EntityMinecart) for enhanced ability
   public abstract IProperty<RailShape> getShapeProperty();

   /* ======================================== FORGE START =====================================*/
   /**
    * Return true if the rail can make corners.
    * Used by placement logic.
    * @param world The world.
    * @param pos Block's position in world
    * @return True if the rail can make corners.
    */
   public boolean isFlexibleRail(BlockState state, IBlockReader world, BlockPos pos)
   {
       return !this.disableCorners;
   }

   /**
    * Returns true if the rail can make up and down slopes.
    * Used by placement logic.
    * @param world The world.
    * @param pos Block's position in world
    * @return True if the rail can make slopes.
    */
   public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
       return true;
   }

   /**
    * Return the rail's direction.
    * Can be used to make the cart think the rail is a different shape,
    * for example when making diamond junctions or switches.
    * The cart parameter will often be null unless it it called from EntityMinecart.
    *
    * @param world The world.
    * @param pos Block's position in world
    * @param state The BlockState
    * @param cart The cart asking for the metadata, null if it is not called by EntityMinecart.
    * @return The direction.
    */
   public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @javax.annotation.Nullable net.minecraft.entity.item.minecart.AbstractMinecartEntity cart) {
       return state.get(getShapeProperty());
   }

   /**
    * Returns the max speed of the rail at the specified position.
    * @param world The world.
    * @param cart The cart on the rail, may be null.
    * @param pos Block's position in world
    * @return The max speed of the current rail.
    */
   public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, net.minecraft.entity.item.minecart.AbstractMinecartEntity cart) {
       return 0.4f;
   }

   /**
    * This function is called by any minecart that passes over this rail.
    * It is called once per update tick that the minecart is on the rail.
    * @param world The world.
    * @param cart The cart on the rail.
    * @param pos Block's position in world
    */
   public void onMinecartPass(BlockState state, World world, BlockPos pos, net.minecraft.entity.item.minecart.AbstractMinecartEntity cart) { }
}