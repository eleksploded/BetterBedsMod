package net.minecraft.enchantment;

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.registry.Registry;

@net.minecraftforge.registries.ObjectHolder("minecraft")
public class Enchantments {
   private static final EquipmentSlotType[] field_222195_L = new EquipmentSlotType[]{EquipmentSlotType.HEAD, EquipmentSlotType.CHEST, EquipmentSlotType.LEGS, EquipmentSlotType.FEET};
   public static final Enchantment PROTECTION = func_222191_a("protection", new ProtectionEnchantment(Enchantment.Rarity.COMMON, ProtectionEnchantment.Type.ALL, field_222195_L));
   public static final Enchantment FIRE_PROTECTION = func_222191_a("fire_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FIRE, field_222195_L));
   public static final Enchantment FEATHER_FALLING = func_222191_a("feather_falling", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.FALL, field_222195_L));
   public static final Enchantment BLAST_PROTECTION = func_222191_a("blast_protection", new ProtectionEnchantment(Enchantment.Rarity.RARE, ProtectionEnchantment.Type.EXPLOSION, field_222195_L));
   public static final Enchantment PROJECTILE_PROTECTION = func_222191_a("projectile_protection", new ProtectionEnchantment(Enchantment.Rarity.UNCOMMON, ProtectionEnchantment.Type.PROJECTILE, field_222195_L));
   public static final Enchantment RESPIRATION = func_222191_a("respiration", new RespirationEnchantment(Enchantment.Rarity.RARE, field_222195_L));
   public static final Enchantment AQUA_AFFINITY = func_222191_a("aqua_affinity", new AquaAffinityEnchantment(Enchantment.Rarity.RARE, field_222195_L));
   public static final Enchantment THORNS = func_222191_a("thorns", new ThornsEnchantment(Enchantment.Rarity.VERY_RARE, field_222195_L));
   public static final Enchantment DEPTH_STRIDER = func_222191_a("depth_strider", new DepthStriderEnchantment(Enchantment.Rarity.RARE, field_222195_L));
   public static final Enchantment FROST_WALKER = func_222191_a("frost_walker", new FrostWalkerEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.FEET));
   public static final Enchantment BINDING_CURSE = func_222191_a("binding_curse", new BindingCurseEnchantment(Enchantment.Rarity.VERY_RARE, field_222195_L));
   public static final Enchantment SHARPNESS = func_222191_a("sharpness", new DamageEnchantment(Enchantment.Rarity.COMMON, 0, EquipmentSlotType.MAINHAND));
   public static final Enchantment SMITE = func_222191_a("smite", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 1, EquipmentSlotType.MAINHAND));
   public static final Enchantment BANE_OF_ARTHROPODS = func_222191_a("bane_of_arthropods", new DamageEnchantment(Enchantment.Rarity.UNCOMMON, 2, EquipmentSlotType.MAINHAND));
   public static final Enchantment KNOCKBACK = func_222191_a("knockback", new KnockbackEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment FIRE_ASPECT = func_222191_a("fire_aspect", new FireAspectEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment LOOTING = func_222191_a("looting", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentType.WEAPON, EquipmentSlotType.MAINHAND));
   public static final Enchantment SWEEPING = func_222191_a("sweeping", new SweepingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment EFFICIENCY = func_222191_a("efficiency", new EfficiencyEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment SILK_TOUCH = func_222191_a("silk_touch", new SilkTouchEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment UNBREAKING = func_222191_a("unbreaking", new UnbreakingEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment FORTUNE = func_222191_a("fortune", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentType.DIGGER, EquipmentSlotType.MAINHAND));
   public static final Enchantment POWER = func_222191_a("power", new PowerEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment PUNCH = func_222191_a("punch", new PunchEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment FLAME = func_222191_a("flame", new FlameEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment INFINITY = func_222191_a("infinity", new InfinityEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment LUCK_OF_THE_SEA = func_222191_a("luck_of_the_sea", new LootBonusEnchantment(Enchantment.Rarity.RARE, EnchantmentType.FISHING_ROD, EquipmentSlotType.MAINHAND));
   public static final Enchantment LURE = func_222191_a("lure", new LureEnchantment(Enchantment.Rarity.RARE, EnchantmentType.FISHING_ROD, EquipmentSlotType.MAINHAND));
   public static final Enchantment LOYALTY = func_222191_a("loyalty", new LoyaltyEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment IMPALING = func_222191_a("impaling", new ImpalingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment RIPTIDE = func_222191_a("riptide", new RiptideEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment CHANNELING = func_222191_a("channeling", new ChannelingEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment field_222192_G = func_222191_a("multishot", new MultishotEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.MAINHAND));
   public static final Enchantment field_222193_H = func_222191_a("quick_charge", new QuickChargeEnchantment(Enchantment.Rarity.UNCOMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment field_222194_I = func_222191_a("piercing", new PiercingEnchantment(Enchantment.Rarity.COMMON, EquipmentSlotType.MAINHAND));
   public static final Enchantment MENDING = func_222191_a("mending", new MendingEnchantment(Enchantment.Rarity.RARE, EquipmentSlotType.values()));
   public static final Enchantment VANISHING_CURSE = func_222191_a("vanishing_curse", new VanishingCurseEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.values()));

   private static Enchantment func_222191_a(String p_222191_0_, Enchantment p_222191_1_) {
      return Registry.register(Registry.field_212628_q, p_222191_0_, p_222191_1_);
   }
}