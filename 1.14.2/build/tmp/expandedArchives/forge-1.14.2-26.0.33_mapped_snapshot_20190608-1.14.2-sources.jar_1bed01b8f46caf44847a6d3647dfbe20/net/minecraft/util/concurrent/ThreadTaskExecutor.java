package net.minecraft.util.concurrent;

import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadTaskExecutor<R extends Runnable> implements ITaskExecutor<R>, Executor {
   private final String name;
   private static final Logger field_213172_c = LogManager.getLogger();
   private final Queue<R> queue = Queues.newConcurrentLinkedQueue();
   private int field_213174_e;

   protected ThreadTaskExecutor(String p_i50403_1_) {
      this.name = p_i50403_1_;
   }

   protected abstract R wrapTask(Runnable p_212875_1_);

   protected abstract boolean canRun(R p_212874_1_);

   public boolean isOnExecutionThread() {
      return Thread.currentThread() == this.getExecutionThread();
   }

   protected abstract Thread getExecutionThread();

   protected boolean shouldDeferTasks() {
      return !this.isOnExecutionThread();
   }

   public String getName() {
      return this.name;
   }

   @OnlyIn(Dist.CLIENT)
   public <V> CompletableFuture<V> supplyAsync(Supplier<V> p_213169_1_) {
      return this.shouldDeferTasks() ? CompletableFuture.supplyAsync(p_213169_1_, this) : CompletableFuture.completedFuture(p_213169_1_.get());
   }

   public CompletableFuture<Void> func_213165_a(Runnable p_213165_1_) {
      return CompletableFuture.supplyAsync(() -> {
         p_213165_1_.run();
         return null;
      }, this);
   }

   public CompletableFuture<Void> runAsync(Runnable p_222817_1_) {
      if (this.shouldDeferTasks()) {
         return this.func_213165_a(p_222817_1_);
      } else {
         p_222817_1_.run();
         return CompletableFuture.completedFuture((Void)null);
      }
   }

   public void runImmediately(Runnable p_213167_1_) {
      if (!this.isOnExecutionThread()) {
         this.func_213165_a(p_213167_1_).join();
      } else {
         p_213167_1_.run();
      }

   }

   public void enqueue(R p_212871_1_) {
      this.queue.add(p_212871_1_);
      LockSupport.unpark(this.getExecutionThread());
   }

   public void execute(Runnable p_execute_1_) {
      if (this.shouldDeferTasks()) {
         this.enqueue(this.wrapTask(p_execute_1_));
      } else {
         p_execute_1_.run();
      }

   }

   @OnlyIn(Dist.CLIENT)
   protected void dropTasks() {
      this.queue.clear();
   }

   protected void drainTasks() {
      while(this.driveOne()) {
         ;
      }

   }

   protected boolean driveOne() {
      R r = this.queue.peek();
      if (r == null) {
         return false;
      } else if (this.field_213174_e == 0 && !this.canRun(r)) {
         return false;
      } else {
         this.func_213166_h((R)(this.queue.remove()));
         return true;
      }
   }

   /**
    * Drive the executor until the given BooleanSupplier returns true
    */
   public void driveUntil(BooleanSupplier p_213161_1_) {
      ++this.field_213174_e;

      try {
         while(!p_213161_1_.getAsBoolean()) {
            if (!this.driveOne()) {
               LockSupport.parkNanos("waiting for tasks", 1000L);
            }
         }
      } finally {
         --this.field_213174_e;
      }

   }

   protected void func_213166_h(R p_213166_1_) {
      try {
         p_213166_1_.run();
      } catch (Exception exception) {
         field_213172_c.fatal("Error executing task on {}", this.getName(), exception);
      }

   }
}