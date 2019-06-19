package net.minecraft.world.gen.surfacebuilders;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;

public abstract class SurfaceBuilder<C extends ISurfaceBuilderConfig> extends net.minecraftforge.registries.ForgeRegistryEntry<SurfaceBuilder<?>> {
   public static final BlockState field_215409_f = Blocks.AIR.getDefaultState();
   public static final BlockState field_215410_g = Blocks.DIRT.getDefaultState();
   public static final BlockState field_215411_h = Blocks.GRASS_BLOCK.getDefaultState();
   public static final BlockState field_215412_i = Blocks.PODZOL.getDefaultState();
   public static final BlockState field_215413_j = Blocks.GRAVEL.getDefaultState();
   public static final BlockState field_215414_k = Blocks.STONE.getDefaultState();
   public static final BlockState field_215415_l = Blocks.COARSE_DIRT.getDefaultState();
   public static final BlockState field_215416_m = Blocks.SAND.getDefaultState();
   public static final BlockState field_215417_n = Blocks.RED_SAND.getDefaultState();
   public static final BlockState field_215418_o = Blocks.WHITE_TERRACOTTA.getDefaultState();
   public static final BlockState field_215419_p = Blocks.MYCELIUM.getDefaultState();
   public static final BlockState field_215420_q = Blocks.NETHERRACK.getDefaultState();
   public static final BlockState field_215421_r = Blocks.END_STONE.getDefaultState();
   public static final SurfaceBuilderConfig field_215422_s = new SurfaceBuilderConfig(field_215409_f, field_215409_f, field_215409_f);
   public static final SurfaceBuilderConfig field_215423_t = new SurfaceBuilderConfig(field_215412_i, field_215410_g, field_215413_j);
   public static final SurfaceBuilderConfig field_215424_u = new SurfaceBuilderConfig(field_215413_j, field_215413_j, field_215413_j);
   public static final SurfaceBuilderConfig field_215425_v = new SurfaceBuilderConfig(field_215411_h, field_215410_g, field_215413_j);
   public static final SurfaceBuilderConfig field_215426_w = new SurfaceBuilderConfig(field_215410_g, field_215410_g, field_215413_j);
   public static final SurfaceBuilderConfig field_215427_x = new SurfaceBuilderConfig(field_215414_k, field_215414_k, field_215413_j);
   public static final SurfaceBuilderConfig field_215428_y = new SurfaceBuilderConfig(field_215415_l, field_215410_g, field_215413_j);
   public static final SurfaceBuilderConfig field_215429_z = new SurfaceBuilderConfig(field_215416_m, field_215416_m, field_215413_j);
   public static final SurfaceBuilderConfig field_215390_A = new SurfaceBuilderConfig(field_215411_h, field_215410_g, field_215416_m);
   public static final SurfaceBuilderConfig field_215391_B = new SurfaceBuilderConfig(field_215416_m, field_215416_m, field_215416_m);
   public static final SurfaceBuilderConfig field_215392_C = new SurfaceBuilderConfig(field_215417_n, field_215418_o, field_215413_j);
   public static final SurfaceBuilderConfig field_215393_D = new SurfaceBuilderConfig(field_215419_p, field_215410_g, field_215413_j);
   public static final SurfaceBuilderConfig field_215394_E = new SurfaceBuilderConfig(field_215420_q, field_215420_q, field_215420_q);
   public static final SurfaceBuilderConfig field_215395_F = new SurfaceBuilderConfig(field_215421_r, field_215421_r, field_215421_r);
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215396_G = func_215389_a("default", new DefaultSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215397_H = func_215389_a("mountain", new MountainSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215398_I = func_215389_a("shattered_savanna", new ShatteredSavannaSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215399_J = func_215389_a("gravelly_mountain", new GravellyMountainSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215400_K = func_215389_a("giant_tree_taiga", new GiantTreeTaigaSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215401_L = func_215389_a("swamp", new SwampSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215402_M = func_215389_a("badlands", new BadlandsSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215403_N = func_215389_a("wooded_badlands", new WoodedBadlandsSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215404_O = func_215389_a("eroded_badlands", new ErodedBadlandsSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215405_P = func_215389_a("frozen_ocean", new FrozenOceanSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215406_Q = func_215389_a("nether", new NetherSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   public static final SurfaceBuilder<SurfaceBuilderConfig> field_215407_R = func_215389_a("nope", new NoopSurfaceBuilder(SurfaceBuilderConfig::func_215455_a));
   private final Function<Dynamic<?>, ? extends C> field_215408_a;

   private static <C extends ISurfaceBuilderConfig, F extends SurfaceBuilder<C>> F func_215389_a(String p_215389_0_, F p_215389_1_) {
      return (F)(Registry.<SurfaceBuilder<?>>register(Registry.field_218378_p, p_215389_0_, p_215389_1_));
   }

   public SurfaceBuilder(Function<Dynamic<?>, ? extends C> p_i51305_1_) {
      this.field_215408_a = p_i51305_1_;
   }

   public abstract void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, C config);

   public void setSeed(long seed) {
   }
}