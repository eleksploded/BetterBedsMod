package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PistonBlockStructureHelper {
   private final World world;
   private final BlockPos pistonPos;
   private final boolean extending;
   private final BlockPos blockToMove;
   private final Direction field_177257_d;
   private final List<BlockPos> toMove = Lists.newArrayList();
   private final List<BlockPos> toDestroy = Lists.newArrayList();
   private final Direction field_211906_h;

   public PistonBlockStructureHelper(World worldIn, BlockPos posIn, Direction pistonFacing, boolean extending) {
      this.world = worldIn;
      this.pistonPos = posIn;
      this.field_211906_h = pistonFacing;
      this.extending = extending;
      if (extending) {
         this.field_177257_d = pistonFacing;
         this.blockToMove = posIn.offset(pistonFacing);
      } else {
         this.field_177257_d = pistonFacing.getOpposite();
         this.blockToMove = posIn.offset(pistonFacing, 2);
      }

   }

   public boolean canMove() {
      this.toMove.clear();
      this.toDestroy.clear();
      BlockState blockstate = this.world.getBlockState(this.blockToMove);
      if (!PistonBlock.canPush(blockstate, this.world, this.blockToMove, this.field_177257_d, false, this.field_211906_h)) {
         if (this.extending && blockstate.getPushReaction() == PushReaction.DESTROY) {
            this.toDestroy.add(this.blockToMove);
            return true;
         } else {
            return false;
         }
      } else if (!this.addBlockLine(this.blockToMove, this.field_177257_d)) {
         return false;
      } else {
         for(int i = 0; i < this.toMove.size(); ++i) {
            BlockPos blockpos = this.toMove.get(i);
            if (this.world.getBlockState(blockpos).isStickyBlock() && !this.addBranchingBlocks(blockpos)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean addBlockLine(BlockPos origin, Direction facingIn) {
      BlockState blockstate = this.world.getBlockState(origin);
      if (world.isAirBlock(origin)) {
         return true;
      } else if (!PistonBlock.canPush(blockstate, this.world, origin, this.field_177257_d, false, facingIn)) {
         return true;
      } else if (origin.equals(this.pistonPos)) {
         return true;
      } else if (this.toMove.contains(origin)) {
         return true;
      } else {
         int i = 1;
         if (i + this.toMove.size() > 12) {
            return false;
         } else {
            while(blockstate.isStickyBlock()) {
               BlockPos blockpos = origin.offset(this.field_177257_d.getOpposite(), i);
               blockstate = this.world.getBlockState(blockpos);
               if (blockstate.isAir(this.world, blockpos) || !PistonBlock.canPush(blockstate, this.world, blockpos, this.field_177257_d, false, this.field_177257_d.getOpposite()) || blockpos.equals(this.pistonPos)) {
                  break;
               }

               ++i;
               if (i + this.toMove.size() > 12) {
                  return false;
               }
            }

            int i1 = 0;

            for(int j = i - 1; j >= 0; --j) {
               this.toMove.add(origin.offset(this.field_177257_d.getOpposite(), j));
               ++i1;
            }

            int j1 = 1;

            while(true) {
               BlockPos blockpos1 = origin.offset(this.field_177257_d, j1);
               int k = this.toMove.indexOf(blockpos1);
               if (k > -1) {
                  this.reorderListAtCollision(i1, k);

                  for(int l = 0; l <= k + i1; ++l) {
                     BlockPos blockpos2 = this.toMove.get(l);
                     if (this.world.getBlockState(blockpos2).isStickyBlock() && !this.addBranchingBlocks(blockpos2)) {
                        return false;
                     }
                  }

                  return true;
               }

               blockstate = this.world.getBlockState(blockpos1);
               if (blockstate.isAir(world, blockpos1)) {
                  return true;
               }

               if (!PistonBlock.canPush(blockstate, this.world, blockpos1, this.field_177257_d, true, this.field_177257_d) || blockpos1.equals(this.pistonPos)) {
                  return false;
               }

               if (blockstate.getPushReaction() == PushReaction.DESTROY) {
                  this.toDestroy.add(blockpos1);
                  return true;
               }

               if (this.toMove.size() >= 12) {
                  return false;
               }

               this.toMove.add(blockpos1);
               ++i1;
               ++j1;
            }
         }
      }
   }

   private void reorderListAtCollision(int p_177255_1_, int p_177255_2_) {
      List<BlockPos> list = Lists.newArrayList();
      List<BlockPos> list1 = Lists.newArrayList();
      List<BlockPos> list2 = Lists.newArrayList();
      list.addAll(this.toMove.subList(0, p_177255_2_));
      list1.addAll(this.toMove.subList(this.toMove.size() - p_177255_1_, this.toMove.size()));
      list2.addAll(this.toMove.subList(p_177255_2_, this.toMove.size() - p_177255_1_));
      this.toMove.clear();
      this.toMove.addAll(list);
      this.toMove.addAll(list1);
      this.toMove.addAll(list2);
   }

   private boolean addBranchingBlocks(BlockPos fromPos) {
      for(Direction direction : Direction.values()) {
         if (direction.getAxis() != this.field_177257_d.getAxis() && !this.addBlockLine(fromPos.offset(direction), direction)) {
            return false;
         }
      }

      return true;
   }

   /**
    * Returns a List<BlockPos> of all the blocks that are being moved by the piston.
    */
   public List<BlockPos> getBlocksToMove() {
      return this.toMove;
   }

   /**
    * Returns an List<BlockPos> of all the blocks that are being destroyed by the piston.
    */
   public List<BlockPos> getBlocksToDestroy() {
      return this.toDestroy;
   }
}