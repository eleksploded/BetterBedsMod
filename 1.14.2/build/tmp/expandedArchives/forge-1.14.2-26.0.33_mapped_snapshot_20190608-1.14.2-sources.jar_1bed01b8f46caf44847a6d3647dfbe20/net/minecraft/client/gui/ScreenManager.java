package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.EnchantmentScreen;
import net.minecraft.client.gui.screen.GrindstoneScreen;
import net.minecraft.client.gui.screen.HopperScreen;
import net.minecraft.client.gui.screen.LecternScreen;
import net.minecraft.client.gui.screen.LoomScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.AnvilScreen;
import net.minecraft.client.gui.screen.inventory.BeaconScreen;
import net.minecraft.client.gui.screen.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screen.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screen.inventory.CartographyTableScreen;
import net.minecraft.client.gui.screen.inventory.ChestScreen;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.client.gui.screen.inventory.DispenserScreen;
import net.minecraft.client.gui.screen.inventory.FurnaceScreen;
import net.minecraft.client.gui.screen.inventory.MerchantScreen;
import net.minecraft.client.gui.screen.inventory.ShulkerBoxScreen;
import net.minecraft.client.gui.screen.inventory.SmokerScreen;
import net.minecraft.client.gui.screen.inventory.StonecutterScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ScreenManager {
   private static final Logger LOG = LogManager.getLogger();
   private static final Map<ContainerType<?>, ScreenManager.IScreenFactory<?, ?>> FACTORIES = Maps.newHashMap();

   public static <T extends Container> void openScreen(@Nullable ContainerType<T> p_216909_0_, Minecraft p_216909_1_, int p_216909_2_, ITextComponent p_216909_3_) {
      getScreenFactory(p_216909_0_, p_216909_1_, p_216909_2_, p_216909_3_).ifPresent(f -> f.createScreen(p_216909_3_, p_216909_0_, p_216909_1_, p_216909_2_));
   }

   public static <T extends Container> java.util.Optional<IScreenFactory<T, ?>> getScreenFactory(@Nullable ContainerType<T> p_216909_0_, Minecraft p_216909_1_, int p_216909_2_, ITextComponent p_216909_3_) {
      if (p_216909_0_ == null) {
         LOG.warn("Trying to open invalid screen with name: {}", (Object)p_216909_3_.getString());
      } else {
         ScreenManager.IScreenFactory<T, ?> iscreenfactory = getFactory(p_216909_0_);
         if (iscreenfactory == null) {
            LOG.warn("Failed to create screen for menu type: {}", (Object)Registry.field_218366_G.getKey(p_216909_0_));
         } else {
            return java.util.Optional.of(iscreenfactory);
         }
      }
      return java.util.Optional.empty();
   }

   @Nullable
   private static <T extends Container> ScreenManager.IScreenFactory<T, ?> getFactory(ContainerType<T> type) {
      return (ScreenManager.IScreenFactory<T, ?>)FACTORIES.get(type);
   }

   public static <M extends Container, U extends Screen & IHasContainer<M>> void registerFactory(ContainerType<? extends M> p_216911_0_, ScreenManager.IScreenFactory<M, U> p_216911_1_) {
      ScreenManager.IScreenFactory<?, ?> iscreenfactory = FACTORIES.put(p_216911_0_, p_216911_1_);
      if (iscreenfactory != null) {
         throw new IllegalStateException("Duplicate registration for " + Registry.field_218366_G.getKey(p_216911_0_));
      }
   }

   public static boolean func_216910_a() {
      boolean flag = false;

      for(ContainerType<?> containertype : Registry.field_218366_G) {
         if (!FACTORIES.containsKey(containertype)) {
            LOG.debug("Menu {} has no matching screen", (Object)Registry.field_218366_G.getKey(containertype));
            flag = true;
         }
      }

      return flag;
   }

   static {
      registerFactory(ContainerType.field_221507_a, ChestScreen::new);
      registerFactory(ContainerType.field_221508_b, ChestScreen::new);
      registerFactory(ContainerType.field_221509_c, ChestScreen::new);
      registerFactory(ContainerType.field_221510_d, ChestScreen::new);
      registerFactory(ContainerType.field_221511_e, ChestScreen::new);
      registerFactory(ContainerType.field_221512_f, ChestScreen::new);
      registerFactory(ContainerType.field_221513_g, DispenserScreen::new);
      registerFactory(ContainerType.field_221514_h, AnvilScreen::new);
      registerFactory(ContainerType.field_221515_i, BeaconScreen::new);
      registerFactory(ContainerType.field_221516_j, BlastFurnaceScreen::new);
      registerFactory(ContainerType.field_221517_k, BrewingStandScreen::new);
      registerFactory(ContainerType.field_221518_l, CraftingScreen::new);
      registerFactory(ContainerType.field_221519_m, EnchantmentScreen::new);
      registerFactory(ContainerType.field_221520_n, FurnaceScreen::new);
      registerFactory(ContainerType.field_221521_o, GrindstoneScreen::new);
      registerFactory(ContainerType.field_221522_p, HopperScreen::new);
      registerFactory(ContainerType.field_221523_q, LecternScreen::new);
      registerFactory(ContainerType.field_221524_r, LoomScreen::new);
      registerFactory(ContainerType.field_221525_s, MerchantScreen::new);
      registerFactory(ContainerType.field_221526_t, ShulkerBoxScreen::new);
      registerFactory(ContainerType.field_221527_u, SmokerScreen::new);
      registerFactory(ContainerType.field_221528_v, CartographyTableScreen::new);
      registerFactory(ContainerType.field_221529_w, StonecutterScreen::new);
   }

   @OnlyIn(Dist.CLIENT)
   public interface IScreenFactory<T extends Container, U extends Screen & IHasContainer<T>> {
      default void createScreen(ITextComponent p_216908_1_, ContainerType<T> p_216908_2_, Minecraft p_216908_3_, int p_216908_4_) {
         U u = this.create(p_216908_2_.func_221506_a(p_216908_4_, p_216908_3_.player.inventory), p_216908_3_.player.inventory, p_216908_1_);
         p_216908_3_.player.openContainer = ((IHasContainer)u).getContainer();
         p_216908_3_.displayGuiScreen(u);
      }

      U create(T p_create_1_, PlayerInventory p_create_2_, ITextComponent p_create_3_);
   }
}