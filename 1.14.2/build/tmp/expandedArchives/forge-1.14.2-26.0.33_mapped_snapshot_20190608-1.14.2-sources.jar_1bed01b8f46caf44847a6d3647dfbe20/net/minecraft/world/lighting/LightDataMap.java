package net.minecraft.world.lighting;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.world.chunk.NibbleArray;

public abstract class LightDataMap<M extends LightDataMap<M>> {
   private final long[] field_215646_b = new long[2];
   private final NibbleArray[] field_215647_c = new NibbleArray[2];
   private boolean field_215648_d;
   protected final Long2ObjectOpenHashMap<NibbleArray> field_215645_a;

   protected LightDataMap(Long2ObjectOpenHashMap<NibbleArray> p_i51299_1_) {
      this.field_215645_a = p_i51299_1_;
      this.func_215643_c();
      this.field_215648_d = true;
   }

   public abstract M func_212858_b_();

   public void func_215641_a(long p_215641_1_) {
      this.field_215645_a.put(p_215641_1_, this.field_215645_a.get(p_215641_1_).func_215654_b());
      this.func_215643_c();
   }

   public boolean func_215642_b(long p_215642_1_) {
      return this.field_215645_a.containsKey(p_215642_1_);
   }

   @Nullable
   public NibbleArray func_215638_c(long p_215638_1_) {
      if (this.field_215648_d) {
         for(int i = 0; i < 2; ++i) {
            if (p_215638_1_ == this.field_215646_b[i]) {
               return this.field_215647_c[i];
            }
         }
      }

      NibbleArray nibblearray = this.field_215645_a.get(p_215638_1_);
      if (nibblearray == null) {
         return null;
      } else {
         if (this.field_215648_d) {
            for(int j = 1; j > 0; --j) {
               this.field_215646_b[j] = this.field_215646_b[j - 1];
               this.field_215647_c[j] = this.field_215647_c[j - 1];
            }

            this.field_215646_b[0] = p_215638_1_;
            this.field_215647_c[0] = nibblearray;
         }

         return nibblearray;
      }
   }

   @Nullable
   public NibbleArray func_223130_d(long p_223130_1_) {
      return this.field_215645_a.remove(p_223130_1_);
   }

   public void func_215640_a(long p_215640_1_, NibbleArray p_215640_3_) {
      this.field_215645_a.put(p_215640_1_, p_215640_3_);
   }

   public void func_215643_c() {
      for(int i = 0; i < 2; ++i) {
         this.field_215646_b[i] = Long.MAX_VALUE;
         this.field_215647_c[i] = null;
      }

   }

   public void func_215644_d() {
      this.field_215648_d = false;
   }
}