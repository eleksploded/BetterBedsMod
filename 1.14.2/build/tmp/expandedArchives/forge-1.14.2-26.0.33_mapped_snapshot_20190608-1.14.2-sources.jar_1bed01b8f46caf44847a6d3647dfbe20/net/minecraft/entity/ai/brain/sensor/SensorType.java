package net.minecraft.entity.ai.brain.sensor;

import java.util.function.Supplier;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class SensorType<U extends Sensor<?>> extends net.minecraftforge.registries.ForgeRegistryEntry<SensorType<?>> {
   public static final SensorType<DummySensor> field_220997_a = func_220996_a("dummy", DummySensor::new);
   public static final SensorType<NearestLivingEntitiesSensor> field_220998_b = func_220996_a("nearest_living_entities", NearestLivingEntitiesSensor::new);
   public static final SensorType<NearestPlayersSensor> field_220999_c = func_220996_a("nearest_players", NearestPlayersSensor::new);
   public static final SensorType<InteractableDoorsSensor> field_221000_d = func_220996_a("interactable_doors", InteractableDoorsSensor::new);
   public static final SensorType<NearestBedSensor> field_221001_e = func_220996_a("nearest_bed", NearestBedSensor::new);
   public static final SensorType<HurtBySensor> field_221002_f = func_220996_a("hurt_by", HurtBySensor::new);
   public static final SensorType<VillagerHostilesSensor> field_221003_g = func_220996_a("villager_hostiles", VillagerHostilesSensor::new);
   public static final SensorType<VillagerBabiesSensor> field_221004_h = func_220996_a("villager_babies", VillagerBabiesSensor::new);
   public static final SensorType<SecondaryPositionSensor> field_221005_i = func_220996_a("secondary_pois", SecondaryPositionSensor::new);
   private final Supplier<U> field_221006_j;

   public SensorType(Supplier<U> p_i51500_1_) {
      this.field_221006_j = p_i51500_1_;
   }

   public U func_220995_a() {
      return (U)(this.field_221006_j.get());
   }

   private static <U extends Sensor<?>> SensorType<U> func_220996_a(String p_220996_0_, Supplier<U> p_220996_1_) {
      return Registry.register(Registry.field_218373_O, new ResourceLocation(p_220996_0_), new SensorType<>(p_220996_1_));
   }
}