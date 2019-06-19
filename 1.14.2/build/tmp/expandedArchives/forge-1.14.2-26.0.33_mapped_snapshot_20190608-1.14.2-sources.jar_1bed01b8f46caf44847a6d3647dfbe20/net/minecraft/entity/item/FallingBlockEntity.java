package net.minecraft.entity.item;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FallingBlockEntity extends Entity {
   private BlockState field_175132_d = Blocks.SAND.getDefaultState();
   public int fallTime;
   public boolean shouldDropItem = true;
   private boolean dontSetBlock;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public CompoundNBT field_145810_d;
   protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(FallingBlockEntity.class, DataSerializers.field_187200_j);

   public FallingBlockEntity(EntityType<? extends FallingBlockEntity> p_i50218_1_, World p_i50218_2_) {
      super(p_i50218_1_, p_i50218_2_);
   }

   public FallingBlockEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
      this(EntityType.FALLING_BLOCK, worldIn);
      this.field_175132_d = fallingBlockState;
      this.preventEntitySpawning = true;
      this.setPosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
      this.setMotion(Vec3d.ZERO);
      this.prevPosX = x;
      this.prevPosY = y;
      this.prevPosZ = z;
      this.setOrigin(new BlockPos(this));
   }

   /**
    * Returns true if it's possible to attack this entity with an item.
    */
   public boolean canBeAttackedWithItem() {
      return false;
   }

   public void setOrigin(BlockPos p_184530_1_) {
      this.dataManager.set(ORIGIN, p_184530_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public BlockPos getOrigin() {
      return this.dataManager.get(ORIGIN);
   }

   /**
    * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
    * prevent them from trampling crops
    */
   protected boolean canTriggerWalking() {
      return false;
   }

   protected void registerData() {
      this.dataManager.register(ORIGIN, BlockPos.ZERO);
   }

   /**
    * Returns true if other Entities should be prevented from moving through this Entity.
    */
   public boolean canBeCollidedWith() {
      return !this.removed;
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (this.field_175132_d.isAir()) {
         this.remove();
      } else {
         this.prevPosX = this.posX;
         this.prevPosY = this.posY;
         this.prevPosZ = this.posZ;
         Block block = this.field_175132_d.getBlock();
         if (this.fallTime++ == 0) {
            BlockPos blockpos = new BlockPos(this);
            if (this.world.getBlockState(blockpos).getBlock() == block) {
               this.world.removeBlock(blockpos, false);
            } else if (!this.world.isRemote) {
               this.remove();
               return;
            }
         }

         if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
         }

         this.move(MoverType.SELF, this.getMotion());
         if (!this.world.isRemote) {
            BlockPos blockpos1 = new BlockPos(this);
            boolean flag = this.field_175132_d.getBlock() instanceof ConcretePowderBlock;
            boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
            double d0 = this.getMotion().lengthSquared();
            if (flag && d0 > 1.0D) {
               BlockRayTraceResult blockraytraceresult = this.world.func_217299_a(new RayTraceContext(new Vec3d(this.prevPosX, this.prevPosY, this.prevPosZ), new Vec3d(this.posX, this.posY, this.posZ), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
               if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
                  blockpos1 = blockraytraceresult.getPos();
                  flag1 = true;
               }
            }

            if (!this.onGround && !flag1) {
               if (!this.world.isRemote && (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)) {
                  if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                     this.entityDropItem(block);
                  }

                  this.remove();
               }
            } else {
               BlockState blockstate = this.world.getBlockState(blockpos1);
               this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
               if (blockstate.getBlock() != Blocks.MOVING_PISTON) {
                  this.remove();
                  if (!this.dontSetBlock) {
                     if (flag1 || blockstate.isReplaceable(new DirectionalPlaceContext(this.world, blockpos1, Direction.DOWN, ItemStack.EMPTY, Direction.UP)) && this.field_175132_d.isValidPosition(this.world, blockpos1)) {
                        if (this.field_175132_d.has(BlockStateProperties.WATERLOGGED) && this.world.getFluidState(blockpos1).getFluid() == Fluids.WATER) {
                           this.field_175132_d = this.field_175132_d.with(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true));
                        }

                        if (this.world.setBlockState(blockpos1, this.field_175132_d, 3)) {
                           if (block instanceof FallingBlock) {
                              ((FallingBlock)block).onEndFalling(this.world, blockpos1, this.field_175132_d, blockstate);
                           }

                           if (this.field_145810_d != null && this.field_175132_d.hasTileEntity()) {
                              TileEntity tileentity = this.world.getTileEntity(blockpos1);
                              if (tileentity != null) {
                                 CompoundNBT compoundnbt = tileentity.write(new CompoundNBT());

                                 for(String s : this.field_145810_d.keySet()) {
                                    INBT inbt = this.field_145810_d.get(s);
                                    if (!"x".equals(s) && !"y".equals(s) && !"z".equals(s)) {
                                       compoundnbt.put(s, inbt.copy());
                                    }
                                 }

                                 tileentity.read(compoundnbt);
                                 tileentity.markDirty();
                              }
                           }
                        } else if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                           this.entityDropItem(block);
                        }
                     } else if (this.shouldDropItem && this.world.getGameRules().getBoolean("doEntityDrops")) {
                        this.entityDropItem(block);
                     }
                  } else if (block instanceof FallingBlock) {
                     ((FallingBlock)block).onBroken(this.world, blockpos1);
                  }
               }
            }
         }

         this.setMotion(this.getMotion().scale(0.98D));
      }
   }

   public void fall(float distance, float damageMultiplier) {
      if (this.hurtEntities) {
         int i = MathHelper.ceil(distance - 1.0F);
         if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
            boolean flag = this.field_175132_d.isIn(BlockTags.ANVIL);
            DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;

            for(Entity entity : list) {
               entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor((float)i * this.fallHurtAmount), this.fallHurtMax));
            }

            if (flag && (double)this.rand.nextFloat() < (double)0.05F + (double)i * 0.05D) {
               BlockState blockstate = AnvilBlock.damage(this.field_175132_d);
               if (blockstate == null) {
                  this.dontSetBlock = true;
               } else {
                  this.field_175132_d = blockstate;
               }
            }
         }
      }

   }

   protected void writeAdditional(CompoundNBT p_213281_1_) {
      p_213281_1_.put("BlockState", NBTUtil.writeBlockState(this.field_175132_d));
      p_213281_1_.putInt("Time", this.fallTime);
      p_213281_1_.putBoolean("DropItem", this.shouldDropItem);
      p_213281_1_.putBoolean("HurtEntities", this.hurtEntities);
      p_213281_1_.putFloat("FallHurtAmount", this.fallHurtAmount);
      p_213281_1_.putInt("FallHurtMax", this.fallHurtMax);
      if (this.field_145810_d != null) {
         p_213281_1_.put("TileEntityData", this.field_145810_d);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   protected void readAdditional(CompoundNBT compound) {
      this.field_175132_d = NBTUtil.readBlockState(compound.getCompound("BlockState"));
      this.fallTime = compound.getInt("Time");
      if (compound.contains("HurtEntities", 99)) {
         this.hurtEntities = compound.getBoolean("HurtEntities");
         this.fallHurtAmount = compound.getFloat("FallHurtAmount");
         this.fallHurtMax = compound.getInt("FallHurtMax");
      } else if (this.field_175132_d.isIn(BlockTags.ANVIL)) {
         this.hurtEntities = true;
      }

      if (compound.contains("DropItem", 99)) {
         this.shouldDropItem = compound.getBoolean("DropItem");
      }

      if (compound.contains("TileEntityData", 10)) {
         this.field_145810_d = compound.getCompound("TileEntityData");
      }

      if (this.field_175132_d.isAir()) {
         this.field_175132_d = Blocks.SAND.getDefaultState();
      }

   }

   @OnlyIn(Dist.CLIENT)
   public World getWorldObj() {
      return this.world;
   }

   public void setHurtEntities(boolean hurtEntitiesIn) {
      this.hurtEntities = hurtEntitiesIn;
   }

   /**
    * Return whether this entity should be rendered as on fire.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean canRenderOnFire() {
      return false;
   }

   public void fillCrashReport(CrashReportCategory category) {
      super.fillCrashReport(category);
      category.addDetail("Immitating BlockState", this.field_175132_d.toString());
   }

   public BlockState getBlockState() {
      return this.field_175132_d;
   }

   /**
    * Checks if players can use this entity to access operator (permission level 2) commands either directly or
    * indirectly, such as give or setblock. A similar method exists for entities at {@link
    * net.minecraft.tileentity.TileEntity#onlyOpsCanSetNbt()}.<p>For example, {@link
    * net.minecraft.entity.item.EntityMinecartCommandBlock#ignoreItemEntityData() command block minecarts} and {@link
    * net.minecraft.entity.item.EntityMinecartMobSpawner#ignoreItemEntityData() mob spawner minecarts} (spawning command
    * block minecarts or drops) are considered accessible.</p>@return true if this entity offers ways for unauthorized
    * players to use restricted commands
    */
   public boolean ignoreItemEntityData() {
      return true;
   }

   public IPacket<?> createSpawnPacket() {
      return new SSpawnObjectPacket(this, Block.getStateId(this.getBlockState()));
   }
}