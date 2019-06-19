package net.minecraft.world.chunk;

import java.util.Objects;

public final class Ticket<T> implements Comparable<Ticket<?>> {
   private final TicketType<T> type;
   private final int field_219481_b;
   private final T field_219482_c;
   private final long timestamp;

   protected Ticket(TicketType<T> p_i50700_1_, int p_i50700_2_, T p_i50700_3_, long p_i50700_4_) {
      this.type = p_i50700_1_;
      this.field_219481_b = p_i50700_2_;
      this.field_219482_c = p_i50700_3_;
      this.timestamp = p_i50700_4_;
   }

   public int compareTo(Ticket<?> p_compareTo_1_) {
      int i = Integer.compare(this.field_219481_b, p_compareTo_1_.field_219481_b);
      if (i != 0) {
         return i;
      } else {
         int j = Integer.compare(System.identityHashCode(this.type), System.identityHashCode(p_compareTo_1_.type));
         return j != 0 ? j : this.type.func_219487_a().compare(this.field_219482_c, (T)p_compareTo_1_.field_219482_c);
      }
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof Ticket)) {
         return false;
      } else {
         Ticket<?> ticket = (Ticket)p_equals_1_;
         return this.field_219481_b == ticket.field_219481_b && Objects.equals(this.type, ticket.type) && Objects.equals(this.field_219482_c, ticket.field_219482_c);
      }
   }

   public int hashCode() {
      return Objects.hash(this.type, this.field_219481_b, this.field_219482_c);
   }

   public String toString() {
      return "Ticket[" + this.type + " " + this.field_219481_b + " (" + this.field_219482_c + ")] at " + this.timestamp;
   }

   public TicketType<T> func_219479_a() {
      return this.type;
   }

   public int func_219477_b() {
      return this.field_219481_b;
   }

   public boolean isExpired(long currentTime) {
      long i = this.type.getLifespan();
      return i != 0L && currentTime - this.timestamp > i;
   }
}