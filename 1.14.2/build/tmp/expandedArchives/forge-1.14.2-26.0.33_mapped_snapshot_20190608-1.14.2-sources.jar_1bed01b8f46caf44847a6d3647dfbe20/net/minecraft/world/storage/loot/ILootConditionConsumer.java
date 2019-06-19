package net.minecraft.world.storage.loot;

import net.minecraft.world.storage.loot.conditions.ILootCondition;

public interface ILootConditionConsumer<T> {
   T acceptCondition(ILootCondition.IBuilder p_212840_1_);

   T cast();
}