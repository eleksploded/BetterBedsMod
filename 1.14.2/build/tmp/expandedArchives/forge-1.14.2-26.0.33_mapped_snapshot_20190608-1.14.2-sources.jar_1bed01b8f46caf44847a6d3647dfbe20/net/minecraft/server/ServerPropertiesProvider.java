package net.minecraft.server;

import java.nio.file.Path;
import java.util.function.UnaryOperator;
import net.minecraft.server.dedicated.ServerProperties;

public class ServerPropertiesProvider {
   private final Path field_219036_a;
   private ServerProperties field_219037_b;

   public ServerPropertiesProvider(Path p_i50718_1_) {
      this.field_219036_a = p_i50718_1_;
      this.field_219037_b = ServerProperties.func_218985_a(p_i50718_1_);
   }

   public ServerProperties func_219034_a() {
      return this.field_219037_b;
   }

   public void func_219035_b() {
      this.field_219037_b.func_218970_c(this.field_219036_a);
   }

   public ServerPropertiesProvider func_219033_a(UnaryOperator<ServerProperties> p_219033_1_) {
      (this.field_219037_b = p_219033_1_.apply(this.field_219037_b)).func_218970_c(this.field_219036_a);
      return this;
   }
}