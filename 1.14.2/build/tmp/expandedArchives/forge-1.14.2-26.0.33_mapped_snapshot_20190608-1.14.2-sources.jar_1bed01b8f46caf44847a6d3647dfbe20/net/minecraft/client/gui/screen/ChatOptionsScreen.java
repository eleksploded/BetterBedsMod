package net.minecraft.client.gui.screen;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatOptionsScreen extends Screen {
   private static final AbstractOption[] field_146399_a = new AbstractOption[]{AbstractOption.field_216711_r, AbstractOption.field_216678_B, AbstractOption.field_216679_C, AbstractOption.field_216680_D, AbstractOption.field_216697_d, AbstractOption.field_216708_o, AbstractOption.field_216698_e, AbstractOption.field_216699_f, AbstractOption.field_216695_b, AbstractOption.field_216696_c, AbstractOption.field_216687_K, AbstractOption.field_216677_A, AbstractOption.field_216715_v};
   private final Screen field_146396_g;
   private final GameSettings game_settings;
   private Widget field_193025_i;

   public ChatOptionsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(new TranslationTextComponent("options.chat.title"));
      this.field_146396_g = parentScreenIn;
      this.game_settings = gameSettingsIn;
   }

   protected void init() {
      int i = 0;

      for(AbstractOption abstractoption : field_146399_a) {
         int j = this.width / 2 - 155 + i % 2 * 160;
         int k = this.height / 6 + 24 * (i >> 1);
         Widget widget = this.addButton(abstractoption.func_216586_a(this.minecraft.gameSettings, j, k, 150));
         if (abstractoption == AbstractOption.field_216715_v) {
            this.field_193025_i = widget;
            widget.active = NarratorChatListener.INSTANCE.isActive();
         }

         ++i;
      }

      this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 24 * (i + 1) / 2, 200, 20, I18n.format("gui.done"), (p_212990_1_) -> {
         this.minecraft.displayGuiScreen(this.field_146396_g);
      }));
   }

   public void removed() {
      this.minecraft.gameSettings.saveOptions();
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 20, 16777215);
      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   public void updateNarratorButton() {
      this.field_193025_i.setMessage(AbstractOption.field_216715_v.func_216720_c(this.game_settings));
   }
}