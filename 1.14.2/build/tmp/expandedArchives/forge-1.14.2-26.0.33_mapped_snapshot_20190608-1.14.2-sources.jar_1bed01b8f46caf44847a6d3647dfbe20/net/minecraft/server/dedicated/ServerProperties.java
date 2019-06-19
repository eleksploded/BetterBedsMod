package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldType;

public class ServerProperties extends PropertyManager<ServerProperties> {
   public final boolean field_219007_a = this.func_218982_a("online-mode", true);
   public final boolean field_219008_b = this.func_218982_a("prevent-proxy-connections", false);
   public final String field_219009_c = this.func_218973_a("server-ip", "");
   public final boolean field_219010_d = this.func_218982_a("spawn-animals", true);
   public final boolean field_219011_e = this.func_218982_a("spawn-npcs", true);
   public final boolean field_219012_f = this.func_218982_a("pvp", true);
   public final boolean field_219013_g = this.func_218982_a("allow-flight", false);
   public final String field_219014_h = this.func_218973_a("resource-pack", "");
   public final String field_219015_i = this.func_218973_a("motd", "A Minecraft Server");
   public final boolean field_219016_j = this.func_218982_a("force-gamemode", false);
   public final boolean field_219017_k = this.func_218982_a("enforce-whitelist", false);
   public final boolean field_219018_l = this.func_218982_a("generate-structures", true);
   public final Difficulty field_219019_m = this.func_218983_a("difficulty", func_218964_a(Difficulty::byId, Difficulty::func_219963_a), Difficulty::getTranslationKey, Difficulty.EASY);
   public final GameType field_219020_n = this.func_218983_a("gamemode", func_218964_a(GameType::getByID, GameType::getByName), GameType::getName, GameType.SURVIVAL);
   public final String field_219021_o = this.func_218973_a("level-name", "world");
   public final String field_219022_p = this.func_218973_a("level-seed", "");
   public final WorldType field_219023_q = this.func_218983_a("level-type", WorldType::byName, WorldType::getName, WorldType.DEFAULT);
   public final String field_219024_r = this.func_218973_a("generator-settings", "");
   public final int field_219025_s = this.func_218968_a("server-port", 25565);
   public final int field_219026_t = this.func_218962_a("max-build-height", (p_218987_0_) -> {
      return MathHelper.clamp((p_218987_0_ + 8) / 16 * 16, 64, 256);
   }, 256);
   public final Boolean field_219027_u = this.func_218978_b("announce-player-achievements");
   public final boolean field_219028_v = this.func_218982_a("enable-query", false);
   public final int field_219029_w = this.func_218968_a("query.port", 25565);
   public final boolean field_219030_x = this.func_218982_a("enable-rcon", false);
   public final int field_219031_y = this.func_218968_a("rcon.port", 25575);
   public final String field_219032_z = this.func_218973_a("rcon.password", "");
   public final String field_218988_A = this.func_218980_a("resource-pack-hash");
   public final String field_218989_B = this.func_218973_a("resource-pack-sha1", "");
   public final boolean field_218990_C = this.func_218982_a("hardcore", false);
   public final boolean field_218991_D = this.func_218982_a("allow-nether", true);
   public final boolean field_218992_E = this.func_218982_a("spawn-monsters", true);
   public final boolean field_218993_F;
   public final boolean field_218994_G;
   public final boolean field_218995_H;
   public final int field_218996_I;
   public final int field_218997_J;
   public final long field_218998_K;
   public final int field_218999_L;
   public final int field_219000_M;
   public final int field_219001_N;
   public final boolean field_219002_O;
   public final boolean field_219003_P;
   public final int field_219004_Q;
   public final PropertyManager<ServerProperties>.Property<Integer> field_219005_R;
   public final PropertyManager<ServerProperties>.Property<Boolean> field_219006_S;

   public ServerProperties(Properties p_i50719_1_) {
      super(p_i50719_1_);
      if (this.func_218982_a("snooper-enabled", true)) {
         ;
      }

      this.field_218993_F = false;
      this.field_218994_G = this.func_218982_a("use-native-transport", true);
      this.field_218995_H = this.func_218982_a("enable-command-block", false);
      this.field_218996_I = this.func_218968_a("spawn-protection", 16);
      this.field_218997_J = this.func_218968_a("op-permission-level", 4);
      this.field_218998_K = this.func_218967_a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
      this.field_218999_L = this.func_218968_a("view-distance", 10);
      this.field_219000_M = this.func_218968_a("max-players", 20);
      this.field_219001_N = this.func_218968_a("network-compression-threshold", 256);
      this.field_219002_O = this.func_218982_a("broadcast-rcon-to-ops", true);
      this.field_219003_P = this.func_218982_a("broadcast-console-to-ops", true);
      this.field_219004_Q = this.func_218962_a("max-world-size", (p_218986_0_) -> {
         return MathHelper.clamp(p_218986_0_, 1, 29999984);
      }, 29999984);
      this.field_219005_R = this.func_218974_b("player-idle-timeout", 0);
      this.field_219006_S = this.func_218961_b("white-list", false);
   }

   public static ServerProperties func_218985_a(Path p_218985_0_) {
      return new ServerProperties(func_218969_b(p_218985_0_));
   }

   protected ServerProperties func_212857_b_(Properties p_212857_1_) {
      return new ServerProperties(p_212857_1_);
   }
}