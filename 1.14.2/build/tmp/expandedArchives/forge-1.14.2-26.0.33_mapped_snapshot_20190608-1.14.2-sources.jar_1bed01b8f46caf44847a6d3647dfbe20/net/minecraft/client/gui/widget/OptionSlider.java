package net.minecraft.client.gui.widget;

import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.AbstractOption;
import net.minecraft.client.settings.SliderPercentageOption;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionSlider extends AbstractSlider {
   private final SliderPercentageOption field_146133_q;

   public OptionSlider(GameSettings p_i51129_1_, int p_i51129_2_, int p_i51129_3_, int p_i51129_4_, int p_i51129_5_, SliderPercentageOption p_i51129_6_) {
      super(p_i51129_1_, p_i51129_2_, p_i51129_3_, p_i51129_4_, p_i51129_5_, (double)((float)p_i51129_6_.func_216726_a(p_i51129_6_.func_216729_a(p_i51129_1_))));
      this.field_146133_q = p_i51129_6_;
      this.updateMessage();
   }

   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
      if (this.field_146133_q == AbstractOption.field_216702_i) {
         this.updateMessage();
      }

      super.renderButton(p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
   }

   protected void applyValue() {
      this.field_146133_q.func_216727_a(this.options, this.field_146133_q.func_216725_b(this.value));
      this.options.saveOptions();
   }

   protected void updateMessage() {
      this.setMessage(this.field_146133_q.func_216730_c(this.options));
   }
}