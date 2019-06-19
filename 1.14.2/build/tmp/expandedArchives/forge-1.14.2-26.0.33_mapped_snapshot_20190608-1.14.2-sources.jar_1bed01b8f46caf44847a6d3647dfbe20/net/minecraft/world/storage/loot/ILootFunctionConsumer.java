package net.minecraft.world.storage.loot;

import net.minecraft.world.storage.loot.functions.ILootFunction;

public interface ILootFunctionConsumer<T> {
   T func_212841_b_(ILootFunction.IBuilder p_212841_1_);

   T cast();
}