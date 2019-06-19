package net.minecraft.block;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class CraftingTableBlock extends Block {
   private static final ITextComponent field_220271_a = new TranslationTextComponent("container.crafting");

   protected CraftingTableBlock(Block.Properties properties) {
      super(properties);
   }

   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      p_220051_4_.openContainer(p_220051_1_.getContainer(p_220051_2_, p_220051_3_));
      p_220051_4_.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
      return true;
   }

   public INamedContainerProvider getContainer(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
      return new SimpleNamedContainerProvider((p_220270_2_, p_220270_3_, p_220270_4_) -> {
         return new WorkbenchContainer(p_220270_2_, p_220270_3_, IWorldPosCallable.of(p_220052_2_, p_220052_3_));
      }, field_220271_a);
   }
}