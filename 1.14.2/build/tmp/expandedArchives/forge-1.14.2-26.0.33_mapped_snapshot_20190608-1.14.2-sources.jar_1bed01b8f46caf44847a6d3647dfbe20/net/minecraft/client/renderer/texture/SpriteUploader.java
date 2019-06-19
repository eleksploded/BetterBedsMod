package net.minecraft.client.renderer.texture;

import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SpriteUploader extends ReloadListener<AtlasTexture.SheetData> implements AutoCloseable {
   private final AtlasTexture field_215284_a;

   public SpriteUploader(TextureManager p_i50905_1_, ResourceLocation p_i50905_2_, String p_i50905_3_) {
      this.field_215284_a = new AtlasTexture(p_i50905_3_);
      p_i50905_1_.loadTickableTexture(p_i50905_2_, this.field_215284_a);
   }

   protected abstract Iterable<ResourceLocation> func_215283_a();

   protected TextureAtlasSprite func_215282_a(ResourceLocation p_215282_1_) {
      return this.field_215284_a.getSprite(p_215282_1_);
   }

   protected AtlasTexture.SheetData func_212854_a_(IResourceManager p_212854_1_, IProfiler p_212854_2_) {
      p_212854_2_.func_219894_a();
      p_212854_2_.startSection("stitching");
      AtlasTexture.SheetData atlastexture$sheetdata = this.field_215284_a.func_215254_a(p_212854_1_, this.func_215283_a(), p_212854_2_);
      p_212854_2_.endSection();
      p_212854_2_.func_219897_b();
      return atlastexture$sheetdata;
   }

   protected void func_212853_a_(AtlasTexture.SheetData p_212853_1_, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
      p_212853_3_.func_219894_a();
      p_212853_3_.startSection("upload");
      this.field_215284_a.func_215260_a(p_212853_1_);
      p_212853_3_.endSection();
      p_212853_3_.func_219897_b();
   }

   public void close() {
      this.field_215284_a.clear();
   }
}