package net.minecraft.profiler;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilledProfileResult implements IProfileResult {
   private static final Logger field_219930_a = LogManager.getLogger();
   private final Map<String, Long> field_219931_b;
   private final long field_219932_c;
   private final int field_219933_d;
   private final long field_219934_e;
   private final int field_219935_f;

   public FilledProfileResult(Map<String, Long> p_i50407_1_, long p_i50407_2_, int p_i50407_4_, long p_i50407_5_, int p_i50407_7_) {
      this.field_219931_b = p_i50407_1_;
      this.field_219932_c = p_i50407_2_;
      this.field_219933_d = p_i50407_4_;
      this.field_219934_e = p_i50407_5_;
      this.field_219935_f = p_i50407_7_;
   }

   public List<DataPoint> func_219917_a(String p_219917_1_) {
      long i = this.field_219931_b.containsKey("root") ? this.field_219931_b.get("root") : 0L;
      long j = this.field_219931_b.containsKey(p_219917_1_) ? this.field_219931_b.get(p_219917_1_) : -1L;
      List<DataPoint> list = Lists.newArrayList();
      if (!p_219917_1_.isEmpty()) {
         p_219917_1_ = p_219917_1_ + ".";
      }

      long k = 0L;

      for(String s : this.field_219931_b.keySet()) {
         if (s.length() > p_219917_1_.length() && s.startsWith(p_219917_1_) && s.indexOf(".", p_219917_1_.length() + 1) < 0) {
            k += this.field_219931_b.get(s);
         }
      }

      float f = (float)k;
      if (k < j) {
         k = j;
      }

      if (i < k) {
         i = k;
      }

      for(String s1 : this.field_219931_b.keySet()) {
         if (s1.length() > p_219917_1_.length() && s1.startsWith(p_219917_1_) && s1.indexOf(".", p_219917_1_.length() + 1) < 0) {
            long l = this.field_219931_b.get(s1);
            double d0 = (double)l * 100.0D / (double)k;
            double d1 = (double)l * 100.0D / (double)i;
            String s2 = s1.substring(p_219917_1_.length());
            list.add(new DataPoint(s2, d0, d1));
         }
      }

      for(String s3 : this.field_219931_b.keySet()) {
         this.field_219931_b.put(s3, this.field_219931_b.get(s3) * 999L / 1000L);
      }

      if ((float)k > f) {
         list.add(new DataPoint("unspecified", (double)((float)k - f) * 100.0D / (double)k, (double)((float)k - f) * 100.0D / (double)i));
      }

      Collections.sort(list);
      list.add(0, new DataPoint(p_219917_1_, 100.0D, (double)k * 100.0D / (double)i));
      return list;
   }

   public long func_219918_a() {
      return this.field_219932_c;
   }

   public int func_219922_b() {
      return this.field_219933_d;
   }

   public long func_219923_c() {
      return this.field_219934_e;
   }

   public int func_219921_d() {
      return this.field_219935_f;
   }

   public boolean func_219919_a(File p_219919_1_) {
      p_219919_1_.getParentFile().mkdirs();
      Writer writer = null;

      boolean flag1;
      try {
         writer = new OutputStreamWriter(new FileOutputStream(p_219919_1_), StandardCharsets.UTF_8);
         writer.write(this.func_219929_a(this.func_219924_f(), this.func_219925_g()));
         boolean lvt_3_1_ = true;
         return lvt_3_1_;
      } catch (Throwable throwable) {
         field_219930_a.error("Could not save profiler results to {}", p_219919_1_, throwable);
         flag1 = false;
      } finally {
         IOUtils.closeQuietly(writer);
      }

      return flag1;
   }

   protected String func_219929_a(long p_219929_1_, int p_219929_3_) {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("---- Minecraft Profiler Results ----\n");
      stringbuilder.append("// ");
      stringbuilder.append(func_219927_h());
      stringbuilder.append("\n\n");
      stringbuilder.append("Time span: ").append(p_219929_1_ / 1000000L).append(" ms\n");
      stringbuilder.append("Tick span: ").append(p_219929_3_).append(" ticks\n");
      stringbuilder.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", (float)p_219929_3_ / ((float)p_219929_1_ / 1.0E9F))).append(" ticks per second. It should be ").append((int)20).append(" ticks per second\n\n");
      stringbuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.func_219928_a(0, "root", stringbuilder);
      stringbuilder.append("--- END PROFILE DUMP ---\n\n");
      return stringbuilder.toString();
   }

   public String func_219920_e() {
      StringBuilder stringbuilder = new StringBuilder();
      this.func_219928_a(0, "root", stringbuilder);
      return stringbuilder.toString();
   }

   private void func_219928_a(int p_219928_1_, String p_219928_2_, StringBuilder p_219928_3_) {
      List<DataPoint> list = this.func_219917_a(p_219928_2_);
      if (list.size() >= 3) {
         for(int i = 1; i < list.size(); ++i) {
            DataPoint datapoint = list.get(i);
            p_219928_3_.append(String.format("[%02d] ", p_219928_1_));

            for(int j = 0; j < p_219928_1_; ++j) {
               p_219928_3_.append("|   ");
            }

            p_219928_3_.append(datapoint.field_219945_c).append(" - ").append(String.format(Locale.ROOT, "%.2f", datapoint.field_219943_a)).append("%/").append(String.format(Locale.ROOT, "%.2f", datapoint.field_219944_b)).append("%\n");
            if (!"unspecified".equals(datapoint.field_219945_c)) {
               try {
                  this.func_219928_a(p_219928_1_ + 1, p_219928_2_ + "." + datapoint.field_219945_c, p_219928_3_);
               } catch (Exception exception) {
                  p_219928_3_.append("[[ EXCEPTION ").append((Object)exception).append(" ]]");
               }
            }
         }

      }
   }

   private static String func_219927_h() {
      String[] astring = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};

      try {
         return astring[(int)(Util.nanoTime() % (long)astring.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }
}