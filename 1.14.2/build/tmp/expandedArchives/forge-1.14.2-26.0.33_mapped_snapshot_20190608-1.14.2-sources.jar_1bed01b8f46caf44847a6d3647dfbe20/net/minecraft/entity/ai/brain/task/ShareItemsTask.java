package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ServerWorld;

public class ShareItemsTask extends Task<VillagerEntity> {
   private Set<Item> field_220588_a = ImmutableSet.of();

   public ShareItemsTask() {
      super(ImmutableMap.of(MemoryModuleType.field_220952_m, MemoryModuleStatus.VALUE_PRESENT, MemoryModuleType.field_220946_g, MemoryModuleStatus.VALUE_PRESENT));
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      return BrainUtil.func_220623_a(owner.getBrain(), MemoryModuleType.field_220952_m, EntityType.VILLAGER);
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
      return this.shouldExecute(p_212834_1_, p_212834_2_);
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      VillagerEntity villagerentity = (VillagerEntity)p_212831_2_.getBrain().getMemory(MemoryModuleType.field_220952_m).get();
      BrainUtil.func_220618_a(p_212831_2_, villagerentity);
      this.field_220588_a = func_220585_a(p_212831_2_, villagerentity);
   }

   protected void updateTask(ServerWorld p_212833_1_, VillagerEntity p_212833_2_, long p_212833_3_) {
      VillagerEntity villagerentity = (VillagerEntity)p_212833_2_.getBrain().getMemory(MemoryModuleType.field_220952_m).get();
      if (!(p_212833_2_.getDistanceSq(villagerentity) > 5.0D)) {
         BrainUtil.func_220618_a(p_212833_2_, villagerentity);
         p_212833_2_.func_213746_a(villagerentity, p_212833_3_);
         if (p_212833_2_.canAbondonItems() && villagerentity.wantsMoreFood()) {
            func_220586_a(p_212833_2_, VillagerEntity.field_213788_bA.keySet(), villagerentity);
         }

         if (!this.field_220588_a.isEmpty() && p_212833_2_.func_213715_ed().hasAny(this.field_220588_a)) {
            func_220586_a(p_212833_2_, this.field_220588_a, villagerentity);
         }

      }
   }

   protected void resetTask(ServerWorld p_212835_1_, VillagerEntity p_212835_2_, long p_212835_3_) {
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220952_m);
   }

   private static Set<Item> func_220585_a(VillagerEntity p_220585_0_, VillagerEntity p_220585_1_) {
      ImmutableSet<Item> immutableset = p_220585_1_.func_213700_eh().getProfession().func_221146_c();
      ImmutableSet<Item> immutableset1 = p_220585_0_.func_213700_eh().getProfession().func_221146_c();
      return immutableset.stream().filter((p_220587_1_) -> {
         return !immutableset1.contains(p_220587_1_);
      }).collect(Collectors.toSet());
   }

   private static void func_220586_a(VillagerEntity p_220586_0_, Set<Item> p_220586_1_, LivingEntity p_220586_2_) {
      Inventory inventory = p_220586_0_.func_213715_ed();
      ItemStack itemstack = ItemStack.EMPTY;

      for(int i = 0; i < inventory.getSizeInventory(); ++i) {
         ItemStack itemstack1 = inventory.getStackInSlot(i);
         if (!itemstack1.isEmpty()) {
            Item item = itemstack1.getItem();
            if (p_220586_1_.contains(item)) {
               int j = itemstack1.getCount() / 2;
               itemstack1.shrink(j);
               itemstack = new ItemStack(item, j);
               break;
            }
         }
      }

      if (!itemstack.isEmpty()) {
         BrainUtil.func_220624_a(p_220586_0_, itemstack, p_220586_2_);
      }

   }
}