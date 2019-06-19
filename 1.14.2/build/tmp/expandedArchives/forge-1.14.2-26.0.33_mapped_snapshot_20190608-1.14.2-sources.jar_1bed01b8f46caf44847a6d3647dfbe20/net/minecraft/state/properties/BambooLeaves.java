package net.minecraft.state.properties;

import net.minecraft.util.IStringSerializable;

public enum BambooLeaves implements IStringSerializable {
   NONE("none"),
   SMALL("small"),
   LARGE("large");

   private final String field_218391_d;

   private BambooLeaves(String p_i49957_3_) {
      this.field_218391_d = p_i49957_3_;
   }

   public String toString() {
      return this.field_218391_d;
   }

   public String getName() {
      return this.field_218391_d;
   }
}