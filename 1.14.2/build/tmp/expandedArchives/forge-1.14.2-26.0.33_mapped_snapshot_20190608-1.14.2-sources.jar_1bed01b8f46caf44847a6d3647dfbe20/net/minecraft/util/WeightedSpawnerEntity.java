package net.minecraft.util;

import net.minecraft.nbt.CompoundNBT;

public class WeightedSpawnerEntity extends WeightedRandom.Item {
   private final CompoundNBT field_185279_b;

   public WeightedSpawnerEntity() {
      super(1);
      this.field_185279_b = new CompoundNBT();
      this.field_185279_b.putString("id", "minecraft:pig");
   }

   public WeightedSpawnerEntity(CompoundNBT nbtIn) {
      this(nbtIn.contains("Weight", 99) ? nbtIn.getInt("Weight") : 1, nbtIn.getCompound("Entity"));
   }

   public WeightedSpawnerEntity(int itemWeightIn, CompoundNBT nbtIn) {
      super(itemWeightIn);
      this.field_185279_b = nbtIn;
   }

   public CompoundNBT toCompoundTag() {
      CompoundNBT compoundnbt = new CompoundNBT();
      if (!this.field_185279_b.contains("id", 8)) {
         this.field_185279_b.putString("id", "minecraft:pig");
      } else if (!this.field_185279_b.getString("id").contains(":")) {
         this.field_185279_b.putString("id", (new ResourceLocation(this.field_185279_b.getString("id"))).toString());
      }

      compoundnbt.put("Entity", this.field_185279_b);
      compoundnbt.putInt("Weight", this.itemWeight);
      return compoundnbt;
   }

   public CompoundNBT getNbt() {
      return this.field_185279_b;
   }
}