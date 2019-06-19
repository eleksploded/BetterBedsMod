package net.minecraft.world.storage.loot;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.util.Set;

public class LootParameterSet {
   private final Set<LootParameter<?>> field_216278_a;
   private final Set<LootParameter<?>> field_216279_b;

   private LootParameterSet(Set<LootParameter<?>> required, Set<LootParameter<?>> optional) {
      this.field_216278_a = ImmutableSet.copyOf(required);
      this.field_216279_b = ImmutableSet.copyOf(Sets.union(required, optional));
   }

   public Set<LootParameter<?>> func_216277_a() {
      return this.field_216278_a;
   }

   public Set<LootParameter<?>> func_216276_b() {
      return this.field_216279_b;
   }

   public String toString() {
      return "[" + Joiner.on(", ").join(this.field_216279_b.stream().map((p_216275_1_) -> {
         return (this.field_216278_a.contains(p_216275_1_) ? "!" : "") + p_216275_1_.getId();
      }).iterator()) + "]";
   }

   public void func_216274_a(ValidationResults p_216274_1_, IParameterized p_216274_2_) {
      Set<LootParameter<?>> set = p_216274_2_.getRequiredParameters();
      Set<LootParameter<?>> set1 = Sets.difference(set, this.field_216279_b);
      if (!set1.isEmpty()) {
         p_216274_1_.func_216105_a("Parameters " + set1 + " are not provided in this context");
      }

   }

   public static class Builder {
      private final Set<LootParameter<?>> required = Sets.newIdentityHashSet();
      private final Set<LootParameter<?>> optional = Sets.newIdentityHashSet();

      public LootParameterSet.Builder required(LootParameter<?> p_216269_1_) {
         if (this.optional.contains(p_216269_1_)) {
            throw new IllegalArgumentException("Parameter " + p_216269_1_.getId() + " is already optional");
         } else {
            this.required.add(p_216269_1_);
            return this;
         }
      }

      public LootParameterSet.Builder optional(LootParameter<?> p_216271_1_) {
         if (this.required.contains(p_216271_1_)) {
            throw new IllegalArgumentException("Parameter " + p_216271_1_.getId() + " is already required");
         } else {
            this.optional.add(p_216271_1_);
            return this;
         }
      }

      public LootParameterSet build() {
         return new LootParameterSet(this.required, this.optional);
      }
   }
}