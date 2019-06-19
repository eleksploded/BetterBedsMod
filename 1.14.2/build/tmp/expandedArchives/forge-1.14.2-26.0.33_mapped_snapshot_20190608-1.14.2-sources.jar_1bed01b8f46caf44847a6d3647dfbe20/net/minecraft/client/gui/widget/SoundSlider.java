package net.minecraft.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SoundSlider extends AbstractSlider {
   private final SoundCategory field_212933_a;

   public SoundSlider(Minecraft p_i51127_1_, int p_i51127_2_, int p_i51127_3_, SoundCategory p_i51127_4_, int p_i51127_5_) {
      super(p_i51127_1_.gameSettings, p_i51127_2_, p_i51127_3_, p_i51127_5_, 20, (double)p_i51127_1_.gameSettings.getSoundLevel(p_i51127_4_));
      this.field_212933_a = p_i51127_4_;
      this.updateMessage();
   }

   protected void updateMessage() {
      String s = (float)this.value == (float)this.getYImage(false) ? I18n.format("options.off") : (int)((float)this.value * 100.0F) + "%";
      this.setMessage(I18n.format("soundCategory." + this.field_212933_a.getName()) + ": " + s);
   }

   protected void applyValue() {
      this.options.setSoundLevel(this.field_212933_a, (float)this.value);
      this.options.saveOptions();
   }
}