package net.minecraft.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.profiler.IProfiler;

public interface IFutureReloadListener {
   CompletableFuture<Void> func_215226_a(IFutureReloadListener.IStage p_215226_1_, IResourceManager p_215226_2_, IProfiler p_215226_3_, IProfiler p_215226_4_, Executor p_215226_5_, Executor p_215226_6_);

   public interface IStage {
      <T> CompletableFuture<T> func_216872_a(T p_216872_1_);
   }
}