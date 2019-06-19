package net.minecraft.client.renderer.entity.model;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArmorStandModel extends ArmorStandArmorModel {
   private final RendererModel field_178740_a;
   private final RendererModel field_178738_b;
   private final RendererModel field_178739_c;
   private final RendererModel field_178737_d;

   public ArmorStandModel() {
      this(0.0F);
   }

   public ArmorStandModel(float modelSize) {
      super(modelSize, 64, 64);
      this.field_78116_c = new RendererModel(this, 0, 0);
      this.field_78116_c.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, modelSize);
      this.field_78116_c.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_78115_e = new RendererModel(this, 0, 26);
      this.field_78115_e.addBox(-6.0F, 0.0F, -1.5F, 12, 3, 3, modelSize);
      this.field_78115_e.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_178723_h = new RendererModel(this, 24, 0);
      this.field_178723_h.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178723_h.setRotationPoint(-5.0F, 2.0F, 0.0F);
      this.field_178724_i = new RendererModel(this, 32, 16);
      this.field_178724_i.mirror = true;
      this.field_178724_i.addBox(0.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
      this.field_178724_i.setRotationPoint(5.0F, 2.0F, 0.0F);
      this.field_178721_j = new RendererModel(this, 8, 0);
      this.field_178721_j.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, modelSize);
      this.field_178721_j.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.field_178722_k = new RendererModel(this, 40, 16);
      this.field_178722_k.mirror = true;
      this.field_178722_k.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, modelSize);
      this.field_178722_k.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178740_a = new RendererModel(this, 16, 0);
      this.field_178740_a.addBox(-3.0F, 3.0F, -1.0F, 2, 7, 2, modelSize);
      this.field_178740_a.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_178740_a.showModel = true;
      this.field_178738_b = new RendererModel(this, 48, 16);
      this.field_178738_b.addBox(1.0F, 3.0F, -1.0F, 2, 7, 2, modelSize);
      this.field_178738_b.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_178739_c = new RendererModel(this, 0, 48);
      this.field_178739_c.addBox(-4.0F, 10.0F, -1.0F, 8, 2, 2, modelSize);
      this.field_178739_c.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.field_178737_d = new RendererModel(this, 0, 32);
      this.field_178737_d.addBox(-6.0F, 11.0F, -6.0F, 12, 1, 12, modelSize);
      this.field_178737_d.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.field_178720_f.showModel = false;
   }

   public void func_212844_a_(ArmorStandEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      this.field_178724_i.showModel = p_212844_1_.getShowArms();
      this.field_178723_h.showModel = p_212844_1_.getShowArms();
      this.field_178737_d.showModel = !p_212844_1_.hasNoBasePlate();
      this.field_178722_k.setRotationPoint(1.9F, 12.0F, 0.0F);
      this.field_178721_j.setRotationPoint(-1.9F, 12.0F, 0.0F);
      this.field_178740_a.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getX();
      this.field_178740_a.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getY();
      this.field_178740_a.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getZ();
      this.field_178738_b.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getX();
      this.field_178738_b.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getY();
      this.field_178738_b.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getZ();
      this.field_178739_c.rotateAngleX = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getX();
      this.field_178739_c.rotateAngleY = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getY();
      this.field_178739_c.rotateAngleZ = ((float)Math.PI / 180F) * p_212844_1_.getBodyRotation().getZ();
      this.field_178737_d.rotateAngleX = 0.0F;
      this.field_178737_d.rotateAngleY = ((float)Math.PI / 180F) * -p_212844_1_.rotationYaw;
      this.field_178737_d.rotateAngleZ = 0.0F;
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(ArmorStandEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      GlStateManager.pushMatrix();
      if (this.field_217114_e) {
         float f = 2.0F;
         GlStateManager.scalef(0.5F, 0.5F, 0.5F);
         GlStateManager.translatef(0.0F, 24.0F * scale, 0.0F);
         this.field_178740_a.render(scale);
         this.field_178738_b.render(scale);
         this.field_178739_c.render(scale);
         this.field_178737_d.render(scale);
      } else {
         if (entityIn.isSneaking()) {
            GlStateManager.translatef(0.0F, 0.2F, 0.0F);
         }

         this.field_178740_a.render(scale);
         this.field_178738_b.render(scale);
         this.field_178739_c.render(scale);
         this.field_178737_d.render(scale);
      }

      GlStateManager.popMatrix();
   }

   public void postRenderArm(float scale, HandSide side) {
      RendererModel renderermodel = this.getArmForSide(side);
      boolean flag = renderermodel.showModel;
      renderermodel.showModel = true;
      super.postRenderArm(scale, side);
      renderermodel.showModel = flag;
   }
}