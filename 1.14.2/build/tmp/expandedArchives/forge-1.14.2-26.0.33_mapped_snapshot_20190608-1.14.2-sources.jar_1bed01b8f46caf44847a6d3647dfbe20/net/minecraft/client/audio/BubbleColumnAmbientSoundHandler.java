package net.minecraft.client.audio;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BubbleColumnBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BubbleColumnAmbientSoundHandler implements IAmbientSoundHandler {
   private final ClientPlayerEntity field_217864_a;
   private boolean field_217865_b;
   private boolean field_217866_c = true;

   public BubbleColumnAmbientSoundHandler(ClientPlayerEntity p_i50900_1_) {
      this.field_217864_a = p_i50900_1_;
   }

   public void tick() {
      World world = this.field_217864_a.world;
      BlockState blockstate = world.findBlockstateInArea(this.field_217864_a.getBoundingBox().grow(0.0D, (double)-0.4F, 0.0D).shrink(0.001D), Blocks.BUBBLE_COLUMN);
      if (blockstate != null) {
         if (!this.field_217865_b && !this.field_217866_c && blockstate.getBlock() == Blocks.BUBBLE_COLUMN && !this.field_217864_a.isSpectator()) {
            boolean flag = blockstate.get(BubbleColumnBlock.DRAG);
            if (flag) {
               this.field_217864_a.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_WHIRLPOOL_INSIDE, 1.0F, 1.0F);
            } else {
               this.field_217864_a.playSound(SoundEvents.BLOCK_BUBBLE_COLUMN_UPWARDS_INSIDE, 1.0F, 1.0F);
            }
         }

         this.field_217865_b = true;
      } else {
         this.field_217865_b = false;
      }

      this.field_217866_c = false;
   }
}