package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WitchModel<T extends Entity> extends VillagerModel<T> {
   private boolean holdingItem;
   private final RendererModel field_82901_h = (new RendererModel(this)).setTextureSize(64, 128);

   public WitchModel(float scale) {
      super(scale, 64, 128);
      this.field_82901_h.setRotationPoint(0.0F, -2.0F, 0.0F);
      this.field_82901_h.setTextureOffset(0, 0).addBox(0.0F, 3.0F, -6.75F, 1, 1, 1, -0.25F);
      this.field_82898_f.addChild(this.field_82901_h);
      this.field_78191_a.func_217179_c(this.field_217151_b);
      this.field_217151_b = (new RendererModel(this)).setTextureSize(64, 128);
      this.field_217151_b.setRotationPoint(-5.0F, -10.03125F, -5.0F);
      this.field_217151_b.setTextureOffset(0, 64).addBox(0.0F, 0.0F, 0.0F, 10, 2, 10);
      this.field_78191_a.addChild(this.field_217151_b);
      RendererModel renderermodel = (new RendererModel(this)).setTextureSize(64, 128);
      renderermodel.setRotationPoint(1.75F, -4.0F, 2.0F);
      renderermodel.setTextureOffset(0, 76).addBox(0.0F, 0.0F, 0.0F, 7, 4, 7);
      renderermodel.rotateAngleX = -0.05235988F;
      renderermodel.rotateAngleZ = 0.02617994F;
      this.field_217151_b.addChild(renderermodel);
      RendererModel renderermodel1 = (new RendererModel(this)).setTextureSize(64, 128);
      renderermodel1.setRotationPoint(1.75F, -4.0F, 2.0F);
      renderermodel1.setTextureOffset(0, 87).addBox(0.0F, 0.0F, 0.0F, 4, 4, 4);
      renderermodel1.rotateAngleX = -0.10471976F;
      renderermodel1.rotateAngleZ = 0.05235988F;
      renderermodel.addChild(renderermodel1);
      RendererModel renderermodel2 = (new RendererModel(this)).setTextureSize(64, 128);
      renderermodel2.setRotationPoint(1.75F, -2.0F, 2.0F);
      renderermodel2.setTextureOffset(0, 95).addBox(0.0F, 0.0F, 0.0F, 1, 2, 1, 0.25F);
      renderermodel2.rotateAngleX = -0.20943952F;
      renderermodel2.rotateAngleZ = 0.10471976F;
      renderermodel1.addChild(renderermodel2);
   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_82898_f.offsetX = 0.0F;
      this.field_82898_f.offsetY = 0.0F;
      this.field_82898_f.offsetZ = 0.0F;
      float f = 0.01F * (float)(p_212844_1_.getEntityId() % 10);
      this.field_82898_f.rotateAngleX = MathHelper.sin((float)p_212844_1_.ticksExisted * f) * 4.5F * ((float)Math.PI / 180F);
      this.field_82898_f.rotateAngleY = 0.0F;
      this.field_82898_f.rotateAngleZ = MathHelper.cos((float)p_212844_1_.ticksExisted * f) * 2.5F * ((float)Math.PI / 180F);
      if (this.holdingItem) {
         this.field_82898_f.rotateAngleX = -0.9F;
         this.field_82898_f.offsetZ = -0.09375F;
         this.field_82898_f.offsetY = 0.1875F;
      }

   }

   public RendererModel func_205073_b() {
      return this.field_82898_f;
   }

   public void func_205074_a(boolean p_205074_1_) {
      this.holdingItem = p_205074_1_;
   }
}