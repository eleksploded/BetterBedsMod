package net.minecraft.client;

import com.mojang.bridge.Bridge;
import com.mojang.bridge.game.GameSession;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.Language;
import com.mojang.bridge.game.PerformanceMetrics;
import com.mojang.bridge.game.RunningGame;
import com.mojang.bridge.launcher.Launcher;
import com.mojang.bridge.launcher.SessionEventListener;
import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.SharedConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MinecraftGame implements RunningGame {
   private final Minecraft field_216816_a;
   @Nullable
   private final Launcher field_216817_b;
   private SessionEventListener field_216818_c = SessionEventListener.NONE;

   public MinecraftGame(Minecraft p_i51163_1_) {
      this.field_216816_a = p_i51163_1_;
      this.field_216817_b = Bridge.getLauncher();
      if (this.field_216817_b != null) {
         this.field_216817_b.registerGame(this);
      }

   }

   public GameVersion getVersion() {
      return SharedConstants.getVersion();
   }

   public Language getSelectedLanguage() {
      return this.field_216816_a.getLanguageManager().getCurrentLanguage();
   }

   @Nullable
   public GameSession getCurrentSession() {
      ClientWorld clientworld = this.field_216816_a.world;
      return clientworld == null ? null : new ClientGameSession(clientworld, this.field_216816_a.player, this.field_216816_a.player.field_71174_a);
   }

   public PerformanceMetrics getPerformanceMetrics() {
      FrameTimer frametimer = this.field_216816_a.getFrameTimer();
      long i = 2147483647L;
      long j = -2147483648L;
      long k = 0L;

      for(long l : frametimer.getFrames()) {
         i = Math.min(i, l);
         j = Math.max(j, l);
         k += l;
      }

      return new MinecraftGame.MinecraftPerformanceMetrics((int)i, (int)j, (int)(k / (long)frametimer.getFrames().length), frametimer.getFrames().length);
   }

   public void setSessionEventListener(SessionEventListener p_setSessionEventListener_1_) {
      this.field_216818_c = p_setSessionEventListener_1_;
   }

   public void func_216814_a() {
      this.field_216818_c.onStartGameSession(this.getCurrentSession());
   }

   public void func_216815_b() {
      this.field_216818_c.onLeaveGameSession(this.getCurrentSession());
   }

   @OnlyIn(Dist.CLIENT)
   static class MinecraftPerformanceMetrics implements PerformanceMetrics {
      private final int field_216810_a;
      private final int field_216811_b;
      private final int field_216812_c;
      private final int field_216813_d;

      public MinecraftPerformanceMetrics(int p_i51282_1_, int p_i51282_2_, int p_i51282_3_, int p_i51282_4_) {
         this.field_216810_a = p_i51282_1_;
         this.field_216811_b = p_i51282_2_;
         this.field_216812_c = p_i51282_3_;
         this.field_216813_d = p_i51282_4_;
      }

      public int getMinTime() {
         return this.field_216810_a;
      }

      public int getMaxTime() {
         return this.field_216811_b;
      }

      public int getAverageTime() {
         return this.field_216812_c;
      }

      public int getSampleCount() {
         return this.field_216813_d;
      }
   }
}