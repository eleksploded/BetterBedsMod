package net.minecraft.util;

import com.mojang.datafixers.types.DynamicOps;

public interface IDynamicSerializable {
   <T> T serializeDynamic(DynamicOps<T> p_218175_1_);
}