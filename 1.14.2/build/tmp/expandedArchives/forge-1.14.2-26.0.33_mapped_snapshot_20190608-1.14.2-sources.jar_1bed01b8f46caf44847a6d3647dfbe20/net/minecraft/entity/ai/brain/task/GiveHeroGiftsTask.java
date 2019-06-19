package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.BrainUtil;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;

public class GiveHeroGiftsTask extends Task<VillagerEntity> {
   private static final Map<VillagerProfession, ResourceLocation> field_220403_a = Util.make(Maps.newHashMap(), (p_220395_0_) -> {
      p_220395_0_.put(VillagerProfession.field_221152_b, LootTables.field_215798_ag);
      p_220395_0_.put(VillagerProfession.field_221153_c, LootTables.field_215799_ah);
      p_220395_0_.put(VillagerProfession.field_221154_d, LootTables.field_215800_ai);
      p_220395_0_.put(VillagerProfession.field_221155_e, LootTables.field_215801_aj);
      p_220395_0_.put(VillagerProfession.field_221156_f, LootTables.field_215802_ak);
      p_220395_0_.put(VillagerProfession.field_221157_g, LootTables.field_215803_al);
      p_220395_0_.put(VillagerProfession.field_221158_h, LootTables.field_215804_am);
      p_220395_0_.put(VillagerProfession.field_221159_i, LootTables.field_215805_an);
      p_220395_0_.put(VillagerProfession.field_221160_j, LootTables.field_215806_ao);
      p_220395_0_.put(VillagerProfession.field_221161_k, LootTables.field_215807_ap);
      p_220395_0_.put(VillagerProfession.field_221163_m, LootTables.field_215808_aq);
      p_220395_0_.put(VillagerProfession.field_221164_n, LootTables.field_215809_ar);
      p_220395_0_.put(VillagerProfession.field_221165_o, LootTables.field_215810_as);
   });
   private int field_220404_b = 600;
   private boolean field_220405_c;
   private long field_220406_d;

   public GiveHeroGiftsTask(int p_i50366_1_) {
      super(ImmutableMap.of(MemoryModuleType.field_220950_k, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220951_l, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220952_m, MemoryModuleStatus.REGISTERED, MemoryModuleType.field_220949_j, MemoryModuleStatus.VALUE_PRESENT), p_i50366_1_);
   }

   protected boolean shouldExecute(ServerWorld worldIn, VillagerEntity owner) {
      if (!this.func_220396_b(owner)) {
         return false;
      } else if (this.field_220404_b > 0) {
         --this.field_220404_b;
         return false;
      } else {
         return true;
      }
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      this.field_220405_c = false;
      this.field_220406_d = p_212831_3_;
      PlayerEntity playerentity = this.func_220400_c(p_212831_2_).get();
      p_212831_2_.getBrain().setMemory(MemoryModuleType.field_220952_m, playerentity);
      BrainUtil.func_220625_c(p_212831_2_, playerentity);
   }

   protected boolean shouldContinueExecuting(ServerWorld p_212834_1_, VillagerEntity p_212834_2_, long p_212834_3_) {
      return this.func_220396_b(p_212834_2_) && !this.field_220405_c;
   }

   protected void updateTask(ServerWorld p_212833_1_, VillagerEntity p_212833_2_, long p_212833_3_) {
      PlayerEntity playerentity = this.func_220400_c(p_212833_2_).get();
      BrainUtil.func_220625_c(p_212833_2_, playerentity);
      if (this.func_220401_a(p_212833_2_, playerentity)) {
         if (p_212833_3_ - this.field_220406_d > 20L) {
            this.func_220398_a(p_212833_2_, playerentity);
            this.field_220405_c = true;
         }
      } else {
         BrainUtil.func_220621_a(p_212833_2_, playerentity, 5);
      }

   }

   protected void resetTask(ServerWorld p_212835_1_, VillagerEntity p_212835_2_, long p_212835_3_) {
      this.field_220404_b = func_220397_a(p_212835_1_);
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220952_m);
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220950_k);
      p_212835_2_.getBrain().removeMemory(MemoryModuleType.field_220951_l);
   }

   private void func_220398_a(VillagerEntity p_220398_1_, LivingEntity p_220398_2_) {
      for(ItemStack itemstack : this.func_220399_a(p_220398_1_)) {
         BrainUtil.func_220624_a(p_220398_1_, itemstack, p_220398_2_);
      }

   }

   private List<ItemStack> func_220399_a(VillagerEntity p_220399_1_) {
      if (p_220399_1_.isChild()) {
         return ImmutableList.of(new ItemStack(Items.field_221620_aV));
      } else {
         VillagerProfession villagerprofession = p_220399_1_.func_213700_eh().getProfession();
         if (field_220403_a.containsKey(villagerprofession)) {
            LootTable loottable = p_220399_1_.world.getServer().getLootTableManager().getLootTableFromLocation(field_220403_a.get(villagerprofession));
            LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerWorld)p_220399_1_.world)).withParameter(LootParameters.field_216286_f, new BlockPos(p_220399_1_)).withParameter(LootParameters.field_216281_a, p_220399_1_).withRandom(p_220399_1_.getRNG());
            return loottable.func_216113_a(lootcontext$builder.build(LootParameterSets.field_216264_e));
         } else {
            return ImmutableList.of(new ItemStack(Items.WHEAT_SEEDS));
         }
      }
   }

   private boolean func_220396_b(VillagerEntity p_220396_1_) {
      return this.func_220400_c(p_220396_1_).isPresent();
   }

   private Optional<PlayerEntity> func_220400_c(VillagerEntity p_220400_1_) {
      return p_220400_1_.getBrain().getMemory(MemoryModuleType.field_220949_j).filter(this::func_220402_a);
   }

   private boolean func_220402_a(PlayerEntity p_220402_1_) {
      return p_220402_1_.isPotionActive(Effects.field_220310_F);
   }

   private boolean func_220401_a(VillagerEntity p_220401_1_, PlayerEntity p_220401_2_) {
      BlockPos blockpos = new BlockPos(p_220401_2_);
      BlockPos blockpos1 = new BlockPos(p_220401_1_);
      return blockpos1.func_218141_a(blockpos, 5.0D);
   }

   private static int func_220397_a(ServerWorld p_220397_0_) {
      return 600 + p_220397_0_.rand.nextInt(6001);
   }
}