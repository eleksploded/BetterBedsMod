package net.minecraft.client.audio;

import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.openal.AL10;

@OnlyIn(Dist.CLIENT)
public class Listener {
   public static final Vec3d field_216470_a = new Vec3d(0.0D, 1.0D, 0.0D);
   private float field_216471_b = 1.0F;

   public void func_216465_a(Vec3d p_216465_1_) {
      AL10.alListener3f(4100, (float)p_216465_1_.x, (float)p_216465_1_.y, (float)p_216465_1_.z);
   }

   public void func_216469_a(Vec3d p_216469_1_, Vec3d p_216469_2_) {
      AL10.alListenerfv(4111, new float[]{(float)p_216469_1_.x, (float)p_216469_1_.y, (float)p_216469_1_.z, (float)p_216469_2_.x, (float)p_216469_2_.y, (float)p_216469_2_.z});
   }

   public void func_216466_a(float p_216466_1_) {
      AL10.alListenerf(4106, p_216466_1_);
      this.field_216471_b = p_216466_1_;
   }

   public float func_216467_a() {
      return this.field_216471_b;
   }

   public void func_216468_b() {
      this.func_216465_a(Vec3d.ZERO);
      this.func_216469_a(new Vec3d(0.0D, 0.0D, -1.0D), field_216470_a);
   }
}