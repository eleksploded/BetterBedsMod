package net.minecraft.tileentity;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import java.util.Set;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.DataFixesManager;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TileEntityType<T extends TileEntity> extends net.minecraftforge.registries.ForgeRegistryEntry<TileEntityType<?>> {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final TileEntityType<FurnaceTileEntity> FURNACE = register("furnace", TileEntityType.Builder.func_223042_a(FurnaceTileEntity::new, Blocks.FURNACE));
   public static final TileEntityType<ChestTileEntity> CHEST = register("chest", TileEntityType.Builder.func_223042_a(ChestTileEntity::new, Blocks.CHEST));
   public static final TileEntityType<TrappedChestTileEntity> TRAPPED_CHEST = register("trapped_chest", TileEntityType.Builder.func_223042_a(TrappedChestTileEntity::new, Blocks.TRAPPED_CHEST));
   public static final TileEntityType<EnderChestTileEntity> ENDER_CHEST = register("ender_chest", TileEntityType.Builder.func_223042_a(EnderChestTileEntity::new, Blocks.ENDER_CHEST));
   public static final TileEntityType<JukeboxTileEntity> JUKEBOX = register("jukebox", TileEntityType.Builder.func_223042_a(JukeboxTileEntity::new, Blocks.JUKEBOX));
   public static final TileEntityType<DispenserTileEntity> DISPENSER = register("dispenser", TileEntityType.Builder.func_223042_a(DispenserTileEntity::new, Blocks.DISPENSER));
   public static final TileEntityType<DropperTileEntity> DROPPER = register("dropper", TileEntityType.Builder.func_223042_a(DropperTileEntity::new, Blocks.DROPPER));
   public static final TileEntityType<SignTileEntity> SIGN = register("sign", TileEntityType.Builder.func_223042_a(SignTileEntity::new, Blocks.field_222384_bX, Blocks.field_222385_bY, Blocks.field_222386_bZ, Blocks.field_222389_ca, Blocks.field_222390_cb, Blocks.field_222391_cc, Blocks.field_222392_ch, Blocks.field_222393_ci, Blocks.field_222394_cj, Blocks.field_222395_ck, Blocks.field_222396_cl, Blocks.field_222397_cm));
   public static final TileEntityType<MobSpawnerTileEntity> MOB_SPAWNER = register("mob_spawner", TileEntityType.Builder.func_223042_a(MobSpawnerTileEntity::new, Blocks.SPAWNER));
   public static final TileEntityType<PistonTileEntity> PISTON = register("piston", TileEntityType.Builder.func_223042_a(PistonTileEntity::new, Blocks.MOVING_PISTON));
   public static final TileEntityType<BrewingStandTileEntity> BREWING_STAND = register("brewing_stand", TileEntityType.Builder.func_223042_a(BrewingStandTileEntity::new, Blocks.BREWING_STAND));
   public static final TileEntityType<EnchantingTableTileEntity> ENCHANTING_TABLE = register("enchanting_table", TileEntityType.Builder.func_223042_a(EnchantingTableTileEntity::new, Blocks.ENCHANTING_TABLE));
   public static final TileEntityType<EndPortalTileEntity> END_PORTAL = register("end_portal", TileEntityType.Builder.func_223042_a(EndPortalTileEntity::new, Blocks.END_PORTAL));
   public static final TileEntityType<BeaconTileEntity> BEACON = register("beacon", TileEntityType.Builder.func_223042_a(BeaconTileEntity::new, Blocks.BEACON));
   public static final TileEntityType<SkullTileEntity> SKULL = register("skull", TileEntityType.Builder.func_223042_a(SkullTileEntity::new, Blocks.SKELETON_SKULL, Blocks.SKELETON_WALL_SKULL, Blocks.CREEPER_HEAD, Blocks.CREEPER_WALL_HEAD, Blocks.DRAGON_HEAD, Blocks.DRAGON_WALL_HEAD, Blocks.ZOMBIE_HEAD, Blocks.ZOMBIE_WALL_HEAD, Blocks.WITHER_SKELETON_SKULL, Blocks.WITHER_SKELETON_WALL_SKULL, Blocks.PLAYER_HEAD, Blocks.PLAYER_WALL_HEAD));
   public static final TileEntityType<DaylightDetectorTileEntity> DAYLIGHT_DETECTOR = register("daylight_detector", TileEntityType.Builder.func_223042_a(DaylightDetectorTileEntity::new, Blocks.DAYLIGHT_DETECTOR));
   public static final TileEntityType<HopperTileEntity> HOPPER = register("hopper", TileEntityType.Builder.func_223042_a(HopperTileEntity::new, Blocks.HOPPER));
   public static final TileEntityType<ComparatorTileEntity> COMPARATOR = register("comparator", TileEntityType.Builder.func_223042_a(ComparatorTileEntity::new, Blocks.COMPARATOR));
   public static final TileEntityType<BannerTileEntity> BANNER = register("banner", TileEntityType.Builder.func_223042_a(BannerTileEntity::new, Blocks.WHITE_BANNER, Blocks.ORANGE_BANNER, Blocks.MAGENTA_BANNER, Blocks.LIGHT_BLUE_BANNER, Blocks.YELLOW_BANNER, Blocks.LIME_BANNER, Blocks.PINK_BANNER, Blocks.GRAY_BANNER, Blocks.LIGHT_GRAY_BANNER, Blocks.CYAN_BANNER, Blocks.PURPLE_BANNER, Blocks.BLUE_BANNER, Blocks.BROWN_BANNER, Blocks.GREEN_BANNER, Blocks.RED_BANNER, Blocks.BLACK_BANNER, Blocks.WHITE_WALL_BANNER, Blocks.ORANGE_WALL_BANNER, Blocks.MAGENTA_WALL_BANNER, Blocks.LIGHT_BLUE_WALL_BANNER, Blocks.YELLOW_WALL_BANNER, Blocks.LIME_WALL_BANNER, Blocks.PINK_WALL_BANNER, Blocks.GRAY_WALL_BANNER, Blocks.LIGHT_GRAY_WALL_BANNER, Blocks.CYAN_WALL_BANNER, Blocks.PURPLE_WALL_BANNER, Blocks.BLUE_WALL_BANNER, Blocks.BROWN_WALL_BANNER, Blocks.GREEN_WALL_BANNER, Blocks.RED_WALL_BANNER, Blocks.BLACK_WALL_BANNER));
   public static final TileEntityType<StructureBlockTileEntity> STRUCTURE_BLOCK = register("structure_block", TileEntityType.Builder.func_223042_a(StructureBlockTileEntity::new, Blocks.STRUCTURE_BLOCK));
   public static final TileEntityType<EndGatewayTileEntity> END_GATEWAY = register("end_gateway", TileEntityType.Builder.func_223042_a(EndGatewayTileEntity::new, Blocks.END_GATEWAY));
   public static final TileEntityType<CommandBlockTileEntity> COMMAND_BLOCK = register("command_block", TileEntityType.Builder.func_223042_a(CommandBlockTileEntity::new, Blocks.COMMAND_BLOCK, Blocks.CHAIN_COMMAND_BLOCK, Blocks.REPEATING_COMMAND_BLOCK));
   public static final TileEntityType<ShulkerBoxTileEntity> SHULKER_BOX = register("shulker_box", TileEntityType.Builder.func_223042_a(ShulkerBoxTileEntity::new, Blocks.SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX));
   public static final TileEntityType<BedTileEntity> BED = register("bed", TileEntityType.Builder.func_223042_a(BedTileEntity::new, Blocks.RED_BED, Blocks.BLACK_BED, Blocks.BLUE_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GRAY_BED, Blocks.GREEN_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED, Blocks.PURPLE_BED, Blocks.WHITE_BED, Blocks.YELLOW_BED));
   public static final TileEntityType<ConduitTileEntity> CONDUIT = register("conduit", TileEntityType.Builder.func_223042_a(ConduitTileEntity::new, Blocks.CONDUIT));
   public static final TileEntityType<BarrelTileEntity> field_222489_z = register("barrel", TileEntityType.Builder.func_223042_a(BarrelTileEntity::new, Blocks.field_222422_lK));
   public static final TileEntityType<SmokerTileEntity> field_222483_A = register("smoker", TileEntityType.Builder.func_223042_a(SmokerTileEntity::new, Blocks.field_222423_lL));
   public static final TileEntityType<BlastFurnaceTileEntity> field_222484_B = register("blast_furnace", TileEntityType.Builder.func_223042_a(BlastFurnaceTileEntity::new, Blocks.field_222424_lM));
   public static final TileEntityType<LecternTileEntity> field_222485_C = register("lectern", TileEntityType.Builder.func_223042_a(LecternTileEntity::new, Blocks.field_222428_lQ));
   public static final TileEntityType<BellTileEntity> field_222486_D = register("bell", TileEntityType.Builder.func_223042_a(BellTileEntity::new, Blocks.field_222431_lT));
   public static final TileEntityType<JigsawTileEntity> field_222487_E = register("jigsaw", TileEntityType.Builder.func_223042_a(JigsawTileEntity::new, Blocks.field_222435_lY));
   public static final TileEntityType<CampfireTileEntity> field_222488_F = register("campfire", TileEntityType.Builder.func_223042_a(CampfireTileEntity::new, Blocks.field_222433_lV));
   private final Supplier<? extends T> factory;
   private final Set<Block> validBlocks;
   private final Type<?> datafixerType;

   @Nullable
   public static ResourceLocation getId(TileEntityType<?> tileEntityTypeIn) {
      return Registry.field_212626_o.getKey(tileEntityTypeIn);
   }

   private static <T extends TileEntity> TileEntityType<T> register(String id, TileEntityType.Builder<T> builder) {
      Type<?> type = null;

      try {
         type = DataFixesManager.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getVersion().getWorldVersion())).getChoiceType(TypeReferences.BLOCK_ENTITY, id);
      } catch (IllegalArgumentException illegalstateexception) {
         if (SharedConstants.developmentMode) {
            throw illegalstateexception;
         }

         LOGGER.warn("No data fixer registered for block entity {}", (Object)id);
      }

      if (builder.field_223044_b.isEmpty()) {
         LOGGER.warn("Block entity type {} requires at least one valid block to be defined!", (Object)id);
      }

      return Registry.register(Registry.field_212626_o, id, builder.build(type));
   }

   public TileEntityType(Supplier<? extends T> p_i51497_1_, Set<Block> p_i51497_2_, Type<?> p_i51497_3_) {
      this.factory = p_i51497_1_;
      this.validBlocks = p_i51497_2_;
      this.datafixerType = p_i51497_3_;
   }

   @Nullable
   public T create() {
      return (T)(this.factory.get());
   }

   public boolean isValidBlock(Block p_223045_1_) {
      return this.validBlocks.contains(p_223045_1_);
   }

   public static final class Builder<T extends TileEntity> {
      private final Supplier<? extends T> factory;
      private final Set<Block> field_223044_b;

      private Builder(Supplier<? extends T> p_i51498_1_, Set<Block> p_i51498_2_) {
         this.factory = p_i51498_1_;
         this.field_223044_b = p_i51498_2_;
      }

      public static <T extends TileEntity> TileEntityType.Builder<T> func_223042_a(Supplier<? extends T> p_223042_0_, Block... p_223042_1_) {
         return new TileEntityType.Builder<>(p_223042_0_, ImmutableSet.copyOf(p_223042_1_));
      }

      public TileEntityType<T> build(Type<?> datafixerType) {
         return new TileEntityType<>(this.factory, this.field_223044_b, datafixerType);
      }
   }
}