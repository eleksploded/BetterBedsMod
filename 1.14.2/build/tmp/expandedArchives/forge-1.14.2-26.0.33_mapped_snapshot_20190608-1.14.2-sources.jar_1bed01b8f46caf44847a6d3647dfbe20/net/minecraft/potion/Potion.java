package net.minecraft.potion;

import com.google.common.collect.ImmutableList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class Potion extends net.minecraftforge.registries.ForgeRegistryEntry<Potion> {
   private final String baseName;
   private final ImmutableList<EffectInstance> effects;

   public static Potion getPotionTypeForName(String name) {
      return Registry.field_212621_j.getOrDefault(ResourceLocation.tryCreate(name));
   }

   public Potion(EffectInstance... p_i46739_1_) {
      this((String)null, p_i46739_1_);
   }

   public Potion(@Nullable String p_i46740_1_, EffectInstance... p_i46740_2_) {
      this.baseName = p_i46740_1_;
      this.effects = ImmutableList.copyOf(p_i46740_2_);
   }

   /**
    * Gets the name of this PotionType with a prefix (such as "Splash" or "Lingering") prepended
    */
   public String getNamePrefixed(String p_185174_1_) {
      return p_185174_1_ + (this.baseName == null ? Registry.field_212621_j.getKey(this).getPath() : this.baseName);
   }

   public List<EffectInstance> getEffects() {
      return this.effects;
   }

   public boolean hasInstantEffect() {
      if (!this.effects.isEmpty()) {
         for(EffectInstance effectinstance : this.effects) {
            if (effectinstance.getPotion().isInstant()) {
               return true;
            }
         }
      }

      return false;
   }
}