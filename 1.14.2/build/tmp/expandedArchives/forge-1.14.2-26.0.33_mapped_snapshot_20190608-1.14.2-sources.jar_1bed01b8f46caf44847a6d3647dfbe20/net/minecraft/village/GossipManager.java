package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.DoublePredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.util.Util;

public class GossipManager {
   private final Map<UUID, GossipManager.Gossips> field_220928_a = Maps.newHashMap();

   private Stream<GossipManager.GossipEntry> func_220911_b() {
      return this.field_220928_a.entrySet().stream().flatMap((p_220917_0_) -> {
         return p_220917_0_.getValue().func_220895_a(p_220917_0_.getKey());
      });
   }

   private Collection<GossipManager.GossipEntry> func_220920_a(Random p_220920_1_, int p_220920_2_) {
      List<GossipManager.GossipEntry> list = this.func_220911_b().collect(Collectors.toList());
      if (list.isEmpty()) {
         return Collections.emptyList();
      } else {
         int[] aint = new int[list.size()];
         int i = 0;

         for(int j = 0; j < list.size(); ++j) {
            GossipManager.GossipEntry gossipmanager$gossipentry = list.get(j);
            i += Math.abs(gossipmanager$gossipentry.func_220904_a());
            aint[j] = i - 1;
         }

         Set<GossipManager.GossipEntry> set = Sets.newIdentityHashSet();

         for(int i1 = 0; i1 < p_220920_2_; ++i1) {
            int k = p_220920_1_.nextInt(i);
            int l = Arrays.binarySearch(aint, k);
            set.add(list.get(l < 0 ? -l - 1 : l));
         }

         return set;
      }
   }

   private GossipManager.Gossips func_220926_a(UUID p_220926_1_) {
      return this.field_220928_a.computeIfAbsent(p_220926_1_, (p_220922_0_) -> {
         return new GossipManager.Gossips();
      });
   }

   public void func_220912_a(GossipManager p_220912_1_, Random p_220912_2_, int p_220912_3_) {
      Collection<GossipManager.GossipEntry> collection = p_220912_1_.func_220920_a(p_220912_2_, p_220912_3_);
      collection.forEach((p_220923_1_) -> {
         int i = p_220923_1_.field_220909_c - p_220923_1_.field_220908_b.field_220935_k;
         if (i > 2) {
            this.func_220926_a(p_220923_1_.field_220907_a).field_220900_a.mergeInt(p_220923_1_.field_220908_b, i, GossipManager::func_220924_a);
         }

      });
   }

   public int func_220921_a(UUID p_220921_1_, Predicate<GossipType> p_220921_2_) {
      GossipManager.Gossips gossipmanager$gossips = this.field_220928_a.get(p_220921_1_);
      return gossipmanager$gossips != null ? gossipmanager$gossips.func_220896_a(p_220921_2_) : 0;
   }

   public long func_220910_a(GossipType p_220910_1_, DoublePredicate p_220910_2_) {
      return this.field_220928_a.values().stream().filter((p_220913_2_) -> {
         return p_220910_2_.test((double)(p_220913_2_.field_220900_a.getOrDefault(p_220910_1_, 0) * p_220910_1_.field_220932_h));
      }).count();
   }

   public void func_220916_a(UUID p_220916_1_, GossipType p_220916_2_, int p_220916_3_) {
      this.func_220926_a(p_220916_1_).field_220900_a.mergeInt(p_220916_2_, p_220916_3_, (p_220915_2_, p_220915_3_) -> {
         return this.func_220925_a(p_220916_2_, p_220915_2_, p_220915_3_);
      });
   }

   public <T> Dynamic<T> func_220914_a(DynamicOps<T> p_220914_1_) {
      return new Dynamic<>(p_220914_1_, p_220914_1_.createList(this.func_220911_b().map((p_220919_1_) -> {
         return p_220919_1_.func_220905_a(p_220914_1_);
      }).map(Dynamic::getValue)));
   }

   public void func_220918_a(Dynamic<?> p_220918_1_) {
      p_220918_1_.asStream().map(GossipManager.GossipEntry::func_220902_a).<GossipManager.GossipEntry>flatMap(Util::func_215081_a).forEach((p_220927_1_) -> {
         this.func_220926_a(p_220927_1_.field_220907_a).field_220900_a.put(p_220927_1_.field_220908_b, p_220927_1_.field_220909_c);
      });
   }

   private static int func_220924_a(int p_220924_0_, int p_220924_1_) {
      return Math.max(p_220924_0_, p_220924_1_);
   }

   private int func_220925_a(GossipType p_220925_1_, int p_220925_2_, int p_220925_3_) {
      int i = p_220925_2_ + p_220925_3_;
      return i > p_220925_1_.field_220933_i ? Math.max(p_220925_1_.field_220933_i, p_220925_2_) : i;
   }

   static class GossipEntry {
      public final UUID field_220907_a;
      public final GossipType field_220908_b;
      public final int field_220909_c;

      public GossipEntry(UUID p_i50613_1_, GossipType p_i50613_2_, int p_i50613_3_) {
         this.field_220907_a = p_i50613_1_;
         this.field_220908_b = p_i50613_2_;
         this.field_220909_c = p_i50613_3_;
      }

      public int func_220904_a() {
         return this.field_220909_c * this.field_220908_b.field_220932_h;
      }

      public String toString() {
         return "GossipEntry{target=" + this.field_220907_a + ", type=" + this.field_220908_b + ", value=" + this.field_220909_c + '}';
      }

      public <T> Dynamic<T> func_220905_a(DynamicOps<T> p_220905_1_) {
         return Util.func_215084_a("Target", this.field_220907_a, new Dynamic<>(p_220905_1_, p_220905_1_.createMap(ImmutableMap.of(p_220905_1_.createString("Type"), p_220905_1_.createString(this.field_220908_b.field_220931_g), p_220905_1_.createString("Value"), p_220905_1_.createInt(this.field_220909_c)))));
      }

      public static Optional<GossipManager.GossipEntry> func_220902_a(Dynamic<?> p_220902_0_) {
         return p_220902_0_.get("Type").asString().map(GossipType::func_220929_a).flatMap((p_220903_1_) -> {
            return Util.func_215074_a("Target", p_220902_0_).flatMap((p_220901_2_) -> {
               return p_220902_0_.get("Value").asNumber().map((p_220906_2_) -> {
                  return new GossipManager.GossipEntry(p_220901_2_, p_220903_1_, p_220906_2_.intValue());
               });
            });
         });
      }
   }

   static class Gossips {
      private final Object2IntMap<GossipType> field_220900_a = new Object2IntOpenHashMap<>();

      private Gossips() {
      }

      public int func_220896_a(Predicate<GossipType> p_220896_1_) {
         return this.field_220900_a.object2IntEntrySet().stream().filter((p_220898_1_) -> {
            return p_220896_1_.test(p_220898_1_.getKey());
         }).mapToInt((p_220894_0_) -> {
            return p_220894_0_.getIntValue() * (p_220894_0_.getKey()).field_220932_h;
         }).sum();
      }

      public Stream<GossipManager.GossipEntry> func_220895_a(UUID p_220895_1_) {
         return this.field_220900_a.object2IntEntrySet().stream().map((p_220897_1_) -> {
            return new GossipManager.GossipEntry(p_220895_1_, p_220897_1_.getKey(), p_220897_1_.getIntValue());
         });
      }
   }
}