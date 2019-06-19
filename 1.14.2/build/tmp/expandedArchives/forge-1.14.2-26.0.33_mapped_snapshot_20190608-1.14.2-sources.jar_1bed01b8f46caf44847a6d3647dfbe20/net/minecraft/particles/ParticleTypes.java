package net.minecraft.particles;

import net.minecraft.util.registry.Registry;

//@net.minecraftforge.registries.ObjectHolder("minecraft")
public class ParticleTypes {
   public static final BasicParticleType AMBIENT_ENTITY_EFFECT = func_218415_a("ambient_entity_effect", false);
   public static final BasicParticleType ANGRY_VILLAGER = func_218415_a("angry_villager", false);
   public static final BasicParticleType BARRIER = func_218415_a("barrier", false);
   public static final ParticleType<BlockParticleData> BLOCK = func_218416_a("block", BlockParticleData.DESERIALIZER);
   public static final BasicParticleType BUBBLE = func_218415_a("bubble", false);
   public static final BasicParticleType CLOUD = func_218415_a("cloud", false);
   public static final BasicParticleType CRIT = func_218415_a("crit", false);
   public static final BasicParticleType DAMAGE_INDICATOR = func_218415_a("damage_indicator", true);
   public static final BasicParticleType DRAGON_BREATH = func_218415_a("dragon_breath", false);
   public static final BasicParticleType DRIPPING_LAVA = func_218415_a("dripping_lava", false);
   public static final BasicParticleType field_218423_k = func_218415_a("falling_lava", false);
   public static final BasicParticleType field_218424_l = func_218415_a("landing_lava", false);
   public static final BasicParticleType DRIPPING_WATER = func_218415_a("dripping_water", false);
   public static final BasicParticleType field_218425_n = func_218415_a("falling_water", false);
   public static final ParticleType<RedstoneParticleData> DUST = func_218416_a("dust", RedstoneParticleData.DESERIALIZER);
   public static final BasicParticleType EFFECT = func_218415_a("effect", false);
   public static final BasicParticleType ELDER_GUARDIAN = func_218415_a("elder_guardian", true);
   public static final BasicParticleType ENCHANTED_HIT = func_218415_a("enchanted_hit", false);
   public static final BasicParticleType ENCHANT = func_218415_a("enchant", false);
   public static final BasicParticleType END_ROD = func_218415_a("end_rod", false);
   public static final BasicParticleType ENTITY_EFFECT = func_218415_a("entity_effect", false);
   public static final BasicParticleType EXPLOSION_EMITTER = func_218415_a("explosion_emitter", true);
   public static final BasicParticleType EXPLOSION = func_218415_a("explosion", true);
   public static final ParticleType<BlockParticleData> FALLING_DUST = func_218416_a("falling_dust", BlockParticleData.DESERIALIZER);
   public static final BasicParticleType FIREWORK = func_218415_a("firework", false);
   public static final BasicParticleType FISHING = func_218415_a("fishing", false);
   public static final BasicParticleType FLAME = func_218415_a("flame", false);
   public static final BasicParticleType field_218419_B = func_218415_a("flash", false);
   public static final BasicParticleType HAPPY_VILLAGER = func_218415_a("happy_villager", false);
   public static final BasicParticleType field_218420_D = func_218415_a("composter", false);
   public static final BasicParticleType HEART = func_218415_a("heart", false);
   public static final BasicParticleType INSTANT_EFFECT = func_218415_a("instant_effect", false);
   public static final ParticleType<ItemParticleData> ITEM = func_218416_a("item", ItemParticleData.DESERIALIZER);
   public static final BasicParticleType ITEM_SLIME = func_218415_a("item_slime", false);
   public static final BasicParticleType ITEM_SNOWBALL = func_218415_a("item_snowball", false);
   public static final BasicParticleType LARGE_SMOKE = func_218415_a("large_smoke", false);
   public static final BasicParticleType LAVA = func_218415_a("lava", false);
   public static final BasicParticleType MYCELIUM = func_218415_a("mycelium", false);
   public static final BasicParticleType NOTE = func_218415_a("note", false);
   public static final BasicParticleType POOF = func_218415_a("poof", true);
   public static final BasicParticleType PORTAL = func_218415_a("portal", false);
   public static final BasicParticleType RAIN = func_218415_a("rain", false);
   public static final BasicParticleType SMOKE = func_218415_a("smoke", false);
   public static final BasicParticleType field_218421_R = func_218415_a("sneeze", false);
   public static final BasicParticleType SPIT = func_218415_a("spit", true);
   public static final BasicParticleType SQUID_INK = func_218415_a("squid_ink", true);
   public static final BasicParticleType SWEEP_ATTACK = func_218415_a("sweep_attack", true);
   public static final BasicParticleType TOTEM_OF_UNDYING = func_218415_a("totem_of_undying", false);
   public static final BasicParticleType UNDERWATER = func_218415_a("underwater", false);
   public static final BasicParticleType field_218422_X = func_218415_a("splash", false);
   public static final BasicParticleType WITCH = func_218415_a("witch", false);
   public static final BasicParticleType BUBBLE_POP = func_218415_a("bubble_pop", false);
   public static final BasicParticleType CURRENT_DOWN = func_218415_a("current_down", false);
   public static final BasicParticleType BUBBLE_COLUMN_UP = func_218415_a("bubble_column_up", false);
   public static final BasicParticleType NAUTILUS = func_218415_a("nautilus", false);
   public static final BasicParticleType DOLPHIN = func_218415_a("dolphin", false);
   public static final BasicParticleType field_218417_ae = func_218415_a("campfire_cosy_smoke", true);
   public static final BasicParticleType field_218418_af = func_218415_a("campfire_signal_smoke", true);

   private static BasicParticleType func_218415_a(String p_218415_0_, boolean p_218415_1_) {
      return (BasicParticleType)Registry.<ParticleType<? extends IParticleData>>register(Registry.field_212632_u, p_218415_0_, new BasicParticleType(p_218415_1_));
   }

   private static <T extends IParticleData> ParticleType<T> func_218416_a(String p_218416_0_, IParticleData.IDeserializer<T> p_218416_1_) {
      return Registry.register(Registry.field_212632_u, p_218416_0_, new ParticleType<>(false, p_218416_1_));
   }
}