package net.minecraft.client.gui.screen;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerListScreen extends Screen {
   private Button field_195170_a;
   private final ServerData serverData;
   private TextFieldWidget field_146302_g;
   private final BooleanConsumer field_213027_d;

   public ServerListScreen(BooleanConsumer p_i51117_1_, ServerData p_i51117_2_) {
      super(new TranslationTextComponent("selectServer.direct"));
      this.serverData = p_i51117_2_;
      this.field_213027_d = p_i51117_1_;
   }

   public void tick() {
      this.field_146302_g.tick();
   }

   protected void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.field_195170_a = this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20, I18n.format("selectServer.select"), (p_213026_1_) -> {
         this.func_195167_h();
      }));
      this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20, I18n.format("gui.cancel"), (p_213025_1_) -> {
         this.field_213027_d.accept(false);
      }));
      this.field_146302_g = new TextFieldWidget(this.font, this.width / 2 - 100, 116, 200, 20, I18n.format("addServer.enterIp"));
      this.field_146302_g.setMaxStringLength(128);
      this.field_146302_g.setFocused2(true);
      this.field_146302_g.setText(this.minecraft.gameSettings.lastServer);
      this.field_146302_g.func_212954_a((p_213024_1_) -> {
         this.func_195168_i();
      });
      this.children.add(this.field_146302_g);
      this.func_212928_a(this.field_146302_g);
      this.func_195168_i();
   }

   public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
      String s = this.field_146302_g.getText();
      this.init(p_resize_1_, p_resize_2_, p_resize_3_);
      this.field_146302_g.setText(s);
   }

   private void func_195167_h() {
      this.serverData.serverIP = this.field_146302_g.getText();
      this.field_213027_d.accept(true);
   }

   public void removed() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
      this.minecraft.gameSettings.lastServer = this.field_146302_g.getText();
      this.minecraft.gameSettings.saveOptions();
   }

   private void func_195168_i() {
      this.field_195170_a.active = !this.field_146302_g.getText().isEmpty() && this.field_146302_g.getText().split(":").length > 0;
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
      this.drawString(this.font, I18n.format("addServer.enterIp"), this.width / 2 - 100, 100, 10526880);
      this.field_146302_g.render(p_render_1_, p_render_2_, p_render_3_);
      super.render(p_render_1_, p_render_2_, p_render_3_);
   }
}