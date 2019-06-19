package net.minecraft.resources;

import java.util.concurrent.CompletableFuture;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IAsyncReloader {
   CompletableFuture<Unit> func_219552_a();

   @OnlyIn(Dist.CLIENT)
   float func_219555_b();

   @OnlyIn(Dist.CLIENT)
   boolean func_219553_c();

   @OnlyIn(Dist.CLIENT)
   boolean func_219554_d();

   @OnlyIn(Dist.CLIENT)
   void func_219556_e();
}