package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Items;
import net.minecraft.util.JSONUtils;
import net.minecraft.world.raid.Raid;

public class EntityEquipmentPredicate {
   public static final EntityEquipmentPredicate field_217958_a = new EntityEquipmentPredicate(ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY);
   public static final EntityEquipmentPredicate field_217959_b = new EntityEquipmentPredicate(ItemPredicate.Builder.create().item(Items.WHITE_BANNER).func_218002_a(Raid.ILLAGER_BANNER.getTag()).build(), ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY, ItemPredicate.ANY);
   private final ItemPredicate field_217960_c;
   private final ItemPredicate field_217961_d;
   private final ItemPredicate field_217962_e;
   private final ItemPredicate field_217963_f;
   private final ItemPredicate field_217964_g;
   private final ItemPredicate field_217965_h;

   public EntityEquipmentPredicate(ItemPredicate p_i50809_1_, ItemPredicate p_i50809_2_, ItemPredicate p_i50809_3_, ItemPredicate p_i50809_4_, ItemPredicate p_i50809_5_, ItemPredicate p_i50809_6_) {
      this.field_217960_c = p_i50809_1_;
      this.field_217961_d = p_i50809_2_;
      this.field_217962_e = p_i50809_3_;
      this.field_217963_f = p_i50809_4_;
      this.field_217964_g = p_i50809_5_;
      this.field_217965_h = p_i50809_6_;
   }

   public boolean func_217955_a(@Nullable Entity p_217955_1_) {
      if (this == field_217958_a) {
         return true;
      } else if (!(p_217955_1_ instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity livingentity = (LivingEntity)p_217955_1_;
         if (!this.field_217960_c.test(livingentity.getItemStackFromSlot(EquipmentSlotType.HEAD))) {
            return false;
         } else if (!this.field_217961_d.test(livingentity.getItemStackFromSlot(EquipmentSlotType.CHEST))) {
            return false;
         } else if (!this.field_217962_e.test(livingentity.getItemStackFromSlot(EquipmentSlotType.LEGS))) {
            return false;
         } else if (!this.field_217963_f.test(livingentity.getItemStackFromSlot(EquipmentSlotType.FEET))) {
            return false;
         } else if (!this.field_217964_g.test(livingentity.getItemStackFromSlot(EquipmentSlotType.MAINHAND))) {
            return false;
         } else {
            return this.field_217965_h.test(livingentity.getItemStackFromSlot(EquipmentSlotType.OFFHAND));
         }
      }
   }

   public static EntityEquipmentPredicate func_217956_a(@Nullable JsonElement p_217956_0_) {
      if (p_217956_0_ != null && !p_217956_0_.isJsonNull()) {
         JsonObject jsonobject = JSONUtils.getJsonObject(p_217956_0_, "equipment");
         ItemPredicate itempredicate = ItemPredicate.deserialize(jsonobject.get("head"));
         ItemPredicate itempredicate1 = ItemPredicate.deserialize(jsonobject.get("chest"));
         ItemPredicate itempredicate2 = ItemPredicate.deserialize(jsonobject.get("legs"));
         ItemPredicate itempredicate3 = ItemPredicate.deserialize(jsonobject.get("feet"));
         ItemPredicate itempredicate4 = ItemPredicate.deserialize(jsonobject.get("mainhand"));
         ItemPredicate itempredicate5 = ItemPredicate.deserialize(jsonobject.get("offhand"));
         return new EntityEquipmentPredicate(itempredicate, itempredicate1, itempredicate2, itempredicate3, itempredicate4, itempredicate5);
      } else {
         return field_217958_a;
      }
   }

   public JsonElement func_217957_a() {
      if (this == field_217958_a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.add("head", this.field_217960_c.serialize());
         jsonobject.add("chest", this.field_217961_d.serialize());
         jsonobject.add("legs", this.field_217962_e.serialize());
         jsonobject.add("feet", this.field_217963_f.serialize());
         jsonobject.add("mainhand", this.field_217964_g.serialize());
         jsonobject.add("offhand", this.field_217965_h.serialize());
         return jsonobject;
      }
   }
}