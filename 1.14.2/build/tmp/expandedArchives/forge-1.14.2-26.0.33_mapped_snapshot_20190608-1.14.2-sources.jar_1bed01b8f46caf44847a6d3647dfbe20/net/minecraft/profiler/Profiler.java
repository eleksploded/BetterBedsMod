package net.minecraft.profiler;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.time.Duration;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.util.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Profiler implements IResultableProfiler {
   private static final long field_219907_a = Duration.ofMillis(100L).toNanos();
   private static final Logger LOGGER = LogManager.getLogger();
   private final List<String> sectionList = Lists.newArrayList();
   private final LongList field_76326_c = new LongArrayList();
   private final Object2LongMap<String> field_76324_e = new Object2LongOpenHashMap<>();
   private final IntSupplier field_219912_f;
   private final long field_219913_g;
   private final int field_219914_h;
   private String field_219915_i = "";
   private boolean field_219916_j;

   public Profiler(long p_i50408_1_, IntSupplier p_i50408_3_) {
      this.field_219913_g = p_i50408_1_;
      this.field_219914_h = p_i50408_3_.getAsInt();
      this.field_219912_f = p_i50408_3_;
   }

   public void func_219894_a() {
      if (this.field_219916_j) {
         LOGGER.error("Profiler tick already started - missing endTick()?");
      } else {
         this.field_219916_j = true;
         this.field_219915_i = "";
         this.sectionList.clear();
         this.startSection("root");
      }
   }

   public void func_219897_b() {
      if (!this.field_219916_j) {
         LOGGER.error("Profiler tick already ended - missing startTick()?");
      } else {
         this.endSection();
         this.field_219916_j = false;
         if (!this.field_219915_i.isEmpty()) {
            LOGGER.error("Profiler tick ended before path was fully popped (remainder: '{}'). Mismatched push/pop?", (Object)this.field_219915_i);
         }

      }
   }

   /**
    * Start section
    */
   public void startSection(String name) {
      if (!this.field_219916_j) {
         LOGGER.error("Cannot push '{}' to profiler if profiler tick hasn't started - missing startTick()?", (Object)name);
      } else {
         if (!this.field_219915_i.isEmpty()) {
            this.field_219915_i = this.field_219915_i + ".";
         }

         this.field_219915_i = this.field_219915_i + name;
         this.sectionList.add(this.field_219915_i);
         this.field_76326_c.add(Util.nanoTime());
      }
   }

   public void startSection(Supplier<String> nameSupplier) {
      this.startSection(nameSupplier.get());
   }

   /**
    * End section
    */
   public void endSection() {
      if (!this.field_219916_j) {
         LOGGER.error("Cannot pop from profiler if profiler tick hasn't started - missing startTick()?");
      } else if (this.field_76326_c.isEmpty()) {
         LOGGER.error("Tried to pop one too many times! Mismatched push() and pop()?");
      } else {
         long i = Util.nanoTime();
         long j = this.field_76326_c.removeLong(this.field_76326_c.size() - 1);
         this.sectionList.remove(this.sectionList.size() - 1);
         long k = i - j;
         this.field_76324_e.put(this.field_219915_i, this.field_76324_e.getLong(this.field_219915_i) + k);
         if (k > field_219907_a) {
            LOGGER.warn("Something's taking too long! '{}' took aprox {} ms", this.field_219915_i, (double)k / 1000000.0D);
         }

         this.field_219915_i = this.sectionList.isEmpty() ? "" : this.sectionList.get(this.sectionList.size() - 1);
      }
   }

   public void endStartSection(String p_219895_1_) {
      this.endSection();
      this.startSection(p_219895_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void endStartSection(Supplier<String> nameSupplier) {
      this.endSection();
      this.startSection(nameSupplier);
   }

   public IProfileResult func_219905_d() {
      return new FilledProfileResult(this.field_76324_e, this.field_219913_g, this.field_219914_h, Util.nanoTime(), this.field_219912_f.getAsInt());
   }
}