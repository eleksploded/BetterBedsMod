package net.minecraft.world.gen.feature.template;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class IntegrityProcessor extends StructureProcessor {
   private final float field_215195_a;

   public IntegrityProcessor(float p_i51332_1_) {
      this.field_215195_a = p_i51332_1_;
   }

   public IntegrityProcessor(Dynamic<?> p_i51333_1_) {
      this(p_i51333_1_.get("integrity").asFloat(1.0F));
   }

   @Nullable
   public Template.BlockInfo process(IWorldReader p_215194_1_, BlockPos p_215194_2_, Template.BlockInfo p_215194_3_, Template.BlockInfo p_215194_4_, PlacementSettings p_215194_5_) {
      Random random = p_215194_5_.getRandom(p_215194_4_.pos);
      return !(this.field_215195_a >= 1.0F) && !(random.nextFloat() <= this.field_215195_a) ? null : p_215194_4_;
   }

   protected IStructureProcessorType getType() {
      return IStructureProcessorType.field_214921_c;
   }

   protected <T> Dynamic<T> doSerialize(DynamicOps<T> p_215193_1_) {
      return new Dynamic<>(p_215193_1_, p_215193_1_.createMap(ImmutableMap.of(p_215193_1_.createString("integrity"), p_215193_1_.createFloat(this.field_215195_a))));
   }
}