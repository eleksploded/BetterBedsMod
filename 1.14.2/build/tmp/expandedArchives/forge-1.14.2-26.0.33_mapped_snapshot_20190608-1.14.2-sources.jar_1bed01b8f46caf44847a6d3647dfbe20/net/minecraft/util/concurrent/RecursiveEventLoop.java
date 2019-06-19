package net.minecraft.util.concurrent;

public abstract class RecursiveEventLoop<R extends Runnable> extends ThreadTaskExecutor<R> {
   private int field_213183_b;

   public RecursiveEventLoop(String p_i50401_1_) {
      super(p_i50401_1_);
   }

   protected boolean shouldDeferTasks() {
      return this.func_213182_bg() || super.shouldDeferTasks();
   }

   protected boolean func_213182_bg() {
      return this.field_213183_b != 0;
   }

   protected void func_213166_h(R p_213166_1_) {
      ++this.field_213183_b;

      try {
         super.func_213166_h(p_213166_1_);
      } finally {
         --this.field_213183_b;
      }

   }
}