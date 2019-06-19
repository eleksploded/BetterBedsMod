package net.minecraft.world.storage.loot;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import java.util.function.Supplier;

public class ValidationResults {
   private final Multimap<String, String> field_216110_a;
   private final Supplier<String> field_216111_b;
   private String field_216112_c;

   public ValidationResults() {
      this(HashMultimap.create(), () -> {
         return "";
      });
   }

   public ValidationResults(Multimap<String, String> p_i51264_1_, Supplier<String> p_i51264_2_) {
      this.field_216110_a = p_i51264_1_;
      this.field_216111_b = p_i51264_2_;
   }

   private String func_216104_b() {
      if (this.field_216112_c == null) {
         this.field_216112_c = this.field_216111_b.get();
      }

      return this.field_216112_c;
   }

   public void func_216105_a(String p_216105_1_) {
      this.field_216110_a.put(this.func_216104_b(), p_216105_1_);
   }

   public ValidationResults func_216108_b(String p_216108_1_) {
      return new ValidationResults(this.field_216110_a, () -> {
         return this.func_216104_b() + p_216108_1_;
      });
   }

   public Multimap<String, String> func_216106_a() {
      return ImmutableMultimap.copyOf(this.field_216110_a);
   }
}