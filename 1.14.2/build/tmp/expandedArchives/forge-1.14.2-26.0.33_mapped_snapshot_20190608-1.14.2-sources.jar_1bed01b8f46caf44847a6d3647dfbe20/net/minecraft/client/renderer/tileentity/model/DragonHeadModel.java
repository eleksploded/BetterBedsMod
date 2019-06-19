package net.minecraft.client.renderer.tileentity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonHeadModel extends GenericHeadModel {
   private final RendererModel field_187070_a;
   private final RendererModel field_187071_b;

   public DragonHeadModel(float p_i46588_1_) {
      this.textureWidth = 256;
      this.textureHeight = 256;
      float f = -16.0F;
      this.field_187070_a = new RendererModel(this, "head");
      this.field_187070_a.func_217178_a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, p_i46588_1_, 176, 44);
      this.field_187070_a.func_217178_a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, p_i46588_1_, 112, 30);
      this.field_187070_a.mirror = true;
      this.field_187070_a.func_217178_a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, p_i46588_1_, 0, 0);
      this.field_187070_a.func_217178_a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, p_i46588_1_, 112, 0);
      this.field_187070_a.mirror = false;
      this.field_187070_a.func_217178_a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, p_i46588_1_, 0, 0);
      this.field_187070_a.func_217178_a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, p_i46588_1_, 112, 0);
      this.field_187071_b = new RendererModel(this, "jaw");
      this.field_187071_b.setRotationPoint(0.0F, 4.0F, -8.0F);
      this.field_187071_b.func_217178_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, p_i46588_1_, 176, 65);
      this.field_187070_a.addChild(this.field_187071_b);
   }

   public void func_217104_a(float p_217104_1_, float p_217104_2_, float p_217104_3_, float p_217104_4_, float p_217104_5_, float p_217104_6_) {
      this.field_187071_b.rotateAngleX = (float)(Math.sin((double)(p_217104_1_ * (float)Math.PI * 0.2F)) + 1.0D) * 0.2F;
      this.field_187070_a.rotateAngleY = p_217104_4_ * ((float)Math.PI / 180F);
      this.field_187070_a.rotateAngleX = p_217104_5_ * ((float)Math.PI / 180F);
      GlStateManager.translatef(0.0F, -0.374375F, 0.0F);
      GlStateManager.scalef(0.75F, 0.75F, 0.75F);
      this.field_187070_a.render(p_217104_6_);
   }
}