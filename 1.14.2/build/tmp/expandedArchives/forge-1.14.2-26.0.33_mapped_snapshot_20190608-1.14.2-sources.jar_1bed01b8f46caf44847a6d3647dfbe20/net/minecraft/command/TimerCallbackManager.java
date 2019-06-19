package net.minecraft.command;

import com.google.common.collect.Maps;
import com.google.common.primitives.UnsignedLong;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimerCallbackManager<T> {
   private static final Logger field_216333_a = LogManager.getLogger();
   private final TimerCallbackSerializers<T> field_216334_b;
   private final Queue<TimerCallbackManager.Entry<T>> field_216335_c = new PriorityQueue<>(func_216330_c());
   private UnsignedLong field_216336_d = UnsignedLong.ZERO;
   private final Map<String, TimerCallbackManager.Entry<T>> field_216337_e = Maps.newHashMap();

   private static <T> Comparator<TimerCallbackManager.Entry<T>> func_216330_c() {
      return (p_216324_0_, p_216324_1_) -> {
         int i = Long.compare(p_216324_0_.field_216319_a, p_216324_1_.field_216319_a);
         return i != 0 ? i : p_216324_0_.field_216320_b.compareTo(p_216324_1_.field_216320_b);
      };
   }

   public TimerCallbackManager(TimerCallbackSerializers<T> p_i51188_1_) {
      this.field_216334_b = p_i51188_1_;
   }

   public void func_216331_a(T p_216331_1_, long p_216331_2_) {
      while(true) {
         TimerCallbackManager.Entry<T> entry = this.field_216335_c.peek();
         if (entry == null || entry.field_216319_a > p_216331_2_) {
            return;
         }

         this.field_216335_c.remove();
         this.field_216337_e.remove(entry.field_216321_c);
         entry.field_216322_d.func_212869_a_(p_216331_1_, this, p_216331_2_);
      }
   }

   private void func_216328_c(String p_216328_1_, long p_216328_2_, ITimerCallback<T> p_216328_4_) {
      this.field_216336_d = this.field_216336_d.plus(UnsignedLong.ONE);
      TimerCallbackManager.Entry<T> entry = new TimerCallbackManager.Entry<>(p_216328_2_, this.field_216336_d, p_216328_1_, p_216328_4_);
      this.field_216337_e.put(p_216328_1_, entry);
      this.field_216335_c.add(entry);
   }

   public boolean func_216325_a(String p_216325_1_, long p_216325_2_, ITimerCallback<T> p_216325_4_) {
      if (this.field_216337_e.containsKey(p_216325_1_)) {
         return false;
      } else {
         this.func_216328_c(p_216325_1_, p_216325_2_, p_216325_4_);
         return true;
      }
   }

   public void func_216326_b(String p_216326_1_, long p_216326_2_, ITimerCallback<T> p_216326_4_) {
      TimerCallbackManager.Entry<T> entry = this.field_216337_e.remove(p_216326_1_);
      if (entry != null) {
         this.field_216335_c.remove(entry);
      }

      this.func_216328_c(p_216326_1_, p_216326_2_, p_216326_4_);
   }

   private void func_216329_a(CompoundNBT p_216329_1_) {
      CompoundNBT compoundnbt = p_216329_1_.getCompound("Callback");
      ITimerCallback<T> itimercallback = this.field_216334_b.func_216341_a(compoundnbt);
      if (itimercallback != null) {
         String s = p_216329_1_.getString("Name");
         long i = p_216329_1_.getLong("TriggerTime");
         this.func_216325_a(s, i, itimercallback);
      }

   }

   public void func_216323_a(ListNBT p_216323_1_) {
      this.field_216335_c.clear();
      this.field_216337_e.clear();
      this.field_216336_d = UnsignedLong.ZERO;
      if (!p_216323_1_.isEmpty()) {
         if (p_216323_1_.getTagType() != 10) {
            field_216333_a.warn("Invalid format of events: " + p_216323_1_);
         } else {
            for(INBT inbt : p_216323_1_) {
               this.func_216329_a((CompoundNBT)inbt);
            }

         }
      }
   }

   private CompoundNBT func_216332_a(TimerCallbackManager.Entry<T> p_216332_1_) {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("Name", p_216332_1_.field_216321_c);
      compoundnbt.putLong("TriggerTime", p_216332_1_.field_216319_a);
      compoundnbt.put("Callback", this.field_216334_b.func_216339_a(p_216332_1_.field_216322_d));
      return compoundnbt;
   }

   public ListNBT func_216327_b() {
      ListNBT listnbt = new ListNBT();
      this.field_216335_c.stream().sorted(func_216330_c()).map(this::func_216332_a).forEach(listnbt::add);
      return listnbt;
   }

   public static class Entry<T> {
      public final long field_216319_a;
      public final UnsignedLong field_216320_b;
      public final String field_216321_c;
      public final ITimerCallback<T> field_216322_d;

      private Entry(long p_i50837_1_, UnsignedLong p_i50837_3_, String p_i50837_4_, ITimerCallback<T> p_i50837_5_) {
         this.field_216319_a = p_i50837_1_;
         this.field_216320_b = p_i50837_3_;
         this.field_216321_c = p_i50837_4_;
         this.field_216322_d = p_i50837_5_;
      }
   }
}