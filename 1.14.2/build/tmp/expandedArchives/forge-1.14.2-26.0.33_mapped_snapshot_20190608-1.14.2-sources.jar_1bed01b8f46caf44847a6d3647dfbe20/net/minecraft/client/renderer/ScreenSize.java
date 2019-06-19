package net.minecraft.client.renderer;

import java.util.Optional;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenSize {
   public final int field_216494_a;
   public final int field_216495_b;
   public final Optional<Integer> field_216496_c;
   public final Optional<Integer> field_216497_d;
   public final boolean field_216498_e;

   public ScreenSize(int p_i51174_1_, int p_i51174_2_, Optional<Integer> p_i51174_3_, Optional<Integer> p_i51174_4_, boolean p_i51174_5_) {
      this.field_216494_a = p_i51174_1_;
      this.field_216495_b = p_i51174_2_;
      this.field_216496_c = p_i51174_3_;
      this.field_216497_d = p_i51174_4_;
      this.field_216498_e = p_i51174_5_;
   }
}