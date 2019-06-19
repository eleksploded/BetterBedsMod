package net.minecraft.client.gui.screen;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.JsonOps;
import java.util.Random;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.FileUtil;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;

@OnlyIn(Dist.CLIENT)
public class CreateWorldScreen extends Screen {
   private final Screen field_146332_f;
   private TextFieldWidget field_146333_g;
   private TextFieldWidget field_146335_h;
   private String saveDirName;
   private String gameMode = "survival";
   private String savedGameMode;
   private boolean generateStructuresEnabled = true;
   private boolean allowCheats;
   private boolean allowCheatsWasSetByUser;
   private boolean bonusChestEnabled;
   private boolean hardCoreMode;
   private boolean alreadyGenerated;
   private boolean inMoreWorldOptionsDisplay;
   private Button field_195355_B;
   private Button field_146343_z;
   private Button field_146324_A;
   private Button field_146325_B;
   private Button field_146326_C;
   private Button field_146320_D;
   private Button field_146321_E;
   private Button field_146322_F;
   private String gameModeDesc1;
   private String gameModeDesc2;
   private String worldSeed;
   private String worldName;
   private int selectedIndex;
   public CompoundNBT field_146334_a = new CompoundNBT();

   public CreateWorldScreen(Screen p_i46320_1_) {
      super(new TranslationTextComponent("selectWorld.create"));
      this.field_146332_f = p_i46320_1_;
      this.worldSeed = "";
      this.worldName = I18n.format("selectWorld.newWorld");
   }

   public void tick() {
      this.field_146333_g.tick();
      this.field_146335_h.tick();
   }

