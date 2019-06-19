package net.minecraft.client.settings;

import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractOption {
   public static final SliderPercentageOption field_216694_a = new SliderPercentageOption("options.biomeBlendRadius", 0.0D, 7.0D, 1.0F, (p_216607_0_) -> {
      return (double)p_216607_0_.biomeBlendRadius;
   }, (p_216660_0_, p_216660_1_) -> {
      p_216660_0_.biomeBlendRadius = MathHelper.clamp(p_216660_1_.intValue(), 0, 7);
      Minecraft.getInstance().worldRenderer.loadRenderers();
   }, (p_216595_0_, p_216595_1_) -> {
      double d0 = p_216595_1_.func_216729_a(p_216595_0_);
      String s = p_216595_1_.func_216617_a();
      if (d0 == 0.0D) {
         return s + I18n.format("options.off");
      } else {
         int i = (int)d0 * 2 + 1;
         return s + i + "x" + i;
      }
   });
   public static final SliderPercentageOption field_216695_b = new SliderPercentageOption("options.chat.height.focused", 0.0D, 1.0D, 0.0F, (p_216587_0_) -> {
      return p_216587_0_.chatHeightFocused;
   }, (p_216600_0_, p_216600_1_) -> {
      p_216600_0_.chatHeightFocused = p_216600_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216642_0_, p_216642_1_) -> {
      double d0 = p_216642_1_.func_216726_a(p_216642_1_.func_216729_a(p_216642_0_));
      return p_216642_1_.func_216617_a() + NewChatGui.calculateChatboxHeight(d0) + "px";
   });
   public static final SliderPercentageOption field_216696_c = new SliderPercentageOption("options.chat.height.unfocused", 0.0D, 1.0D, 0.0F, (p_216611_0_) -> {
      return p_216611_0_.chatHeightUnfocused;
   }, (p_216650_0_, p_216650_1_) -> {
      p_216650_0_.chatHeightUnfocused = p_216650_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216604_0_, p_216604_1_) -> {
      double d0 = p_216604_1_.func_216726_a(p_216604_1_.func_216729_a(p_216604_0_));
      return p_216604_1_.func_216617_a() + NewChatGui.calculateChatboxHeight(d0) + "px";
   });
   public static final SliderPercentageOption field_216697_d = new SliderPercentageOption("options.chat.opacity", 0.0D, 1.0D, 0.0F, (p_216649_0_) -> {
      return p_216649_0_.chatOpacity;
   }, (p_216578_0_, p_216578_1_) -> {
      p_216578_0_.chatOpacity = p_216578_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216592_0_, p_216592_1_) -> {
      double d0 = p_216592_1_.func_216726_a(p_216592_1_.func_216729_a(p_216592_0_));
      return p_216592_1_.func_216617_a() + (int)(d0 * 90.0D + 10.0D) + "%";
   });
   public static final SliderPercentageOption field_216698_e = new SliderPercentageOption("options.chat.scale", 0.0D, 1.0D, 0.0F, (p_216591_0_) -> {
      return p_216591_0_.chatScale;
   }, (p_216624_0_, p_216624_1_) -> {
      p_216624_0_.chatScale = p_216624_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216637_0_, p_216637_1_) -> {
      double d0 = p_216637_1_.func_216726_a(p_216637_1_.func_216729_a(p_216637_0_));
      String s = p_216637_1_.func_216617_a();
      return d0 == 0.0D ? s + I18n.format("options.off") : s + (int)(d0 * 100.0D) + "%";
   });
   public static final SliderPercentageOption field_216699_f = new SliderPercentageOption("options.chat.width", 0.0D, 1.0D, 0.0F, (p_216601_0_) -> {
      return p_216601_0_.chatWidth;
   }, (p_216620_0_, p_216620_1_) -> {
      p_216620_0_.chatWidth = p_216620_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216673_0_, p_216673_1_) -> {
      double d0 = p_216673_1_.func_216726_a(p_216673_1_.func_216729_a(p_216673_0_));
      return p_216673_1_.func_216617_a() + NewChatGui.calculateChatboxWidth(d0) + "px";
   });
   public static final SliderPercentageOption field_216700_g = new SliderPercentageOption("options.fov", 30.0D, 110.0D, 1.0F, (p_216655_0_) -> {
      return p_216655_0_.fovSetting;
   }, (p_216612_0_, p_216612_1_) -> {
      p_216612_0_.fovSetting = p_216612_1_;
   }, (p_216590_0_, p_216590_1_) -> {
      double d0 = p_216590_1_.func_216729_a(p_216590_0_);
      String s = p_216590_1_.func_216617_a();
      if (d0 == 70.0D) {
         return s + I18n.format("options.fov.min");
      } else {
         return d0 == p_216590_1_.func_216733_c() ? s + I18n.format("options.fov.max") : s + (int)d0;
      }
   });
   public static final SliderPercentageOption field_216701_h = new SliderPercentageOption("options.framerateLimit", 10.0D, 260.0D, 10.0F, (p_216672_0_) -> {
      return (double)p_216672_0_.limitFramerate;
   }, (p_216608_0_, p_216608_1_) -> {
      p_216608_0_.limitFramerate = p_216608_1_.intValue();
      Minecraft.getInstance().mainWindow.func_216526_a(p_216608_0_.limitFramerate);
   }, (p_216645_0_, p_216645_1_) -> {
      double d0 = p_216645_1_.func_216729_a(p_216645_0_);
      String s = p_216645_1_.func_216617_a();
      return d0 == p_216645_1_.func_216733_c() ? s + I18n.format("options.framerateLimit.max") : s + I18n.format("options.framerate", (int)d0);
   });
   public static final SliderPercentageOption field_216702_i = new SliderPercentageOption("options.fullscreen.resolution", 0.0D, 0.0D, 1.0F, (p_216666_0_) -> {
      return (double)Minecraft.getInstance().mainWindow.getVideoModeIndex();
   }, (p_216599_0_, p_216599_1_) -> {
      Minecraft.getInstance().mainWindow.setFullscreenResolution(p_216599_1_.intValue());
   }, (p_216623_0_, p_216623_1_) -> {
      double d0 = p_216623_1_.func_216729_a(p_216623_0_);
      String s = p_216623_1_.func_216617_a();
      return d0 == 0.0D ? s + I18n.format("options.fullscreen.current") : s + Minecraft.getInstance().mainWindow.getVideoModeString((int)d0 - 1);
   });
   public static final SliderPercentageOption field_216703_j = new SliderPercentageOption("options.gamma", 0.0D, 1.0D, 0.0F, (p_216636_0_) -> {
      return p_216636_0_.gammaSetting;
   }, (p_216651_0_, p_216651_1_) -> {
      p_216651_0_.gammaSetting = p_216651_1_;
   }, (p_216594_0_, p_216594_1_) -> {
      double d0 = p_216594_1_.func_216726_a(p_216594_1_.func_216729_a(p_216594_0_));
      String s = p_216594_1_.func_216617_a();
      if (d0 == 0.0D) {
         return s + I18n.format("options.gamma.min");
      } else {
         return d0 == 1.0D ? s + I18n.format("options.gamma.max") : s + "+" + (int)(d0 * 100.0D) + "%";
      }
   });
   public static final SliderPercentageOption field_216704_k = new SliderPercentageOption("options.mipmapLevels", 0.0D, 4.0D, 1.0F, (p_216667_0_) -> {
      return (double)p_216667_0_.mipmapLevels;
   }, (p_216585_0_, p_216585_1_) -> {
      p_216585_0_.mipmapLevels = p_216585_1_.intValue();
   }, (p_216629_0_, p_216629_1_) -> {
      double d0 = p_216629_1_.func_216729_a(p_216629_0_);
      String s = p_216629_1_.func_216617_a();
      return d0 == 0.0D ? s + I18n.format("options.off") : s + (int)d0;
   });
   public static final SliderPercentageOption field_216705_l = new SliderMultiplierOption("options.mouseWheelSensitivity", 0.01D, 10.0D, 0.01F, (p_216581_0_) -> {
      return p_216581_0_.mouseWheelSensitivity;
   }, (p_216628_0_, p_216628_1_) -> {
      p_216628_0_.mouseWheelSensitivity = p_216628_1_;
   }, (p_216675_0_, p_216675_1_) -> {
      double d0 = p_216675_1_.func_216726_a(p_216675_1_.func_216729_a(p_216675_0_));
      return p_216675_1_.func_216617_a() + String.format("%.2f", p_216675_1_.func_216725_b(d0));
   });
   public static final SliderPercentageOption field_216706_m = new SliderPercentageOption("options.renderDistance", 2.0D, 16.0D, 1.0F, (p_216658_0_) -> {
      return (double)p_216658_0_.renderDistanceChunks;
   }, (p_216579_0_, p_216579_1_) -> {
      p_216579_0_.renderDistanceChunks = p_216579_1_.intValue();
      Minecraft.getInstance().worldRenderer.setDisplayListEntitiesDirty();
   }, (p_216664_0_, p_216664_1_) -> {
      double d0 = p_216664_1_.func_216729_a(p_216664_0_);
      return p_216664_1_.func_216617_a() + I18n.format("options.chunks", (int)d0);
   });
   public static final SliderPercentageOption field_216707_n = new SliderPercentageOption("options.sensitivity", 0.0D, 1.0D, 0.0F, (p_216654_0_) -> {
      return p_216654_0_.mouseSensitivity;
   }, (p_216644_0_, p_216644_1_) -> {
      p_216644_0_.mouseSensitivity = p_216644_1_;
   }, (p_216641_0_, p_216641_1_) -> {
      double d0 = p_216641_1_.func_216726_a(p_216641_1_.func_216729_a(p_216641_0_));
      String s = p_216641_1_.func_216617_a();
      if (d0 == 0.0D) {
         return s + I18n.format("options.sensitivity.min");
      } else {
         return d0 == 1.0D ? s + I18n.format("options.sensitivity.max") : s + (int)(d0 * 200.0D) + "%";
      }
   });
   public static final SliderPercentageOption field_216708_o = new SliderPercentageOption("options.accessibility.text_background_opacity", 0.0D, 1.0D, 0.0F, (p_216597_0_) -> {
      return p_216597_0_.field_216845_l;
   }, (p_216593_0_, p_216593_1_) -> {
      p_216593_0_.field_216845_l = p_216593_1_;
      Minecraft.getInstance().field_71456_v.getChatGUI().refreshChat();
   }, (p_216626_0_, p_216626_1_) -> {
      return p_216626_1_.func_216617_a() + (int)(p_216626_1_.func_216726_a(p_216626_1_.func_216729_a(p_216626_0_)) * 100.0D) + "%";
   });
   public static final IteratableOption field_216709_p = new IteratableOption("options.ao", (p_216653_0_, p_216653_1_) -> {
      p_216653_0_.ambientOcclusionStatus = AmbientOcclusionStatus.func_216570_a(p_216653_0_.ambientOcclusionStatus.func_216572_a() + p_216653_1_);
      Minecraft.getInstance().worldRenderer.loadRenderers();
   }, (p_216630_0_, p_216630_1_) -> {
      return p_216630_1_.func_216617_a() + I18n.format(p_216630_0_.ambientOcclusionStatus.func_216569_b());
   });
   public static final IteratableOption field_216710_q = new IteratableOption("options.attackIndicator", (p_216615_0_, p_216615_1_) -> {
      p_216615_0_.field_186716_M = AttackIndicatorStatus.func_216749_a(p_216615_0_.field_186716_M.func_216751_a() + p_216615_1_);
   }, (p_216609_0_, p_216609_1_) -> {
      return p_216609_1_.func_216617_a() + I18n.format(p_216609_0_.field_186716_M.func_216748_b());
   });
   public static final IteratableOption field_216711_r = new IteratableOption("options.chat.visibility", (p_216640_0_, p_216640_1_) -> {
      p_216640_0_.field_74343_n = ChatVisibility.func_221252_a((p_216640_0_.field_74343_n.func_221254_a() + p_216640_1_) % 3);
   }, (p_216598_0_, p_216598_1_) -> {
      return p_216598_1_.func_216617_a() + I18n.format(p_216598_0_.field_74343_n.func_221251_b());
   });
   public static final IteratableOption field_216712_s = new IteratableOption("options.graphics", (p_216577_0_, p_216577_1_) -> {
      p_216577_0_.fancyGraphics = !p_216577_0_.fancyGraphics;
      Minecraft.getInstance().worldRenderer.loadRenderers();
   }, (p_216633_0_, p_216633_1_) -> {
      return p_216633_0_.fancyGraphics ? p_216633_1_.func_216617_a() + I18n.format("options.graphics.fancy") : p_216633_1_.func_216617_a() + I18n.format("options.graphics.fast");
   });
   public static final IteratableOption field_216713_t = new IteratableOption("options.guiScale", (p_216674_0_, p_216674_1_) -> {
      p_216674_0_.guiScale = Integer.remainderUnsigned(p_216674_0_.guiScale + p_216674_1_, Minecraft.getInstance().mainWindow.func_216521_a(0, Minecraft.getInstance().getForceUnicodeFont()) + 1);
   }, (p_216668_0_, p_216668_1_) -> {
      return p_216668_1_.func_216617_a() + (p_216668_0_.guiScale == 0 ? I18n.format("options.guiScale.auto") : p_216668_0_.guiScale);
   });
   public static final IteratableOption field_216714_u = new IteratableOption("options.mainHand", (p_216584_0_, p_216584_1_) -> {
      p_216584_0_.field_186715_A = p_216584_0_.field_186715_A.opposite();
   }, (p_216596_0_, p_216596_1_) -> {
      return p_216596_1_.func_216617_a() + p_216596_0_.field_186715_A;
   });
   public static final IteratableOption field_216715_v = new IteratableOption("options.narrator", (p_216648_0_, p_216648_1_) -> {
      if (NarratorChatListener.INSTANCE.isActive()) {
         p_216648_0_.field_192571_R = NarratorStatus.func_216825_a(p_216648_0_.field_192571_R.func_216827_a() + p_216648_1_);
      } else {
         p_216648_0_.field_192571_R = NarratorStatus.OFF;
      }

      NarratorChatListener.INSTANCE.func_216865_a(p_216648_0_.field_192571_R);
   }, (p_216632_0_, p_216632_1_) -> {
      return NarratorChatListener.INSTANCE.isActive() ? p_216632_1_.func_216617_a() + I18n.format(p_216632_0_.field_192571_R.func_216824_b()) : p_216632_1_.func_216617_a() + I18n.format("options.narrator.notavailable");
   });
   public static final IteratableOption field_216716_w = new IteratableOption("options.particles", (p_216622_0_, p_216622_1_) -> {
      p_216622_0_.field_74362_aa = ParticleStatus.func_216833_a(p_216622_0_.field_74362_aa.func_216832_b() + p_216622_1_);
   }, (p_216616_0_, p_216616_1_) -> {
      return p_216616_1_.func_216617_a() + I18n.format(p_216616_0_.field_74362_aa.func_216831_a());
   });
   public static final IteratableOption field_216717_x = new IteratableOption("options.renderClouds", (p_216605_0_, p_216605_1_) -> {
      p_216605_0_.field_74345_l = CloudOption.func_216804_a(p_216605_0_.field_74345_l.func_216806_a() + p_216605_1_);
   }, (p_216602_0_, p_216602_1_) -> {
      return p_216602_1_.func_216617_a() + I18n.format(p_216602_0_.field_74345_l.func_216803_b());
   });
   public static final IteratableOption field_216718_y = new IteratableOption("options.accessibility.text_background", (p_216665_0_, p_216665_1_) -> {
      p_216665_0_.field_216844_T = !p_216665_0_.field_216844_T;
   }, (p_216639_0_, p_216639_1_) -> {
      return p_216639_1_.func_216617_a() + I18n.format(p_216639_0_.field_216844_T ? "options.accessibility.text_background.chat" : "options.accessibility.text_background.everywhere");
   });
   public static final BooleanOption field_216719_z = new BooleanOption("options.autoJump", (p_216619_0_) -> {
      return p_216619_0_.autoJump;
   }, (p_216621_0_, p_216621_1_) -> {
      p_216621_0_.autoJump = p_216621_1_;
   });
   public static final BooleanOption field_216677_A = new BooleanOption("options.autoSuggestCommands", (p_216643_0_) -> {
      return p_216643_0_.autoSuggestions;
   }, (p_216656_0_, p_216656_1_) -> {
      p_216656_0_.autoSuggestions = p_216656_1_;
   });
   public static final BooleanOption field_216678_B = new BooleanOption("options.chat.color", (p_216669_0_) -> {
      return p_216669_0_.chatColours;
   }, (p_216659_0_, p_216659_1_) -> {
      p_216659_0_.chatColours = p_216659_1_;
   });
   public static final BooleanOption field_216679_C = new BooleanOption("options.chat.links", (p_216583_0_) -> {
      return p_216583_0_.chatLinks;
   }, (p_216670_0_, p_216670_1_) -> {
      p_216670_0_.chatLinks = p_216670_1_;
   });
   public static final BooleanOption field_216680_D = new BooleanOption("options.chat.links.prompt", (p_216610_0_) -> {
      return p_216610_0_.chatLinksPrompt;
   }, (p_216652_0_, p_216652_1_) -> {
      p_216652_0_.chatLinksPrompt = p_216652_1_;
   });
   public static final BooleanOption field_216681_E = new BooleanOption("options.discrete_mouse_scroll", (p_216634_0_) -> {
      return p_216634_0_.field_216843_O;
   }, (p_216625_0_, p_216625_1_) -> {
      p_216625_0_.field_216843_O = p_216625_1_;
   });
   public static final BooleanOption field_216682_F = new BooleanOption("options.vsync", (p_216661_0_) -> {
      return p_216661_0_.enableVsync;
   }, (p_216635_0_, p_216635_1_) -> {
      p_216635_0_.enableVsync = p_216635_1_;
      if (Minecraft.getInstance().mainWindow != null) {
         Minecraft.getInstance().mainWindow.func_216523_b(p_216635_0_.enableVsync);
      }

   });
   public static final BooleanOption field_216683_G = new BooleanOption("options.entityShadows", (p_216576_0_) -> {
      return p_216576_0_.entityShadows;
   }, (p_216588_0_, p_216588_1_) -> {
      p_216588_0_.entityShadows = p_216588_1_;
   });
   public static final BooleanOption field_216684_H = new BooleanOption("options.forceUnicodeFont", (p_216657_0_) -> {
      return p_216657_0_.forceUnicodeFont;
   }, (p_216631_0_, p_216631_1_) -> {
      p_216631_0_.forceUnicodeFont = p_216631_1_;
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getFontResourceManager() != null) {
         minecraft.getFontResourceManager().func_216883_a(p_216631_0_.forceUnicodeFont, Util.func_215072_e(), minecraft);
      }

   });
   public static final BooleanOption field_216685_I = new BooleanOption("options.invertMouse", (p_216627_0_) -> {
      return p_216627_0_.invertMouse;
   }, (p_216603_0_, p_216603_1_) -> {
      p_216603_0_.invertMouse = p_216603_1_;
   });
   public static final BooleanOption field_216686_J = new BooleanOption("options.realmsNotifications", (p_216606_0_) -> {
      return p_216606_0_.realmsNotifications;
   }, (p_216618_0_, p_216618_1_) -> {
      p_216618_0_.realmsNotifications = p_216618_1_;
   });
   public static final BooleanOption field_216687_K = new BooleanOption("options.reducedDebugInfo", (p_216582_0_) -> {
      return p_216582_0_.reducedDebugInfo;
   }, (p_216613_0_, p_216613_1_) -> {
      p_216613_0_.reducedDebugInfo = p_216613_1_;
   });
   public static final BooleanOption field_216688_L = new BooleanOption("options.showSubtitles", (p_216663_0_) -> {
      return p_216663_0_.showSubtitles;
   }, (p_216662_0_, p_216662_1_) -> {
      p_216662_0_.showSubtitles = p_216662_1_;
   });
   public static final BooleanOption field_216689_M = new BooleanOption("options.snooper", (p_216638_0_) -> {
      if (p_216638_0_.snooperEnabled) {
         ;
      }

      return false;
   }, (p_216676_0_, p_216676_1_) -> {
      p_216676_0_.snooperEnabled = p_216676_1_;
   });
   public static final BooleanOption field_216690_N = new BooleanOption("options.touchscreen", (p_216614_0_) -> {
      return p_216614_0_.touchscreen;
   }, (p_216589_0_, p_216589_1_) -> {
      p_216589_0_.touchscreen = p_216589_1_;
   });
   public static final BooleanOption field_216691_O = new BooleanOption("options.fullscreen", (p_216671_0_) -> {
      return p_216671_0_.fullScreen;
   }, (p_216646_0_, p_216646_1_) -> {
      p_216646_0_.fullScreen = p_216646_1_;
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.mainWindow != null && minecraft.mainWindow.isFullscreen() != p_216646_0_.fullScreen) {
         minecraft.mainWindow.toggleFullscreen();
         p_216646_0_.fullScreen = minecraft.mainWindow.isFullscreen();
      }

   });
   public static final BooleanOption field_216692_P = new BooleanOption("options.viewBobbing", (p_216647_0_) -> {
      return p_216647_0_.viewBobbing;
   }, (p_216580_0_, p_216580_1_) -> {
      p_216580_0_.viewBobbing = p_216580_1_;
   });
   private final String field_216693_Q;

   public AbstractOption(String p_i51158_1_) {
      this.field_216693_Q = p_i51158_1_;
   }

   public abstract Widget func_216586_a(GameSettings p_216586_1_, int p_216586_2_, int p_216586_3_, int p_216586_4_);

   public String func_216617_a() {
      return I18n.format(this.field_216693_Q) + ": ";
   }
}