package net.minecraft.client.gui.screen;

import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.OptionsRowList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VideoSettingsScreen extends Screen {
   private final Screen field_146498_f;
   private final GameSettings guiGameSettings;
   private OptionsRowList field_146501_h;
   private static final AbstractOption[] field_213107_d = new AbstractOption[]{AbstractOption.field_216712_s, AbstractOption.field_216706_m, AbstractOption.field_216709_p, AbstractOption.field_216701_h, AbstractOption.field_216682_F, AbstractOption.field_216692_P, AbstractOption.field_216713_t, AbstractOption.field_216710_q, AbstractOption.field_216703_j, AbstractOption.field_216717_x, AbstractOption.field_216691_O, AbstractOption.field_216716_w, AbstractOption.field_216704_k, AbstractOption.field_216683_G, AbstractOption.field_216694_a};
   private int field_213108_e;

   public VideoSettingsScreen(Screen parentScreenIn, GameSettings gameSettingsIn) {
      super(new TranslationTextComponent("options.videoTitle"));
      this.field_146498_f = parentScreenIn;
      this.guiGameSettings = gameSettingsIn;
   }

   protected void init() {
      this.field_213108_e = this.guiGameSettings.mipmapLevels;
      this.field_146501_h = new OptionsRowList(this.minecraft, this.width, this.height, 32, this.height - 32, 25);
      this.field_146501_h.func_214333_a(AbstractOption.field_216702_i);
      this.field_146501_h.func_214335_a(field_213107_d);
      this.children.add(this.field_146501_h);
      this.addButton(new Button(this.width / 2 - 100, this.height - 27, 200, 20, I18n.format("gui.done"), (p_213106_1_) -> {
         this.minecraft.gameSettings.saveOptions();
         this.minecraft.mainWindow.update();
         this.minecraft.displayGuiScreen(this.field_146498_f);
      }));
   }

   public void removed() {
      if (this.guiGameSettings.mipmapLevels != this.field_213108_e) {
         this.minecraft.getTextureMap().setMipmapLevels(this.guiGameSettings.mipmapLevels);
         this.minecraft.getTextureManager().bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
         this.minecraft.getTextureMap().setBlurMipmapDirect(false, this.guiGameSettings.mipmapLevels > 0);
         this.minecraft.func_213245_w();
      }

      this.minecraft.gameSettings.saveOptions();
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      int i = this.guiGameSettings.guiScale;
      if (super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         if (this.guiGameSettings.guiScale != i) {
            this.minecraft.func_213226_a();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      int i = this.guiGameSettings.guiScale;
      if (super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)) {
         return true;
      } else if (this.field_146501_h.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_)) {
         if (this.guiGameSettings.guiScale != i) {
            this.minecraft.func_213226_a();
         }

         return true;
      } else {
         return false;
      }
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      this.field_146501_h.render(p_render_1_, p_render_2_, p_render_3_);
      this.drawCenteredString(this.font, this.title.getFormattedText(), this.width / 2, 5, 16777215);
      super.render(p_render_1_, p_render_2_, p_render_3_);
   }
}