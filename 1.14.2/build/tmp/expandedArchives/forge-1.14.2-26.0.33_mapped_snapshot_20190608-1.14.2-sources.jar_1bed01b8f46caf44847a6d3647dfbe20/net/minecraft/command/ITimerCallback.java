package net.minecraft.command;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

@FunctionalInterface
public interface ITimerCallback<T> {
   void func_212869_a_(T p_212869_1_, TimerCallbackManager<T> p_212869_2_, long p_212869_3_);

   public abstract static class Serializer<T, C extends ITimerCallback<T>> {
      private final ResourceLocation field_216312_a;
      private final Class<?> field_216313_b;

      public Serializer(ResourceLocation p_i51270_1_, Class<?> p_i51270_2_) {
         this.field_216312_a = p_i51270_1_;
         this.field_216313_b = p_i51270_2_;
      }

      public ResourceLocation func_216310_a() {
         return this.field_216312_a;
      }

      public Class<?> func_216311_b() {
         return this.field_216313_b;
      }

      public abstract void func_212847_a_(CompoundNBT p_212847_1_, C p_212847_2_);

      public abstract C func_212846_b_(CompoundNBT p_212846_1_);
   }
}