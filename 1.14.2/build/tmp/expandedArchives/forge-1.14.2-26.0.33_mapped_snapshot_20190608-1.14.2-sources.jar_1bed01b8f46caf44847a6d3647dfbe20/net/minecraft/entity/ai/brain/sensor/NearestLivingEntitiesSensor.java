package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.world.ServerWorld;

public class NearestLivingEntitiesSensor extends Sensor<LivingEntity> {
   private static final EntityPredicate field_220982_b = (new EntityPredicate()).func_221013_a(16.0D).func_221011_b().func_221009_d().func_221014_c();

   protected void update(ServerWorld p_212872_1_, LivingEntity p_212872_2_) {
      List<LivingEntity> list = p_212872_1_.getEntitiesWithinAABB(LivingEntity.class, p_212872_2_.getBoundingBox().grow(16.0D, 16.0D, 16.0D), (p_220980_1_) -> {
         return p_220980_1_ != p_212872_2_ && p_220980_1_.isAlive();
      });
      list.sort(Comparator.comparingDouble(p_212872_2_::getDistanceSq));
      Brain<?> brain = p_212872_2_.getBrain();
      brain.setMemory(MemoryModuleType.field_220945_f, list);
      brain.setMemory(MemoryModuleType.field_220946_g, list.stream().filter((p_220981_1_) -> {
         return field_220982_b.canTarget(p_212872_2_, p_220981_1_);
      }).filter(p_212872_2_::canEntityBeSeen).collect(Collectors.toList()));
   }

   public Set<MemoryModuleType<?>> getUsedMemories() {
      return ImmutableSet.of(MemoryModuleType.field_220945_f, MemoryModuleType.field_220946_g);
   }
}