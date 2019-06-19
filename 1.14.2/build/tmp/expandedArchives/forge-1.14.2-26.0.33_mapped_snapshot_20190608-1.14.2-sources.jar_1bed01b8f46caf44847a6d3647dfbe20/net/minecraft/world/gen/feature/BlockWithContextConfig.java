package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

public class BlockWithContextConfig implements IFeatureConfig {
   protected final BlockState field_206924_a;
   protected final List<BlockState> placeOn;
   protected final List<BlockState> placeIn;
   protected final List<BlockState> placeUnder;

   public BlockWithContextConfig(BlockState p_i51439_1_, List<BlockState> p_i51439_2_, List<BlockState> p_i51439_3_, List<BlockState> p_i51439_4_) {
      this.field_206924_a = p_i51439_1_;
      this.placeOn = p_i51439_2_;
      this.placeIn = p_i51439_3_;
      this.placeUnder = p_i51439_4_;
   }

   public BlockWithContextConfig(BlockState state, BlockState[] placeOn, BlockState[] placeIn, BlockState[] placeUnder) {
      this(state, Lists.newArrayList(placeOn), Lists.newArrayList(placeIn), Lists.newArrayList(placeUnder));
   }

   public <T> Dynamic<T> func_214634_a(DynamicOps<T> p_214634_1_) {
      T t = BlockState.func_215689_a(p_214634_1_, this.field_206924_a).getValue();
      T t1 = p_214634_1_.createList(this.placeOn.stream().map((p_214662_1_) -> {
         return BlockState.func_215689_a(p_214634_1_, p_214662_1_).getValue();
      }));
      T t2 = p_214634_1_.createList(this.placeIn.stream().map((p_214661_1_) -> {
         return BlockState.func_215689_a(p_214634_1_, p_214661_1_).getValue();
      }));
      T t3 = p_214634_1_.createList(this.placeUnder.stream().map((p_214660_1_) -> {
         return BlockState.func_215689_a(p_214634_1_, p_214660_1_).getValue();
      }));
      return new Dynamic<>(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("to_place"), t, p_214634_1_.createString("place_on"), t1, p_214634_1_.createString("place_in"), t2, p_214634_1_.createString("place_under"), t3)));
   }

   public static <T> BlockWithContextConfig func_214663_a(Dynamic<T> p_214663_0_) {
      BlockState blockstate = p_214663_0_.get("to_place").map(BlockState::func_215698_a).orElse(Blocks.AIR.getDefaultState());
      List<BlockState> list = p_214663_0_.get("place_on").asList(BlockState::func_215698_a);
      List<BlockState> list1 = p_214663_0_.get("place_in").asList(BlockState::func_215698_a);
      List<BlockState> list2 = p_214663_0_.get("place_under").asList(BlockState::func_215698_a);
      return new BlockWithContextConfig(blockstate, list, list1, list2);
   }
}