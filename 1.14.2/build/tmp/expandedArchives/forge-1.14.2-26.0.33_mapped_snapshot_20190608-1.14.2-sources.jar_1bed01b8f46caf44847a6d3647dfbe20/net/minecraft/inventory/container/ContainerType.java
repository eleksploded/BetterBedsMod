package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ContainerType<T extends Container> extends net.minecraftforge.registries.ForgeRegistryEntry<ContainerType<?>> implements net.minecraftforge.common.extensions.IForgeContainerType<T> {
   public static final ContainerType<ChestContainer> field_221507_a = func_221505_a("generic_9x1", ChestContainer::func_216986_a);
   public static final ContainerType<ChestContainer> field_221508_b = func_221505_a("generic_9x2", ChestContainer::func_216987_b);
   public static final ContainerType<ChestContainer> field_221509_c = func_221505_a("generic_9x3", ChestContainer::func_216988_c);
   public static final ContainerType<ChestContainer> field_221510_d = func_221505_a("generic_9x4", ChestContainer::func_216991_d);
   public static final ContainerType<ChestContainer> field_221511_e = func_221505_a("generic_9x5", ChestContainer::func_216989_e);
   public static final ContainerType<ChestContainer> field_221512_f = func_221505_a("generic_9x6", ChestContainer::func_216990_f);
   public static final ContainerType<DispenserContainer> field_221513_g = func_221505_a("generic_3x3", DispenserContainer::new);
   public static final ContainerType<RepairContainer> field_221514_h = func_221505_a("anvil", RepairContainer::new);
   public static final ContainerType<BeaconContainer> field_221515_i = func_221505_a("beacon", BeaconContainer::new);
   public static final ContainerType<BlastFurnaceContainer> field_221516_j = func_221505_a("blast_furnace", BlastFurnaceContainer::new);
   public static final ContainerType<BrewingStandContainer> field_221517_k = func_221505_a("brewing_stand", BrewingStandContainer::new);
   public static final ContainerType<WorkbenchContainer> field_221518_l = func_221505_a("crafting", WorkbenchContainer::new);
   public static final ContainerType<EnchantmentContainer> field_221519_m = func_221505_a("enchantment", EnchantmentContainer::new);
   public static final ContainerType<FurnaceContainer> field_221520_n = func_221505_a("furnace", FurnaceContainer::new);
   public static final ContainerType<GrindstoneContainer> field_221521_o = func_221505_a("grindstone", GrindstoneContainer::new);
   public static final ContainerType<HopperContainer> field_221522_p = func_221505_a("hopper", HopperContainer::new);
   public static final ContainerType<LecternContainer> field_221523_q = func_221505_a("lectern", (p_221504_0_, p_221504_1_) -> {
      return new LecternContainer(p_221504_0_);
   });
   public static final ContainerType<LoomContainer> field_221524_r = func_221505_a("loom", LoomContainer::new);
   public static final ContainerType<MerchantContainer> field_221525_s = func_221505_a("merchant", MerchantContainer::new);
   public static final ContainerType<ShulkerBoxContainer> field_221526_t = func_221505_a("shulker_box", ShulkerBoxContainer::new);
   public static final ContainerType<SmokerContainer> field_221527_u = func_221505_a("smoker", SmokerContainer::new);
   public static final ContainerType<CartographyContainer> field_221528_v = func_221505_a("cartography", CartographyContainer::new);
   public static final ContainerType<StonecutterContainer> field_221529_w = func_221505_a("stonecutter", StonecutterContainer::new);
   private final ContainerType.IFactory<T> field_221530_x;

   private static <T extends Container> ContainerType<T> func_221505_a(String p_221505_0_, ContainerType.IFactory<T> p_221505_1_) {
      return Registry.register(Registry.field_218366_G, p_221505_0_, new ContainerType<>(p_221505_1_));
   }

   public ContainerType(ContainerType.IFactory<T> p_i50072_1_) {
      this.field_221530_x = p_i50072_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public T func_221506_a(int p_221506_1_, PlayerInventory p_221506_2_) {
      return this.field_221530_x.create(p_221506_1_, p_221506_2_);
   }
   
   @Override
   public T create(int windowId, PlayerInventory playerInv, net.minecraft.network.PacketBuffer extraData) {
      if (this.field_221530_x instanceof net.minecraftforge.fml.network.IContainerFactory) {
         return ((net.minecraftforge.fml.network.IContainerFactory<T>) this.field_221530_x).create(windowId, playerInv, extraData);
      }
      return func_221506_a(windowId, playerInv);
   }

   public interface IFactory<T extends Container> {
      @OnlyIn(Dist.CLIENT)
      T create(int p_create_1_, PlayerInventory p_create_2_);
   }
}