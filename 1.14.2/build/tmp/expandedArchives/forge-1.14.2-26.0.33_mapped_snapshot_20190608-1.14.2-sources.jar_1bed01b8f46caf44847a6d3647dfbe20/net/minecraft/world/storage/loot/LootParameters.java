package net.minecraft.world.storage.loot;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class LootParameters {
   public static final LootParameter<Entity> field_216281_a = func_216280_a("this_entity");
   public static final LootParameter<PlayerEntity> field_216282_b = func_216280_a("last_damage_player");
   public static final LootParameter<DamageSource> field_216283_c = func_216280_a("damage_source");
   public static final LootParameter<Entity> field_216284_d = func_216280_a("killer_entity");
   public static final LootParameter<Entity> field_216285_e = func_216280_a("direct_killer_entity");
   public static final LootParameter<BlockPos> field_216286_f = func_216280_a("position");
   public static final LootParameter<BlockState> field_216287_g = func_216280_a("block_state");
   public static final LootParameter<TileEntity> field_216288_h = func_216280_a("block_entity");
   public static final LootParameter<ItemStack> field_216289_i = func_216280_a("tool");
   public static final LootParameter<Float> field_216290_j = func_216280_a("explosion_radius");

   private static <T> LootParameter<T> func_216280_a(String p_216280_0_) {
      return new LootParameter<>(new ResourceLocation(p_216280_0_));
   }
}