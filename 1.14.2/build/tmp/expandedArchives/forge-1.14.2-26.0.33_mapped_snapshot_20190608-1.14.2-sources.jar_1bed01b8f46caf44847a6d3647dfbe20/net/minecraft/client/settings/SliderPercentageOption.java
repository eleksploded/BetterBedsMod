package net.minecraft.client.settings;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.GameSettings;
import net.minecraft.client.gui.widget.OptionSlider;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SliderPercentageOption extends AbstractOption {
   protected final float field_216734_Q;
   protected final double field_216735_R;
   protected double field_216736_S;
   private final Function<GameSettings, Double> field_216737_T;
   private final BiConsumer<GameSettings, Double> field_216738_U;
   private final BiFunction<GameSettings, SliderPercentageOption, String> field_216739_V;

   public SliderPercentageOption(String p_i51155_1_, double p_i51155_2_, double p_i51155_4_, float p_i51155_6_, Function<GameSettings, Double> p_i51155_7_, BiConsumer<GameSettings, Double> p_i51155_8_, BiFunction<GameSettings, SliderPercentageOption, String> p_i51155_9_) {
      super(p_i51155_1_);
      this.field_216735_R = p_i51155_2_;
      this.field_216736_S = p_i51155_4_;
      this.field_216734_Q = p_i51155_6_;
      this.field_216737_T = p_i51155_7_;
      this.field_216738_U = p_i51155_8_;
      this.field_216739_V = p_i51155_9_;
   }

   public Widget func_216586_a(GameSettings p_216586_1_, int p_216586_2_, int p_216586_3_, int p_216586_4_) {
      return new OptionSlider(p_216586_1_, p_216586_2_, p_216586_3_, p_216586_4_, 20, this);
   }

   public double func_216726_a(double p_216726_1_) {
      return MathHelper.clamp((this.func_216731_c(p_216726_1_) - this.field_216735_R) / (this.field_216736_S - this.field_216735_R), 0.0D, 1.0D);
   }

   public double func_216725_b(double p_216725_1_) {
      return this.func_216731_c(MathHelper.func_219803_d(MathHelper.clamp(p_216725_1_, 0.0D, 1.0D), this.field_216735_R, this.field_216736_S));
   }

   private double func_216731_c(double p_216731_1_) {
      if (this.field_216734_Q > 0.0F) {
         p_216731_1_ = (double)(this.field_216734_Q * (float)Math.round(p_216731_1_ / (double)this.field_216734_Q));
      }

      return MathHelper.clamp(p_216731_1_, this.field_216735_R, this.field_216736_S);
   }

   public double func_216732_b() {
      return this.field_216735_R;
   }

   public double func_216733_c() {
      return this.field_216736_S;
   }

   public void func_216728_a(float p_216728_1_) {
      this.field_216736_S = (double)p_216728_1_;
   }

   public void func_216727_a(GameSettings p_216727_1_, double p_216727_2_) {
      this.field_216738_U.accept(p_216727_1_, p_216727_2_);
   }

   public double func_216729_a(GameSettings p_216729_1_) {
      return this.field_216737_T.apply(p_216729_1_);
   }

   public String func_216730_c(GameSettings p_216730_1_) {
      return this.field_216739_V.apply(p_216730_1_, this);
   }
}