package net.minecraft.village;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.state.properties.BedPart;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.registry.Registry;

public class PointOfInterestType extends net.minecraftforge.registries.ForgeRegistryEntry<PointOfInterestType> {
   private static final Predicate<PointOfInterestType> field_221071_s = (p_221041_0_) -> {
      return Registry.field_218370_L.stream().map(VillagerProfession::func_221149_b).collect(Collectors.toSet()).contains(p_221041_0_);
   };
   public static final Predicate<PointOfInterestType> field_221053_a = (p_221049_0_) -> {
      return true;
   };
   private static final Set<BlockState> field_221072_t = ImmutableList.of(Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED).stream().flatMap((p_221043_0_) -> {
      return p_221043_0_.getStateContainer().getValidStates().stream();
   }).filter((p_221050_0_) -> {
      return p_221050_0_.get(BedBlock.PART) == BedPart.HEAD;
   }).collect(ImmutableSet.toImmutableSet());
   private static final Map<BlockState, PointOfInterestType> field_221073_u = Maps.newHashMap();
   public static final PointOfInterestType field_221054_b = func_221039_a("unemployed", ImmutableSet.of(), 1, (SoundEvent)null, field_221071_s);
   public static final PointOfInterestType field_221055_c = func_221051_a("armorer", func_221042_a(Blocks.field_222424_lM), 1, SoundEvents.field_219694_mB);
   public static final PointOfInterestType field_221056_d = func_221051_a("butcher", func_221042_a(Blocks.field_222423_lL), 1, SoundEvents.field_219695_mC);
   public static final PointOfInterestType field_221057_e = func_221051_a("cartographer", func_221042_a(Blocks.field_222425_lN), 1, SoundEvents.field_219696_mD);
   public static final PointOfInterestType field_221058_f = func_221051_a("cleric", func_221042_a(Blocks.BREWING_STAND), 1, SoundEvents.field_219697_mE);
   public static final PointOfInterestType field_221059_g = func_221051_a("farmer", func_221042_a(Blocks.field_222436_lZ), 1, SoundEvents.field_219698_mF);
   public static final PointOfInterestType field_221060_h = func_221051_a("fisherman", func_221042_a(Blocks.field_222422_lK), 1, SoundEvents.field_219699_mG);
   public static final PointOfInterestType field_221061_i = func_221051_a("fletcher", func_221042_a(Blocks.field_222426_lO), 1, SoundEvents.field_219700_mH);
   public static final PointOfInterestType field_221062_j = func_221051_a("leatherworker", func_221042_a(Blocks.CAULDRON), 1, SoundEvents.field_219701_mI);
   public static final PointOfInterestType field_221063_k = func_221051_a("librarian", func_221042_a(Blocks.field_222428_lQ), 1, SoundEvents.field_219702_mJ);
   public static final PointOfInterestType field_221064_l = func_221051_a("mason", func_221042_a(Blocks.field_222430_lS), 1, SoundEvents.field_219703_mK);
   public static final PointOfInterestType field_221065_m = func_221051_a("nitwit", ImmutableSet.of(), 1, (SoundEvent)null);
   public static final PointOfInterestType field_221066_n = func_221051_a("shepherd", func_221042_a(Blocks.field_222421_lJ), 1, SoundEvents.field_219704_mL);
   public static final PointOfInterestType field_221067_o = func_221051_a("toolsmith", func_221042_a(Blocks.field_222429_lR), 1, SoundEvents.field_219705_mM);
   public static final PointOfInterestType field_221068_p = func_221051_a("weaponsmith", func_221042_a(Blocks.field_222427_lP), 1, SoundEvents.field_219706_mN);
   public static final PointOfInterestType field_221069_q = func_221051_a("home", field_221072_t, 1, (SoundEvent)null);
   public static final PointOfInterestType field_221070_r = func_221051_a("meeting", func_221042_a(Blocks.field_222431_lT), 32, (SoundEvent)null);
   private final String field_221074_v;
   private final Set<BlockState> field_221075_w;
   private final int field_221076_x;
   @Nullable
   private final SoundEvent field_221077_y;
   private final Predicate<PointOfInterestType> field_221078_z;

   private static Set<BlockState> func_221042_a(Block p_221042_0_) {
      return ImmutableSet.copyOf(p_221042_0_.getStateContainer().getValidStates());
   }

   public PointOfInterestType(String p_i50291_1_, Set<BlockState> p_i50291_2_, int p_i50291_3_, @Nullable SoundEvent p_i50291_4_, Predicate<PointOfInterestType> p_i50291_5_) {
      this.field_221074_v = p_i50291_1_;
      this.field_221075_w = ImmutableSet.copyOf(p_i50291_2_);
      this.field_221076_x = p_i50291_3_;
      this.field_221077_y = p_i50291_4_;
      this.field_221078_z = p_i50291_5_;
   }

   public PointOfInterestType(String p_i50292_1_, Set<BlockState> p_i50292_2_, int p_i50292_3_, @Nullable SoundEvent p_i50292_4_) {
      this.field_221074_v = p_i50292_1_;
      this.field_221075_w = ImmutableSet.copyOf(p_i50292_2_);
      this.field_221076_x = p_i50292_3_;
      this.field_221077_y = p_i50292_4_;
      this.field_221078_z = (p_221046_1_) -> {
         return p_221046_1_ == this;
      };
   }

   public int func_221044_b() {
      return this.field_221076_x;
   }

   public Predicate<PointOfInterestType> func_221045_c() {
      return this.field_221078_z;
   }

   public String toString() {
      return this.field_221074_v;
   }

   @Nullable
   public SoundEvent func_221048_d() {
      return this.field_221077_y;
   }

   private static PointOfInterestType func_221051_a(String p_221051_0_, Set<BlockState> p_221051_1_, int p_221051_2_, @Nullable SoundEvent p_221051_3_) {
      return func_221052_a(Registry.field_218371_M.register(new ResourceLocation(p_221051_0_), new PointOfInterestType(p_221051_0_, p_221051_1_, p_221051_2_, p_221051_3_)));
   }

   private static PointOfInterestType func_221039_a(String p_221039_0_, Set<BlockState> p_221039_1_, int p_221039_2_, @Nullable SoundEvent p_221039_3_, Predicate<PointOfInterestType> p_221039_4_) {
      return func_221052_a(Registry.field_218371_M.register(new ResourceLocation(p_221039_0_), new PointOfInterestType(p_221039_0_, p_221039_1_, p_221039_2_, p_221039_3_, p_221039_4_)));
   }

   private static PointOfInterestType func_221052_a(PointOfInterestType p_221052_0_) {
      p_221052_0_.field_221075_w.forEach((p_221040_1_) -> {
         PointOfInterestType pointofinteresttype = field_221073_u.put(p_221040_1_, p_221052_0_);
         if (pointofinteresttype != null) {
            throw new IllegalStateException(String.format("%s is defined in too many tags", p_221040_1_));
         }
      });
      return p_221052_0_;
   }

   public static Optional<PointOfInterestType> func_221047_b(BlockState p_221047_0_) {
      return Optional.ofNullable(field_221073_u.get(p_221047_0_));
   }

   public static Stream<BlockState> func_221038_e() {
      return field_221073_u.keySet().stream();
   }
}