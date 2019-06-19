package net.minecraft.resources;

import com.google.common.base.Stopwatch;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.profiler.IProfileResult;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugAsyncReloader extends AsyncReloader<DebugAsyncReloader.DataPoint> {
   private static final Logger field_219583_d = LogManager.getLogger();
   private final Stopwatch field_219584_e = Stopwatch.createUnstarted();

   public DebugAsyncReloader(IResourceManager p_i50694_1_, List<IFutureReloadListener> p_i50694_2_, Executor p_i50694_3_, Executor p_i50694_4_, CompletableFuture<Unit> p_i50694_5_) {
      super(p_i50694_3_, p_i50694_4_, p_i50694_1_, p_i50694_2_, (p_219578_1_, p_219578_2_, p_219578_3_, p_219578_4_, p_219578_5_) -> {
         AtomicLong atomiclong = new AtomicLong();
         AtomicLong atomiclong1 = new AtomicLong();
         Profiler profiler = new Profiler(Util.nanoTime(), () -> {
            return 0;
         });
         Profiler profiler1 = new Profiler(Util.nanoTime(), () -> {
            return 0;
         });
         CompletableFuture<Void> completablefuture = p_219578_3_.func_215226_a(p_219578_1_, p_219578_2_, profiler, profiler1, (p_219577_2_) -> {
            p_219578_4_.execute(() -> {
               long i = Util.nanoTime();
               p_219577_2_.run();
               atomiclong.addAndGet(Util.nanoTime() - i);
            });
         }, (p_219574_2_) -> {
            p_219578_5_.execute(() -> {
               long i = Util.nanoTime();
               p_219574_2_.run();
               atomiclong1.addAndGet(Util.nanoTime() - i);
            });
         });
         return completablefuture.thenApplyAsync((p_219576_5_) -> {
            return new DebugAsyncReloader.DataPoint(p_219578_3_.getClass().getSimpleName(), profiler.func_219905_d(), profiler1.func_219905_d(), atomiclong, atomiclong1);
         }, p_i50694_4_);
      }, p_i50694_5_);
      this.field_219584_e.start();
      this.field_219567_c.thenAcceptAsync(this::func_219575_a, p_i50694_4_);
   }

   private void func_219575_a(List<DebugAsyncReloader.DataPoint> p_219575_1_) {
      this.field_219584_e.stop();
      int i = 0;
      field_219583_d.info("Resource reload finished after " + this.field_219584_e.elapsed(TimeUnit.MILLISECONDS) + " ms");

      for(DebugAsyncReloader.DataPoint debugasyncreloader$datapoint : p_219575_1_) {
         IProfileResult iprofileresult = debugasyncreloader$datapoint.field_219548_b;
         IProfileResult iprofileresult1 = debugasyncreloader$datapoint.field_219549_c;
         int j = (int)((double)debugasyncreloader$datapoint.field_219550_d.get() / 1000000.0D);
         int k = (int)((double)debugasyncreloader$datapoint.field_219551_e.get() / 1000000.0D);
         int l = j + k;
         String s = debugasyncreloader$datapoint.field_219547_a;
         field_219583_d.info(s + " took approximately " + l + " ms (" + j + " ms preparing, " + k + " ms applying)");
         String s1 = iprofileresult.func_219920_e();
         if (s1.length() > 0) {
            field_219583_d.debug(s + " preparations:\n" + s1);
         }

         String s2 = iprofileresult1.func_219920_e();
         if (s2.length() > 0) {
            field_219583_d.debug(s + " reload:\n" + s2);
         }

         field_219583_d.info("----------");
         i += k;
      }

      field_219583_d.info("Total blocking time: " + i + " ms");
   }

   public static class DataPoint {
      private final String field_219547_a;
      private final IProfileResult field_219548_b;
      private final IProfileResult field_219549_c;
      private final AtomicLong field_219550_d;
      private final AtomicLong field_219551_e;

      private DataPoint(String p_i50542_1_, IProfileResult p_i50542_2_, IProfileResult p_i50542_3_, AtomicLong p_i50542_4_, AtomicLong p_i50542_5_) {
         this.field_219547_a = p_i50542_1_;
         this.field_219548_b = p_i50542_2_;
         this.field_219549_c = p_i50542_3_;
         this.field_219550_d = p_i50542_4_;
         this.field_219551_e = p_i50542_5_;
      }
   }
}