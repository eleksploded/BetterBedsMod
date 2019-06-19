package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChatPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ScaffoldingItem extends BlockItem {
   public ScaffoldingItem(Block p_i50039_1_, Item.Properties p_i50039_2_) {
      super(p_i50039_1_, p_i50039_2_);
   }

   @Nullable
   public BlockItemUseContext func_219984_b(BlockItemUseContext p_219984_1_) {
      BlockPos blockpos = p_219984_1_.getPos();
      World world = p_219984_1_.getWorld();
      BlockState blockstate = world.getBlockState(blockpos);
      Block block = this.getBlock();
      if (blockstate.getBlock() != block) {
         return ScaffoldingBlock.func_220117_a(world, blockpos) == 7 ? null : p_219984_1_;
      } else {
         Direction direction;
         if (p_219984_1_.isPlacerSneaking()) {
            direction = p_219984_1_.func_221533_k() ? p_219984_1_.getFace().getOpposite() : p_219984_1_.getFace();
         } else {
            direction = p_219984_1_.getFace() == Direction.UP ? p_219984_1_.getPlacementHorizontalFacing() : Direction.UP;
         }

         int i = 0;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = (new BlockPos.MutableBlockPos(blockpos)).move(direction);

         while(i < 7) {
            if (!world.isRemote && !World.isValid(blockpos$mutableblockpos)) {
               PlayerEntity playerentity = p_219984_1_.getPlayer();
               int j = world.func_217301_I();
               if (playerentity instanceof ServerPlayerEntity && blockpos$mutableblockpos.getY() >= j) {
                  SChatPacket schatpacket = new SChatPacket((new TranslationTextComponent("build.tooHigh", j)).applyTextStyle(TextFormatting.RED), ChatType.GAME_INFO);
                  ((ServerPlayerEntity)playerentity).connection.sendPacket(schatpacket);
               }
               break;
            }

            blockstate = world.getBlockState(blockpos$mutableblockpos);
            if (blockstate.getBlock() != this.getBlock()) {
               if (blockstate.isReplaceable(p_219984_1_)) {
                  return BlockItemUseContext.func_221536_a(p_219984_1_, blockpos$mutableblockpos, direction);
               }
               break;
            }

            blockpos$mutableblockpos.move(direction);
            if (direction.getAxis().isHorizontal()) {
               ++i;
            }
         }

         return null;
      }
   }

   protected boolean func_219987_d() {
      return false;
   }
}