package net.minecraft.client.renderer.entity.model;

import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ZombieVillagerModel<T extends ZombieEntity> extends BipedModel<T> implements IHeadToggle {
   private RendererModel field_217150_a;

   public ZombieVillagerModel() {
      this(0.0F, false);
   }

   public ZombieVillagerModel(float p_i51058_1_, boolean p_i51058_2_) {
      super(p_i51058_1_, 0.0F, 64, p_i51058_2_ ? 32 : 64);
      if (p_i51058_2_) {
         this.field_78116_c = new RendererModel(this, 0, 0);
         this.field_78116_c.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i51058_1_);
         this.field_78115_e = new RendererModel(this, 16, 16);
         this.field_78115_e.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i51058_1_ + 0.1F);
         this.field_178721_j = new RendererModel(this, 0, 16);
         this.field_178721_j.setRotationPoint(-2.0F, 12.0F, 0.0F);
         this.field_178721_j.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51058_1_ + 0.1F);
         this.field_178722_k = new RendererModel(this, 0, 16);
         this.field_178722_k.mirror = true;
         this.field_178722_k.setRotationPoint(2.0F, 12.0F, 0.0F);
         this.field_178722_k.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51058_1_ + 0.1F);
      } else {
         this.field_78116_c = new RendererModel(this, 0, 0);
         this.field_78116_c.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51058_1_);
         this.field_78116_c.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i51058_1_);
         this.field_178720_f = new RendererModel(this, 32, 0);
         this.field_178720_f.addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i51058_1_ + 0.5F);
         this.field_217150_a = new RendererModel(this);
         this.field_217150_a.setTextureOffset(30, 47).addBox(-8.0F, -8.0F, -6.0F, 16, 16, 1, p_i51058_1_);
         this.field_217150_a.rotateAngleX = (-(float)Math.PI / 2F);
         this.field_178720_f.addChild(this.field_217150_a);
         this.field_78115_e = new RendererModel(this, 16, 20);
         this.field_78115_e.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i51058_1_);
         this.field_78115_e.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i51058_1_ + 0.05F);
         this.field_178723_h = new RendererModel(this, 44, 22);
         this.field_178723_h.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i51058_1_);
         this.field_178723_h.setRotationPoint(-5.0F, 2.0F, 0.0F);
         this.field_178724_i = new RendererModel(this, 44, 22);
         this.field_178724_i.mirror = true;
         this.field_178724_i.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i51058_1_);
         this.field_178724_i.setRotationPoint(5.0F, 2.0F, 0.0F);
         this.field_178721_j = new RendererModel(this, 0, 22);
         this.field_178721_j.setRotationPoint(-2.0F, 12.0F, 0.0F);
         this.field_178721_j.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51058_1_);
         this.field_178722_k = new RendererModel(this, 0, 22);
         this.field_178722_k.mirror = true;
         this.field_178722_k.setRotationPoint(2.0F, 12.0F, 0.0F);
         this.field_178722_k.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i51058_1_);
      }

   }

   public void func_212844_a_(T p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      super.func_212844_a_(p_212844_1_, p_212844_2_, p_212844_3_, p_212844_4_, p_212844_5_, p_212844_6_, p_212844_7_);
      float f = MathHelper.sin(this.field_217112_c * (float)Math.PI);
      float f1 = MathHelper.sin((1.0F - (1.0F - this.field_217112_c) * (1.0F - this.field_217112_c)) * (float)Math.PI);
      this.field_178723_h.rotateAngleZ = 0.0F;
      this.field_178724_i.rotateAngleZ = 0.0F;
      this.field_178723_h.rotateAngleY = -(0.1F - f * 0.6F);
      this.field_178724_i.rotateAngleY = 0.1F - f * 0.6F;
      float f2 = -(float)Math.PI / (p_212844_1_.func_213398_dR() ? 1.5F : 2.25F);
      this.field_178723_h.rotateAngleX = f2;
      this.field_178724_i.rotateAngleX = f2;
      this.field_178723_h.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.field_178724_i.rotateAngleX += f * 1.2F - f1 * 0.4F;
      this.field_178723_h.rotateAngleZ += MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178724_i.rotateAngleZ -= MathHelper.cos(p_212844_4_ * 0.09F) * 0.05F + 0.05F;
      this.field_178723_h.rotateAngleX += MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
      this.field_178724_i.rotateAngleX -= MathHelper.sin(p_212844_4_ * 0.067F) * 0.05F;
   }

   public void func_217146_a(boolean p_217146_1_) {
      this.field_78116_c.showModel = p_217146_1_;
      this.field_178720_f.showModel = p_217146_1_;
      this.field_217150_a.showModel = p_217146_1_;
   }
}