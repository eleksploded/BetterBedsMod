package net.minecraft.entity.ai.brain.memory;

import com.mojang.datafixers.Dynamic;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.IPosWrapper;
import net.minecraft.util.registry.Registry;

public class MemoryModuleType<U> extends net.minecraftforge.registries.ForgeRegistryEntry<MemoryModuleType<?>> {
   public static final MemoryModuleType<Void> field_220940_a = register("dummy", Optional.empty());
   public static final MemoryModuleType<GlobalPos> field_220941_b = register("home", Optional.of(GlobalPos::deserialize));
   public static final MemoryModuleType<GlobalPos> field_220942_c = register("job_site", Optional.of(GlobalPos::deserialize));
   public static final MemoryModuleType<GlobalPos> field_220943_d = register("meeting_point", Optional.of(GlobalPos::deserialize));
   public static final MemoryModuleType<List<GlobalPos>> field_220944_e = register("secondary_job_site", Optional.empty());
   public static final MemoryModuleType<List<LivingEntity>> field_220945_f = register("mobs", Optional.empty());
   public static final MemoryModuleType<List<LivingEntity>> field_220946_g = register("visible_mobs", Optional.empty());
   public static final MemoryModuleType<List<LivingEntity>> field_220947_h = register("visible_villager_babies", Optional.empty());
   public static final MemoryModuleType<List<PlayerEntity>> field_220948_i = register("nearest_players", Optional.empty());
   public static final MemoryModuleType<PlayerEntity> field_220949_j = register("nearest_visible_player", Optional.empty());
   public static final MemoryModuleType<WalkTarget> field_220950_k = register("walk_target", Optional.empty());
   public static final MemoryModuleType<IPosWrapper> field_220951_l = register("look_target", Optional.empty());
   public static final MemoryModuleType<LivingEntity> field_220952_m = register("interaction_target", Optional.empty());
   public static final MemoryModuleType<VillagerEntity> field_220953_n = register("breed_target", Optional.empty());
   public static final MemoryModuleType<Path> field_220954_o = register("path", Optional.empty());
   public static final MemoryModuleType<List<GlobalPos>> field_220955_p = register("interactable_doors", Optional.empty());
   public static final MemoryModuleType<BlockPos> field_220956_q = register("nearest_bed", Optional.empty());
   public static final MemoryModuleType<DamageSource> field_220957_r = register("hurt_by", Optional.empty());
   public static final MemoryModuleType<LivingEntity> field_220958_s = register("hurt_by_entity", Optional.empty());
   public static final MemoryModuleType<LivingEntity> field_220959_t = register("nearest_hostile", Optional.empty());
   public static final MemoryModuleType<VillagerEntity.GolemStatus> field_220960_u = register("golem_spawn_conditions", Optional.empty());
   public static final MemoryModuleType<GlobalPos> field_220961_v = register("hiding_place", Optional.empty());
   public static final MemoryModuleType<Long> field_220962_w = register("heard_bell_time", Optional.empty());
   public static final MemoryModuleType<Long> field_223021_x = register("cant_reach_walk_target_since", Optional.empty());
   private final Optional<Function<Dynamic<?>, U>> deserializer;

   public MemoryModuleType(Optional<Function<Dynamic<?>, U>> p_i50306_1_) {
      this.deserializer = p_i50306_1_;
   }

   public String toString() {
      return Registry.field_218372_N.getKey(this).toString();
   }

   public Optional<Function<Dynamic<?>, U>> getDeserializer() {
      return this.deserializer;
   }

   private static <U> MemoryModuleType<U> register(String p_220937_0_, Optional<Function<Dynamic<?>, U>> p_220937_1_) {
      return Registry.register(Registry.field_218372_N, new ResourceLocation(p_220937_0_), new MemoryModuleType<>(p_220937_1_));
   }
}