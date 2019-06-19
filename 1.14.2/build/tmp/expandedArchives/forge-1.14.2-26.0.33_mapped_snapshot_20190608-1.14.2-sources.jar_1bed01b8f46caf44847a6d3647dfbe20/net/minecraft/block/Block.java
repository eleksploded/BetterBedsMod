package net.minecraft.block;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.DebugPacketSender;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTables;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Block extends net.minecraftforge.registries.ForgeRegistryEntry<Block> implements IItemProvider, net.minecraftforge.common.extensions.IForgeBlock {
   protected static final Logger LOGGER = LogManager.getLogger();
   @Deprecated //Forge: Do not use, use GameRegistry
   public static final ObjectIntIdentityMap<BlockState> BLOCK_STATE_IDS = net.minecraftforge.registries.GameData.getBlockStateIDMap();
   private static final Direction[] field_212556_a = new Direction[]{Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH, Direction.DOWN, Direction.UP};
   private static final LoadingCache<VoxelShape, Boolean> field_223006_b = CacheBuilder.newBuilder().maximumSize(512L).weakKeys().build(new CacheLoader<VoxelShape, Boolean>() {
      public Boolean load(VoxelShape p_load_1_) {
         return !VoxelShapes.compare(VoxelShapes.fullCube(), p_load_1_, IBooleanFunction.NOT_SAME);
      }
   });
   private static final VoxelShape field_220083_b = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D), IBooleanFunction.ONLY_FIRST);
   private static final VoxelShape field_220084_c = makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
   protected final int lightValue;
   protected final float blockHardness;
   protected final float blockResistance;
   protected final boolean ticksRandomly;
   protected final SoundType soundType;
   protected final Material material;
   protected final MaterialColor materialColor;
   private final float slipperiness;
   protected final StateContainer<Block, BlockState> stateContainer;
   private BlockState defaultState;
   protected final boolean blocksMovement;
   private final boolean variableOpacity;
   @Nullable
   private ResourceLocation lootTable;
   @Nullable
   private String translationKey;
   @Nullable
   private Item item;
   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>> SHOULD_SIDE_RENDER_CACHE = ThreadLocal.withInitial(() -> {
      Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> object2bytelinkedopenhashmap = new Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey>(200) {
         protected void rehash(int p_rehash_1_) {
         }
      };
      object2bytelinkedopenhashmap.defaultReturnValue((byte)127);
      return object2bytelinkedopenhashmap;
   });

   public static int getStateId(@Nullable BlockState state) {
      if (state == null) {
         return 0;
      } else {
         int i = BLOCK_STATE_IDS.get(state);
         return i == -1 ? 0 : i;
      }
   }

   public static BlockState getStateById(int id) {
      BlockState blockstate = BLOCK_STATE_IDS.getByValue(id);
      return blockstate == null ? Blocks.AIR.getDefaultState() : blockstate;
   }

   public static Block getBlockFromItem(@Nullable Item itemIn) {
      return itemIn instanceof BlockItem ? ((BlockItem)itemIn).getBlock() : Blocks.AIR;
   }

   public static BlockState nudgeEntitiesWithNewState(BlockState oldState, BlockState newState, World worldIn, BlockPos pos) {
      VoxelShape voxelshape = VoxelShapes.combine(oldState.getCollisionShape(worldIn, pos), newState.getCollisionShape(worldIn, pos), IBooleanFunction.ONLY_SECOND).withOffset((double)pos.getX(), (double)pos.getY(), (double)pos.getZ());

      for(Entity entity : worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, voxelshape.getBoundingBox())) {
         double d0 = VoxelShapes.getAllowedOffset(Direction.Axis.Y, entity.getBoundingBox().offset(0.0D, 1.0D, 0.0D), Stream.of(voxelshape), -1.0D);
         entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D + d0, entity.posZ);
      }

      return newState;
   }

   public static VoxelShape makeCuboidShape(double x1, double y1, double z1, double x2, double y2, double z2) {
      return VoxelShapes.create(x1 / 16.0D, y1 / 16.0D, z1 / 16.0D, x2 / 16.0D, y2 / 16.0D, z2 / 16.0D);
   }

   @Deprecated
   public boolean canEntitySpawn(BlockState p_220067_1_, IBlockReader p_220067_2_, BlockPos p_220067_3_, EntityType<?> p_220067_4_) {
      return func_220056_d(p_220067_1_, p_220067_2_, p_220067_3_, Direction.UP);
   }

   @Deprecated
   public boolean isAir(BlockState state) {
      return false;
   }

   /**
    * Amount of light emitted
    * @deprecated prefer calling {@link IBlockState#getLightValue()}
    */
   @Deprecated
   public int getLightValue(BlockState state) {
      return this.lightValue;
   }

   /**
    * Get a material of block
    * @deprecated call via {@link IBlockState#getMaterial()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public Material getMaterial(BlockState state) {
      return this.material;
   }

   /**
    * Get the MapColor for this Block and the given BlockState
    * @deprecated call via {@link IBlockState#getMapColor(IBlockAccess,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return this.materialColor;
   }

   /**
    * For all neighbors, have them react to this block's existence, potentially updating their states as needed. For
    * example, fences make their connections to this block if possible and observers pulse if this block was placed in
    * front of their detector
    */
   @Deprecated
   public void updateNeighbors(BlockState stateIn, IWorld worldIn, BlockPos pos, int flags) {
      try (BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain()) {
         for(Direction direction : field_212556_a) {
            blockpos$pooledmutableblockpos.setPos(pos).move(direction);
            BlockState blockstate = worldIn.getBlockState(blockpos$pooledmutableblockpos);
            BlockState blockstate1 = blockstate.updatePostPlacement(direction.getOpposite(), stateIn, worldIn, blockpos$pooledmutableblockpos, pos);
            replaceBlock(blockstate, blockstate1, worldIn, blockpos$pooledmutableblockpos, flags);
         }
      }

   }

   public boolean isIn(Tag<Block> tagIn) {
      return tagIn.contains(this);
   }

   /**
    * With the provided block state, performs neighbor checks for all neighboring blocks to get an "adjusted" blockstate
    * for placement in the world, if the current state is not valid.
    */
   public static BlockState getValidBlockForPosition(BlockState currentState, IWorld worldIn, BlockPos pos) {
      BlockState blockstate = currentState;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : field_212556_a) {
         blockpos$mutableblockpos.setPos(pos).move(direction);
         blockstate = blockstate.updatePostPlacement(direction, worldIn.getBlockState(blockpos$mutableblockpos), worldIn, pos, blockpos$mutableblockpos);
      }

      return blockstate;
   }

   /**
    * Replaces oldState with newState, possibly playing effects and creating drops. Flags are as in {@link
    * World#setBlockState}
    */
   public static void replaceBlock(BlockState oldState, BlockState newState, IWorld worldIn, BlockPos pos, int flags) {
      if (newState != oldState) {
         if (newState.isAir()) {
            if (!worldIn.isRemote()) {
               worldIn.destroyBlock(pos, (flags & 32) == 0);
            }
         } else {
            worldIn.setBlockState(pos, newState, flags & -33);
         }
      }

   }

   /**
    * performs updates on diagonal neighbors of the target position and passes in the flags. The flags can be referenced
    * from the docs for {@link IWorldWriter#setBlockState(IBlockState, BlockPos, int)}.
    */
   @Deprecated
   public void updateDiagonalNeighbors(BlockState state, IWorld worldIn, BlockPos pos, int flags) {
   }

   /**
    * Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
    * returns its solidified counterpart.
    * Note that this method should ideally consider only the specific face passed in.
    */
   @Deprecated
   public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
      return stateIn;
   }

   /**
    * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever possible. Implementing/overriding is
    * fine.
    */
   @Deprecated
   public BlockState rotate(BlockState state, Rotation rot) {
      return state;
   }

   /**
    * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
    * blockstate.
    * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state;
   }

   public Block(Block.Properties properties) {
      StateContainer.Builder<Block, BlockState> builder = new StateContainer.Builder<>(this);
      this.fillStateContainer(builder);
      this.material = properties.material;
      this.materialColor = properties.mapColor;
      this.blocksMovement = properties.blocksMovement;
      this.soundType = properties.soundType;
      this.lightValue = properties.lightValue;
      this.blockResistance = properties.resistance;
      this.blockHardness = properties.hardness;
      this.ticksRandomly = properties.ticksRandomly;
      this.slipperiness = properties.slipperiness;
      this.variableOpacity = properties.variableOpacity;
      this.lootTable = properties.field_222381_j;
      this.stateContainer = builder.create(BlockState::new);
      this.setDefaultState(this.stateContainer.getBaseState());
   }

   /**
    * Checks if the provided block is in the hardcoded list of blocks that will not attach to fences/panes/walls
    */
   public static boolean cannotAttach(Block p_220073_0_) {
      return p_220073_0_ instanceof LeavesBlock || p_220073_0_ == Blocks.BARRIER || p_220073_0_ == Blocks.CARVED_PUMPKIN || p_220073_0_ == Blocks.JACK_O_LANTERN || p_220073_0_ == Blocks.MELON || p_220073_0_ == Blocks.PUMPKIN;
   }

   @Deprecated
   public boolean func_220081_d(BlockState p_220081_1_, IBlockReader p_220081_2_, BlockPos p_220081_3_) {
      return p_220081_1_.getMaterial().isOpaque() && isOpaque(p_220081_1_.getCollisionShape(p_220081_2_, p_220081_3_)) && !p_220081_1_.canProvidePower();
   }

   @Deprecated
   public boolean func_220060_c(BlockState p_220060_1_, IBlockReader p_220060_2_, BlockPos p_220060_3_) {
      return this.material.blocksMovement() && isOpaque(p_220060_1_.getCollisionShape(p_220060_2_, p_220060_3_));
   }

   /**
    * @deprecated call via {@link IBlockState#hasCustomBreakingProgress()} whenever possible. Implementing/overriding is
    * fine.
    */
   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public boolean hasCustomBreakingProgress(BlockState state) {
      return false;
   }

   @Deprecated
   public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
      switch(type) {
      case LAND:
         return !isOpaque(state.getCollisionShape(worldIn, pos));
      case WATER:
         return worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
      case AIR:
         return !isOpaque(state.getCollisionShape(worldIn, pos));
      default:
         return false;
      }
   }

   /**
    * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
    * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
    * @deprecated call via {@link IBlockState#getRenderType()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public BlockRenderType getRenderType(BlockState state) {
      return BlockRenderType.MODEL;
   }

   @Deprecated
   public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
      return state.getMaterial().isReplaceable() && (useContext.getItem().isEmpty() || useContext.getItem().getItem() != this.asItem());
   }

   /**
    * @deprecated call via {@link IBlockState#getBlockHardness(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public float getBlockHardness(BlockState blockState, IBlockReader worldIn, BlockPos pos) {
      return this.blockHardness;
   }

   /**
    * Returns whether or not this block is of a type that needs random ticking. Called for ref-counting purposes by
    * ExtendedBlockStorage in order to broadly cull a chunk from the random chunk update list for efficiency's sake.
    */
   public boolean ticksRandomly(BlockState state) {
      return this.ticksRandomly;
   }

   @Deprecated //Forge: New State sensitive version.
   public boolean hasTileEntity() {
      return hasTileEntity(getDefaultState());
   }

   @Deprecated
   public boolean needsPostProcessing(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return false;
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public int getPackedLightmapCoords(BlockState p_220058_1_, IEnviromentBlockReader p_220058_2_, BlockPos p_220058_3_) {
      return p_220058_2_.getCombinedLight(p_220058_3_, p_220058_1_.getLightValue(p_220058_2_, p_220058_3_));
   }

   /**
    * ""
    */
   @OnlyIn(Dist.CLIENT)
   public static boolean shouldSideBeRendered(BlockState adjacentState, IBlockReader blockState, BlockPos blockAccess, Direction pos) {
      BlockPos blockpos = blockAccess.offset(pos);
      BlockState blockstate = blockState.getBlockState(blockpos);
      if (adjacentState.isSideInvisible(blockstate, pos)) {
         return false;
      } else if (blockstate.isSolid()) {
         Block.RenderSideCacheKey block$rendersidecachekey = new Block.RenderSideCacheKey(adjacentState, blockstate, pos);
         Object2ByteLinkedOpenHashMap<Block.RenderSideCacheKey> object2bytelinkedopenhashmap = SHOULD_SIDE_RENDER_CACHE.get();
         byte b0 = object2bytelinkedopenhashmap.getAndMoveToFirst(block$rendersidecachekey);
         if (b0 != 127) {
            return b0 != 0;
         } else {
            VoxelShape voxelshape = adjacentState.func_215702_a(blockState, blockAccess, pos);
            VoxelShape voxelshape1 = blockstate.func_215702_a(blockState, blockpos, pos.getOpposite());
            boolean flag = VoxelShapes.compare(voxelshape, voxelshape1, IBooleanFunction.ONLY_FIRST);
            if (object2bytelinkedopenhashmap.size() == 200) {
               object2bytelinkedopenhashmap.removeLastByte();
            }

            object2bytelinkedopenhashmap.putAndMoveToFirst(block$rendersidecachekey, (byte)(flag ? 1 : 0));
            return flag;
         }
      } else {
         return true;
      }
   }

   @Deprecated
   public boolean isSolid(BlockState state) {
      return this.blocksMovement && this.getRenderLayer() == BlockRenderLayer.SOLID;
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
      return false;
   }

   @Deprecated
   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return VoxelShapes.fullCube();
   }

   @Deprecated
   public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
      return this.blocksMovement ? p_220071_1_.getShape(p_220071_2_, p_220071_3_) : VoxelShapes.empty();
   }

   @Deprecated
   public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.getShape(worldIn, pos);
   }

   @Deprecated
   public VoxelShape getRaytraceShape(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return VoxelShapes.empty();
   }

   public static boolean func_220064_c(IBlockReader p_220064_0_, BlockPos p_220064_1_) {
      BlockState blockstate = p_220064_0_.getBlockState(p_220064_1_);
      return !blockstate.isIn(BlockTags.LEAVES) && !VoxelShapes.compare(blockstate.getCollisionShape(p_220064_0_, p_220064_1_).func_212434_a(Direction.UP), field_220083_b, IBooleanFunction.ONLY_SECOND);
   }

   public static boolean func_220055_a(IWorldReader p_220055_0_, BlockPos p_220055_1_, Direction p_220055_2_) {
      BlockState blockstate = p_220055_0_.getBlockState(p_220055_1_);
      return !blockstate.isIn(BlockTags.LEAVES) && !VoxelShapes.compare(blockstate.getCollisionShape(p_220055_0_, p_220055_1_).func_212434_a(p_220055_2_), field_220084_c, IBooleanFunction.ONLY_SECOND);
   }

   public static boolean func_220056_d(BlockState p_220056_0_, IBlockReader p_220056_1_, BlockPos p_220056_2_, Direction p_220056_3_) {
      return !p_220056_0_.isIn(BlockTags.LEAVES) && doesSideFillSquare(p_220056_0_.getCollisionShape(p_220056_1_, p_220056_2_), p_220056_3_);
   }

   public static boolean doesSideFillSquare(VoxelShape shape, Direction side) {
      VoxelShape voxelshape = shape.func_212434_a(side);
      return isOpaque(voxelshape);
   }

   /**
    * Gets whether the provided {@link VoxelShape} is opaque
    */
   public static boolean isOpaque(VoxelShape shape) {
      return field_223006_b.getUnchecked(shape);
   }

   @Deprecated
   public final boolean isOpaqueCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
      return state.isSolid() ? isOpaque(state.getRenderShape(worldIn, pos)) : false;
   }

   public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
      return !isOpaque(state.getShape(reader, pos)) && state.getFluidState().isEmpty();
   }

   @Deprecated
   public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
      if (state.isOpaqueCube(worldIn, pos)) {
         return worldIn.getMaxLightLevel();
      } else {
         return state.propagatesSkylightDown(worldIn, pos) ? 0 : 1;
      }
   }

   @Deprecated
   public boolean func_220074_n(BlockState p_220074_1_) {
      return false;
   }

   @Deprecated
   public void randomTick(BlockState state, World worldIn, BlockPos pos, Random random) {
      this.tick(state, worldIn, pos, random);
   }

   @Deprecated
   public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
   }

   /**
    * Called periodically clientside on blocks near the player to show effects (like furnace fire particles). Note that
    * this method is unrelated to {@link randomTick} and {@link #needsRandomTick}, and will always be called regardless
    * of whether the block can receive random update ticks
    */
   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
   }

   /**
    * Called after a player destroys this Block - the posiiton pos may no longer hold the state indicated.
    */
   public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
   }

   @Deprecated
   public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
      DebugPacketSender.func_218806_a(p_220069_2_, p_220069_3_);
   }

   /**
    * How many world ticks before ticking
    */
   public int tickRate(IWorldReader worldIn) {
      return 10;
   }

   @Nullable
   @Deprecated
   public INamedContainerProvider getContainer(BlockState p_220052_1_, World p_220052_2_, BlockPos p_220052_3_) {
      return null;
   }

   @Deprecated
   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
   }

   @Deprecated
   public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.hasTileEntity() && state.getBlock() != newState.getBlock()) {
         worldIn.removeTileEntity(pos);
      }
   }

   /**
    * Get the hardness of this Block relative to the ability of the given player
    * @deprecated call via {@link IBlockState#getPlayerRelativeBlockHardness(EntityPlayer,World,BlockPos)} whenever
    * possible. Implementing/overriding is fine.
    */
   @Deprecated
   public float getPlayerRelativeBlockHardness(BlockState state, PlayerEntity player, IBlockReader worldIn, BlockPos pos) {
      float f = state.getBlockHardness(worldIn, pos);
      if (f == -1.0F) {
         return 0.0F;
      } else {
         int i = net.minecraftforge.common.ForgeHooks.canHarvestBlock(state, player, worldIn, pos) ? 30 : 100;
         return player.getDigSpeed(state, pos) / f / (float)i;
      }
   }

   /**
    * Spawn additional block drops such as experience or other entities
    */
   @Deprecated
   public void spawnAdditionalDrops(BlockState p_220062_1_, World p_220062_2_, BlockPos p_220062_3_, ItemStack p_220062_4_) {
   }

   public ResourceLocation getLootTable() {
      if (this.lootTable == null) {
         ResourceLocation resourcelocation = Registry.field_212618_g.getKey(this);
         this.lootTable = new ResourceLocation(resourcelocation.getNamespace(), "blocks/" + resourcelocation.getPath());
      }

      return this.lootTable;
   }

   @Deprecated
   public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
      ResourceLocation resourcelocation = this.getLootTable();
      if (resourcelocation == LootTables.EMPTY) {
         return Collections.emptyList();
      } else {
         LootContext lootcontext = p_220076_2_.withParameter(LootParameters.field_216287_g, p_220076_1_).build(LootParameterSets.field_216267_h);
         ServerWorld serverworld = lootcontext.getWorld();
         LootTable loottable = serverworld.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
         return loottable.func_216113_a(lootcontext);
      }
   }

   public static List<ItemStack> getDrops(BlockState p_220070_0_, ServerWorld p_220070_1_, BlockPos p_220070_2_, @Nullable TileEntity p_220070_3_) {
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(p_220070_1_)).withRandom(p_220070_1_.rand).withParameter(LootParameters.field_216286_f, p_220070_2_).withParameter(LootParameters.field_216289_i, ItemStack.EMPTY).withNullableParameter(LootParameters.field_216288_h, p_220070_3_);
      return p_220070_0_.getDrops(lootcontext$builder);
   }

   public static List<ItemStack> getDrops(BlockState p_220077_0_, ServerWorld p_220077_1_, BlockPos p_220077_2_, @Nullable TileEntity p_220077_3_, Entity p_220077_4_, ItemStack p_220077_5_) {
      LootContext.Builder lootcontext$builder = (new LootContext.Builder(p_220077_1_)).withRandom(p_220077_1_.rand).withParameter(LootParameters.field_216286_f, p_220077_2_).withParameter(LootParameters.field_216289_i, p_220077_5_).withParameter(LootParameters.field_216281_a, p_220077_4_).withNullableParameter(LootParameters.field_216288_h, p_220077_3_);
      return p_220077_0_.getDrops(lootcontext$builder);
   }

   public static void spawnDrops(BlockState p_220078_0_, LootContext.Builder p_220078_1_) {
      ServerWorld serverworld = p_220078_1_.func_216018_a();
      BlockPos blockpos = p_220078_1_.assertPresent(LootParameters.field_216286_f);
      p_220078_0_.getDrops(p_220078_1_).forEach((p_220063_2_) -> {
         spawnAsEntity(serverworld, blockpos, p_220063_2_);
      });
      p_220078_0_.spawnAdditionalDrops(serverworld, blockpos, ItemStack.EMPTY);
   }

   public static void spawnDrops(BlockState p_220075_0_, World p_220075_1_, BlockPos p_220075_2_) {
      if (p_220075_1_ instanceof ServerWorld) {
         getDrops(p_220075_0_, (ServerWorld)p_220075_1_, p_220075_2_, (TileEntity)null).forEach((p_220079_2_) -> {
            spawnAsEntity(p_220075_1_, p_220075_2_, p_220079_2_);
         });
      }

      p_220075_0_.spawnAdditionalDrops(p_220075_1_, p_220075_2_, ItemStack.EMPTY);
   }

   public static void spawnDrops(BlockState p_220059_0_, World p_220059_1_, BlockPos p_220059_2_, @Nullable TileEntity p_220059_3_) {
      if (p_220059_1_ instanceof ServerWorld) {
         getDrops(p_220059_0_, (ServerWorld)p_220059_1_, p_220059_2_, p_220059_3_).forEach((p_220061_2_) -> {
            spawnAsEntity(p_220059_1_, p_220059_2_, p_220061_2_);
         });
      }

      p_220059_0_.spawnAdditionalDrops(p_220059_1_, p_220059_2_, ItemStack.EMPTY);
   }

   public static void spawnDrops(BlockState p_220054_0_, World p_220054_1_, BlockPos p_220054_2_, @Nullable TileEntity p_220054_3_, Entity p_220054_4_, ItemStack p_220054_5_) {
      if (p_220054_1_ instanceof ServerWorld) {
         getDrops(p_220054_0_, (ServerWorld)p_220054_1_, p_220054_2_, p_220054_3_, p_220054_4_, p_220054_5_).forEach((p_220057_2_) -> {
            spawnAsEntity(p_220054_1_, p_220054_2_, p_220057_2_);
         });
      }

      p_220054_0_.spawnAdditionalDrops(p_220054_1_, p_220054_2_, p_220054_5_);
   }

   /**
    * Spawns the given ItemStack as an EntityItem into the World at the given position
    */
   public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
      if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
         float f = 0.5F;
         double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
         double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
         double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
         ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
         itementity.setDefaultPickupDelay();
         worldIn.func_217376_c(itementity);
      }
   }

   /**
    * Spawns the given amount of experience into the World as XP orb entities
    */
   public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
      if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops") && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
         while(amount > 0) {
            int i = ExperienceOrbEntity.getXPSplit(amount);
            amount -= i;
            worldIn.func_217376_c(new ExperienceOrbEntity(worldIn, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, i));
         }
      }

   }

   /**
    * Returns how much this block can resist explosions from the passed in entity.
    */
   @Deprecated //Forge: State sensitive version
   public float getExplosionResistance() {
      return this.blockResistance;
   }

   /**
    * Called when this Block is destroyed by an Explosion
    */
   public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
   }

   /**
    * Gets the render layer this block will render on. SOLID for solid blocks, CUTOUT or CUTOUT_MIPPED for on-off
    * transparency (glass, reeds), TRANSLUCENT for fully blended transparency (stained glass)
    */
   public BlockRenderLayer getRenderLayer() {
      return BlockRenderLayer.SOLID;
   }

   @Deprecated
   public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
      return true;
   }

   @Deprecated
   public boolean onBlockActivated(BlockState p_220051_1_, World p_220051_2_, BlockPos p_220051_3_, PlayerEntity p_220051_4_, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
      return false;
   }

   /**
    * Called when the given entity walks on this Block
    */
   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext context) {
      return this.getDefaultState();
   }

   @Deprecated
   public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
   }

   /**
    * @deprecated call via {@link IBlockState#getWeakPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return 0;
   }

   /**
    * Can this block provide power. Only wire currently seems to have this change based on its state.
    * @deprecated call via {@link IBlockState#canProvidePower()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public boolean canProvidePower(BlockState state) {
      return false;
   }

   @Deprecated
   public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
   }

   /**
    * @deprecated call via {@link IBlockState#getStrongPower(IBlockAccess,BlockPos,EnumFacing)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
      return 0;
   }

   /**
    * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
    * Block.removedByPlayer
    */
   public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
      player.addStat(Stats.BLOCK_MINED.get(this));
      player.addExhaustion(0.005F);
      spawnDrops(state, worldIn, pos, te, player, stack);
   }

   /**
    * Called by ItemBlocks after a block is set in the world, to allow post-place logic
    */
   public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
   }

   /**
    * Return true if an entity can be spawned inside the block (used to get the player's bed spawn location)
    */
   public boolean canSpawnInBlock() {
      return !this.material.isSolid() && !this.material.isLiquid();
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getNameTextComponent() {
      return new TranslationTextComponent(this.getTranslationKey());
   }

   /**
    * Returns the unlocalized name of the block with "tile." appended to the front.
    */
   public String getTranslationKey() {
      if (this.translationKey == null) {
         this.translationKey = Util.makeTranslationKey("block", Registry.field_212618_g.getKey(this));
      }

      return this.translationKey;
   }

   /**
    * Called on server when World#addBlockEvent is called. If server returns true, then also called on the client. On
    * the Server, this may perform additional changes to the world, like pistons replacing the block with an extended
    * base. On the client, the update may involve replacing tile entities or effects such as sounds or particles
    * @deprecated call via {@link IBlockState#onBlockEventReceived(World,BlockPos,int,int)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public boolean eventReceived(BlockState state, World worldIn, BlockPos pos, int id, int param) {
      return false;
   }

   /**
    * @deprecated call via {@link IBlockState#getMobilityFlag()} whenever possible. Implementing/overriding is fine.
    */
   @Deprecated
   public PushReaction getPushReaction(BlockState state) {
      return this.material.getPushReaction();
   }

   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public float func_220080_a(BlockState p_220080_1_, IBlockReader p_220080_2_, BlockPos p_220080_3_) {
      return isOpaque(p_220080_1_.getCollisionShape(p_220080_2_, p_220080_3_)) ? 0.2F : 1.0F;
   }

   /**
    * Block's chance to react to a living entity falling on it.
    */
   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
      entityIn.fall(fallDistance, 1.0F);
   }

   /**
    * Called when an Entity lands on this Block. This method *must* update motionY because the entity will not do that
    * on its own
    */
   public void onLanded(IBlockReader worldIn, Entity entityIn) {
      entityIn.setMotion(entityIn.getMotion().mul(1.0D, 0.0D, 1.0D));
   }

   @Deprecated // Forge: Use more sensitive version below: getPickBlock
   @OnlyIn(Dist.CLIENT)
   public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
      return new ItemStack(this);
   }

   /**
    * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
    */
   public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
      items.add(new ItemStack(this));
   }

   @Deprecated
   public IFluidState getFluidState(BlockState state) {
      return Fluids.EMPTY.getDefaultState();
   }

   @Deprecated //Forge: Use more sensitive version
   public float getSlipperiness() {
      return this.slipperiness;
   }

   /**
    * Return a random long to be passed to {@link IBakedModel#getQuads}, used for random model rotations
    */
   @Deprecated
   @OnlyIn(Dist.CLIENT)
   public long getPositionRandom(BlockState state, BlockPos pos) {
      return MathHelper.getPositionRandom(pos);
   }

   public void onProjectileCollision(World p_220066_1_, BlockState p_220066_2_, BlockRayTraceResult p_220066_3_, Entity p_220066_4_) {
   }

   /**
    * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
    * this block
    */
   public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
      worldIn.playEvent(player, 2001, pos, getStateId(state));
   }

   /**
    * Called similar to random ticks, but only when it is raining.
    */
   public void fillWithRain(World worldIn, BlockPos pos) {
   }

   /**
    * Return whether this block can drop from an explosion.
    */
   @Deprecated //Forge: Use more sensitive version
   public boolean canDropFromExplosion(Explosion explosionIn) {
      return true;
   }

   /**
    * @deprecated call via {@link IBlockState#hasComparatorInputOverride()} whenever possible. Implementing/overriding
    * is fine.
    */
   @Deprecated
   public boolean hasComparatorInputOverride(BlockState state) {
      return false;
   }

   /**
    * @deprecated call via {@link IBlockState#getComparatorInputOverride(World,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
      return 0;
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
   }

   public StateContainer<Block, BlockState> getStateContainer() {
      return this.stateContainer;
   }

   protected final void setDefaultState(BlockState state) {
      this.defaultState = state;
   }

   /**
    * Gets the default state for this block
    */
   public final BlockState getDefaultState() {
      return this.defaultState;
   }

   /**
    * Get the OffsetType for this Block. Determines if the model is rendered slightly offset.
    */
   public Block.OffsetType getOffsetType() {
      return Block.OffsetType.NONE;
   }

   /**
    * @deprecated call via {@link IBlockState#getOffset(IBlockAccess,BlockPos)} whenever possible.
    * Implementing/overriding is fine.
    */
   @Deprecated
   public Vec3d getOffset(BlockState state, IBlockReader worldIn, BlockPos pos) {
      Block.OffsetType block$offsettype = this.getOffsetType();
      if (block$offsettype == Block.OffsetType.NONE) {
         return Vec3d.ZERO;
      } else {
         long i = MathHelper.getCoordinateRandom(pos.getX(), 0, pos.getZ());
         return new Vec3d(((double)((float)(i & 15L) / 15.0F) - 0.5D) * 0.5D, block$offsettype == Block.OffsetType.XYZ ? ((double)((float)(i >> 4 & 15L) / 15.0F) - 1.0D) * 0.2D : 0.0D, ((double)((float)(i >> 8 & 15L) / 15.0F) - 0.5D) * 0.5D);
      }
   }

   @Deprecated //Forge: Use more sensitive version {@link IForgeBlockState#getSoundType(IWorldReader, BlockPos, Entity) }
   public SoundType getSoundType(BlockState p_220072_1_) {
      return this.soundType;
   }

   public Item asItem() {
      if (this.item == null) {
         this.item = Item.getItemFromBlock(this);
      }

      return this.item;
   }

   public boolean isVariableOpacity() {
      return this.variableOpacity;
   }

   public String toString() {
      return "Block{" + Registry.field_212618_g.getKey(this) + "}";
   }

   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
   }

   public static boolean isRock(Block blockIn) {
      return net.minecraftforge.common.Tags.Blocks.STONE.contains(blockIn);
   }

   public static boolean isDirt(Block blockIn) {
      return net.minecraftforge.common.Tags.Blocks.DIRT.contains(blockIn);
   }

   /* ======================================== FORGE START =====================================*/
   protected Random RANDOM = new Random();
   private net.minecraftforge.common.ToolType harvestTool;
   private int harvestLevel;
   private final net.minecraftforge.common.util.ReverseTagWrapper<Block> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, BlockTags::getGeneration, BlockTags::getCollection);

   @Override
   public float getSlipperiness(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
      return this.slipperiness;
   }

   @Nullable
   @Override
   public net.minecraftforge.common.ToolType getHarvestTool(BlockState state) {
      return harvestTool; //TODO: RE-Evaluate
   }

   @Override
   public int getHarvestLevel(BlockState state) {
      return harvestLevel; //TODO: RE-Evaluate
   }

   @Override
   public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, net.minecraftforge.common.IPlantable plantable) {
       BlockState plant = plantable.getPlant(world, pos.offset(facing));
       net.minecraftforge.common.PlantType type = plantable.getPlantType(world, pos.offset(facing));

       if (plant.getBlock() == Blocks.CACTUS)
           return this.getBlock() == Blocks.CACTUS || this.getBlock() == Blocks.SAND || this.getBlock() == Blocks.RED_SAND;

       if (plant.getBlock() == Blocks.SUGAR_CANE && this == Blocks.SUGAR_CANE)
           return true;

       if (plantable instanceof BushBlock && ((BushBlock)plantable).isValidGround(state, world, pos))
           return true;

       switch (type) {
           case Desert: return this.getBlock() == Blocks.SAND || this.getBlock() == Blocks.TERRACOTTA || this.getBlock() instanceof GlazedTerracottaBlock;
           case Nether: return this.getBlock() == Blocks.SOUL_SAND;
           case Crop:   return this.getBlock() == Blocks.FARMLAND;
           case Cave:   return Block.func_220056_d(state, world, pos, Direction.UP);
           case Plains: return this.getBlock() == Blocks.GRASS_BLOCK || Block.isDirt(this) || this.getBlock() == Blocks.FARMLAND;
           case Water:  return state.getMaterial() == Material.WATER; //&& state.getValue(BlockLiquidWrapper)
           case Beach:
               boolean isBeach = this.getBlock() == Blocks.GRASS_BLOCK || Block.isDirt(this) || this.getBlock() == Blocks.SAND;
               boolean hasWater = (world.getBlockState(pos.east()).getMaterial() == Material.WATER ||
                       world.getBlockState(pos.west()).getMaterial() == Material.WATER ||
                       world.getBlockState(pos.north()).getMaterial() == Material.WATER ||
                       world.getBlockState(pos.south()).getMaterial() == Material.WATER);
               return isBeach && hasWater;
       }
       return false;
   }

   @Override
   public final java.util.Set<ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   static {
      net.minecraftforge.common.ForgeHooks.setBlockToolSetter((block, tool, level) -> {
         block.harvestTool = tool;
         block.harvestLevel = level;
      });
   }
   /* ========================================= FORGE END ======================================*/

   public static enum OffsetType {
      NONE,
      XZ,
      XYZ;
   }

   public static class Properties {
      private Material material;
      private MaterialColor mapColor;
      private boolean blocksMovement = true;
      private SoundType soundType = SoundType.STONE;
      private int lightValue;
      private float resistance;
      private float hardness;
      private boolean ticksRandomly;
      private float slipperiness = 0.6F;
      private ResourceLocation field_222381_j;
      private boolean variableOpacity;

      private Properties(Material materialIn, MaterialColor mapColorIn) {
         this.material = materialIn;
         this.mapColor = mapColorIn;
      }

      public static Block.Properties create(Material materialIn) {
         return create(materialIn, materialIn.getColor());
      }

      public static Block.Properties create(Material materialIn, DyeColor color) {
         return create(materialIn, color.getMapColor());
      }

      public static Block.Properties create(Material materialIn, MaterialColor mapColorIn) {
         return new Block.Properties(materialIn, mapColorIn);
      }

      public static Block.Properties from(Block blockIn) {
         Block.Properties block$properties = new Block.Properties(blockIn.material, blockIn.materialColor);
         block$properties.material = blockIn.material;
         block$properties.hardness = blockIn.blockHardness;
         block$properties.resistance = blockIn.blockResistance;
         block$properties.blocksMovement = blockIn.blocksMovement;
         block$properties.ticksRandomly = blockIn.ticksRandomly;
         block$properties.lightValue = blockIn.lightValue;
         block$properties.mapColor = blockIn.materialColor;
         block$properties.soundType = blockIn.soundType;
         block$properties.slipperiness = blockIn.getSlipperiness();
         block$properties.variableOpacity = blockIn.variableOpacity;
         return block$properties;
      }

      public Block.Properties doesNotBlockMovement() {
         this.blocksMovement = false;
         return this;
      }

      public Block.Properties slipperiness(float slipperinessIn) {
         this.slipperiness = slipperinessIn;
         return this;
      }

      public Block.Properties sound(SoundType soundTypeIn) {
         this.soundType = soundTypeIn;
         return this;
      }

      public Block.Properties lightValue(int lightValueIn) {
         this.lightValue = lightValueIn;
         return this;
      }

      public Block.Properties hardnessAndResistance(float hardnessIn, float resistanceIn) {
         this.hardness = hardnessIn;
         this.resistance = Math.max(0.0F, resistanceIn);
         return this;
      }

      protected Block.Properties zeroHardnessAndResistance() {
         return this.hardnessAndResistance(0.0F);
      }

      public Block.Properties hardnessAndResistance(float hardnessAndResistance) {
         this.hardnessAndResistance(hardnessAndResistance, hardnessAndResistance);
         return this;
      }

      public Block.Properties tickRandomly() {
         this.ticksRandomly = true;
         return this;
      }

      public Block.Properties variableOpacity() {
         this.variableOpacity = true;
         return this;
      }

      protected Block.Properties func_222380_e() {
         this.field_222381_j = LootTables.EMPTY;
         return this;
      }

      public Block.Properties func_222379_b(Block p_222379_1_) {
         this.field_222381_j = p_222379_1_.getLootTable();
         return this;
      }
   }

   public static final class RenderSideCacheKey {
      private final BlockState field_212164_a;
      private final BlockState field_212165_b;
      private final Direction field_212166_c;

      public RenderSideCacheKey(BlockState state, BlockState adjacentState, Direction side) {
         this.field_212164_a = state;
         this.field_212165_b = adjacentState;
         this.field_212166_c = side;
      }

      public boolean equals(Object p_equals_1_) {
         if (this == p_equals_1_) {
            return true;
         } else if (!(p_equals_1_ instanceof Block.RenderSideCacheKey)) {
            return false;
         } else {
            Block.RenderSideCacheKey block$rendersidecachekey = (Block.RenderSideCacheKey)p_equals_1_;
            return this.field_212164_a == block$rendersidecachekey.field_212164_a && this.field_212165_b == block$rendersidecachekey.field_212165_b && this.field_212166_c == block$rendersidecachekey.field_212166_c;
         }
      }

      public int hashCode() {
         int i = this.field_212164_a.hashCode();
         i = 31 * i + this.field_212165_b.hashCode();
         i = 31 * i + this.field_212166_c.hashCode();
         return i;
      }
   }
}