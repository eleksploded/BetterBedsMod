package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.CartographyContainer;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CartographyTableBlock extends Block {
   private static final TranslationTextComponent field_220268_a = new TranslationTextComponent("container.cartography_table");

   protected CartographyTableBlock(Block.Properties p_i49987_1_) {
      super(p_i49987_1_);
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      p_220051_4_.openContainer(p_220051_1_.getContainer(p_220051_2_, p_220051_3_));
      p_220051_4_.addStat(Stats.field_219737_au);
      return true;
   }

   @Nullable
   public INamedContainerProvider getContainer(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
      return new SimpleNamedContainerProvider((p_220267_2_, p_220267_3_, p_220267_4_) -> {
         return new CartographyContainer(p_220267_2_, p_220267_3_, IWorldPosCallable.of(p_220052_2_, p_220052_3_));
      }, field_220268_a);
   }
}