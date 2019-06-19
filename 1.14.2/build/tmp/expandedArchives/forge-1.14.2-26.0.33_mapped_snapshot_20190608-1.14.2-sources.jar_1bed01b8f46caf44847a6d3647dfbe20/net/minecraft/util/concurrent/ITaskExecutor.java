package net.minecraft.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ITaskExecutor<Msg> extends AutoCloseable {
   String getName();

   void enqueue(Msg p_212871_1_);

   default void close() {
   }

   default <Source> CompletableFuture<Source> func_213141_a(Function<? super ITaskExecutor<Source>, ? extends Msg> p_213141_1_) {
      CompletableFuture<Source> completablefuture = new CompletableFuture<>();
      Msg msg = p_213141_1_.apply(inline("ask future procesor handle", completablefuture::complete));
      this.enqueue(msg);
      return completablefuture;
   }

   static <Msg> ITaskExecutor<Msg> inline(final String p_213140_0_, final Consumer<Msg> p_213140_1_) {
      return new ITaskExecutor<Msg>() {
         public String getName() {
            return p_213140_0_;
         }

         public void enqueue(Msg p_212871_1_) {
            p_213140_1_.accept(p_212871_1_);
         }

         public String toString() {
            return p_213140_0_;
         }
      };
   }
}