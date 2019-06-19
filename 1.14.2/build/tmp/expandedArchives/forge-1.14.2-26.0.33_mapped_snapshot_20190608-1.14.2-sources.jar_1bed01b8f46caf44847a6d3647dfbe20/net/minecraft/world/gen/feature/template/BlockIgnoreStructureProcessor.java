package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class BlockIgnoreStructureProcessor extends StructureProcessor {
   public static final BlockIgnoreStructureProcessor field_215204_a = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_BLOCK));
   public static final BlockIgnoreStructureProcessor field_215205_b = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.AIR));
   public static final BlockIgnoreStructureProcessor field_215206_c = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.AIR, Blocks.STRUCTURE_BLOCK));
   private final ImmutableList<Block> field_215207_d;

   public BlockIgnoreStructureProcessor(List<Block> p_i51336_1_) {
      this.field_215207_d = ImmutableList.copyOf(p_i51336_1_);
   }

   public BlockIgnoreStructureProcessor(Dynamic<?> p_i51337_1_) {
      this(p_i51337_1_.get("blocks").asList((p_215203_0_) -> {
         return BlockState.func_215698_a(p_215203_0_).getBlock();
      }));
   }

   @Nullable
   public Template.BlockInfo process(IWorldReader p_215194_1_, BlockPos p_215194_2_, Template.BlockInfo p_215194_3_, Template.BlockInfo p_215194_4_, PlacementSettings p_215194_5_) {
      return this.field_215207_d.contains(p_215194_4_.blockState.getBlock()) ? null : p_215194_4_;
   }

   protected IStructureProcessorType getType() {
      return IStructureProcessorType.field_214920_b;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215193_1_) {
      return new Dynamic<>(p_215193_1_, p_215193_1_.createMap(ImmutableMap.of(p_215193_1_.createString("blocks"), p_215193_1_.createList(this.field_215207_d.stream().map((p_215202_1_) -> {
         return BlockState.func_215689_a(p_215193_1_, p_215202_1_.getDefaultState()).getValue();
      })))));
   }
}