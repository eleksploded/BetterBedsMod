package net.minecraft.client.renderer.model;

import java.util.Map;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModelManager extends ReloadListener<ModelBakery> {
   private Map<ResourceLocation, IBakedModel> modelRegistry = new java.util.HashMap<>();
   private final AtlasTexture field_174956_b;
   private final BlockModelShapes modelProvider;
   private IBakedModel defaultModel;

   public ModelManager(AtlasTexture textures) {
      this.field_174956_b = textures;
      this.modelProvider = new BlockModelShapes(this);
   }

   public IBakedModel getModel(ModelResourceLocation modelLocation) {
      return this.modelRegistry.getOrDefault(modelLocation, this.defaultModel);
   }

   public IBakedModel getMissingModel() {
      return this.defaultModel;
   }

   public BlockModelShapes getBlockModelShapes() {
      return this.modelProvider;
   }

   // TODO
   //@Override
   public net.minecraftforge.resource.IResourceType getResourceType() {
      return net.minecraftforge.resource.VanillaResourceType.MODELS;
   }

   protected ModelBakery func_212854_a_(IResourceManager p_212854_1_, IProfiler p_212854_2_) {
      p_212854_2_.func_219894_a();
      net.minecraftforge.client.model.ModelLoader modelbakery = new net.minecraftforge.client.model.ModelLoader(p_212854_1_, this.field_174956_b, p_212854_2_);
      p_212854_2_.func_219897_b();
      return modelbakery;
   }

   protected void func_212853_a_(ModelBakery p_212853_1_, IResourceManager p_212853_2_, IProfiler p_212853_3_) {
      p_212853_3_.func_219894_a();
      p_212853_3_.startSection("upload");
      p_212853_1_.func_217844_a(p_212853_3_);
      this.modelRegistry = p_212853_1_.func_217846_a();
      this.defaultModel = this.modelRegistry.get(ModelBakery.MODEL_MISSING);
      p_212853_3_.endStartSection("cache");
      this.modelProvider.reloadModels();
      p_212853_3_.endSection();
      p_212853_3_.func_219897_b();
   }
}