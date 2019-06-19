package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.IRecipeHolder;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public abstract class AbstractFurnaceTileEntity extends LockableTileEntity implements ISidedInventory, IRecipeHolder, IRecipeHelperPopulator, ITickableTileEntity {
   private static final int[] field_214015_g = new int[]{0};
   private static final int[] field_214016_h = new int[]{2, 1};
   private static final int[] field_214017_i = new int[]{1};
   protected NonNullList<ItemStack> field_214012_a = NonNullList.withSize(3, ItemStack.EMPTY);
   private int field_214018_j;
   private int field_214019_k;
   private int field_214020_l;
   private int field_214021_m;
   protected final IIntArray field_214013_b = new IIntArray() {
      public int func_221476_a(int p_221476_1_) {
         switch(p_221476_1_) {
         case 0:
            return AbstractFurnaceTileEntity.this.field_214018_j;
         case 1:
            return AbstractFurnaceTileEntity.this.field_214019_k;
         case 2:
            return AbstractFurnaceTileEntity.this.field_214020_l;
         case 3:
            return AbstractFurnaceTileEntity.this.field_214021_m;
         default:
            return 0;
         }
      }

      public void func_221477_a(int p_221477_1_, int p_221477_2_) {
         switch(p_221477_1_) {
         case 0:
            AbstractFurnaceTileEntity.this.field_214018_j = p_221477_2_;
            break;
         case 1:
            AbstractFurnaceTileEntity.this.field_214019_k = p_221477_2_;
            break;
         case 2:
            AbstractFurnaceTileEntity.this.field_214020_l = p_221477_2_;
            break;
         case 3:
            AbstractFurnaceTileEntity.this.field_214021_m = p_221477_2_;
         }

      }

      public int func_221478_a() {
         return 4;
      }
   };
   private final Map<ResourceLocation, Integer> field_214022_n = Maps.newHashMap();
   protected final IRecipeType<? extends AbstractCookingRecipe> field_214014_c;

   protected AbstractFurnaceTileEntity(TileEntityType<?> p_i49964_1_, IRecipeType<? extends AbstractCookingRecipe> p_i49964_2_) {
      super(p_i49964_1_);
      this.field_214014_c = p_i49964_2_;
   }

   public static Map<Item, Integer> func_214001_f() {
      Map<Item, Integer> map = Maps.newLinkedHashMap();
      func_213996_a(map, Items.LAVA_BUCKET, 20000);
      func_213996_a(map, Blocks.COAL_BLOCK, 16000);
      func_213996_a(map, Items.BLAZE_ROD, 2400);
      func_213996_a(map, Items.COAL, 1600);
      func_213996_a(map, Items.CHARCOAL, 1600);
      func_213992_a(map, ItemTags.LOGS, 300);
      func_213992_a(map, ItemTags.PLANKS, 300);
      func_213992_a(map, ItemTags.WOODEN_STAIRS, 300);
      func_213992_a(map, ItemTags.WOODEN_SLABS, 150);
      func_213992_a(map, ItemTags.WOODEN_TRAPDOORS, 300);
      func_213992_a(map, ItemTags.WOODEN_PRESSURE_PLATES, 300);
      func_213996_a(map, Blocks.OAK_FENCE, 300);
      func_213996_a(map, Blocks.BIRCH_FENCE, 300);
      func_213996_a(map, Blocks.SPRUCE_FENCE, 300);
      func_213996_a(map, Blocks.JUNGLE_FENCE, 300);
      func_213996_a(map, Blocks.DARK_OAK_FENCE, 300);
      func_213996_a(map, Blocks.ACACIA_FENCE, 300);
      func_213996_a(map, Blocks.OAK_FENCE_GATE, 300);
      func_213996_a(map, Blocks.BIRCH_FENCE_GATE, 300);
      func_213996_a(map, Blocks.SPRUCE_FENCE_GATE, 300);
      func_213996_a(map, Blocks.JUNGLE_FENCE_GATE, 300);
      func_213996_a(map, Blocks.DARK_OAK_FENCE_GATE, 300);
      func_213996_a(map, Blocks.ACACIA_FENCE_GATE, 300);
      func_213996_a(map, Blocks.NOTE_BLOCK, 300);
      func_213996_a(map, Blocks.BOOKSHELF, 300);
      func_213996_a(map, Blocks.field_222428_lQ, 300);
      func_213996_a(map, Blocks.JUKEBOX, 300);
      func_213996_a(map, Blocks.CHEST, 300);
      func_213996_a(map, Blocks.TRAPPED_CHEST, 300);
      func_213996_a(map, Blocks.CRAFTING_TABLE, 300);
      func_213996_a(map, Blocks.DAYLIGHT_DETECTOR, 300);
      func_213992_a(map, ItemTags.BANNERS, 300);
      func_213996_a(map, Items.BOW, 300);
      func_213996_a(map, Items.FISHING_ROD, 300);
      func_213996_a(map, Blocks.LADDER, 300);
      func_213992_a(map, ItemTags.field_219773_J, 200);
      func_213996_a(map, Items.WOODEN_SHOVEL, 200);
      func_213996_a(map, Items.WOODEN_SWORD, 200);
      func_213996_a(map, Items.WOODEN_HOE, 200);
      func_213996_a(map, Items.WOODEN_AXE, 200);
      func_213996_a(map, Items.WOODEN_PICKAXE, 200);
      func_213992_a(map, ItemTags.WOODEN_DOORS, 200);
      func_213992_a(map, ItemTags.BOATS, 200);
      func_213992_a(map, ItemTags.WOOL, 100);
      func_213992_a(map, ItemTags.WOODEN_BUTTONS, 100);
      func_213996_a(map, Items.STICK, 100);
      func_213992_a(map, ItemTags.SAPLINGS, 100);
      func_213996_a(map, Items.BOWL, 100);
      func_213992_a(map, ItemTags.CARPETS, 67);
      func_213996_a(map, Blocks.DRIED_KELP_BLOCK, 4001);
      func_213996_a(map, Items.field_222114_py, 300);
      func_213996_a(map, Blocks.field_222405_kQ, 50);
      func_213996_a(map, Blocks.DEAD_BUSH, 100);
      func_213996_a(map, Blocks.field_222420_lI, 50);
      func_213996_a(map, Blocks.field_222421_lJ, 300);
      func_213996_a(map, Blocks.field_222422_lK, 300);
      func_213996_a(map, Blocks.field_222425_lN, 300);
      func_213996_a(map, Blocks.field_222426_lO, 300);
      func_213996_a(map, Blocks.field_222429_lR, 300);
      func_213996_a(map, Blocks.field_222436_lZ, 300);
      return map;
   }

   private static void func_213992_a(Map<Item, Integer> p_213992_0_, Tag<Item> p_213992_1_, int p_213992_2_) {
      for(Item item : p_213992_1_.getAllElements()) {
         p_213992_0_.put(item, p_213992_2_);
      }

   }

   private static void func_213996_a(Map<Item, Integer> p_213996_0_, IItemProvider p_213996_1_, int p_213996_2_) {
      p_213996_0_.put(p_213996_1_.asItem(), p_213996_2_);
   }

   private boolean func_214006_r() {
      return this.field_214018_j > 0;
   }

   public void read(CompoundNBT compound) {
      super.read(compound);
      this.field_214012_a = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
      ItemStackHelper.loadAllItems(compound, this.field_214012_a);
      this.field_214018_j = compound.getInt("BurnTime");
      this.field_214020_l = compound.getInt("CookTime");
      this.field_214021_m = compound.getInt("CookTimeTotal");
      this.field_214019_k = this.getBurnTime(this.field_214012_a.get(1));
      int i = compound.getShort("RecipesUsedSize");

      for(int j = 0; j < i; ++j) {
         ResourceLocation resourcelocation = new ResourceLocation(compound.getString("RecipeLocation" + j));
         int k = compound.getInt("RecipeAmount" + j);
         this.field_214022_n.put(resourcelocation, k);
      }

   }

   public CompoundNBT write(CompoundNBT compound) {
      super.write(compound);
      compound.putInt("BurnTime", this.field_214018_j);
      compound.putInt("CookTime", this.field_214020_l);
      compound.putInt("CookTimeTotal", this.field_214021_m);
      ItemStackHelper.saveAllItems(compound, this.field_214012_a);
      compound.putShort("RecipesUsedSize", (short)this.field_214022_n.size());
      int i = 0;

      for(Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
         compound.putString("RecipeLocation" + i, entry.getKey().toString());
         compound.putInt("RecipeAmount" + i, entry.getValue());
         ++i;
      }

      return compound;
   }

   public void tick() {
      boolean flag = this.func_214006_r();
      boolean flag1 = false;
      if (this.func_214006_r()) {
         --this.field_214018_j;
      }

      if (!this.world.isRemote) {
         ItemStack itemstack = this.field_214012_a.get(1);
         if (this.func_214006_r() || !itemstack.isEmpty() && !this.field_214012_a.get(0).isEmpty()) {
            IRecipe<?> irecipe = this.world.getRecipeManager().func_215371_a((IRecipeType<AbstractCookingRecipe>)this.field_214014_c, this, this.world).orElse(null);
            if (!this.func_214006_r() && this.func_214008_b(irecipe)) {
               this.field_214018_j = this.getBurnTime(itemstack);
               this.field_214019_k = this.field_214018_j;
               if (this.func_214006_r()) {
                  flag1 = true;
                  if (itemstack.hasContainerItem())
                      this.field_214012_a.set(1, itemstack.getContainerItem());
                  else
                  if (!itemstack.isEmpty()) {
                     Item item = itemstack.getItem();
                     itemstack.shrink(1);
                     if (itemstack.isEmpty()) {
                        this.field_214012_a.set(1, itemstack.getContainerItem());
                     }
                  }
               }
            }

            if (this.func_214006_r() && this.func_214008_b(irecipe)) {
               ++this.field_214020_l;
               if (this.field_214020_l == this.field_214021_m) {
                  this.field_214020_l = 0;
                  this.field_214021_m = this.func_214005_h();
                  this.func_214007_c(irecipe);
                  flag1 = true;
               }
            } else {
               this.field_214020_l = 0;
            }
         } else if (!this.func_214006_r() && this.field_214020_l > 0) {
            this.field_214020_l = MathHelper.clamp(this.field_214020_l - 2, 0, this.field_214021_m);
         }

         if (flag != this.func_214006_r()) {
            flag1 = true;
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.field_220091_b, Boolean.valueOf(this.func_214006_r())), 3);
         }
      }

      if (flag1) {
         this.markDirty();
      }

   }

   protected boolean func_214008_b(@Nullable IRecipe<?> p_214008_1_) {
      if (!this.field_214012_a.get(0).isEmpty() && p_214008_1_ != null) {
         ItemStack itemstack = p_214008_1_.getRecipeOutput();
         if (itemstack.isEmpty()) {
            return false;
         } else {
            ItemStack itemstack1 = this.field_214012_a.get(2);
            if (itemstack1.isEmpty()) {
               return true;
            } else if (!itemstack1.isItemEqual(itemstack)) {
               return false;
            } else if (itemstack1.getCount() + itemstack.getCount() <= this.getInventoryStackLimit() && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) { // Forge fix: make furnace respect stack sizes in furnace recipes
               return true;
            } else {
               return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize(); // Forge fix: make furnace respect stack sizes in furnace recipes
            }
         }
      } else {
         return false;
      }
   }

   private void func_214007_c(@Nullable IRecipe<?> p_214007_1_) {
      if (p_214007_1_ != null && this.func_214008_b(p_214007_1_)) {
         ItemStack itemstack = this.field_214012_a.get(0);
         ItemStack itemstack1 = p_214007_1_.getRecipeOutput();
         ItemStack itemstack2 = this.field_214012_a.get(2);
         if (itemstack2.isEmpty()) {
            this.field_214012_a.set(2, itemstack1.copy());
         } else if (itemstack2.getItem() == itemstack1.getItem()) {
            itemstack2.grow(itemstack1.getCount());
         }

         if (!this.world.isRemote) {
            this.setRecipeUsed(p_214007_1_);
         }

         if (itemstack.getItem() == Blocks.WET_SPONGE.asItem() && !this.field_214012_a.get(1).isEmpty() && this.field_214012_a.get(1).getItem() == Items.BUCKET) {
            this.field_214012_a.set(1, new ItemStack(Items.WATER_BUCKET));
         }

         itemstack.shrink(1);
      }
   }

   protected int getBurnTime(ItemStack p_213997_1_) {
      if (p_213997_1_.isEmpty()) {
         return 0;
      } else {
         Item item = p_213997_1_.getItem();
         int ret = p_213997_1_.getBurnTime();
         return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(p_213997_1_, ret == -1 ? func_214001_f().getOrDefault(item, 0) : ret);
      }
   }

   protected int func_214005_h() {
      return this.world.getRecipeManager().func_215371_a((IRecipeType<AbstractCookingRecipe>)this.field_214014_c, this, this.world).map(AbstractCookingRecipe::func_222137_e).orElse(200);
   }

   public static boolean func_213991_b(ItemStack p_213991_0_) {
      int ret = p_213991_0_.getBurnTime();
      return net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(p_213991_0_, ret == -1 ? func_214001_f().getOrDefault(p_213991_0_.getItem(), 0) : ret) > 0;
   }

   public int[] getSlotsForFace(Direction side) {
      if (side == Direction.DOWN) {
         return field_214016_h;
      } else {
         return side == Direction.UP ? field_214015_g : field_214017_i;
      }
   }

   /**
    * Returns true if automation can insert the given item in the given slot from the given side.
    */
   public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
      return this.isItemValidForSlot(index, itemStackIn);
   }

   /**
    * Returns true if automation can extract the given item in the given slot from the given side.
    */
   public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
      if (direction == Direction.DOWN && index == 1) {
         Item item = stack.getItem();
         if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
            return false;
         }
      }

      return true;
   }

   /**
    * Returns the number of slots in the inventory.
    */
   public int getSizeInventory() {
      return this.field_214012_a.size();
   }

   public boolean isEmpty() {
      for(ItemStack itemstack : this.field_214012_a) {
         if (!itemstack.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   /**
    * Returns the stack in the given slot.
    */
   public ItemStack getStackInSlot(int index) {
      return this.field_214012_a.get(index);
   }

   /**
    * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
    */
   public ItemStack decrStackSize(int index, int count) {
      return ItemStackHelper.getAndSplit(this.field_214012_a, index, count);
   }

   /**
    * Removes a stack from the given slot and returns it.
    */
   public ItemStack removeStackFromSlot(int index) {
      return ItemStackHelper.getAndRemove(this.field_214012_a, index);
   }

   /**
    * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
    */
   public void setInventorySlotContents(int index, ItemStack stack) {
      ItemStack itemstack = this.field_214012_a.get(index);
      boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
      this.field_214012_a.set(index, stack);
      if (stack.getCount() > this.getInventoryStackLimit()) {
         stack.setCount(this.getInventoryStackLimit());
      }

      if (index == 0 && !flag) {
         this.field_214021_m = this.func_214005_h();
         this.field_214020_l = 0;
         this.markDirty();
      }

   }

   /**
    * Don't rename this method to canInteractWith due to conflicts with Container
    */
   public boolean isUsableByPlayer(PlayerEntity player) {
      if (this.world.getTileEntity(this.pos) != this) {
         return false;
      } else {
         return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
      }
   }

   /**
    * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
    * guis use Slot.isItemValid
    */
   public boolean isItemValidForSlot(int index, ItemStack stack) {
      if (index == 2) {
         return false;
      } else if (index != 1) {
         return true;
      } else {
         ItemStack itemstack = this.field_214012_a.get(1);
         return func_213991_b(stack) || stack.getItem() == Items.BUCKET && itemstack.getItem() != Items.BUCKET;
      }
   }

   public void clear() {
      this.field_214012_a.clear();
   }

   public void setRecipeUsed(@Nullable IRecipe<?> recipe) {
      if (recipe != null) {
         this.field_214022_n.compute(recipe.getId(), (p_214004_0_, p_214004_1_) -> {
            return 1 + (p_214004_1_ == null ? 0 : p_214004_1_);
         });
      }

   }

   @Nullable
   public IRecipe<?> getRecipeUsed() {
      return null;
   }

   public void onCrafting(PlayerEntity player) {
   }

   public void func_213995_d(PlayerEntity p_213995_1_) {
      List<IRecipe<?>> list = Lists.newArrayList();

      for(Entry<ResourceLocation, Integer> entry : this.field_214022_n.entrySet()) {
         p_213995_1_.world.getRecipeManager().func_215367_a(entry.getKey()).ifPresent((p_213993_3_) -> {
            list.add(p_213993_3_);
            func_214003_a(p_213995_1_, entry.getValue(), ((AbstractCookingRecipe)p_213993_3_).func_222138_b());
         });
      }

      p_213995_1_.unlockRecipes(list);
      this.field_214022_n.clear();
   }

   private static void func_214003_a(PlayerEntity p_214003_0_, int p_214003_1_, float p_214003_2_) {
      if (p_214003_2_ == 0.0F) {
         p_214003_1_ = 0;
      } else if (p_214003_2_ < 1.0F) {
         int i = MathHelper.floor((float)p_214003_1_ * p_214003_2_);
         if (i < MathHelper.ceil((float)p_214003_1_ * p_214003_2_) && Math.random() < (double)((float)p_214003_1_ * p_214003_2_ - (float)i)) {
            ++i;
         }

         p_214003_1_ = i;
      }

      while(p_214003_1_ > 0) {
         int j = ExperienceOrbEntity.getXPSplit(p_214003_1_);
         p_214003_1_ -= j;
         p_214003_0_.world.func_217376_c(new ExperienceOrbEntity(p_214003_0_.world, p_214003_0_.posX, p_214003_0_.posY + 0.5D, p_214003_0_.posZ + 0.5D, j));
      }

   }

   public void fillStackedContents(RecipeItemHelper helper) {
      for(ItemStack itemstack : this.field_214012_a) {
         helper.accountStack(itemstack);
      }

   }

   net.minecraftforge.common.util.LazyOptional<? extends net.minecraftforge.items.IItemHandler>[] handlers =
           net.minecraftforge.items.wrapper.SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
      if (!this.removed && facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == Direction.UP)
            return handlers[0].cast();
         else if (facing == Direction.DOWN)
            return handlers[1].cast();
         else
            return handlers[2].cast();
      }
      return super.getCapability(capability, facing);
   }

   /**
    * invalidates a tile entity
    */
   @Override
   public void remove() {
      super.remove();
      for (int x = 0; x < handlers.length; x++)
        handlers[x].invalidate();
   }
}