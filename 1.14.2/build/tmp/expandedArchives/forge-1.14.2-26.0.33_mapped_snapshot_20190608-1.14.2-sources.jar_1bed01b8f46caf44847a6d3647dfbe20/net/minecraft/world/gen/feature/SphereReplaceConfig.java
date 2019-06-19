package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class SphereReplaceConfig implements IFeatureConfig {
   public final BlockState field_214693_a;
   public final int radius;
   public final int ySize;
   public final List<BlockState> targets;

   public SphereReplaceConfig(BlockState p_i49886_1_, int p_i49886_2_, int p_i49886_3_, List<BlockState> p_i49886_4_) {
      this.field_214693_a = p_i49886_1_;
      this.radius = p_i49886_2_;
      this.ySize = p_i49886_3_;
      this.targets = p_i49886_4_;
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("state"), BlockState.func_215689_a(p_214634_1_, this.field_214693_a).getValue(), p_214634_1_.createString("radius"), p_214634_1_.createInt(this.radius), p_214634_1_.createString("y_size"), p_214634_1_.createInt(this.ySize), p_214634_1_.createString("targets"), p_214634_1_.createList(this.targets.stream().map((p_214692_1_) -> {
         return BlockState.func_215689_a(p_214634_1_, p_214692_1_).getValue();
      })))));
   }

   public static <T> SphereReplaceConfig func_214691_a(Dynamic<T> p_214691_0_) {
      BlockState blockstate = p_214691_0_.get("state").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      int i = p_214691_0_.get("radius").asInt(0);
      int j = p_214691_0_.get("y_size").asInt(0);
      List<BlockState> list = p_214691_0_.get("targets").asList(BlockState::func_215698_a);
      return new SphereReplaceConfig(blockstate, i, j, list);
   }
}