   protected void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.field_146333_g = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 200, 20, I18n.format("selectWorld.enterName"));
      this.field_146333_g.setText(this.worldName);
      this.field_146333_g.func_212954_a((p_214319_1_) -> {
         this.worldName = p_214319_1_;
         this.field_195355_B.active = !this.field_146333_g.getText().isEmpty();
         this.calcSaveDirName();
      });
      this.children.add(this.field_146333_g);
      this.field_146343_z = this.addButton(new Button(this.width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode"), (p_214316_1_) -> {
         if ("survival".equals(this.gameMode)) {
            if (!this.allowCheatsWasSetByUser) {
               this.allowCheats = false;
            }

            this.hardCoreMode = false;
            this.gameMode = "hardcore";
            this.hardCoreMode = true;
            this.field_146321_E.active = false;
            this.field_146326_C.active = false;
            this.updateDisplayState();
         } else if ("hardcore".equals(this.gameMode)) {
            if (!this.allowCheatsWasSetByUser) {
               this.allowCheats = true;
            }

            this.hardCoreMode = false;
            this.gameMode = "creative";
            this.updateDisplayState();
            this.hardCoreMode = false;
            this.field_146321_E.active = true;
            this.field_146326_C.active = true;
         } else {
            if (!this.allowCheatsWasSetByUser) {
               this.allowCheats = false;
            }

            this.gameMode = "survival";
            this.updateDisplayState();
            this.field_146321_E.active = true;
            this.field_146326_C.active = true;
            this.hardCoreMode = false;
         }

         this.updateDisplayState();
      }));
      this.field_146335_h = new TextFieldWidget(this.font, this.width / 2 - 100, 60, 200, 20, I18n.format("selectWorld.enterSeed"));
      this.field_146335_h.setText(this.worldSeed);
      this.field_146335_h.func_212954_a((p_214313_1_) -> {
         this.worldSeed = this.field_146335_h.getText();
      });
      this.children.add(this.field_146335_h);
      this.field_146325_B = this.addButton(new Button(this.width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures"), (p_214322_1_) -> {
         this.generateStructuresEnabled = !this.generateStructuresEnabled;
         this.updateDisplayState();
      }));
      this.field_146325_B.visible = false;
      this.field_146320_D = this.addButton(new Button(this.width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType"), (p_214320_1_) -> {
         ++this.selectedIndex;
         if (this.selectedIndex >= WorldType.WORLD_TYPES.length) {
            this.selectedIndex = 0;
         }

         while(!this.canSelectCurWorldType()) {
            ++this.selectedIndex;
            if (this.selectedIndex >= WorldType.WORLD_TYPES.length) {
               this.selectedIndex = 0;
            }
         }

         this.field_146334_a = new CompoundNBT();
         this.updateDisplayState();
         this.showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
      }));
      this.field_146320_D.visible = false;
      this.field_146322_F = this.addButton(new Button(this.width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType"), (p_214314_1_) -> {
         WorldType.WORLD_TYPES[this.selectedIndex].onCustomizeButton(this.minecraft, CreateWorldScreen.this);
      }));
      this.field_146322_F.visible = false;
      this.field_146321_E = this.addButton(new Button(this.width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands"), (p_214315_1_) -> {
         this.allowCheatsWasSetByUser = true;
         this.allowCheats = !this.allowCheats;
         this.updateDisplayState();
      }));
      this.field_146321_E.visible = false;
      this.field_146326_C = this.addButton(new Button(this.width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems"), (p_214312_1_) -> {
         this.bonusChestEnabled = !this.bonusChestEnabled;
         this.updateDisplayState();
      }));
      this.field_146326_C.visible = false;
      this.field_146324_A = this.addButton(new Button(this.width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions"), (p_214321_1_) -> {
         this.toggleMoreWorldOptions();
      }));
      this.field_195355_B = this.addButton(new Button(this.width / 2 - 155, this.height - 28, 150, 20, I18n.format("selectWorld.create"), (p_214318_1_) -> {
         this.func_195352_j();
      }));
      this.addButton(new Button(this.width / 2 + 5, this.height - 28, 150, 20, I18n.format("gui.cancel"), (p_214317_1_) -> {
         this.minecraft.displayGuiScreen(this.field_146332_f);
      }));
      this.showMoreWorldOptions(this.inMoreWorldOptionsDisplay);
      this.func_212928_a(this.field_146333_g);
      this.calcSaveDirName();
      this.updateDisplayState();
   }

   /**
    * Determine a save-directory name from the world name
    */
   private void calcSaveDirName() {
      this.saveDirName = this.field_146333_g.getText().trim();
      if (this.saveDirName.length() == 0) {
         this.saveDirName = "World";
      }

      try {
         this.saveDirName = FileUtil.func_214992_a(this.minecraft.getSaveLoader().func_215781_c(), this.saveDirName, "");
      } catch (Exception var4) {
         this.saveDirName = "World";

         try {
            this.saveDirName = FileUtil.func_214992_a(this.minecraft.getSaveLoader().func_215781_c(), this.saveDirName, "");
         } catch (Exception exception) {
            throw new RuntimeException("Could not create save folder", exception);
         }
      }

   }

   /**
    * Sets displayed GUI elements according to the current settings state
    */
   private void updateDisplayState() {
      this.field_146343_z.setMessage(I18n.format("selectWorld.gameMode") + ": " + I18n.format("selectWorld.gameMode." + this.gameMode));
      this.gameModeDesc1 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line1");
      this.gameModeDesc2 = I18n.format("selectWorld.gameMode." + this.gameMode + ".line2");
      this.field_146325_B.setMessage(I18n.format("selectWorld.mapFeatures") + ' ' + I18n.format(this.generateStructuresEnabled ? "options.on" : "options.off"));
      this.field_146326_C.setMessage(I18n.format("selectWorld.bonusItems") + ' ' + I18n.format(this.bonusChestEnabled && !this.hardCoreMode ? "options.on" : "options.off"));
      this.field_146320_D.setMessage(I18n.format("selectWorld.mapType") + ' ' + I18n.format(WorldType.WORLD_TYPES[this.selectedIndex].getTranslationKey()));
      this.field_146321_E.setMessage(I18n.format("selectWorld.allowCommands") + ' ' + I18n.format(this.allowCheats && !this.hardCoreMode ? "options.on" : "options.off"));
   }

   public void removed() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
   }

   private void func_195352_j() {
      this.minecraft.displayGuiScreen((Screen)null);
      if (!this.alreadyGenerated) {
         this.alreadyGenerated = true;
         long i = (new Random()).nextLong();
         String s = this.field_146335_h.getText();
         if (!StringUtils.isEmpty(s)) {
            try {
               long j = Long.parseLong(s);
               if (j != 0L) {
                  i = j;
               }
            } catch (NumberFormatException var6) {
               i = (long)s.hashCode();
            }
         }
         WorldType.WORLD_TYPES[this.selectedIndex].onGUICreateWorldPress();

         WorldSettings worldsettings = new WorldSettings(i, GameType.getByName(this.gameMode), this.generateStructuresEnabled, this.hardCoreMode, WorldType.WORLD_TYPES[this.selectedIndex]);
         worldsettings.setGeneratorOptions(Dynamic.convert(NBTDynamicOps.INSTANCE, JsonOps.INSTANCE, this.field_146334_a));
         if (this.bonusChestEnabled && !this.hardCoreMode) {
            worldsettings.enableBonusChest();
         }

         if (this.allowCheats && !this.hardCoreMode) {
            worldsettings.enableCommands();
         }

         this.minecraft.launchIntegratedServer(this.saveDirName, this.field_146333_g.getText().trim(), worldsettings);
      }
   }

   /**
    * Returns whether the currently-selected world type is actually acceptable for selection
    * Used to hide the "debug" world type unless the shift key is depressed.
    */
   private boolean canSelectCurWorldType() {
      WorldType worldtype = WorldType.WORLD_TYPES[this.selectedIndex];
      if (worldtype != null && worldtype.canBeCreated()) {
         return worldtype == WorldType.DEBUG_ALL_BLOCK_STATES ? hasShiftDown() : true;
      } else {
         return false;
      }
   }

   /**
    * Toggles between initial world-creation display, and "more options" display.
    * Called when user clicks "More World Options..." or "Done" (same button, different labels depending on current
    * display).
    */
   private void toggleMoreWorldOptions() {
      this.showMoreWorldOptions(!this.inMoreWorldOptionsDisplay);
   }

   /**
    * Shows additional world-creation options if toggle is true, otherwise shows main world-creation elements
    */
   private void showMoreWorldOptions(boolean toggle) {
      this.inMoreWorldOptionsDisplay = toggle;
      if (WorldType.WORLD_TYPES[this.selectedIndex] == WorldType.DEBUG_ALL_BLOCK_STATES) {
         this.field_146343_z.visible = !this.inMoreWorldOptionsDisplay;
         this.field_146343_z.active = false;
         if (this.savedGameMode == null) {
            this.savedGameMode = this.gameMode;
         }

         this.gameMode = "spectator";
         this.field_146325_B.visible = false;
         this.field_146326_C.visible = false;
         this.field_146320_D.visible = this.inMoreWorldOptionsDisplay;
         this.field_146321_E.visible = false;
         this.field_146322_F.visible = false;
      } else {
         this.field_146343_z.visible = !this.inMoreWorldOptionsDisplay;
         this.field_146343_z.active = true;
         if (this.savedGameMode != null) {
            this.gameMode = this.savedGameMode;
            this.savedGameMode = null;
         }

         this.field_146325_B.visible = this.inMoreWorldOptionsDisplay && WorldType.WORLD_TYPES[this.selectedIndex] != WorldType.CUSTOMIZED;
         this.field_146326_C.visible = this.inMoreWorldOptionsDisplay;
         this.field_146320_D.visible = this.inMoreWorldOptionsDisplay;
         this.field_146321_E.visible = this.inMoreWorldOptionsDisplay;
         this.field_146322_F.visible = this.inMoreWorldOptionsDisplay && WorldType.WORLD_TYPES[this.selectedIndex].hasCustomOptions();
      }

      this.updateDisplayState();
      this.field_146335_h.setVisible(this.inMoreWorldOptionsDisplay);
      this.field_146333_g.setVisible(!this.inMoreWorldOptionsDisplay);
      if (this.inMoreWorldOptionsDisplay) {
         this.field_146324_A.setMessage(I18n.format("gui.done"));
      } else {
         this.field_146324_A.setMessage(I18n.format("selectWorld.moreWorldOptions"));
      }

   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
         return false;
      } else {
         this.func_195352_j();
         return true;
      }
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, -1);
      if (this.inMoreWorldOptionsDisplay) {
         this.drawString(this.font, I18n.format("selectWorld.enterSeed"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.font, I18n.format("selectWorld.seedInfo"), this.width / 2 - 100, 85, -6250336);
         if (this.field_146325_B.visible) {
            this.drawString(this.font, I18n.format("selectWorld.mapFeatures.info"), this.width / 2 - 150, 122, -6250336);
         }

         if (this.field_146321_E.visible) {
            this.drawString(this.font, I18n.format("selectWorld.allowCommands.info"), this.width / 2 - 150, 172, -6250336);
         }

         this.field_146335_h.render(p_render_1_, p_render_2_, p_render_3_);
         if (WorldType.WORLD_TYPES[this.selectedIndex].hasInfoNotice()) {
            this.font.drawSplitString(I18n.format(WorldType.WORLD_TYPES[this.selectedIndex].getInfoTranslationKey()), this.field_146320_D.x + 2, this.field_146320_D.y + 22, this.field_146320_D.getWidth(), 10526880);
         }
      } else {
         this.drawString(this.font, I18n.format("selectWorld.enterName"), this.width / 2 - 100, 47, -6250336);
         this.drawString(this.font, I18n.format("selectWorld.resultFolder") + " " + this.saveDirName, this.width / 2 - 100, 85, -6250336);
         this.field_146333_g.render(p_render_1_, p_render_2_, p_render_3_);
         this.drawCenteredString(this.font, this.gameModeDesc1, this.width / 2, 137, -6250336);
         this.drawCenteredString(this.font, this.gameModeDesc2, this.width / 2, 149, -6250336);
      }

      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   /**
    * Set the initial values of a new world to create, from the values from an existing world.
    *  
    * Called after construction when a user selects the "Recreate" button.
    */
   public void recreateFromExistingWorld(WorldInfo original) {
      this.worldName = original.getWorldName();
      this.worldSeed = original.getSeed() + "";
      WorldType worldtype = original.getGenerator() == WorldType.CUSTOMIZED ? WorldType.DEFAULT : original.getGenerator();
      this.selectedIndex = worldtype.getId();
      this.field_146334_a = original.getGeneratorOptions();
      this.generateStructuresEnabled = original.isMapFeaturesEnabled();
      this.allowCheats = original.areCommandsAllowed();
      if (original.isHardcore()) {
         this.gameMode = "hardcore";
      } else if (original.getGameType().isSurvivalOrAdventure()) {
         this.gameMode = "survival";
      } else if (original.getGameType().isCreative()) {
         this.gameMode = "creative";
      }

   }
}