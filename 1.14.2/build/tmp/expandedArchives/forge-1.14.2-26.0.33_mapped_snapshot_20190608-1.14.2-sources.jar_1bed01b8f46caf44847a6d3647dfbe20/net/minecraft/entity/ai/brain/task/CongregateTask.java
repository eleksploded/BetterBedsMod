package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.util.math.EntityPosWrapper;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.ServerWorld;

public class CongregateTask extends Task<LivingEntity> {
   public CongregateTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220950_k, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220951_l, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220943_d, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220946_g, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220952_m, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, LivingEntity owner) {
      Brain<?> brain = owner.getBrain();
      Optional<GlobalPos> optional = brain.getMemory(MemoryModuleType.field_220943_d);
      return worldIn.getRandom().nextInt(100) == 0 && optional.isPresent() && Objects.equals(worldIn.getDimension().getType(), optional.get().func_218177_a()) && optional.get().func_218180_b().func_218137_a(owner.getPositionVec(), 4.0D) && brain.getMemory(MemoryModuleType.field_220946_g).get().stream().anyMatch((p_220570_0_) -> {
         return EntityType.VILLAGER.equals(p_220570_0_.getType());
      });
   }

   protected void startExecuting(ServerWorld p_212831_1_, LivingEntity p_212831_2_, long p_212831_3_) {
      Brain<?> brain = p_212831_2_.getBrain();
      brain.getMemory(MemoryModuleType.field_220946_g).ifPresent((p_220568_2_) -> {
         p_220568_2_.stream().filter((p_220572_0_) -> {
            return EntityType.VILLAGER.equals(p_220572_0_.getType());
         }).filter((p_220571_1_) -> {
            return p_220571_1_.getDistanceSq(p_212831_2_) <= 32.0D;
         }).findFirst().ifPresent((p_220569_1_) -> {
            brain.setMemory(MemoryModuleType.field_220952_m, p_220569_1_);
            brain.setMemory(MemoryModuleType.field_220951_l, new EntityPosWrapper(p_220569_1_));
            brain.setMemory(MemoryModuleType.field_220950_k, new WalkTarget(new EntityPosWrapper(p_220569_1_), 0.3F, 1));
         });
      });
   }
}