package net.minecraft.client.gui;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AccessibilityScreen extends Screen {
   private static final AbstractOption[] field_212986_a = new AbstractOption[]{AbstractOption.field_216715_v, AbstractOption.field_216688_L, AbstractOption.field_216708_o, AbstractOption.field_216718_y, AbstractOption.field_216697_d, AbstractOption.field_216719_z};
   private final Screen field_212987_b;
   private final GameSettings field_212988_c;
   private Widget field_212989_d;

   public AccessibilityScreen(Screen p_i51123_1_, GameSettings p_i51123_2_) {
      super(new TranslationTextComponent("options.accessibility.title"));
      this.field_212987_b = p_i51123_1_;
      this.field_212988_c = p_i51123_2_;
   }

   protected void init() {
      int i = 0;

      for(AbstractOption abstractoption : field_212986_a) {
         int j = this.width / 2 - 155 + i % 2 * 160;
         int k = this.height / 6 + 24 * (i >> 1);
         Widget widget = this.addButton(abstractoption.func_216586_a(this.minecraft.gameSettings, j, k, 150));
         if (abstractoption == AbstractOption.field_216715_v) {
            this.field_212989_d = widget;
            widget.active = NarratorChatListener.INSTANCE.isActive();
         }

         ++i;
      }

      this.addButton(new Button(this.width / 2 - 100, this.height / 6 + 144, 200, 20, I18n.format("gui.done"), (p_212984_1_) -> {
         this.minecraft.displayGuiScreen(this.field_212987_b);
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

   public void func_212985_a() {
      this.field_212989_d.setMessage(AbstractOption.field_216715_v.func_216720_c(this.field_212988_c));
   }
}