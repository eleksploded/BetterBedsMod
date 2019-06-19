package net.minecraft.entity.ai.attributes;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.util.math.MathHelper;

public class AttributeModifier {
   private final double amount;
   private final AttributeModifier.Operation field_111172_b;
   private final Supplier<String> name;
   private final UUID id;
   private boolean isSaved = true;

   public AttributeModifier(String p_i50375_1_, double p_i50375_2_, AttributeModifier.Operation p_i50375_4_) {
      this(MathHelper.getRandomUUID(ThreadLocalRandom.current()), () -> {
         return p_i50375_1_;
      }, p_i50375_2_, p_i50375_4_);
   }

   public AttributeModifier(UUID p_i50376_1_, String p_i50376_2_, double p_i50376_3_, AttributeModifier.Operation p_i50376_5_) {
      this(p_i50376_1_, () -> {
         return p_i50376_2_;
      }, p_i50376_3_, p_i50376_5_);
   }

   public AttributeModifier(UUID p_i50377_1_, Supplier<String> p_i50377_2_, double p_i50377_3_, AttributeModifier.Operation p_i50377_5_) {
      this.id = p_i50377_1_;
      this.name = p_i50377_2_;
      this.amount = p_i50377_3_;
      this.field_111172_b = p_i50377_5_;
   }

   public UUID getID() {
      return this.id;
   }

   public String getName() {
      return this.name.get();
   }

   public AttributeModifier.Operation func_220375_c() {
      return this.field_111172_b;
   }

   public double getAmount() {
      return this.amount;
   }

   /**
    * @see #isSaved
    */
   public boolean isSaved() {
      return this.isSaved;
   }

   /**
    * @see #isSaved
    */
   public AttributeModifier setSaved(boolean saved) {
      this.isSaved = saved;
      return this;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;
         return Objects.equals(this.id, attributemodifier.id);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.id != null ? this.id.hashCode() : 0;
   }

   public String toString() {
      return "AttributeModifier{amount=" + this.amount + ", operation=" + this.field_111172_b + ", name='" + (String)this.name.get() + '\'' + ", id=" + this.id + ", serialize=" + this.isSaved + '}';
   }

   public static enum Operation {
      ADDITION(0),
      MULTIPLY_BASE(1),
      MULTIPLY_TOTAL(2);

      private static final AttributeModifier.Operation[] field_220373_d = new AttributeModifier.Operation[]{ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL};
      private final int field_220374_e;

      private Operation(int p_i50050_3_) {
         this.field_220374_e = p_i50050_3_;
      }

      public int func_220371_a() {
         return this.field_220374_e;
      }

      public static AttributeModifier.Operation func_220372_a(int p_220372_0_) {
         if (p_220372_0_ >= 0 && p_220372_0_ < field_220373_d.length) {
            return field_220373_d[p_220372_0_];
         } else {
            throw new IllegalArgumentException("No operation with value " + p_220372_0_);
         }
      }
   }
}