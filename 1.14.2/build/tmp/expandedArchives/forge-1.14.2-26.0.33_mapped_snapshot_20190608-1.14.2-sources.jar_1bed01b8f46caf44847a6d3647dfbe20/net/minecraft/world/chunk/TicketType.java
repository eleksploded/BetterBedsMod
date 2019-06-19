package net.minecraft.world.chunk;

import java.util.Comparator;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ColumnPos;

public class TicketType<T> {
   private final String name;
   private final Comparator<T> field_219496_i;
   private final long lifespan;
   public static final TicketType<Unit> field_219488_a = create("start", (p_219486_0_, p_219486_1_) -> {
      return 0;
   });
   public static final TicketType<Unit> field_219489_b = create("dragon", (p_219485_0_, p_219485_1_) -> {
      return 0;
   });
   public static final TicketType<ChunkPos> field_219490_c = create("player", Comparator.comparingLong(ChunkPos::asLong));
   public static final TicketType<ChunkPos> field_219491_d = create("forced", Comparator.comparingLong(ChunkPos::asLong));
   public static final TicketType<ChunkPos> field_219492_e = create("light", Comparator.comparingLong(ChunkPos::asLong));
   public static final TicketType<ColumnPos> field_219493_f = create("portal", Comparator.comparingLong(ColumnPos::func_219438_b));
   public static final TicketType<Integer> field_223185_g = create("post_teleport", Integer::compareTo, 5);
   public static final TicketType<ChunkPos> field_219494_g = create("unknown", Comparator.comparingLong(ChunkPos::asLong), 1);

   public static <T> TicketType<T> create(String p_219484_0_, Comparator<T> p_219484_1_) {
      return new TicketType<>(p_219484_0_, p_219484_1_, 0L);
   }

   public static <T> TicketType<T> create(String p_223183_0_, Comparator<T> p_223183_1_, int p_223183_2_) {
      return new TicketType<>(p_223183_0_, p_223183_1_, (long)p_223183_2_);
   }

   protected TicketType(String p_i51521_1_, Comparator<T> p_i51521_2_, long p_i51521_3_) {
      this.name = p_i51521_1_;
      this.field_219496_i = p_i51521_2_;
      this.lifespan = p_i51521_3_;
   }

   public String toString() {
      return this.name;
   }

   public Comparator<T> func_219487_a() {
      return this.field_219496_i;
   }

   public long getLifespan() {
      return this.lifespan;
   }
}