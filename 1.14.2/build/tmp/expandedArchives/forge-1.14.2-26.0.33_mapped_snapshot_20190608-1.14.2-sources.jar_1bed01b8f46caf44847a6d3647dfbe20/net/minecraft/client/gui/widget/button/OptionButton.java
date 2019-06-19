package net.minecraft.client.gui.widget.button;

import net.minecraft.client.settings.AbstractOption;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OptionButton extends Button {
   private final AbstractOption field_146137_o;

   public OptionButton(int p_i51132_1_, int p_i51132_2_, int p_i51132_3_, int p_i51132_4_, AbstractOption p_i51132_5_, String p_i51132_6_, Button.IPressable p_i51132_7_) {
      super(p_i51132_1_, p_i51132_2_, p_i51132_3_, p_i51132_4_, p_i51132_6_, p_i51132_7_);
      this.field_146137_o = p_i51132_5_;
   }
}