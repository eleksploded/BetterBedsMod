package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorld;

public class AssignProfessionTask extends Task<VillagerEntity> {
   public AssignProfessionTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220942_c, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      return owner.func_213700_eh().getProfession() == VillagerProfession.field_221151_a;
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      GlobalPos globalpos = p_212831_2_.getBrain().getMemory(MemoryModuleType.field_220942_c).get();
      MinecraftServer minecraftserver = p_212831_1_.getServer();
      minecraftserver.getWorld(globalpos.func_218177_a()).func_217443_B().func_219148_c(globalpos.func_218180_b()).ifPresent((p_220390_2_) -> {
         Registry.field_218370_L.stream().filter((p_220389_1_) -> {
            return p_220389_1_.func_221149_b() == p_220390_2_;
         }).findFirst().ifPresent((p_220388_2_) -> {
            p_212831_2_.func_213753_a(p_212831_2_.func_213700_eh().func_221126_a(p_220388_2_));
            p_212831_2_.func_213770_a(p_212831_1_);
         });
      });
   }
}