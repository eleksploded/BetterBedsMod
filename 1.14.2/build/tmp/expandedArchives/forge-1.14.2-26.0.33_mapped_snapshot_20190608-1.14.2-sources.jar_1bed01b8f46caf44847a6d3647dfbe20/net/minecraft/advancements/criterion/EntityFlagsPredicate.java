package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.JSONUtils;

public class EntityFlagsPredicate {
   public static final EntityFlagsPredicate field_217979_a = (new EntityFlagsPredicate.Builder()).func_217966_b();
   @Nullable
   private final Boolean field_217980_b;
   @Nullable
   private final Boolean field_217981_c;
   @Nullable
   private final Boolean field_217982_d;
   @Nullable
   private final Boolean field_217983_e;
   @Nullable
   private final Boolean field_217984_f;

   public EntityFlagsPredicate(@Nullable Boolean p_i50808_1_, @Nullable Boolean p_i50808_2_, @Nullable Boolean p_i50808_3_, @Nullable Boolean p_i50808_4_, @Nullable Boolean p_i50808_5_) {
      this.field_217980_b = p_i50808_1_;
      this.field_217981_c = p_i50808_2_;
      this.field_217982_d = p_i50808_3_;
      this.field_217983_e = p_i50808_4_;
      this.field_217984_f = p_i50808_5_;
   }

   public boolean func_217974_a(Entity p_217974_1_) {
      if (this.field_217980_b != null && p_217974_1_.isBurning() != this.field_217980_b) {
         return false;
      } else if (this.field_217981_c != null && p_217974_1_.isSneaking() != this.field_217981_c) {
         return false;
      } else if (this.field_217982_d != null && p_217974_1_.isSprinting() != this.field_217982_d) {
         return false;
      } else if (this.field_217983_e != null && p_217974_1_.isSwimming() != this.field_217983_e) {
         return false;
      } else {
         return this.field_217984_f == null || !(p_217974_1_ instanceof LivingEntity) || ((LivingEntity)p_217974_1_).isChild() == this.field_217984_f;
      }
   }

   @Nullable
   private static Boolean func_217977_a(JsonObject p_217977_0_, String p_217977_1_) {
      return p_217977_0_.has(p_217977_1_) ? JSONUtils.getBoolean(p_217977_0_, p_217977_1_) : null;
   }

   public static EntityFlagsPredicate func_217975_a(@Nullable JsonElement p_217975_0_) {
      if (p_217975_0_ != null && !p_217975_0_.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(p_217975_0_, "entity flags");
         Boolean obool = func_217977_a(jsonobject, "is_on_fire");
         Boolean obool1 = func_217977_a(jsonobject, "is_sneaking");
         Boolean obool2 = func_217977_a(jsonobject, "is_sprinting");
         Boolean obool3 = func_217977_a(jsonobject, "is_swimming");
         Boolean obool4 = func_217977_a(jsonobject, "is_baby");
         return new EntityFlagsPredicate(obool, obool1, obool2, obool3, obool4);
      } else {
         return field_217979_a;
      }
   }

   private void func_217978_a(JsonObject p_217978_1_, String p_217978_2_, @Nullable Boolean p_217978_3_) {
      if (p_217978_3_ != null) {
         p_217978_1_.addProperty(p_217978_2_, p_217978_3_);
      }

   }

   public JsonElement func_217976_a() {
      if (this == field_217979_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         this.func_217978_a(jsonobject, "is_on_fire", this.field_217980_b);
         this.func_217978_a(jsonobject, "is_sneaking", this.field_217981_c);
         this.func_217978_a(jsonobject, "is_sprinting", this.field_217982_d);
         this.func_217978_a(jsonobject, "is_swimming", this.field_217983_e);
         this.func_217978_a(jsonobject, "is_baby", this.field_217984_f);
         return jsonobject;
      }
   }

   public static class Builder {
      @Nullable
      private Boolean field_217969_a;
      @Nullable
      private Boolean field_217970_b;
      @Nullable
      private Boolean field_217971_c;
      @Nullable
      private Boolean field_217972_d;
      @Nullable
      private Boolean field_217973_e;

      public static EntityFlagsPredicate.Builder func_217967_a() {
         return new EntityFlagsPredicate.Builder();
      }

      public EntityFlagsPredicate.Builder func_217968_a(@Nullable Boolean p_217968_1_) {
         this.field_217969_a = p_217968_1_;
         return this;
      }

      public EntityFlagsPredicate func_217966_b() {
         return new EntityFlagsPredicate(this.field_217969_a, this.field_217970_b, this.field_217971_c, this.field_217972_d, this.field_217973_e);
      }
   }
}