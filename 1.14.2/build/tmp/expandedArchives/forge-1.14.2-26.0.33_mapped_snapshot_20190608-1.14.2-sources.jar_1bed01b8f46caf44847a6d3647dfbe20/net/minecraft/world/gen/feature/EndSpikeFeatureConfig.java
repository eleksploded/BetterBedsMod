package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;

public class EndSpikeFeatureConfig implements IFeatureConfig {
   private final boolean field_214674_a;
   private final List<EndSpikeFeature.EndSpike> field_214675_b;
   @Nullable
   private final BlockPos field_214676_c;

   public EndSpikeFeatureConfig(boolean p_i51433_1_, List<EndSpikeFeature.EndSpike> p_i51433_2_, @Nullable BlockPos p_i51433_3_) {
      this.field_214674_a = p_i51433_1_;
      this.field_214675_b = p_i51433_2_;
      this.field_214676_c = p_i51433_3_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("crystalInvulnerable"), p_214634_1_.createBoolean(this.field_214674_a), p_214634_1_.createString("spikes"), p_214634_1_.createList(this.field_214675_b.stream().map((p_214670_1_) -> {
         return p_214670_1_.func_214749_a(p_214634_1_).getValue();
      })), p_214634_1_.createString("crystalBeamTarget"), (T)(this.field_214676_c == null ? p_214634_1_.createList(Stream.empty()) : p_214634_1_.createList(IntStream.of(this.field_214676_c.getX(), this.field_214676_c.getY(), this.field_214676_c.getZ()).mapToObj(p_214634_1_::createInt))))));
   }

   public static <T> EndSpikeFeatureConfig func_214673_a(Dynamic<T> p_214673_0_) {
      List<EndSpikeFeature.EndSpike> list = p_214673_0_.get("spikes").asList(EndSpikeFeature.EndSpike::func_214747_a);
      List<Integer> list1 = p_214673_0_.get("crystalBeamTarget").asList((p_214672_0_) -> {
         return p_214672_0_.asInt(0);
      });
      BlockPos blockpos;
      if (list1.size() == 3) {
         blockpos = new BlockPos(list1.get(0), list1.get(1), list1.get(2));
      } else {
         blockpos = null;
      }

      return new EndSpikeFeatureConfig(p_214673_0_.get("crystalInvulnerable").asBoolean(false), list, blockpos);
   }

   public boolean func_214669_a() {
      return this.field_214674_a;
   }

   public List<EndSpikeFeature.EndSpike> func_214671_b() {
      return this.field_214675_b;
   }

   @Nullable
   public BlockPos func_214668_c() {
      return this.field_214676_c;
   }
}