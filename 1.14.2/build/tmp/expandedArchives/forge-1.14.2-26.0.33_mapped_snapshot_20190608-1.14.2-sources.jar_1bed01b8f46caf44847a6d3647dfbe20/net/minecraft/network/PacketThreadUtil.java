package net.minecraft.network;

import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.world.ServerWorld;

public class PacketThreadUtil {
   public static <T extends INetHandler> void func_218796_a(IPacket<T> p_218796_0_, T p_218796_1_, ServerWorld p_218796_2_) throws ThreadQuickExitException {
      func_218797_a(p_218796_0_, p_218796_1_, p_218796_2_.getServer());
   }

   public static <T extends INetHandler> void func_218797_a(IPacket<T> p_218797_0_, T p_218797_1_, ThreadTaskExecutor<?> p_218797_2_) throws ThreadQuickExitException {
      if (!p_218797_2_.isOnExecutionThread()) {
         p_218797_2_.execute(() -> {
            p_218797_0_.processPacket(p_218797_1_);
         });
         throw ThreadQuickExitException.INSTANCE;
      }
   }
}