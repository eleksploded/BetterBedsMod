package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(
   value = Dist.CLIENT,
   _interface = IRendersAsItem.class
)
public abstract class ProjectileItemEntity extends ThrowableEntity implements IRendersAsItem {
   private static final DataParameter<ItemStack> field_213886_e = EntityDataManager.createKey(ProjectileItemEntity.class, DataSerializers.field_187196_f);

   public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> p_i50155_1_, World p_i50155_2_) {
      super(p_i50155_1_, p_i50155_2_);
   }

   public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> p_i50156_1_, double p_i50156_2_, double p_i50156_4_, double p_i50156_6_, World p_i50156_8_) {
      super(p_i50156_1_, p_i50156_2_, p_i50156_4_, p_i50156_6_, p_i50156_8_);
   }

   public ProjectileItemEntity(EntityType<? extends ProjectileItemEntity> p_i50157_1_, LivingEntity p_i50157_2_, World p_i50157_3_) {
      super(p_i50157_1_, p_i50157_2_, p_i50157_3_);
   }

   public void func_213884_b(ItemStack p_213884_1_) {
      if (p_213884_1_.getItem() != this.func_213885_i() || p_213884_1_.hasTag()) {
         this.getDataManager().set(field_213886_e, Util.make(p_213884_1_.copy(), (p_213883_0_) -> {
            p_213883_0_.setCount(1);
         }));
      }

   }

   protected abstract Item func_213885_i();

   protected ItemStack func_213882_k() {
      return this.getDataManager().get(field_213886_e);
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getPotion() {
      ItemStack itemstack = this.func_213882_k();
      return itemstack.isEmpty() ? new ItemStack(this.func_213885_i()) : itemstack;
   }

   protected void registerData() {
      this.getDataManager().register(field_213886_e, ItemStack.EMPTY);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
      super.writeAdditional(p_213281_1_);
      ItemStack itemstack = this.func_213882_k();
      if (!itemstack.isEmpty()) {
         p_213281_1_.put("Item", itemstack.write(new CompoundNBT()));
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      super.readAdditional(compound);
      ItemStack itemstack = ItemStack.read(compound.getCompound("Item"));
      this.func_213884_b(itemstack);
   }
}