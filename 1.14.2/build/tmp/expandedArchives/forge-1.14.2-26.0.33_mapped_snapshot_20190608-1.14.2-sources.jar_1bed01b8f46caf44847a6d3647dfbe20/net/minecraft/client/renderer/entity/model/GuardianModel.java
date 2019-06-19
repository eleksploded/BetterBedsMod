package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuardianModel extends EntityModel<GuardianEntity> {
   private static final float[] field_217136_a = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
   private static final float[] field_217137_b = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
   private static final float[] field_217138_f = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
   private static final float[] field_217139_g = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
   private static final float[] field_217140_h = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
   private static final float[] field_217141_i = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
   private final RendererModel field_178710_a;
   private final RendererModel field_178708_b;
   private final RendererModel[] field_178709_c;
   private final RendererModel[] field_178707_d;

   public GuardianModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_178709_c = new RendererModel[12];
      this.field_178710_a = new RendererModel(this);
      this.field_178710_a.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
      this.field_178710_a.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12);
      this.field_178710_a.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
      this.field_178710_a.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12);
      this.field_178710_a.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12);

      for(int i = 0; i < this.field_178709_c.length; ++i) {
         this.field_178709_c[i] = new RendererModel(this, 0, 0);
         this.field_178709_c[i].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
         this.field_178710_a.addChild(this.field_178709_c[i]);
      }

      this.field_178708_b = new RendererModel(this, 8, 0);
      this.field_178708_b.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
      this.field_178710_a.addChild(this.field_178708_b);
      this.field_178707_d = new RendererModel[3];
      this.field_178707_d[0] = new RendererModel(this, 40, 0);
      this.field_178707_d[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
      this.field_178707_d[1] = new RendererModel(this, 0, 54);
      this.field_178707_d[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
      this.field_178707_d[2] = new RendererModel(this);
      this.field_178707_d[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
      this.field_178707_d[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
      this.field_178710_a.addChild(this.field_178707_d[0]);
      this.field_178707_d[0].addChild(this.field_178707_d[1]);
      this.field_178707_d[1].addChild(this.field_178707_d[2]);
   }

   /**
    * Sets the models various rotation angles then renders the model.
    */
   public void render(GuardianEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
      this.func_212844_a_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
      this.field_178710_a.render(scale);
   }

   public void func_212844_a_(GuardianEntity p_212844_1_, float p_212844_2_, float p_212844_3_, float p_212844_4_, float p_212844_5_, float p_212844_6_, float p_212844_7_) {
      float f = p_212844_4_ - (float)p_212844_1_.ticksExisted;
      this.field_178710_a.rotateAngleY = p_212844_5_ * ((float)Math.PI / 180F);
      this.field_178710_a.rotateAngleX = p_212844_6_ * ((float)Math.PI / 180F);
      float f1 = (1.0F - p_212844_1_.getSpikesAnimation(f)) * 0.55F;

      for(int i = 0; i < 12; ++i) {
         this.field_178709_c[i].rotateAngleX = (float)Math.PI * field_217136_a[i];
         this.field_178709_c[i].rotateAngleY = (float)Math.PI * field_217137_b[i];
         this.field_178709_c[i].rotateAngleZ = (float)Math.PI * field_217138_f[i];
         this.field_178709_c[i].rotationPointX = field_217139_g[i] * (1.0F + MathHelper.cos(p_212844_4_ * 1.5F + (float)i) * 0.01F - f1);
         this.field_178709_c[i].rotationPointY = 16.0F + field_217140_h[i] * (1.0F + MathHelper.cos(p_212844_4_ * 1.5F + (float)i) * 0.01F - f1);
         this.field_178709_c[i].rotationPointZ = field_217141_i[i] * (1.0F + MathHelper.cos(p_212844_4_ * 1.5F + (float)i) * 0.01F - f1);
      }

      this.field_178708_b.rotationPointZ = -8.25F;
      Entity entity = Minecraft.getInstance().getRenderViewEntity();
      if (p_212844_1_.hasTargetedEntity()) {
         entity = p_212844_1_.getTargetedEntity();
      }

      if (entity != null) {
         Vec3d vec3d = entity.getEyePosition(0.0F);
         Vec3d vec3d1 = p_212844_1_.getEyePosition(0.0F);
         double d0 = vec3d.y - vec3d1.y;
         if (d0 > 0.0D) {
            this.field_178708_b.rotationPointY = 0.0F;
         } else {
            this.field_178708_b.rotationPointY = 1.0F;
         }

         Vec3d vec3d2 = p_212844_1_.getLook(0.0F);
         vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);
         Vec3d vec3d3 = (new Vec3d(vec3d1.x - vec3d.x, 0.0D, vec3d1.z - vec3d.z)).normalize().rotateYaw(((float)Math.PI / 2F));
         double d1 = vec3d2.dotProduct(vec3d3);
         this.field_178708_b.rotationPointX = MathHelper.sqrt((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
      }

      this.field_178708_b.showModel = true;
      float f2 = p_212844_1_.getTailAnimation(f);
      this.field_178707_d[0].rotateAngleY = MathHelper.sin(f2) * (float)Math.PI * 0.05F;
      this.field_178707_d[1].rotateAngleY = MathHelper.sin(f2) * (float)Math.PI * 0.1F;
      this.field_178707_d[1].rotationPointX = -1.5F;
      this.field_178707_d[1].rotationPointY = 0.5F;
      this.field_178707_d[1].rotationPointZ = 14.0F;
      this.field_178707_d[2].rotateAngleY = MathHelper.sin(f2) * (float)Math.PI * 0.15F;
      this.field_178707_d[2].rotationPointX = 0.5F;
      this.field_178707_d[2].rotationPointY = 0.5F;
      this.field_178707_d[2].rotationPointZ = 6.0F;
   }
}