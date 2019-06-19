package net.minecraft.tags;

import java.util.Collection;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class EntityTypeTags {
   private static TagCollection<EntityType<?>> field_219766_c = new TagCollection<>((p_219758_0_) -> {
      return Optional.empty();
   }, "", false, "");
   private static int field_219767_d;
   public static final Tag<EntityType<?>> field_219764_a = func_219763_a("skeletons");
   public static final Tag<EntityType<?>> field_219765_b = func_219763_a("raiders");

   public static void func_219759_a(TagCollection<EntityType<?>> p_219759_0_) {
      field_219766_c = p_219759_0_;
      ++field_219767_d;
   }

   public static TagCollection<EntityType<?>> func_219762_a() {
      return field_219766_c;
   }

   public static int getGeneration() {
      return field_219767_d;
   }

   private static Tag<EntityType<?>> func_219763_a(String p_219763_0_) {
      return new EntityTypeTags.Wrapper(new ResourceLocation(p_219763_0_));
   }

   public static class Wrapper extends Tag<EntityType<?>> {
      private int field_219743_a = -1;
      private Tag<EntityType<?>> field_219744_b;

      public Wrapper(ResourceLocation p_i50383_1_) {
         super(p_i50383_1_);
      }

      public boolean contains(EntityType<?> itemIn) {
         if (this.field_219743_a != EntityTypeTags.field_219767_d) {
            this.field_219744_b = EntityTypeTags.field_219766_c.getOrCreate(this.getId());
            this.field_219743_a = EntityTypeTags.field_219767_d;
         }

         return this.field_219744_b.contains(itemIn);
      }

      public Collection<EntityType<?>> getAllElements() {
         if (this.field_219743_a != EntityTypeTags.field_219767_d) {
            this.field_219744_b = EntityTypeTags.field_219766_c.getOrCreate(this.getId());
            this.field_219743_a = EntityTypeTags.field_219767_d;
         }

         return this.field_219744_b.getAllElements();
      }

      public Collection<Tag.ITagEntry<EntityType<?>>> getEntries() {
         if (this.field_219743_a != EntityTypeTags.field_219767_d) {
            this.field_219744_b = EntityTypeTags.field_219766_c.getOrCreate(this.getId());
            this.field_219743_a = EntityTypeTags.field_219767_d;
         }

         return this.field_219744_b.getEntries();
      }
   }
}