package net.minecraft.entity.passive;

import java.util.UUID;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SuspiciousStewItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;

public class MooshroomEntity extends CowEntity implements net.minecraftforge.common.IShearable {
   private static final DataParameter<String> field_213449_bz = EntityDataManager.createKey(MooshroomEntity.class, DataSerializers.field_187194_d);
   private Effect field_213450_bA;
   private int field_213447_bB;
   private UUID field_213448_bD;

   public MooshroomEntity(EntityType<? extends MooshroomEntity> p_i50257_1_, World p_i50257_2_) {
      super(p_i50257_1_, p_i50257_2_);
      this.spawnableBlock = Blocks.MYCELIUM;
   }

   /**
    * Called when a lightning bolt hits the entity.
    */
   public void onStruckByLightning(LightningBoltEntity lightningBolt) {
      UUID uuid = lightningBolt.getUniqueID();
      if (!uuid.equals(this.field_213448_bD)) {
         this.func_213446_a(this.func_213444_dV() == MooshroomEntity.Type.RED ? MooshroomEntity.Type.BROWN : MooshroomEntity.Type.RED);
         this.field_213448_bD = uuid;
         this.playSound(SoundEvents.field_219658_gv, 2.0F, 1.0F);
      }

   }

   protected void registerData() {
      super.registerData();
      this.dataManager.register(field_213449_bz, MooshroomEntity.Type.RED.field_221098_c);
   }

