package net.minecraft.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger LOGGER = LogManager.getLogger();
   private final Map<String, FallbackResourceManager> namespaceResourceManagers = Maps.newHashMap();
   private final List<IFutureReloadListener> reloadListeners = Lists.newArrayList();
   private final List<IFutureReloadListener> field_219539_d = Lists.newArrayList();
   private final Set<String> resourceNamespaces = Sets.newLinkedHashSet();
   private final ResourcePackType type;
   private final Thread field_219540_g;

   public SimpleReloadableResourceManager(ResourcePackType p_i50689_1_, Thread p_i50689_2_) {
      this.type = p_i50689_1_;
      this.field_219540_g = p_i50689_2_;
   }

   public void addResourcePack(IResourcePack resourcePack) {
      for(String s : resourcePack.getResourceNamespaces(this.type)) {
         this.resourceNamespaces.add(s);
         FallbackResourceManager fallbackresourcemanager = this.namespaceResourceManagers.get(s);
         if (fallbackresourcemanager == null) {
            fallbackresourcemanager = new FallbackResourceManager(this.type);
            this.namespaceResourceManagers.put(s, fallbackresourcemanager);
         }

         fallbackresourcemanager.addResourcePack(resourcePack);
      }

   }

   public Set<String> getResourceNamespaces() {
      return this.resourceNamespaces;
   }

   public IResource getResource(ResourceLocation resourceLocationIn) throws IOException {
      IResourceManager iresourcemanager = this.namespaceResourceManagers.get(resourceLocationIn.getNamespace());
      if (iresourcemanager != null) {
         return iresourcemanager.getResource(resourceLocationIn);
      } else {
         throw new FileNotFoundException(resourceLocationIn.toString());
      }
   }

   public boolean func_219533_b(ResourceLocation p_219533_1_) {
      IResourceManager iresourcemanager = this.namespaceResourceManagers.get(p_219533_1_.getNamespace());
      return iresourcemanager != null ? iresourcemanager.func_219533_b(p_219533_1_) : false;
   }

   public List<IResource> getAllResources(ResourceLocation resourceLocationIn) throws IOException {
      IResourceManager iresourcemanager = this.namespaceResourceManagers.get(resourceLocationIn.getNamespace());
      if (iresourcemanager != null) {
         return iresourcemanager.getAllResources(resourceLocationIn);
      } else {
         throw new FileNotFoundException(resourceLocationIn.toString());
      }
   }

   public Collection<ResourceLocation> getAllResourceLocations(String pathIn, Predicate<String> filter) {
      Set<ResourceLocation> set = Sets.newHashSet();

      for(FallbackResourceManager fallbackresourcemanager : this.namespaceResourceManagers.values()) {
         set.addAll(fallbackresourcemanager.getAllResourceLocations(pathIn, filter));
      }

      List<ResourceLocation> list = Lists.newArrayList(set);
      Collections.sort(list);
      return list;
   }

   private void clearResourceNamespaces() {
      this.namespaceResourceManagers.clear();
      this.resourceNamespaces.clear();
   }

   public CompletableFuture<Unit> func_219536_a(Executor p_219536_1_, Executor p_219536_2_, List<IResourcePack> p_219536_3_, CompletableFuture<Unit> p_219536_4_) {
      IAsyncReloader iasyncreloader = this.func_219537_a(p_219536_1_, p_219536_2_, p_219536_4_, p_219536_3_);
      return iasyncreloader.func_219552_a();
   }

   public void func_219534_a(IFutureReloadListener p_219534_1_) {
      this.reloadListeners.add(p_219534_1_);
      this.field_219539_d.add(p_219534_1_);
   }

   protected IAsyncReloader func_219538_b(Executor p_219538_1_, Executor p_219538_2_, List<IFutureReloadListener> p_219538_3_, CompletableFuture<Unit> p_219538_4_) {
      IAsyncReloader iasyncreloader;
      if (LOGGER.isDebugEnabled()) {
         iasyncreloader = new DebugAsyncReloader(this, new ArrayList<>(p_219538_3_), p_219538_1_, p_219538_2_, p_219538_4_);
      } else {
         iasyncreloader = AsyncReloader.func_219562_a(this, new ArrayList<>(p_219538_3_), p_219538_1_, p_219538_2_, p_219538_4_);
      }

      this.field_219539_d.clear();
      return iasyncreloader;
   }

   public IAsyncReloader func_219535_a(Executor p_219535_1_, Executor p_219535_2_, CompletableFuture<Unit> p_219535_3_) {
      return this.func_219538_b(p_219535_1_, p_219535_2_, this.field_219539_d, p_219535_3_);
   }

   public IAsyncReloader func_219537_a(Executor p_219537_1_, Executor p_219537_2_, CompletableFuture<Unit> p_219537_3_, List<IResourcePack> p_219537_4_) {
      net.minecraftforge.fml.common.progress.StartupProgressManager.start("Loading Resources", p_219537_4_.size(), true, bar -> {
      this.clearResourceNamespaces();
      LOGGER.info("Reloading ResourceManager: {}", p_219537_4_.stream().map(IResourcePack::getName).collect(Collectors.joining(", ")));

      for(IResourcePack iresourcepack : p_219537_4_) {
         bar.step(iresourcepack.getName());
         this.addResourcePack(iresourcepack);
      }
      });

      return this.func_219538_b(p_219537_1_, p_219537_2_, this.reloadListeners, p_219537_3_);
   }
}