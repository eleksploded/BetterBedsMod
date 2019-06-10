package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Runnables;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServerDemo;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL;

@OnlyIn(Dist.CLIENT)
public class GuiMainMenu extends GuiScreen {
   private static final Random RANDOM = new Random();
   /**
    * A random number between 0.0 and 1.0, used to determine if the title screen says <a
    * href="https://minecraft.gamepedia.com/Menu_screen#Minceraft">Minceraft</a> instead of Minecraft. Set during
    * construction; if the value is less than .0001, then Minceraft is displayed.
    */
   private final float minceraftRoll;
   /** The splash message. */
   private String splashText;
   private GuiButton buttonOptions;
   private GuiButton buttonResetDemo;
   /** The Object object utilized as a thread lock when performing non thread-safe operations */
   private final Object threadLock = new Object();
   public static final String MORE_INFO_TEXT = "Please click " + TextFormatting.UNDERLINE + "here" + TextFormatting.RESET + " for more information.";
   /** Width of openGLWarning2 */
   private int openGLWarning2Width;
   /** Width of openGLWarning1 */
   private int openGLWarning1Width;
   /** Left x coordinate of the OpenGL warning */
   private int openGLWarningX1;
   /** Top y coordinate of the OpenGL warning */
   private int openGLWarningY1;
   /** Right x coordinate of the OpenGL warning */
   private int openGLWarningX2;
   /** Bottom y coordinate of the OpenGL warning */
   private int openGLWarningY2;
   /** OpenGL graphics card warning. */
   private String openGLWarning1;
   /** OpenGL graphics card warning. */
   private String openGLWarning2 = MORE_INFO_TEXT;
   /** Link to the Mojang Support about minimum requirements */
   private String openGLWarningLink;
   private static final ResourceLocation SPLASH_TEXTS = new ResourceLocation("texts/splashes.txt");
   private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation("textures/gui/title/minecraft.png");
   private static final ResourceLocation MINECRAFT_TITLE_EDITION = new ResourceLocation("textures/gui/title/edition.png");
   /** Has the check for a realms notification screen been performed? */
   private boolean hasCheckedForRealmsNotification;
   /**
    * A screen generated by realms for notifications; drawn in adition to the main menu (buttons and such from both are
    * drawn at the same time). May be null.
    */
   private GuiScreen realmsNotification;
   private int widthCopyright;
   private int widthCopyrightRest;
   private final RenderSkybox panorama = new RenderSkybox(new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama")));
   private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;

   public GuiMainMenu() {
      this.splashText = "missingno";
      IResource iresource = null;

      try {
         List<String> list = Lists.newArrayList();
         iresource = Minecraft.getInstance().getResourceManager().getResource(SPLASH_TEXTS);
         BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(iresource.getInputStream(), StandardCharsets.UTF_8));

         String s;
         while((s = bufferedreader.readLine()) != null) {
            s = s.trim();
            if (!s.isEmpty()) {
               list.add(s);
            }
         }

         if (!list.isEmpty()) {
            while(true) {
               this.splashText = list.get(RANDOM.nextInt(list.size()));
               if (this.splashText.hashCode() != 125780783) {
                  break;
               }
            }
         }
      } catch (IOException var8) {
         ;
      } finally {
         IOUtils.closeQuietly((Closeable)iresource);
      }

      this.minceraftRoll = RANDOM.nextFloat();
      this.openGLWarning1 = "";
      if (!GL.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
         this.openGLWarning1 = I18n.format("title.oldgl1");
         this.openGLWarning2 = I18n.format("title.oldgl2");
         this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
      }

   }

   /**
    * Is there currently a realms notification screen, and are realms notifications enabled?
    */
   private boolean areRealmsNotificationsEnabled() {
      return Minecraft.getInstance().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && this.realmsNotification != null;
   }

   /**
    * Called from the main game loop to update the screen.
    */
   public void tick() {
      if (this.areRealmsNotificationsEnabled()) {
         this.realmsNotification.tick();
      }

   }

