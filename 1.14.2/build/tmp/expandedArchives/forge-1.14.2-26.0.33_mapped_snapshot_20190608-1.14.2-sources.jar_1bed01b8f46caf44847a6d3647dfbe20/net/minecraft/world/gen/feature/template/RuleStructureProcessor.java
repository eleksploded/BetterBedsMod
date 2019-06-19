package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

public class RuleStructureProcessor extends StructureProcessor {
   private final ImmutableList<RuleEntry> field_215201_a;

   public RuleStructureProcessor(List<RuleEntry> p_i51320_1_) {
      this.field_215201_a = ImmutableList.copyOf(p_i51320_1_);
   }

   public RuleStructureProcessor(Dynamic<?> p_i51321_1_) {
      this(p_i51321_1_.get("rules").asList(RuleEntry::func_215213_a));
   }

   @Nullable
   public Template.BlockInfo process(IWorldReader p_215194_1_, BlockPos p_215194_2_, Template.BlockInfo p_215194_3_, Template.BlockInfo p_215194_4_, PlacementSettings p_215194_5_) {
      Random random = new Random(MathHelper.getPositionRandom(p_215194_4_.pos));
      BlockState blockstate = p_215194_1_.getBlockState(p_215194_4_.pos);

      for(RuleEntry ruleentry : this.field_215201_a) {
         if (ruleentry.func_215211_a(p_215194_4_.blockState, blockstate, random)) {
            return new Template.BlockInfo(p_215194_4_.pos, ruleentry.func_215208_a(), ruleentry.func_215209_b());
         }
      }

      return p_215194_4_;
   }

   protected IStructureProcessorType getType() {
      return IStructureProcessorType.field_214924_f;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215193_1_) {
      return new Dynamic<>(p_215193_1_, p_215193_1_.createMap(ImmutableMap.of(p_215193_1_.createString("rules"), p_215193_1_.createList(this.field_215201_a.stream().map((p_215200_1_) -> {
         return p_215200_1_.func_215212_a(p_215193_1_).getValue();
      })))));
   }
}