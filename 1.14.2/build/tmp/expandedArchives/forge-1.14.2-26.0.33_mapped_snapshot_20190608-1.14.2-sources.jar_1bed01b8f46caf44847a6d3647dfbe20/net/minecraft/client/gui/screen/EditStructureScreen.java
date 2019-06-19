package net.minecraft.client.gui.screen;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.play.client.CUpdateStructureBlockPacket;
import net.minecraft.state.properties.StructureMode;
import net.minecraft.tileentity.StructureBlockTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EditStructureScreen extends Screen {
   private final StructureBlockTileEntity field_189846_f;
   private Mirror mirror = Mirror.NONE;
   private Rotation rotation = Rotation.NONE;
   private StructureMode mode = StructureMode.DATA;
   private boolean ignoreEntities;
   private boolean showAir;
   private boolean showBoundingBox;
   private TextFieldWidget field_189853_u;
   private TextFieldWidget field_189854_v;
   private TextFieldWidget field_189855_w;
   private TextFieldWidget field_189856_x;
   private TextFieldWidget field_189857_y;
   private TextFieldWidget field_189858_z;
   private TextFieldWidget field_189825_A;
   private TextFieldWidget field_189826_B;
   private TextFieldWidget field_189827_C;
   private TextFieldWidget field_189828_D;
   private Button field_189829_E;
   private Button field_189830_F;
   private Button field_189831_G;
   private Button field_189832_H;
   private Button field_189833_I;
   private Button field_189834_J;
   private Button field_189835_K;
   private Button field_189836_L;
   private Button field_189837_M;
   private Button field_189838_N;
   private Button field_189839_O;
   private Button field_189840_P;
   private Button field_189841_Q;
   private Button field_189842_R;
   private final DecimalFormat decimalFormat = new DecimalFormat("0.0###");

   public EditStructureScreen(StructureBlockTileEntity p_i47142_1_) {
      super(new TranslationTextComponent(Blocks.STRUCTURE_BLOCK.getTranslationKey()));
      this.field_189846_f = p_i47142_1_;
      this.decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT));
   }

   public void tick() {
      this.field_189853_u.tick();
      this.field_189854_v.tick();
      this.field_189855_w.tick();
      this.field_189856_x.tick();
      this.field_189857_y.tick();
      this.field_189858_z.tick();
      this.field_189825_A.tick();
      this.field_189826_B.tick();
      this.field_189827_C.tick();
      this.field_189828_D.tick();
   }

   private void func_195275_h() {
      if (this.func_210143_a(StructureBlockTileEntity.UpdateCommand.UPDATE_DATA)) {
         this.minecraft.displayGuiScreen((Screen)null);
      }

   }

   private void func_195272_i() {
      this.field_189846_f.setMirror(this.mirror);
      this.field_189846_f.setRotation(this.rotation);
      this.field_189846_f.setMode(this.mode);
      this.field_189846_f.setIgnoresEntities(this.ignoreEntities);
      this.field_189846_f.setShowAir(this.showAir);
      this.field_189846_f.setShowBoundingBox(this.showBoundingBox);
      this.minecraft.displayGuiScreen((Screen)null);
   }

   protected void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.field_189829_E = this.addButton(new Button(this.width / 2 - 4 - 150, 210, 150, 20, I18n.format("gui.done"), (p_214274_1_) -> {
         this.func_195275_h();
      }));
      this.field_189830_F = this.addButton(new Button(this.width / 2 + 4, 210, 150, 20, I18n.format("gui.cancel"), (p_214275_1_) -> {
         this.func_195272_i();
      }));
      this.field_189831_G = this.addButton(new Button(this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.save"), (p_214276_1_) -> {
         if (this.field_189846_f.getMode() == StructureMode.SAVE) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.SAVE_AREA);
            this.minecraft.displayGuiScreen((Screen)null);
         }

      }));
      this.field_189832_H = this.addButton(new Button(this.width / 2 + 4 + 100, 185, 50, 20, I18n.format("structure_block.button.load"), (p_214277_1_) -> {
         if (this.field_189846_f.getMode() == StructureMode.LOAD) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.LOAD_AREA);
            this.minecraft.displayGuiScreen((Screen)null);
         }

      }));
      this.field_189837_M = this.addButton(new Button(this.width / 2 - 4 - 150, 185, 50, 20, "MODE", (p_214280_1_) -> {
         this.field_189846_f.nextMode();
         this.updateMode();
      }));
      this.field_189838_N = this.addButton(new Button(this.width / 2 + 4 + 100, 120, 50, 20, I18n.format("structure_block.button.detect_size"), (p_214278_1_) -> {
         if (this.field_189846_f.getMode() == StructureMode.SAVE) {
            this.func_210143_a(StructureBlockTileEntity.UpdateCommand.SCAN_AREA);
            this.minecraft.displayGuiScreen((Screen)null);
         }

      }));
      this.field_189839_O = this.addButton(new Button(this.width / 2 + 4 + 100, 160, 50, 20, "ENTITIES", (p_214282_1_) -> {
         this.field_189846_f.setIgnoresEntities(!this.field_189846_f.ignoresEntities());
         this.updateEntitiesButton();
      }));
      this.field_189840_P = this.addButton(new Button(this.width / 2 - 20, 185, 40, 20, "MIRROR", (p_214281_1_) -> {
         switch(this.field_189846_f.getMirror()) {
         case NONE:
            this.field_189846_f.setMirror(Mirror.LEFT_RIGHT);
            break;
         case LEFT_RIGHT:
            this.field_189846_f.setMirror(Mirror.FRONT_BACK);
            break;
         case FRONT_BACK:
            this.field_189846_f.setMirror(Mirror.NONE);
         }

         this.updateMirrorButton();
      }));
      this.field_189841_Q = this.addButton(new Button(this.width / 2 + 4 + 100, 80, 50, 20, "SHOWAIR", (p_214269_1_) -> {
         this.field_189846_f.setShowAir(!this.field_189846_f.showsAir());
         this.updateToggleAirButton();
      }));
      this.field_189842_R = this.addButton(new Button(this.width / 2 + 4 + 100, 80, 50, 20, "SHOWBB", (p_214270_1_) -> {
         this.field_189846_f.setShowBoundingBox(!this.field_189846_f.showsBoundingBox());
         this.updateToggleBoundingBox();
      }));
      this.field_189833_I = this.addButton(new Button(this.width / 2 - 1 - 40 - 1 - 40 - 20, 185, 40, 20, "0", (p_214268_1_) -> {
         this.field_189846_f.setRotation(Rotation.NONE);
         this.updateDirectionButtons();
      }));
      this.field_189834_J = this.addButton(new Button(this.width / 2 - 1 - 40 - 20, 185, 40, 20, "90", (p_214273_1_) -> {
         this.field_189846_f.setRotation(Rotation.CLOCKWISE_90);
         this.updateDirectionButtons();
      }));
      this.field_189835_K = this.addButton(new Button(this.width / 2 + 1 + 20, 185, 40, 20, "180", (p_214272_1_) -> {
         this.field_189846_f.setRotation(Rotation.CLOCKWISE_180);
         this.updateDirectionButtons();
      }));
      this.field_189836_L = this.addButton(new Button(this.width / 2 + 1 + 40 + 1 + 20, 185, 40, 20, "270", (p_214271_1_) -> {
         this.field_189846_f.setRotation(Rotation.COUNTERCLOCKWISE_90);
         this.updateDirectionButtons();
      }));
      this.field_189853_u = new TextFieldWidget(this.font, this.width / 2 - 152, 40, 300, 20, I18n.format("structure_block.structure_name")) {
         public boolean charTyped(char p_charTyped_1_, int p_charTyped_2_) {
            return !EditStructureScreen.this.isValidCharacterForName(this.getText(), p_charTyped_1_, this.getCursorPosition()) ? false : super.charTyped(p_charTyped_1_, p_charTyped_2_);
         }
      };
      this.field_189853_u.setMaxStringLength(64);
      this.field_189853_u.setText(this.field_189846_f.getName());
      this.children.add(this.field_189853_u);
      BlockPos blockpos = this.field_189846_f.getPosition();
      this.field_189854_v = new TextFieldWidget(this.font, this.width / 2 - 152, 80, 80, 20, I18n.format("structure_block.position.x"));
      this.field_189854_v.setMaxStringLength(15);
      this.field_189854_v.setText(Integer.toString(blockpos.getX()));
      this.children.add(this.field_189854_v);
      this.field_189855_w = new TextFieldWidget(this.font, this.width / 2 - 72, 80, 80, 20, I18n.format("structure_block.position.y"));
      this.field_189855_w.setMaxStringLength(15);
      this.field_189855_w.setText(Integer.toString(blockpos.getY()));
      this.children.add(this.field_189855_w);
      this.field_189856_x = new TextFieldWidget(this.font, this.width / 2 + 8, 80, 80, 20, I18n.format("structure_block.position.z"));
      this.field_189856_x.setMaxStringLength(15);
      this.field_189856_x.setText(Integer.toString(blockpos.getZ()));
      this.children.add(this.field_189856_x);
      BlockPos blockpos1 = this.field_189846_f.getStructureSize();
      this.field_189857_y = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 80, 20, I18n.format("structure_block.size.x"));
      this.field_189857_y.setMaxStringLength(15);
      this.field_189857_y.setText(Integer.toString(blockpos1.getX()));
      this.children.add(this.field_189857_y);
      this.field_189858_z = new TextFieldWidget(this.font, this.width / 2 - 72, 120, 80, 20, I18n.format("structure_block.size.y"));
      this.field_189858_z.setMaxStringLength(15);
      this.field_189858_z.setText(Integer.toString(blockpos1.getY()));
      this.children.add(this.field_189858_z);
      this.field_189825_A = new TextFieldWidget(this.font, this.width / 2 + 8, 120, 80, 20, I18n.format("structure_block.size.z"));
      this.field_189825_A.setMaxStringLength(15);
      this.field_189825_A.setText(Integer.toString(blockpos1.getZ()));
      this.children.add(this.field_189825_A);
      this.field_189826_B = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 80, 20, I18n.format("structure_block.integrity.integrity"));
      this.field_189826_B.setMaxStringLength(15);
      this.field_189826_B.setText(this.decimalFormat.format((double)this.field_189846_f.getIntegrity()));
      this.children.add(this.field_189826_B);
      this.field_189827_C = new TextFieldWidget(this.font, this.width / 2 - 72, 120, 80, 20, I18n.format("structure_block.integrity.seed"));
      this.field_189827_C.setMaxStringLength(31);
      this.field_189827_C.setText(Long.toString(this.field_189846_f.getSeed()));
      this.children.add(this.field_189827_C);
      this.field_189828_D = new TextFieldWidget(this.font, this.width / 2 - 152, 120, 240, 20, I18n.format("structure_block.custom_data"));
      this.field_189828_D.setMaxStringLength(128);
      this.field_189828_D.setText(this.field_189846_f.getMetadata());
      this.children.add(this.field_189828_D);
      this.mirror = this.field_189846_f.getMirror();
      this.updateMirrorButton();
      this.rotation = this.field_189846_f.getRotation();
      this.updateDirectionButtons();
      this.mode = this.field_189846_f.getMode();
      this.updateMode();
      this.ignoreEntities = this.field_189846_f.ignoresEntities();
      this.updateEntitiesButton();
      this.showAir = this.field_189846_f.showsAir();
      this.updateToggleAirButton();
      this.showBoundingBox = this.field_189846_f.showsBoundingBox();
      this.updateToggleBoundingBox();
      this.func_212928_a(this.field_189853_u);
   }

   public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
      String s = this.field_189853_u.getText();
      String s1 = this.field_189854_v.getText();
      String s2 = this.field_189855_w.getText();
      String s3 = this.field_189856_x.getText();
      String s4 = this.field_189857_y.getText();
      String s5 = this.field_189858_z.getText();
      String s6 = this.field_189825_A.getText();
      String s7 = this.field_189826_B.getText();
      String s8 = this.field_189827_C.getText();
      String s9 = this.field_189828_D.getText();
      this.init(p_resize_1_, p_resize_2_, p_resize_3_);
      this.field_189853_u.setText(s);
      this.field_189854_v.setText(s1);
      this.field_189855_w.setText(s2);
      this.field_189856_x.setText(s3);
      this.field_189857_y.setText(s4);
      this.field_189858_z.setText(s5);
      this.field_189825_A.setText(s6);
      this.field_189826_B.setText(s7);
      this.field_189827_C.setText(s8);
      this.field_189828_D.setText(s9);
   }

   public void removed() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
   }

   private void updateEntitiesButton() {
      boolean flag = !this.field_189846_f.ignoresEntities();
      if (flag) {
         this.field_189839_O.setMessage(I18n.format("options.on"));
      } else {
         this.field_189839_O.setMessage(I18n.format("options.off"));
      }

   }

   private void updateToggleAirButton() {
      boolean flag = this.field_189846_f.showsAir();
      if (flag) {
         this.field_189841_Q.setMessage(I18n.format("options.on"));
      } else {
         this.field_189841_Q.setMessage(I18n.format("options.off"));
      }

   }

   private void updateToggleBoundingBox() {
      boolean flag = this.field_189846_f.showsBoundingBox();
      if (flag) {
         this.field_189842_R.setMessage(I18n.format("options.on"));
      } else {
         this.field_189842_R.setMessage(I18n.format("options.off"));
      }

   }

   private void updateMirrorButton() {
      Mirror mirror = this.field_189846_f.getMirror();
      switch(mirror) {
      case NONE:
         this.field_189840_P.setMessage("|");
         break;
      case LEFT_RIGHT:
         this.field_189840_P.setMessage("< >");
         break;
      case FRONT_BACK:
         this.field_189840_P.setMessage("^ v");
      }

   }

   private void updateDirectionButtons() {
      this.field_189833_I.active = true;
      this.field_189834_J.active = true;
      this.field_189835_K.active = true;
      this.field_189836_L.active = true;
      switch(this.field_189846_f.getRotation()) {
      case NONE:
         this.field_189833_I.active = false;
         break;
      case CLOCKWISE_180:
         this.field_189835_K.active = false;
         break;
      case COUNTERCLOCKWISE_90:
         this.field_189836_L.active = false;
         break;
      case CLOCKWISE_90:
         this.field_189834_J.active = false;
      }

   }

   private void updateMode() {
      this.field_189853_u.setVisible(false);
      this.field_189854_v.setVisible(false);
      this.field_189855_w.setVisible(false);
      this.field_189856_x.setVisible(false);
      this.field_189857_y.setVisible(false);
      this.field_189858_z.setVisible(false);
      this.field_189825_A.setVisible(false);
      this.field_189826_B.setVisible(false);
      this.field_189827_C.setVisible(false);
      this.field_189828_D.setVisible(false);
      this.field_189831_G.visible = false;
      this.field_189832_H.visible = false;
      this.field_189838_N.visible = false;
      this.field_189839_O.visible = false;
      this.field_189840_P.visible = false;
      this.field_189833_I.visible = false;
      this.field_189834_J.visible = false;
      this.field_189835_K.visible = false;
      this.field_189836_L.visible = false;
      this.field_189841_Q.visible = false;
      this.field_189842_R.visible = false;
      switch(this.field_189846_f.getMode()) {
      case SAVE:
         this.field_189853_u.setVisible(true);
         this.field_189854_v.setVisible(true);
         this.field_189855_w.setVisible(true);
         this.field_189856_x.setVisible(true);
         this.field_189857_y.setVisible(true);
         this.field_189858_z.setVisible(true);
         this.field_189825_A.setVisible(true);
         this.field_189831_G.visible = true;
         this.field_189838_N.visible = true;
         this.field_189839_O.visible = true;
         this.field_189841_Q.visible = true;
         break;
      case LOAD:
         this.field_189853_u.setVisible(true);
         this.field_189854_v.setVisible(true);
         this.field_189855_w.setVisible(true);
         this.field_189856_x.setVisible(true);
         this.field_189826_B.setVisible(true);
         this.field_189827_C.setVisible(true);
         this.field_189832_H.visible = true;
         this.field_189839_O.visible = true;
         this.field_189840_P.visible = true;
         this.field_189833_I.visible = true;
         this.field_189834_J.visible = true;
         this.field_189835_K.visible = true;
         this.field_189836_L.visible = true;
         this.field_189842_R.visible = true;
         this.updateDirectionButtons();
         break;
      case CORNER:
         this.field_189853_u.setVisible(true);
         break;
      case DATA:
         this.field_189828_D.setVisible(true);
      }

      this.field_189837_M.setMessage(I18n.format("structure_block.mode." + this.field_189846_f.getMode().getName()));
   }

   private boolean func_210143_a(StructureBlockTileEntity.UpdateCommand p_210143_1_) {
      BlockPos blockpos = new BlockPos(this.parseCoordinate(this.field_189854_v.getText()), this.parseCoordinate(this.field_189855_w.getText()), this.parseCoordinate(this.field_189856_x.getText()));
      BlockPos blockpos1 = new BlockPos(this.parseCoordinate(this.field_189857_y.getText()), this.parseCoordinate(this.field_189858_z.getText()), this.parseCoordinate(this.field_189825_A.getText()));
      float f = this.parseIntegrity(this.field_189826_B.getText());
      long i = this.parseSeed(this.field_189827_C.getText());
      this.minecraft.getConnection().sendPacket(new CUpdateStructureBlockPacket(this.field_189846_f.getPos(), p_210143_1_, this.field_189846_f.getMode(), this.field_189853_u.getText(), blockpos, blockpos1, this.field_189846_f.getMirror(), this.field_189846_f.getRotation(), this.field_189828_D.getText(), this.field_189846_f.ignoresEntities(), this.field_189846_f.showsAir(), this.field_189846_f.showsBoundingBox(), f, i));
      return true;
   }

   private long parseSeed(String p_189821_1_) {
      try {
         return Long.valueOf(p_189821_1_);
      } catch (NumberFormatException var3) {
         return 0L;
      }
   }

   private float parseIntegrity(String p_189819_1_) {
      try {
         return Float.valueOf(p_189819_1_);
      } catch (NumberFormatException var3) {
         return 1.0F;
      }
   }

   private int parseCoordinate(String p_189817_1_) {
      try {
         return Integer.parseInt(p_189817_1_);
      } catch (NumberFormatException var3) {
         return 0;
      }
   }

   public void onClose() {
      this.func_195272_i();
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
         return false;
      } else {
         this.func_195275_h();
         return true;
      }
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      StructureMode structuremode = this.field_189846_f.getMode();
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 10, 16777215);
      if (structuremode != StructureMode.DATA) {
         this.drawString(this.font, I18n.format("structure_block.structure_name"), this.width / 2 - 153, 30, 10526880);
         this.field_189853_u.render(p_render_1_, p_render_2_, p_render_3_);
      }

      if (structuremode == StructureMode.LOAD || structuremode == StructureMode.SAVE) {
         this.drawString(this.font, I18n.format("structure_block.position"), this.width / 2 - 153, 70, 10526880);
         this.field_189854_v.render(p_render_1_, p_render_2_, p_render_3_);
         this.field_189855_w.render(p_render_1_, p_render_2_, p_render_3_);
         this.field_189856_x.render(p_render_1_, p_render_2_, p_render_3_);
         String s = I18n.format("structure_block.include_entities");
         int i = this.font.getStringWidth(s);
         this.drawString(this.font, s, this.width / 2 + 154 - i, 150, 10526880);
      }

      if (structuremode == StructureMode.SAVE) {
         this.drawString(this.font, I18n.format("structure_block.size"), this.width / 2 - 153, 110, 10526880);
         this.field_189857_y.render(p_render_1_, p_render_2_, p_render_3_);
         this.field_189858_z.render(p_render_1_, p_render_2_, p_render_3_);
         this.field_189825_A.render(p_render_1_, p_render_2_, p_render_3_);
         String s2 = I18n.format("structure_block.detect_size");
         int k = this.font.getStringWidth(s2);
         this.drawString(this.font, s2, this.width / 2 + 154 - k, 110, 10526880);
         String s1 = I18n.format("structure_block.show_air");
         int j = this.font.getStringWidth(s1);
         this.drawString(this.font, s1, this.width / 2 + 154 - j, 70, 10526880);
      }

      if (structuremode == StructureMode.LOAD) {
         this.drawString(this.font, I18n.format("structure_block.integrity"), this.width / 2 - 153, 110, 10526880);
         this.field_189826_B.render(p_render_1_, p_render_2_, p_render_3_);
         this.field_189827_C.render(p_render_1_, p_render_2_, p_render_3_);
         String s3 = I18n.format("structure_block.show_boundingbox");
         int l = this.font.getStringWidth(s3);
         this.drawString(this.font, s3, this.width / 2 + 154 - l, 70, 10526880);
      }

      if (structuremode == StructureMode.DATA) {
         this.drawString(this.font, I18n.format("structure_block.custom_data"), this.width / 2 - 153, 110, 10526880);
         this.field_189828_D.render(p_render_1_, p_render_2_, p_render_3_);
      }

      String s4 = "structure_block.mode_info." + structuremode.getName();
      this.drawString(this.font, I18n.format(s4), this.width / 2 - 153, 174, 10526880);
      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   public boolean isPauseScreen() {
      return false;
   }
}