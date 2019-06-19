package net.minecraft.entity.merchant;

public interface IReputationType {
   IReputationType field_221029_a = func_221028_a("zombie_villager_cured");
   IReputationType field_221030_b = func_221028_a("golem_killed");
   IReputationType field_221031_c = func_221028_a("villager_hurt");
   IReputationType field_221032_d = func_221028_a("villager_killed");
   IReputationType field_221033_e = func_221028_a("trade");

   static IReputationType func_221028_a(final String p_221028_0_) {
      return new IReputationType() {
         public String toString() {
            return p_221028_0_;
         }
      };
   }
}