package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.world.ServerWorld;

public class ChangeJobTask extends Task<VillagerEntity> {
   public ChangeJobTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220942_c, MemoryModuleStatus.VALUE_ABSENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      VillagerData villagerdata = owner.func_213700_eh();
      return villagerdata.getProfession() != VillagerProfession.field_221151_a && villagerdata.getProfession() != VillagerProfession.field_221162_l && owner.func_213708_dV() == 0 && villagerdata.getLevel() <= 1;
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      p_212831_2_.func_213753_a(p_212831_2_.func_213700_eh().func_221126_a(VillagerProfession.field_221151_a));
      p_212831_2_.func_213770_a(p_212831_1_);
   }
}