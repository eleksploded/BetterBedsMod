package net.minecraft.block;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class PumpkinBlock extends StemGrownBlock {
   protected PumpkinBlock(Block.Properties builder) {
      super(builder);
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      ItemStack itemstack = p_220051_4_.getHeldItem(p_220051_5_);
      if (itemstack.getItem() == Items.SHEARS) {
         if (!p_220051_2_.isRemote) {
            Direction direction = p_220051_6_.getFace();
            Direction direction1 = direction.getAxis() == Direction.Axis.Y ? p_220051_4_.getHorizontalFacing().getOpposite() : direction;
            p_220051_2_.playSound((PlayerEntity)null, p_220051_3_, SoundEvents.BLOCK_PUMPKIN_CARVE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            p_220051_2_.setBlockState(p_220051_3_, Blocks.CARVED_PUMPKIN.getDefaultState().with(CarvedPumpkinBlock.FACING, direction1), 11);
            ItemEntity itementity = new ItemEntity(p_220051_2_, (double)p_220051_3_.getX() + 0.5D + (double)direction1.getXOffset() * 0.65D, (double)p_220051_3_.getY() + 0.1D, (double)p_220051_3_.getZ() + 0.5D + (double)direction1.getZOffset() * 0.65D, new ItemStack(Items.PUMPKIN_SEEDS, 4));
            itementity.setMotion(0.05D * (double)direction1.getXOffset() + p_220051_2_.rand.nextDouble() * 0.02D, 0.05D, 0.05D * (double)direction1.getZOffset() + p_220051_2_.rand.nextDouble() * 0.02D);
            p_220051_2_.func_217376_c(itementity);
            itemstack.func_222118_a(1, p_220051_4_, (p_220282_1_) -> {
               p_220282_1_.func_213334_d(p_220051_5_);
            });
         }

         return true;
      } else {
         return super.onBlockActivated(p_220051_1_, p_220051_2_, p_220051_3_, p_220051_4_, p_220051_5_, p_220051_6_);
      }
   }

   public StemBlock getStem() {
      return (StemBlock)Blocks.PUMPKIN_STEM;
   }

   public AttachedStemBlock getAttachedStem() {
      return (AttachedStemBlock)Blocks.ATTACHED_PUMPKIN_STEM;
   }
}