   public boolean processInteract(PlayerEntity player, Hand hand) {
      ItemStack itemstack = player.getHeldItem(hand);
      if (itemstack.getItem() == Items.BOWL && this.getGrowingAge() >= 0 && !player.playerAbilities.isCreativeMode) {
         itemstack.shrink(1);
         boolean flag = false;
         ItemStack itemstack1;
         if (this.field_213450_bA != null) {
            flag = true;
            itemstack1 = new ItemStack(Items.field_222115_pz);
            SuspiciousStewItem.func_220037_a(itemstack1, this.field_213450_bA, this.field_213447_bB);
            this.field_213450_bA = null;
            this.field_213447_bB = 0;
         } else {
            itemstack1 = new ItemStack(Items.MUSHROOM_STEW);
         }

         if (itemstack.isEmpty()) {
            player.setHeldItem(hand, itemstack1);
         } else if (!player.inventory.addItemStackToInventory(itemstack1)) {
            player.dropItem(itemstack1, false);
         }

         SoundEvent soundevent;
         if (flag) {
            soundevent = SoundEvents.field_219661_gy;
         } else {
            soundevent = SoundEvents.field_219660_gx;
         }

         this.playSound(soundevent, 1.0F, 1.0F);
         return true;
      } else if (false && itemstack.getItem() == Items.SHEARS && this.getGrowingAge() >= 0) { //Forge: Moved to onSheared
         this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY + (double)(this.getHeight() / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);
         if (!this.world.isRemote) {
            this.remove();
            CowEntity cowentity = EntityType.COW.create(this.world);
            cowentity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            cowentity.setHealth(this.getHealth());
            cowentity.renderYawOffset = this.renderYawOffset;
            if (this.hasCustomName()) {
               cowentity.setCustomName(this.getCustomName());
            }

            this.world.func_217376_c(cowentity);

            for(int k = 0; k < 5; ++k) {
               this.world.func_217376_c(new ItemEntity(this.world, this.posX, this.posY + (double)this.getHeight(), this.posZ, new ItemStack(this.func_213444_dV().field_221099_d.getBlock())));
            }

            itemstack.func_222118_a(1, player, (p_213442_1_) -> {
               p_213442_1_.func_213334_d(hand);
            });
            this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
         }

         return true;
      } else {
         if (this.func_213444_dV() == MooshroomEntity.Type.BROWN && itemstack.getItem().isIn(ItemTags.field_219770_E)) {
            if (this.field_213450_bA != null) {
               for(int i = 0; i < 2; ++i) {
                  this.world.addParticle(ParticleTypes.SMOKE, this.posX + (double)(this.rand.nextFloat() / 2.0F), this.posY + (double)(this.getHeight() / 2.0F), this.posZ + (double)(this.rand.nextFloat() / 2.0F), 0.0D, (double)(this.rand.nextFloat() / 5.0F), 0.0D);
               }
            } else {
               Pair<Effect, Integer> pair = this.func_213443_j(itemstack);
               if (!player.playerAbilities.isCreativeMode) {
                  itemstack.shrink(1);
               }

               for(int j = 0; j < 4; ++j) {
                  this.world.addParticle(ParticleTypes.EFFECT, this.posX + (double)(this.rand.nextFloat() / 2.0F), this.posY + (double)(this.getHeight() / 2.0F), this.posZ + (double)(this.rand.nextFloat() / 2.0F), 0.0D, (double)(this.rand.nextFloat() / 5.0F), 0.0D);
               }

               this.field_213450_bA = pair.getLeft();
               this.field_213447_bB = pair.getRight();
               this.playSound(SoundEvents.field_219659_gw, 2.0F, 1.0F);
            }
         }

         return super.processInteract(player, hand);
      }
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      p_213281_1_.putString("Type", this.func_213444_dV().field_221098_c);
      if (this.field_213450_bA != null) {
         p_213281_1_.putByte("EffectId", (byte)Effect.getIdFromPotion(this.field_213450_bA));
         p_213281_1_.putInt("EffectDuration", this.field_213447_bB);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      this.func_213446_a(MooshroomEntity.Type.func_221097_b(compound.getString("Type")));
      if (compound.contains("EffectId", 1)) {
         this.field_213450_bA = Effect.getPotionById(compound.getByte("EffectId"));
      }

      if (compound.contains("EffectDuration", 3)) {
         this.field_213447_bB = compound.getInt("EffectDuration");
      }

   }

   private Pair<Effect, Integer> func_213443_j(ItemStack p_213443_1_) {
      FlowerBlock flowerblock = (FlowerBlock)((BlockItem)p_213443_1_.getItem()).getBlock();
      return Pair.of(flowerblock.func_220094_d(), flowerblock.func_220095_e());
   }

   private void func_213446_a(MooshroomEntity.Type p_213446_1_) {
      this.dataManager.set(field_213449_bz, p_213446_1_.field_221098_c);
   }

   public MooshroomEntity.Type func_213444_dV() {
      return MooshroomEntity.Type.func_221097_b(this.dataManager.get(field_213449_bz));
   }

   public MooshroomEntity createChild(AgeableEntity ageable) {
      MooshroomEntity mooshroomentity = EntityType.MOOSHROOM.create(this.world);
      mooshroomentity.func_213446_a(this.func_213445_a((MooshroomEntity)ageable));
      return mooshroomentity;
   }

   private MooshroomEntity.Type func_213445_a(MooshroomEntity p_213445_1_) {
      MooshroomEntity.Type mooshroomentity$type = this.func_213444_dV();
      MooshroomEntity.Type mooshroomentity$type1 = p_213445_1_.func_213444_dV();
      MooshroomEntity.Type mooshroomentity$type2;
      if (mooshroomentity$type == mooshroomentity$type1 && this.rand.nextInt(1024) == 0) {
         mooshroomentity$type2 = mooshroomentity$type == MooshroomEntity.Type.BROWN ? MooshroomEntity.Type.RED : MooshroomEntity.Type.BROWN;
      } else {
         mooshroomentity$type2 = this.rand.nextBoolean() ? mooshroomentity$type : mooshroomentity$type1;
      }

      return mooshroomentity$type2;
   }

   @Override
   public boolean isShearable(ItemStack item, net.minecraft.world.IWorldReader world, net.minecraft.util.math.BlockPos pos) {
      return this.getGrowingAge() >= 0;
   }

   @Override
   public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IWorld world, net.minecraft.util.math.BlockPos pos, int fortune) {
      java.util.List<ItemStack> ret = new java.util.ArrayList<>();
      this.world.addParticle(ParticleTypes.EXPLOSION, this.posX, this.posY + (double)(this.getHeight() / 2.0F), this.posZ, 0.0D, 0.0D, 0.0D);
      if (!this.world.isRemote) {
         this.remove();
         CowEntity entitycow = EntityType.COW.create(this.world);
         entitycow.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         entitycow.setHealth(this.getHealth());
         entitycow.renderYawOffset = this.renderYawOffset;
         if (this.hasCustomName()) {
            entitycow.setCustomName(this.getCustomName());
         }
         this.world.func_217376_c(entitycow);
         for(int i = 0; i < 5; ++i) {
            ret.add(new ItemStack(Blocks.RED_MUSHROOM));
         }
         this.playSound(SoundEvents.ENTITY_MOOSHROOM_SHEAR, 1.0F, 1.0F);
      }
      return ret;
   }

   public static enum Type {
      RED("red", Blocks.RED_MUSHROOM.getDefaultState()),
      BROWN("brown", Blocks.BROWN_MUSHROOM.getDefaultState());

      private final String field_221098_c;
      private final BlockState field_221099_d;

      private Type(String p_i50425_3_, BlockState p_i50425_4_) {
         this.field_221098_c = p_i50425_3_;
         this.field_221099_d = p_i50425_4_;
      }

      @OnlyIn(Dist.CLIENT)
      public BlockState func_221093_a() {
         return this.field_221099_d;
      }

      private static MooshroomEntity.Type func_221097_b(String p_221097_0_) {
         for(MooshroomEntity.Type mooshroomentity$type : values()) {
            if (mooshroomentity$type.field_221098_c.equals(p_221097_0_)) {
               return mooshroomentity$type;
            }
         }

         return RED;
      }
   }
}