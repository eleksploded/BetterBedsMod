package net.minecraft.client.renderer.texture;

import com.google.common.collect.Iterables;
import java.util.Collections;
import net.minecraft.entity.item.PaintingType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PaintingSpriteUploader extends SpriteUploader {
   private static final ResourceLocation field_215287_a = new ResourceLocation("back");

   public PaintingSpriteUploader(TextureManager p_i50907_1_) {
      super(p_i50907_1_, AtlasTexture.field_215263_h, "textures/painting");
   }

   protected Iterable<ResourceLocation> func_215283_a() {
      return Iterables.concat(Registry.field_212620_i.keySet(), Collections.singleton(field_215287_a));
   }

   public TextureAtlasSprite func_215285_a(PaintingType p_215285_1_) {
      return this.func_215282_a(Registry.field_212620_i.getKey(p_215285_1_));
   }

   public TextureAtlasSprite func_215286_b() {
      return this.func_215282_a(field_215287_a);
   }
}