   /**
    * Returns true if this GUI should pause the game when it is displayed in single-player
    */
   public boolean doesGuiPauseGame() {
      return false;
   }

   /**
    * Called when escape is pressed in this gui.
    *  
    * @return true if the GUI is allowed to close from this press.
    */
   public boolean allowCloseWithEscape() {
      return false;
   }

   /**
    * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
    * window resizes, the buttonList is cleared beforehand.
    */
   protected void initGui() {
      this.widthCopyright = this.fontRenderer.getStringWidth("Copyright Mojang AB. Do not distribute!");
      this.widthCopyrightRest = this.width - this.widthCopyright - 2;
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
         this.splashText = "Merry X-mas!";
      } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
         this.splashText = "Happy new year!";
      } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
         this.splashText = "OOoooOOOoooo! Spooky!";
      }

      int i = 24;
      int j = this.height / 4 + 48;
      if (this.mc.isDemo()) {
         this.addDemoButtons(j, 24);
      } else {
         this.addSingleplayerMultiplayerButtons(j, 24);
      }

      this.buttonOptions = this.addButton(new GuiButton(0, this.width / 2 - 100, j + 72 + 12, 98, 20, I18n.format("menu.options")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.displayGuiScreen(new GuiOptions(GuiMainMenu.this, GuiMainMenu.this.mc.gameSettings));
         }
      });
      this.addButton(new GuiButton(4, this.width / 2 + 2, j + 72 + 12, 98, 20, I18n.format("menu.quit")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.shutdown();
         }
      });
      this.addButton(new GuiButtonLanguage(5, this.width / 2 - 124, j + 72 + 12) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.displayGuiScreen(new GuiLanguage(GuiMainMenu.this, GuiMainMenu.this.mc.gameSettings, GuiMainMenu.this.mc.getLanguageManager()));
         }
      });
      synchronized(this.threadLock) {
         this.openGLWarning1Width = this.fontRenderer.getStringWidth(this.openGLWarning1);
         this.openGLWarning2Width = this.fontRenderer.getStringWidth(this.openGLWarning2);
         int k = Math.max(this.openGLWarning1Width, this.openGLWarning2Width);
         this.openGLWarningX1 = (this.width - k) / 2;
         this.openGLWarningY1 = j - 24;
         this.openGLWarningX2 = this.openGLWarningX1 + k;
         this.openGLWarningY2 = this.openGLWarningY1 + 24;
      }

      this.mc.setConnectedToRealms(false);
      if (Minecraft.getInstance().gameSettings.getOptionOrdinalValue(GameSettings.Options.REALMS_NOTIFICATIONS) && !this.hasCheckedForRealmsNotification) {
         RealmsBridge realmsbridge = new RealmsBridge();
         this.realmsNotification = realmsbridge.getNotificationScreen(this);
         this.hasCheckedForRealmsNotification = true;
      }

      if (this.areRealmsNotificationsEnabled()) {
         this.realmsNotification.setWorldAndResolution(this.mc, this.width, this.height);
      }
      modUpdateNotification = net.minecraftforge.client.gui.NotificationModUpdateScreen.init(this, buttons.stream().filter(b -> b.id == 6).findFirst().orElse(null)); // The Mobs button is id 6
   }

   /**
    * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
    */
   private void addSingleplayerMultiplayerButtons(int yIn, int rowHeightIn) {
      this.addButton(new GuiButton(1, this.width / 2 - 100, yIn, I18n.format("menu.singleplayer")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.displayGuiScreen(new GuiWorldSelection(GuiMainMenu.this));
         }
      });
      this.addButton(new GuiButton(2, this.width / 2 - 100, yIn + rowHeightIn * 1, I18n.format("menu.multiplayer")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.displayGuiScreen(new GuiMultiplayer(GuiMainMenu.this));
         }
      });
      this.addButton(new GuiButton(14, this.width / 2 + 2, yIn + rowHeightIn * 2, 98, 20, I18n.format("menu.online")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.switchToRealms();
         }
      });
      this.addButton(new GuiButton(6, this.width / 2 - 100, yIn + rowHeightIn * 2, 98, 20, I18n.format("fml.menu.mods")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         @Override
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.displayGuiScreen(new net.minecraftforge.fml.client.gui.GuiModList(GuiMainMenu.this));
         }
      });
   }

   /**
    * Adds Demo buttons on Main Menu for players who are playing Demo.
    */
   private void addDemoButtons(int yIn, int rowHeightIn) {
      this.addButton(new GuiButton(11, this.width / 2 - 100, yIn, I18n.format("menu.playdemo")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            GuiMainMenu.this.mc.launchIntegratedServer("Demo_World", "Demo_World", WorldServerDemo.DEMO_WORLD_SETTINGS);
         }
      });
      this.buttonResetDemo = this.addButton(new GuiButton(12, this.width / 2 - 100, yIn + rowHeightIn * 1, I18n.format("menu.resetdemo")) {
         /**
          * Called when the left mouse button is pressed over this button. This method is specific to GuiButton.
          */
         public void onClick(double mouseX, double mouseY) {
            ISaveFormat isaveformat1 = GuiMainMenu.this.mc.getSaveLoader();
            WorldInfo worldinfo1 = isaveformat1.getWorldInfo("Demo_World");
            if (worldinfo1 != null) {
               GuiMainMenu.this.mc.displayGuiScreen(new GuiYesNo(GuiMainMenu.this, I18n.format("selectWorld.deleteQuestion"), I18n.format("selectWorld.deleteWarning", worldinfo1.getWorldName()), I18n.format("selectWorld.deleteButton"), I18n.format("gui.cancel"), 12));
            }

         }
      });
      ISaveFormat isaveformat = this.mc.getSaveLoader();
      WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");
      if (worldinfo == null) {
         this.buttonResetDemo.enabled = false;
      }

   }

   private void switchToRealms() {
      RealmsBridge realmsbridge = new RealmsBridge();
      realmsbridge.switchToRealms(this);
   }

   public void confirmResult(boolean p_confirmResult_1_, int p_confirmResult_2_) {
      if (p_confirmResult_1_ && p_confirmResult_2_ == 12) {
         ISaveFormat isaveformat = this.mc.getSaveLoader();
         isaveformat.flushCache();
         isaveformat.deleteWorldDirectory("Demo_World");
         this.mc.displayGuiScreen(this);
      } else if (p_confirmResult_2_ == 12) {
         this.mc.displayGuiScreen(this);
      } else if (p_confirmResult_2_ == 13) {
         if (p_confirmResult_1_) {
            Util.getOSType().openURI(this.openGLWarningLink);
         }

         this.mc.displayGuiScreen(this);
      }

   }

   /**
    * Draws the screen and all the components in it.
    */
   public void render(int mouseX, int mouseY, float partialTicks) {
      this.panorama.render(partialTicks);
      int i = 274;
      int j = this.width / 2 - 137;
      int k = 30;
      this.mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/title/background/panorama_overlay.png"));
      drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, 16, 128, this.width, this.height, 16.0F, 128.0F);
      this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_TEXTURES);
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      if ((double)this.minceraftRoll < 1.0E-4D) {
         this.drawTexturedModalRect(j + 0, 30, 0, 0, 99, 44);
         this.drawTexturedModalRect(j + 99, 30, 129, 0, 27, 44);
         this.drawTexturedModalRect(j + 99 + 26, 30, 126, 0, 3, 44);
         this.drawTexturedModalRect(j + 99 + 26 + 3, 30, 99, 0, 26, 44);
         this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
      } else {
         this.drawTexturedModalRect(j + 0, 30, 0, 0, 155, 44);
         this.drawTexturedModalRect(j + 155, 30, 0, 45, 155, 44);
      }

      this.mc.getTextureManager().bindTexture(MINECRAFT_TITLE_EDITION);
      drawModalRectWithCustomSizedTexture(j + 88, 67, 0.0F, 0.0F, 98, 14, 128.0F, 16.0F);

      net.minecraftforge.client.ForgeHooksClient.renderMainMenu(this, this.fontRenderer, this.width, this.height);

      GlStateManager.pushMatrix();
      GlStateManager.translatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
      GlStateManager.rotatef(-20.0F, 0.0F, 0.0F, 1.0F);
      float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.milliTime() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
      f = f * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashText) + 32);
      GlStateManager.scalef(f, f, f);
      this.drawCenteredString(this.fontRenderer, this.splashText, 0, -8, -256);
      GlStateManager.popMatrix();
      String s = "Minecraft 1.13.2";
      if (this.mc.isDemo()) {
         s = s + " Demo";
      } else {
         s = s + ("release".equalsIgnoreCase(this.mc.getVersionType()) ? "" : "/" + this.mc.getVersionType());
      }

      net.minecraftforge.fml.BrandingControl.forEachLine(true, true, (brdline, brd) ->
         this.drawString(this.fontRenderer, brd, 2, this.height - ( 10 + brdline * (this.fontRenderer.FONT_HEIGHT + 1)), 16777215)
      );

      this.drawString(this.fontRenderer, "Copyright Mojang AB. Do not distribute!", this.widthCopyrightRest, this.height - 10, -1);
      if (mouseX > this.widthCopyrightRest && mouseX < this.widthCopyrightRest + this.widthCopyright && mouseY > this.height - 10 && mouseY < this.height) {
         drawRect(this.widthCopyrightRest, this.height - 1, this.widthCopyrightRest + this.widthCopyright, this.height, -1);
      }

      if (this.openGLWarning1 != null && !this.openGLWarning1.isEmpty()) {
         drawRect(this.openGLWarningX1 - 2, this.openGLWarningY1 - 2, this.openGLWarningX2 + 2, this.openGLWarningY2 - 1, 1428160512);
         this.drawString(this.fontRenderer, this.openGLWarning1, this.openGLWarningX1, this.openGLWarningY1, -1);
         this.drawString(this.fontRenderer, this.openGLWarning2, (this.width - this.openGLWarning2Width) / 2, this.openGLWarningY1 + 12, -1);
      }

      super.render(mouseX, mouseY, partialTicks);
      if (this.areRealmsNotificationsEnabled()) {
         this.realmsNotification.render(mouseX, mouseY, partialTicks);
      }
      modUpdateNotification.render(mouseX, mouseY, partialTicks);

   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         return true;
      } else {
         synchronized(this.threadLock) {
            if (!this.openGLWarning1.isEmpty() && !StringUtils.isNullOrEmpty(this.openGLWarningLink) && p_mouseClicked_1_ >= (double)this.openGLWarningX1 && p_mouseClicked_1_ <= (double)this.openGLWarningX2 && p_mouseClicked_3_ >= (double)this.openGLWarningY1 && p_mouseClicked_3_ <= (double)this.openGLWarningY2) {
               GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
               guiconfirmopenlink.disableSecurityWarning();
               this.mc.displayGuiScreen(guiconfirmopenlink);
               return true;
            }
         }

         if (this.areRealmsNotificationsEnabled() && this.realmsNotification.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
            return true;
         } else {
            if (p_mouseClicked_1_ > (double)this.widthCopyrightRest && p_mouseClicked_1_ < (double)(this.widthCopyrightRest + this.widthCopyright) && p_mouseClicked_3_ > (double)(this.height - 10) && p_mouseClicked_3_ < (double)this.height) {
               this.mc.displayGuiScreen(new GuiWinGame(false, Runnables.doNothing()));
            }

            return false;
         }
      }
   }

   /**
    * Called when the screen is unloaded. Used to disable keyboard repeat events
    */
   public void onGuiClosed() {
      if (this.realmsNotification != null) {
         this.realmsNotification.onGuiClosed();
      }

   }
}