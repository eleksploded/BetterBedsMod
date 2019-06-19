package net.minecraft.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ResourceLoadProgressGui extends LoadingGui {
   private static final ResourceLocation field_212973_a = new ResourceLocation("textures/gui/title/mojang.png");
   private final Minecraft field_212974_b;
   private final IAsyncReloader field_212975_c;
   private final Runnable field_212976_d;
   private final boolean field_212977_e;
   private float field_212978_f;
   private long field_212979_g = -1L;
   private long field_212980_h = -1L;

   public ResourceLoadProgressGui(Minecraft p_i51112_1_, IAsyncReloader p_i51112_2_, Runnable p_i51112_3_, boolean p_i51112_4_) {
      this.field_212974_b = p_i51112_1_;
      this.field_212975_c = p_i51112_2_;
      this.field_212976_d = p_i51112_3_;
      this.field_212977_e = p_i51112_4_;
   }

   public static void func_212970_a(Minecraft p_212970_0_) {
      p_212970_0_.getTextureManager().loadTexture(field_212973_a, new ResourceLoadProgressGui.MojangLogoTexture());
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      int i = this.field_212974_b.mainWindow.getScaledWidth();
      int j = this.field_212974_b.mainWindow.getScaledHeight();
      long k = Util.milliTime();
      if (this.field_212977_e && (this.field_212975_c.func_219553_c() || this.field_212974_b.field_71462_r != null) && this.field_212980_h == -1L) {
         this.field_212980_h = k;
      }

      float f = this.field_212979_g > -1L ? (float)(k - this.field_212979_g) / 1000.0F : -1.0F;
      float f1 = this.field_212980_h > -1L ? (float)(k - this.field_212980_h) / 500.0F : -1.0F;
      float f2;
      if (f >= 1.0F) {
         if (this.field_212974_b.field_71462_r != null) {
            this.field_212974_b.field_71462_r.render(0, 0, p_render_3_);
         }

         int l = MathHelper.ceil((1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         fill(0, 0, i, j, 16777215 | l << 24);
         f2 = 1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F);
      } else if (this.field_212977_e) {
         if (this.field_212974_b.field_71462_r != null && f1 < 1.0F) {
            this.field_212974_b.field_71462_r.render(p_render_1_, p_render_2_, p_render_3_);
         }

         int j1 = MathHelper.ceil(MathHelper.clamp((double)f1, 0.15D, 1.0D) * 255.0D);
         fill(0, 0, i, j, 16777215 | j1 << 24);
         f2 = MathHelper.clamp(f1, 0.0F, 1.0F);
      } else {
         fill(0, 0, i, j, -1);
         f2 = 1.0F;
      }

      int k1 = (this.field_212974_b.mainWindow.getScaledWidth() - 256) / 2;
      int i1 = (this.field_212974_b.mainWindow.getScaledHeight() - 256) / 2;
      this.field_212974_b.getTextureManager().bindTexture(field_212973_a);
      GlStateManager.enableBlend();
      GlStateManager.color4f(1.0F, 1.0F, 1.0F, f2);
      this.blit(k1, i1, 0, 0, 256, 256);
      float f3 = this.field_212975_c.func_219555_b();
      this.field_212978_f = this.field_212978_f * 0.95F + f3 * 0.050000012F;
      if (f < 1.0F) {
         this.func_212972_a(i / 2 - 150, j / 4 * 3, i / 2 + 150, j / 4 * 3 + 10, this.field_212978_f, 1.0F - MathHelper.clamp(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.field_212974_b.func_213268_a((LoadingGui)null);
      }

      if (this.field_212979_g == -1L && this.field_212975_c.func_219554_d() && (!this.field_212977_e || f1 >= 2.0F)) {
         this.field_212975_c.func_219556_e();
         this.field_212979_g = Util.milliTime();
         this.field_212976_d.run();
         if (this.field_212974_b.field_71462_r != null) {
            this.field_212974_b.field_71462_r.init(this.field_212974_b, this.field_212974_b.mainWindow.getScaledWidth(), this.field_212974_b.mainWindow.getScaledHeight());
         }
      }

   }

   private void func_212972_a(int p_212972_1_, int p_212972_2_, int p_212972_3_, int p_212972_4_, float p_212972_5_, float p_212972_6_) {
      int i = MathHelper.ceil((float)(p_212972_3_ - p_212972_1_ - 2) * p_212972_5_);
      fill(p_212972_1_ - 1, p_212972_2_ - 1, p_212972_3_ + 1, p_212972_4_ + 1, -16777216 | Math.round((1.0F - p_212972_6_) * 255.0F) << 16 | Math.round((1.0F - p_212972_6_) * 255.0F) << 8 | Math.round((1.0F - p_212972_6_) * 255.0F));
      fill(p_212972_1_, p_212972_2_, p_212972_3_, p_212972_4_, -1);
      fill(p_212972_1_ + 1, p_212972_2_ + 1, p_212972_1_ + i, p_212972_4_ - 1, -16777216 | (int)MathHelper.func_219799_g(1.0F - p_212972_6_, 226.0F, 255.0F) << 16 | (int)MathHelper.func_219799_g(1.0F - p_212972_6_, 40.0F, 255.0F) << 8 | (int)MathHelper.func_219799_g(1.0F - p_212972_6_, 55.0F, 255.0F));
   }

   public boolean func_212969_a() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   static class MojangLogoTexture extends SimpleTexture {
      public MojangLogoTexture() {
         super(ResourceLoadProgressGui.field_212973_a);
      }

      protected SimpleTexture.TextureData func_215246_b(IResourceManager p_215246_1_) {
         Minecraft minecraft = Minecraft.getInstance();
         VanillaPack vanillapack = minecraft.getPackFinder().getVanillaPack();

         try (InputStream inputstream = vanillapack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, ResourceLoadProgressGui.field_212973_a)) {
            SimpleTexture.TextureData simpletexture$texturedata = new SimpleTexture.TextureData((TextureMetadataSection)null, NativeImage.read(inputstream));
            return simpletexture$texturedata;
         } catch (IOException ioexception) {
            return new SimpleTexture.TextureData(ioexception);
         }
      }
   }
}