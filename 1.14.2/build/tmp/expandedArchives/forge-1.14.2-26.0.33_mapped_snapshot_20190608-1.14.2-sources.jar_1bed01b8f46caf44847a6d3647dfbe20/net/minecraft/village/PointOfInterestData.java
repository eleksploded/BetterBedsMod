package net.minecraft.village;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import net.minecraft.util.IDynamicSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.SectionPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PointOfInterestData implements IDynamicSerializable {
   private static final Logger field_218255_a = LogManager.getLogger();
   private final Short2ObjectMap<PointOfInterest> field_218256_b = new Short2ObjectOpenHashMap<>();
   private final Map<PointOfInterestType, Set<PointOfInterest>> field_218257_c = Maps.newHashMap();
   private final Runnable field_218258_d;
   private boolean field_218259_e;

   public PointOfInterestData(Runnable p_i50293_1_) {
      this.field_218258_d = p_i50293_1_;
      this.field_218259_e = true;
   }

   public <T> PointOfInterestData(Runnable p_i50294_1_, Dynamic<T> p_i50294_2_) {
      this.field_218258_d = p_i50294_1_;

      try {
         this.field_218259_e = p_i50294_2_.get("Valid").asBoolean(false);
         p_i50294_2_.get("Records").asStream().forEach((p_218249_2_) -> {
            this.func_218254_a(new PointOfInterest(p_218249_2_, p_i50294_1_));
         });
      } catch (Exception exception) {
         field_218255_a.error("Failed to load POI chunk", (Throwable)exception);
         this.func_218253_a();
         this.field_218259_e = false;
      }

   }

   public Stream<PointOfInterest> func_218247_a(Predicate<PointOfInterestType> p_218247_1_, PointOfInterestManager.Status p_218247_2_) {
      return this.field_218257_c.entrySet().stream().filter((p_218239_1_) -> {
         return p_218247_1_.test(p_218239_1_.getKey());
      }).flatMap((p_218246_0_) -> {
         return p_218246_0_.getValue().stream();
      }).filter(p_218247_2_.func_221035_a());
   }

   public void func_218243_a(BlockPos p_218243_1_, PointOfInterestType p_218243_2_) {
      if (this.func_218254_a(new PointOfInterest(p_218243_1_, p_218243_2_, this.field_218258_d))) {
         field_218255_a.debug(String.format("Added POI of type %s @ %s", p_218243_2_, p_218243_1_));
         this.field_218258_d.run();
      }

   }

   private boolean func_218254_a(PointOfInterest p_218254_1_) {
      BlockPos blockpos = p_218254_1_.func_218261_f();
      PointOfInterestType pointofinteresttype = p_218254_1_.func_218260_g();
      short short1 = SectionPos.toRelativeOffset(blockpos);
      PointOfInterest pointofinterest = this.field_218256_b.get(short1);
      if (pointofinterest != null) {
         if (pointofinteresttype.equals(pointofinterest.func_218260_g())) {
            return false;
         } else {
            throw new IllegalStateException("POI data mismatch: already registered at " + blockpos);
         }
      } else {
         this.field_218256_b.put(short1, p_218254_1_);
         this.field_218257_c.computeIfAbsent(pointofinteresttype, (p_218252_0_) -> {
            return Sets.newHashSet();
         }).add(p_218254_1_);
         return true;
      }
   }

   public void func_218248_a(BlockPos p_218248_1_) {
      PointOfInterest pointofinterest = this.field_218256_b.remove(SectionPos.toRelativeOffset(p_218248_1_));
      if (pointofinterest == null) {
         field_218255_a.error("POI data mismatch: never registered at " + p_218248_1_);
      } else {
         this.field_218257_c.get(pointofinterest.func_218260_g()).remove(pointofinterest);
         field_218255_a.debug(String.format("Removed POI of type %s @ %s", pointofinterest.func_218260_g(), pointofinterest.func_218261_f()));
         this.field_218258_d.run();
      }
   }

   public boolean func_218251_c(BlockPos p_218251_1_) {
      PointOfInterest pointofinterest = this.field_218256_b.get(SectionPos.toRelativeOffset(p_218251_1_));
      if (pointofinterest == null) {
         throw new IllegalStateException("POI never registered at " + p_218251_1_);
      } else {
         boolean flag = pointofinterest.func_218264_c();
         this.field_218258_d.run();
         return flag;
      }
   }

   public boolean func_218245_a(BlockPos p_218245_1_, Predicate<PointOfInterestType> p_218245_2_) {
      short short1 = SectionPos.toRelativeOffset(p_218245_1_);
      PointOfInterest pointofinterest = this.field_218256_b.get(short1);
      return pointofinterest != null && p_218245_2_.test(pointofinterest.func_218260_g());
   }

   public Optional<PointOfInterestType> func_218244_d(BlockPos p_218244_1_) {
      short short1 = SectionPos.toRelativeOffset(p_218244_1_);
      PointOfInterest pointofinterest = this.field_218256_b.get(short1);
      return pointofinterest != null ? Optional.of(pointofinterest.func_218260_g()) : Optional.empty();
   }

   public <T> T serializeDynamic(DynamicOps<T> p_218175_1_) {
      T t = p_218175_1_.createList(this.field_218256_b.values().stream().map((p_218242_1_) -> {
         return p_218242_1_.serializeDynamic(p_218175_1_);
      }));
      return p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("Records"), t, p_218175_1_.createString("Valid"), p_218175_1_.createBoolean(this.field_218259_e)));
   }

   public void func_218240_a(Consumer<BiConsumer<BlockPos, PointOfInterestType>> p_218240_1_) {
      if (!this.field_218259_e) {
         Short2ObjectMap<PointOfInterest> short2objectmap = new Short2ObjectOpenHashMap<>(this.field_218256_b);
         this.func_218253_a();
         p_218240_1_.accept((p_218250_2_, p_218250_3_) -> {
            short short1 = SectionPos.toRelativeOffset(p_218250_2_);
            PointOfInterest pointofinterest = short2objectmap.computeIfAbsent(short1, (p_218241_3_) -> {
               return new PointOfInterest(p_218250_2_, p_218250_3_, this.field_218258_d);
            });
            this.func_218254_a(pointofinterest);
         });
         this.field_218259_e = true;
         this.field_218258_d.run();
      }

   }

   private void func_218253_a() {
      this.field_218256_b.clear();
      this.field_218257_c.clear();
   }
}