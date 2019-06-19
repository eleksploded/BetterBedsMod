package net.minecraft.client.particle;

import com.google.common.base.Charsets;
import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.mojang.blaze3d.platform.GlStateManager;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MissingTextureSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.entity.Entity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Direction;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParticleManager implements IFutureReloadListener {
   private static final List<IParticleRenderType> field_215241_b = ImmutableList.of(IParticleRenderType.field_217601_a, IParticleRenderType.field_217602_b, IParticleRenderType.field_217604_d, IParticleRenderType.field_217603_c, IParticleRenderType.field_217605_e);
   protected World world;
   private final Map<IParticleRenderType, Queue<Particle>> field_78876_b = Maps.newIdentityHashMap();
   private final Queue<EmitterParticle> particleEmitters = Queues.newArrayDeque();
   private final TextureManager renderer;
   private final Random rand = new Random();
   private final Int2ObjectMap<IParticleFactory<?>> factories = new Int2ObjectOpenHashMap<>();
   private final Queue<Particle> queue = Queues.newArrayDeque();
   private final Map<ResourceLocation, ParticleManager.AnimatedSpriteImpl> field_215242_i = Maps.newHashMap();
   private final AtlasTexture field_215243_j = new AtlasTexture("textures/particle");

   public ParticleManager(World worldIn, TextureManager rendererIn) {
      rendererIn.loadTickableTexture(AtlasTexture.field_215262_g, this.field_215243_j);
      this.world = worldIn;
      this.renderer = rendererIn;
      this.registerFactories();
   }

   private void registerFactories() {
      this.func_215234_a(ParticleTypes.AMBIENT_ENTITY_EFFECT, SpellParticle.AmbientMobFactory::new);
      this.func_215234_a(ParticleTypes.ANGRY_VILLAGER, HeartParticle.AngryVillagerFactory::new);
      this.registerFactory(ParticleTypes.BARRIER, new BarrierParticle.Factory());
      this.registerFactory(ParticleTypes.BLOCK, new DiggingParticle.Factory());
      this.func_215234_a(ParticleTypes.BUBBLE, BubbleParticle.Factory::new);
      this.func_215234_a(ParticleTypes.BUBBLE_COLUMN_UP, BubbleColumnUpParticle.Factory::new);
      this.func_215234_a(ParticleTypes.BUBBLE_POP, BubblePopParticle.Factory::new);
      this.func_215234_a(ParticleTypes.field_218417_ae, CampfireParticle.CozySmokeFactory::new);
      this.func_215234_a(ParticleTypes.field_218418_af, CampfireParticle.SignalSmokeFactory::new);
      this.func_215234_a(ParticleTypes.CLOUD, CloudParticle.Factory::new);
      this.func_215234_a(ParticleTypes.field_218420_D, SuspendedTownParticle.ComposterFactory::new);
      this.func_215234_a(ParticleTypes.CRIT, CritParticle.Factory::new);
      this.func_215234_a(ParticleTypes.CURRENT_DOWN, CurrentDownParticle.Factory::new);
      this.func_215234_a(ParticleTypes.DAMAGE_INDICATOR, CritParticle.DamageIndicatorFactory::new);
      this.func_215234_a(ParticleTypes.DRAGON_BREATH, DragonBreathParticle.Factory::new);
      this.func_215234_a(ParticleTypes.DOLPHIN, SuspendedTownParticle.DolphinSpeedFactory::new);
      this.func_215234_a(ParticleTypes.DRIPPING_LAVA, DripParticle.DrippingLavaFactory::new);
      this.func_215234_a(ParticleTypes.field_218423_k, DripParticle.FallingLavaFactory::new);
      this.func_215234_a(ParticleTypes.field_218424_l, DripParticle.LandingLavaFactory::new);
      this.func_215234_a(ParticleTypes.DRIPPING_WATER, DripParticle.DrippingWaterFactory::new);
      this.func_215234_a(ParticleTypes.field_218425_n, DripParticle.FallingWaterFactory::new);
      this.func_215234_a(ParticleTypes.DUST, RedstoneParticle.Factory::new);
      this.func_215234_a(ParticleTypes.EFFECT, SpellParticle.Factory::new);
      this.registerFactory(ParticleTypes.ELDER_GUARDIAN, new MobAppearanceParticle.Factory());
      this.func_215234_a(ParticleTypes.ENCHANTED_HIT, CritParticle.MagicFactory::new);
      this.func_215234_a(ParticleTypes.ENCHANT, EnchantmentTableParticle.EnchantmentTable::new);
      this.func_215234_a(ParticleTypes.END_ROD, EndRodParticle.Factory::new);
      this.func_215234_a(ParticleTypes.ENTITY_EFFECT, SpellParticle.MobFactory::new);
      this.registerFactory(ParticleTypes.EXPLOSION_EMITTER, new HugeExplosionParticle.Factory());
      this.func_215234_a(ParticleTypes.EXPLOSION, LargeExplosionParticle.Factory::new);
      this.func_215234_a(ParticleTypes.FALLING_DUST, FallingDustParticle.Factory::new);
      this.func_215234_a(ParticleTypes.FIREWORK, FireworkParticle.SparkFactory::new);
      this.func_215234_a(ParticleTypes.FISHING, WaterWakeParticle.Factory::new);
      this.func_215234_a(ParticleTypes.FLAME, FlameParticle.Factory::new);
      this.func_215234_a(ParticleTypes.field_218419_B, FireworkParticle.OverlayFactory::new);
      this.func_215234_a(ParticleTypes.HAPPY_VILLAGER, SuspendedTownParticle.HappyVillagerFactory::new);
      this.func_215234_a(ParticleTypes.HEART, HeartParticle.Factory::new);
      this.func_215234_a(ParticleTypes.INSTANT_EFFECT, SpellParticle.InstantFactory::new);
      this.registerFactory(ParticleTypes.ITEM, new BreakingParticle.Factory());
      this.registerFactory(ParticleTypes.ITEM_SLIME, new BreakingParticle.SlimeFactory());
      this.registerFactory(ParticleTypes.ITEM_SNOWBALL, new BreakingParticle.SnowballFactory());
      this.func_215234_a(ParticleTypes.LARGE_SMOKE, LargeSmokeParticle.Factory::new);
      this.func_215234_a(ParticleTypes.LAVA, LavaParticle.Factory::new);
      this.func_215234_a(ParticleTypes.MYCELIUM, SuspendedTownParticle.Factory::new);
      this.func_215234_a(ParticleTypes.NAUTILUS, EnchantmentTableParticle.NautilusFactory::new);
      this.func_215234_a(ParticleTypes.NOTE, NoteParticle.Factory::new);
      this.func_215234_a(ParticleTypes.POOF, PoofParticle.Factory::new);
      this.func_215234_a(ParticleTypes.PORTAL, PortalParticle.Factory::new);
      this.func_215234_a(ParticleTypes.RAIN, RainParticle.Factory::new);
      this.func_215234_a(ParticleTypes.SMOKE, SmokeParticle.Factory::new);
      this.func_215234_a(ParticleTypes.field_218421_R, CloudParticle.SneezeFactory::new);
      this.func_215234_a(ParticleTypes.SPIT, SpitParticle.Factory::new);
      this.func_215234_a(ParticleTypes.SWEEP_ATTACK, SweepAttackParticle.Factory::new);
      this.func_215234_a(ParticleTypes.TOTEM_OF_UNDYING, TotemOfUndyingParticle.Factory::new);
      this.func_215234_a(ParticleTypes.SQUID_INK, SquidInkParticle.Factory::new);
      this.func_215234_a(ParticleTypes.UNDERWATER, UnderwaterParticle.Factory::new);
      this.func_215234_a(ParticleTypes.field_218422_X, SplashParticle.Factory::new);
      this.func_215234_a(ParticleTypes.WITCH, SpellParticle.WitchFactory::new);
   }

   private <T extends IParticleData> void registerFactory(ParticleType<T> particleTypeIn, IParticleFactory<T> particleFactoryIn) {
      this.factories.put(Registry.field_212632_u.getId(particleTypeIn), particleFactoryIn);
   }

   private <T extends IParticleData> void func_215234_a(ParticleType<T> p_215234_1_, ParticleManager.IParticleMetaFactory<T> p_215234_2_) {
      ParticleManager.AnimatedSpriteImpl particlemanager$animatedspriteimpl = new ParticleManager.AnimatedSpriteImpl();
      this.field_215242_i.put(Registry.field_212632_u.getKey(p_215234_1_), particlemanager$animatedspriteimpl);
      this.factories.put(Registry.field_212632_u.getId(p_215234_1_), p_215234_2_.create(particlemanager$animatedspriteimpl));
   }

   public CompletableFuture<Void> func_215226_a(IFutureReloadListener.IStage p_215226_1_, IResourceManager p_215226_2_, IProfiler p_215226_3_, IProfiler p_215226_4_, Executor p_215226_5_, Executor p_215226_6_) {
      Map<ResourceLocation, List<ResourceLocation>> map = Maps.newConcurrentMap();
      CompletableFuture<?>[] completablefuture = Registry.field_212632_u.keySet().stream().map((p_215228_4_) -> {
         return CompletableFuture.runAsync(() -> {
            this.func_215236_a(p_215226_2_, p_215228_4_, map);
         }, p_215226_5_);
      }).toArray((p_215239_0_) -> {
         return new CompletableFuture[p_215239_0_];
      });
      return CompletableFuture.allOf(completablefuture).thenApplyAsync((p_215230_4_) -> {
         p_215226_3_.func_219894_a();
         p_215226_3_.startSection("stitching");
         Set<ResourceLocation> set = map.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
         AtlasTexture.SheetData atlastexture$sheetdata = this.field_215243_j.func_215254_a(p_215226_2_, set, p_215226_3_);
         p_215226_3_.endSection();
         p_215226_3_.func_219897_b();
         return atlastexture$sheetdata;
      }, p_215226_5_).thenCompose(p_215226_1_::func_216872_a).thenAcceptAsync((p_215229_3_) -> {
         p_215226_4_.func_219894_a();
         p_215226_4_.startSection("upload");
         this.field_215243_j.func_215260_a(p_215229_3_);
         p_215226_4_.endStartSection("bindSpriteSets");
         TextureAtlasSprite textureatlassprite = this.field_215243_j.getSprite(MissingTextureSprite.getLocation());
         map.forEach((p_215227_2_, p_215227_3_) -> {
            ImmutableList<TextureAtlasSprite> immutablelist = p_215227_3_.isEmpty() ? ImmutableList.of(textureatlassprite) : p_215227_3_.stream().map(this.field_215243_j::getSprite).collect(ImmutableList.toImmutableList());
            this.field_215242_i.get(p_215227_2_).func_217592_a(immutablelist);
         });
         p_215226_4_.endSection();
         p_215226_4_.func_219897_b();
      }, p_215226_6_);
   }

   public void func_215232_a() {
      this.field_215243_j.clear();
   }

   private void func_215236_a(IResourceManager p_215236_1_, ResourceLocation p_215236_2_, Map<ResourceLocation, List<ResourceLocation>> p_215236_3_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_215236_2_.getNamespace(), "particles/" + p_215236_2_.getPath() + ".json");

      try (
         IResource iresource = p_215236_1_.getResource(resourcelocation);
         Reader reader = new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8);
      ) {
         TexturesParticle texturesparticle = TexturesParticle.func_217595_a(JSONUtils.fromJson(reader));
         List<ResourceLocation> list = texturesparticle.func_217596_a();
         boolean flag = this.field_215242_i.containsKey(p_215236_2_);
         if (list == null) {
            if (flag) {
               throw new IllegalStateException("Missing texture list for particle " + p_215236_2_);
            }
         } else {
            if (!flag) {
               throw new IllegalStateException("Redundant texture list for particle " + p_215236_2_);
            }

            p_215236_3_.put(p_215236_2_, list);
         }

      } catch (IOException ioexception) {
         throw new IllegalStateException("Failed to load description for particle " + p_215236_2_, ioexception);
      }
   }

   public void addParticleEmitter(Entity entityIn, IParticleData particleData) {
      this.particleEmitters.add(new EmitterParticle(this.world, entityIn, particleData));
   }

   public void emitParticleAtEntity(Entity entityIn, IParticleData dataIn, int lifetimeIn) {
      this.particleEmitters.add(new EmitterParticle(this.world, entityIn, dataIn, lifetimeIn));
   }

   @Nullable
   public Particle addParticle(IParticleData particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      Particle particle = this.makeParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
      if (particle != null) {
         this.addEffect(particle);
         return particle;
      } else {
         return null;
      }
   }

   @Nullable
   private <T extends IParticleData> Particle makeParticle(T particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      IParticleFactory<T> iparticlefactory = (IParticleFactory<T>) this.factories.get(Registry.field_212632_u.getId(particleData.getType()));
      return iparticlefactory == null ? null : iparticlefactory.makeParticle(particleData, this.world, x, y, z, xSpeed, ySpeed, zSpeed);
   }

   public void addEffect(Particle effect) {
      if (effect == null) return; //Forge: Prevent modders from being bad and adding nulls causing untraceable NPEs.
      this.queue.add(effect);
   }

   public void tick() {
      this.field_78876_b.forEach((p_215235_1_, p_215235_2_) -> {
         this.world.getProfiler().startSection(p_215235_1_.toString());
         this.tickParticleList(p_215235_2_);
         this.world.getProfiler().endSection();
      });
      if (!this.particleEmitters.isEmpty()) {
         List<EmitterParticle> list = Lists.newArrayList();

         for(EmitterParticle emitterparticle : this.particleEmitters) {
            emitterparticle.tick();
            if (!emitterparticle.isAlive()) {
               list.add(emitterparticle);
            }
         }

         this.particleEmitters.removeAll(list);
      }

      Particle particle;
      if (!this.queue.isEmpty()) {
         while((particle = this.queue.poll()) != null) {
            this.field_78876_b.computeIfAbsent(particle.func_217558_b(), (p_215231_0_) -> {
               return EvictingQueue.create(16384);
            }).add(particle);
         }
      }

   }

   private void tickParticleList(Collection<Particle> particlesIn) {
      if (!particlesIn.isEmpty()) {
         Iterator<Particle> iterator = particlesIn.iterator();

         while(iterator.hasNext()) {
            Particle particle = iterator.next();
            this.tickParticle(particle);
            if (!particle.isAlive()) {
               iterator.remove();
            }
         }
      }

   }

   private void tickParticle(Particle particle) {
      try {
         particle.tick();
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking Particle");
         CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being ticked");
         crashreportcategory.addDetail("Particle", particle::toString);
         IParticleRenderType iparticlerendertype = particle.func_217558_b();
         crashreportcategory.addDetail("Particle Type", iparticlerendertype::toString);
         throw new ReportedException(crashreport);
      }
   }

   public void func_215233_a(ActiveRenderInfo p_215233_1_, float p_215233_2_) {
      float f = MathHelper.cos(p_215233_1_.func_216778_f() * ((float)Math.PI / 180F));
      float f1 = MathHelper.sin(p_215233_1_.func_216778_f() * ((float)Math.PI / 180F));
      float f2 = -f1 * MathHelper.sin(p_215233_1_.func_216777_e() * ((float)Math.PI / 180F));
      float f3 = f * MathHelper.sin(p_215233_1_.func_216777_e() * ((float)Math.PI / 180F));
      float f4 = MathHelper.cos(p_215233_1_.func_216777_e() * ((float)Math.PI / 180F));
      Particle.interpPosX = p_215233_1_.func_216785_c().x;
      Particle.interpPosY = p_215233_1_.func_216785_c().y;
      Particle.interpPosZ = p_215233_1_.func_216785_c().z;

      for(IParticleRenderType iparticlerendertype : field_215241_b) {
         Iterable<Particle> iterable = this.field_78876_b.get(iparticlerendertype);
         if (iterable != null) {
            GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            iparticlerendertype.func_217600_a(bufferbuilder, this.renderer);

            for(Particle particle : iterable) {
               try {
                  particle.renderParticle(bufferbuilder, p_215233_1_, p_215233_2_, f, f4, f1, f2, f3);
               } catch (Throwable throwable) {
                  CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                  CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                  crashreportcategory.addDetail("Particle", particle::toString);
                  crashreportcategory.addDetail("Particle Type", iparticlerendertype::toString);
                  throw new ReportedException(crashreport);
               }
            }

            iparticlerendertype.func_217599_a(tessellator);
         }
      }

      GlStateManager.depthMask(true);
      GlStateManager.disableBlend();
      GlStateManager.alphaFunc(516, 0.1F);
   }

   public void clearEffects(@Nullable World worldIn) {
      this.world = worldIn;
      this.field_78876_b.clear();
      this.particleEmitters.clear();
   }

   public void addBlockDestroyEffects(BlockPos pos, BlockState state) {
      if (!state.isAir(this.world, pos)) {
         VoxelShape voxelshape = state.getShape(this.world, pos);
         double d0 = 0.25D;
         voxelshape.forEachBox((p_199284_3_, p_199284_5_, p_199284_7_, p_199284_9_, p_199284_11_, p_199284_13_) -> {
            double d1 = Math.min(1.0D, p_199284_9_ - p_199284_3_);
            double d2 = Math.min(1.0D, p_199284_11_ - p_199284_5_);
            double d3 = Math.min(1.0D, p_199284_13_ - p_199284_7_);
            int i = Math.max(2, MathHelper.ceil(d1 / 0.25D));
            int j = Math.max(2, MathHelper.ceil(d2 / 0.25D));
            int k = Math.max(2, MathHelper.ceil(d3 / 0.25D));

            for(int l = 0; l < i; ++l) {
               for(int i1 = 0; i1 < j; ++i1) {
                  for(int j1 = 0; j1 < k; ++j1) {
                     double d4 = ((double)l + 0.5D) / (double)i;
                     double d5 = ((double)i1 + 0.5D) / (double)j;
                     double d6 = ((double)j1 + 0.5D) / (double)k;
                     double d7 = d4 * d1 + p_199284_3_;
                     double d8 = d5 * d2 + p_199284_5_;
                     double d9 = d6 * d3 + p_199284_7_;
                     this.addEffect((new DiggingParticle(this.world, (double)pos.getX() + d7, (double)pos.getY() + d8, (double)pos.getZ() + d9, d4 - 0.5D, d5 - 0.5D, d6 - 0.5D, state)).setBlockPos(pos));
                  }
               }
            }

         });
      }
   }

   /**
    * Adds block hit particles for the specified block
    */
   public void addBlockHitEffects(BlockPos pos, Direction side) {
      BlockState blockstate = this.world.getBlockState(pos);
      if (blockstate.getRenderType() != BlockRenderType.INVISIBLE) {
         int i = pos.getX();
         int j = pos.getY();
         int k = pos.getZ();
         float f = 0.1F;
         AxisAlignedBB axisalignedbb = blockstate.getShape(this.world, pos).getBoundingBox();
         double d0 = (double)i + this.rand.nextDouble() * (axisalignedbb.maxX - axisalignedbb.minX - (double)0.2F) + (double)0.1F + axisalignedbb.minX;
         double d1 = (double)j + this.rand.nextDouble() * (axisalignedbb.maxY - axisalignedbb.minY - (double)0.2F) + (double)0.1F + axisalignedbb.minY;
         double d2 = (double)k + this.rand.nextDouble() * (axisalignedbb.maxZ - axisalignedbb.minZ - (double)0.2F) + (double)0.1F + axisalignedbb.minZ;
         if (side == Direction.DOWN) {
            d1 = (double)j + axisalignedbb.minY - (double)0.1F;
         }

         if (side == Direction.UP) {
            d1 = (double)j + axisalignedbb.maxY + (double)0.1F;
         }

         if (side == Direction.NORTH) {
            d2 = (double)k + axisalignedbb.minZ - (double)0.1F;
         }

         if (side == Direction.SOUTH) {
            d2 = (double)k + axisalignedbb.maxZ + (double)0.1F;
         }

         if (side == Direction.WEST) {
            d0 = (double)i + axisalignedbb.minX - (double)0.1F;
         }

         if (side == Direction.EAST) {
            d0 = (double)i + axisalignedbb.maxX + (double)0.1F;
         }

         this.addEffect((new DiggingParticle(this.world, d0, d1, d2, 0.0D, 0.0D, 0.0D, blockstate)).setBlockPos(pos).multiplyVelocity(0.2F).multipleParticleScaleBy(0.6F));
      }
   }

   public String getStatistics() {
      return String.valueOf(this.field_78876_b.values().stream().mapToInt(Collection::size).sum());
   }

   public void addBlockHitEffects(BlockPos pos, net.minecraft.util.math.BlockRayTraceResult target) {
      BlockState state = world.getBlockState(pos);
      if (!state.addHitEffects(world, target, this))
         addBlockHitEffects(pos, target.getFace());
   }

   @OnlyIn(Dist.CLIENT)
   class AnimatedSpriteImpl implements IAnimatedSprite {
      private List<TextureAtlasSprite> field_217594_b;

      private AnimatedSpriteImpl() {
      }

      public TextureAtlasSprite func_217591_a(int p_217591_1_, int p_217591_2_) {
         return this.field_217594_b.get(p_217591_1_ * (this.field_217594_b.size() - 1) / p_217591_2_);
      }

      public TextureAtlasSprite func_217590_a(Random p_217590_1_) {
         return this.field_217594_b.get(p_217590_1_.nextInt(this.field_217594_b.size()));
      }

      public void func_217592_a(List<TextureAtlasSprite> p_217592_1_) {
         this.field_217594_b = ImmutableList.copyOf(p_217592_1_);
      }
   }

   @FunctionalInterface
   @OnlyIn(Dist.CLIENT)
   interface IParticleMetaFactory<T extends IParticleData> {
      IParticleFactory<T> create(IAnimatedSprite p_create_1_);
   }
}