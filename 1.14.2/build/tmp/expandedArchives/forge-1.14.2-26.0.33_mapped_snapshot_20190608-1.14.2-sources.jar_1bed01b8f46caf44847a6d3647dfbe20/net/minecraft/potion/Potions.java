package net.minecraft.potion;

import net.minecraft.util.registry.Registry;

public class Potions {
   public static final Potion field_185229_a = func_222125_a("empty", new Potion());
   public static final Potion field_185230_b = func_222125_a("water", new Potion());
   public static final Potion field_185231_c = func_222125_a("mundane", new Potion());
   public static final Potion field_185232_d = func_222125_a("thick", new Potion());
   public static final Potion field_185233_e = func_222125_a("awkward", new Potion());
   public static final Potion field_185234_f = func_222125_a("night_vision", new Potion(new EffectInstance(Effects.field_76439_r, 3600)));
   public static final Potion field_185235_g = func_222125_a("long_night_vision", new Potion("night_vision", new EffectInstance(Effects.field_76439_r, 9600)));
   public static final Potion field_185236_h = func_222125_a("invisibility", new Potion(new EffectInstance(Effects.field_76441_p, 3600)));
   public static final Potion field_185237_i = func_222125_a("long_invisibility", new Potion("invisibility", new EffectInstance(Effects.field_76441_p, 9600)));
   public static final Potion field_185238_j = func_222125_a("leaping", new Potion(new EffectInstance(Effects.field_76430_j, 3600)));
   public static final Potion field_185239_k = func_222125_a("long_leaping", new Potion("leaping", new EffectInstance(Effects.field_76430_j, 9600)));
   public static final Potion field_185240_l = func_222125_a("strong_leaping", new Potion("leaping", new EffectInstance(Effects.field_76430_j, 1800, 1)));
   public static final Potion field_185241_m = func_222125_a("fire_resistance", new Potion(new EffectInstance(Effects.field_76426_n, 3600)));
   public static final Potion field_185242_n = func_222125_a("long_fire_resistance", new Potion("fire_resistance", new EffectInstance(Effects.field_76426_n, 9600)));
   public static final Potion field_185243_o = func_222125_a("swiftness", new Potion(new EffectInstance(Effects.field_76424_c, 3600)));
   public static final Potion field_185244_p = func_222125_a("long_swiftness", new Potion("swiftness", new EffectInstance(Effects.field_76424_c, 9600)));
   public static final Potion field_185245_q = func_222125_a("strong_swiftness", new Potion("swiftness", new EffectInstance(Effects.field_76424_c, 1800, 1)));
   public static final Potion field_185246_r = func_222125_a("slowness", new Potion(new EffectInstance(Effects.field_76421_d, 1800)));
   public static final Potion field_185247_s = func_222125_a("long_slowness", new Potion("slowness", new EffectInstance(Effects.field_76421_d, 4800)));
   public static final Potion field_203185_t = func_222125_a("strong_slowness", new Potion("slowness", new EffectInstance(Effects.field_76421_d, 400, 3)));
   public static final Potion field_203186_u = func_222125_a("turtle_master", new Potion("turtle_master", new EffectInstance(Effects.field_76421_d, 400, 3), new EffectInstance(Effects.field_76429_m, 400, 2)));
   public static final Potion field_203187_v = func_222125_a("long_turtle_master", new Potion("turtle_master", new EffectInstance(Effects.field_76421_d, 800, 3), new EffectInstance(Effects.field_76429_m, 800, 2)));
   public static final Potion field_203188_w = func_222125_a("strong_turtle_master", new Potion("turtle_master", new EffectInstance(Effects.field_76421_d, 400, 5), new EffectInstance(Effects.field_76429_m, 400, 3)));
   public static final Potion field_185248_t = func_222125_a("water_breathing", new Potion(new EffectInstance(Effects.field_76427_o, 3600)));
   public static final Potion field_185249_u = func_222125_a("long_water_breathing", new Potion("water_breathing", new EffectInstance(Effects.field_76427_o, 9600)));
   public static final Potion field_185250_v = func_222125_a("healing", new Potion(new EffectInstance(Effects.field_76432_h, 1)));
   public static final Potion field_185251_w = func_222125_a("strong_healing", new Potion("healing", new EffectInstance(Effects.field_76432_h, 1, 1)));
   public static final Potion field_185252_x = func_222125_a("harming", new Potion(new EffectInstance(Effects.field_76433_i, 1)));
   public static final Potion field_185253_y = func_222125_a("strong_harming", new Potion("harming", new EffectInstance(Effects.field_76433_i, 1, 1)));
   public static final Potion field_185254_z = func_222125_a("poison", new Potion(new EffectInstance(Effects.field_76436_u, 900)));
   public static final Potion field_185218_A = func_222125_a("long_poison", new Potion("poison", new EffectInstance(Effects.field_76436_u, 1800)));
   public static final Potion field_185219_B = func_222125_a("strong_poison", new Potion("poison", new EffectInstance(Effects.field_76436_u, 432, 1)));
   public static final Potion field_185220_C = func_222125_a("regeneration", new Potion(new EffectInstance(Effects.field_76428_l, 900)));
   public static final Potion field_185221_D = func_222125_a("long_regeneration", new Potion("regeneration", new EffectInstance(Effects.field_76428_l, 1800)));
   public static final Potion field_185222_E = func_222125_a("strong_regeneration", new Potion("regeneration", new EffectInstance(Effects.field_76428_l, 450, 1)));
   public static final Potion field_185223_F = func_222125_a("strength", new Potion(new EffectInstance(Effects.field_76420_g, 3600)));
   public static final Potion field_185224_G = func_222125_a("long_strength", new Potion("strength", new EffectInstance(Effects.field_76420_g, 9600)));
   public static final Potion field_185225_H = func_222125_a("strong_strength", new Potion("strength", new EffectInstance(Effects.field_76420_g, 1800, 1)));
   public static final Potion field_185226_I = func_222125_a("weakness", new Potion(new EffectInstance(Effects.field_76437_t, 1800)));
   public static final Potion field_185227_J = func_222125_a("long_weakness", new Potion("weakness", new EffectInstance(Effects.field_76437_t, 4800)));
   public static final Potion field_222126_O = func_222125_a("luck", new Potion("luck", new EffectInstance(Effects.field_188425_z, 6000)));
   public static final Potion field_204841_O = func_222125_a("slow_falling", new Potion(new EffectInstance(Effects.field_204839_B, 1800)));
   public static final Potion field_204842_P = func_222125_a("long_slow_falling", new Potion("slow_falling", new EffectInstance(Effects.field_204839_B, 4800)));

   private static Potion func_222125_a(String p_222125_0_, Potion p_222125_1_) {
      return Registry.register(Registry.field_212621_j, p_222125_0_, p_222125_1_);
   }
}