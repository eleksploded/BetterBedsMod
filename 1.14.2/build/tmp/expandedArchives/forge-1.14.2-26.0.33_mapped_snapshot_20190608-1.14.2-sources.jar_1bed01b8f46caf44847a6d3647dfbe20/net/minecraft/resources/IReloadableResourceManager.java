package net.minecraft.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IReloadableResourceManager extends IResourceManager {
   CompletableFuture<Unit> func_219536_a(Executor p_219536_1_, Executor p_219536_2_, List<IResourcePack> p_219536_3_, CompletableFuture<Unit> p_219536_4_);

   @OnlyIn(Dist.CLIENT)
   IAsyncReloader func_219535_a(Executor p_219535_1_, Executor p_219535_2_, CompletableFuture<Unit> p_219535_3_);

   @OnlyIn(Dist.CLIENT)
   IAsyncReloader func_219537_a(Executor p_219537_1_, Executor p_219537_2_, CompletableFuture<Unit> p_219537_3_, List<IResourcePack> p_219537_4_);

   void func_219534_a(IFutureReloadListener p_219534_1_);
}