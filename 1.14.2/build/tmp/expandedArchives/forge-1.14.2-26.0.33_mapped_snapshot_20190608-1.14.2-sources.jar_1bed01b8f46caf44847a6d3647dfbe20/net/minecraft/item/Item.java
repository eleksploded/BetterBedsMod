package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Item extends net.minecraftforge.registries.ForgeRegistryEntry<Item> implements IItemProvider, net.minecraftforge.common.extensions.IForgeItem {
   public static final Map<Block, Item> BLOCK_TO_ITEM = net.minecraftforge.registries.GameData.getBlockItemMap();
   private static final IItemPropertyGetter DAMAGED_GETTER = (p_210306_0_, p_210306_1_, p_210306_2_) -> {
      return p_210306_0_.isDamaged() ? 1.0F : 0.0F;
   };
   private static final IItemPropertyGetter DAMAGE_GETTER = (p_210307_0_, p_210307_1_, p_210307_2_) -> {
      return MathHelper.clamp((float)p_210307_0_.getDamage() / (float)p_210307_0_.getMaxDamage(), 0.0F, 1.0F);
   };
   private static final IItemPropertyGetter LEFTHANDED_GETTER = (p_210305_0_, p_210305_1_, p_210305_2_) -> {
      return p_210305_2_ != null && p_210305_2_.getPrimaryHand() != HandSide.RIGHT ? 1.0F : 0.0F;
   };
   private static final IItemPropertyGetter COOLDOWN_GETTER = (p_210308_0_, p_210308_1_, p_210308_2_) -> {
      return p_210308_2_ instanceof PlayerEntity ? ((PlayerEntity)p_210308_2_).getCooldownTracker().getCooldown(p_210308_0_.getItem(), 0.0F) : 0.0F;
   };
   private static final IItemPropertyGetter field_219973_e = (p_219969_0_, p_219969_1_, p_219969_2_) -> {
      return p_219969_0_.hasTag() ? (float)p_219969_0_.getTag().getInt("CustomModelData") : 0.0F;
   };
   protected static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   protected static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
   protected static final Random random = new Random();
   public final Map<ResourceLocation, IItemPropertyGetter> properties = Maps.newHashMap();
   protected final ItemGroup group;
   private final Rarity field_208075_l;
   private final int maxStackSize;
   private final int maxDamage;
   private final Item containerItem;
   @Nullable
   private String translationKey;
   @Nullable
   private final Food field_219974_q;

   public static int getIdFromItem(Item itemIn) {
      return itemIn == null ? 0 : Registry.field_212630_s.getId(itemIn);
   }

   public static Item getItemById(int id) {
      return Registry.field_212630_s.getByValue(id);
   }

   @Deprecated
   public static Item getItemFromBlock(Block blockIn) {
      return BLOCK_TO_ITEM.getOrDefault(blockIn, Items.AIR);
   }

   public Item(Item.Properties properties) {
      this.addPropertyOverride(new ResourceLocation("lefthanded"), LEFTHANDED_GETTER);
      this.addPropertyOverride(new ResourceLocation("cooldown"), COOLDOWN_GETTER);
      this.addPropertyOverride(new ResourceLocation("custom_model_data"), field_219973_e);
      this.group = properties.group;
      this.field_208075_l = properties.field_208104_e;
      this.containerItem = properties.containerItem;
      this.maxDamage = properties.maxDamage;
      this.maxStackSize = properties.maxStackSize;
      this.field_219974_q = properties.field_221541_f;
      if (this.maxDamage > 0) {
         this.addPropertyOverride(new ResourceLocation("damaged"), DAMAGED_GETTER);
         this.addPropertyOverride(new ResourceLocation("damage"), DAMAGE_GETTER);
      }
      this.canRepair = properties.canRepair;
      this.toolClasses.putAll(properties.toolClasses);
      Object tmp = properties.teisr == null ? null : net.minecraftforge.fml.DistExecutor.callWhenOn(Dist.CLIENT, properties.teisr);
      this.teisr = tmp == null ? null : () -> (net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer) tmp;

   }

   public void func_219972_a(World p_219972_1_, LivingEntity p_219972_2_, ItemStack p_219972_3_, int p_219972_4_) {
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public IItemPropertyGetter getPropertyGetter(ResourceLocation key) {
      return this.properties.get(key);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean hasCustomProperties() {
      return !this.properties.isEmpty();
   }

   /**
    * Called when an ItemStack with NBT data is read to potentially that ItemStack's NBT data
    */
   public boolean updateItemStackNBT(CompoundNBT nbt) {
      return false;
   }

   public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
      return true;
   }

   public Item asItem() {
      return this;
   }

   /**
    * Creates a new override param for item models. See usage in clock, compass, elytra, etc.
    */
   public final void addPropertyOverride(ResourceLocation key, IItemPropertyGetter getter) {
      this.properties.put(key, getter);
   }

   /**
    * Called when this item is used when targetting a Block
    */
   public ActionResultType onItemUse(ItemUseContext context) {
      return ActionResultType.PASS;
   }

   public float getDestroySpeed(ItemStack stack, BlockState state) {
      return 1.0F;
   }

   /**
    * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
    * {@link #onItemUse}.
    */
   public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
      if (this.func_219971_r()) {
         ItemStack itemstack = playerIn.getHeldItem(handIn);
         if (playerIn.canEat(this.func_219967_s().func_221468_d())) {
            playerIn.setActiveHand(handIn);
            return new ActionResult<>(ActionResultType.SUCCESS, itemstack);
         } else {
            return new ActionResult<>(ActionResultType.FAIL, itemstack);
         }
      } else {
         return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
      }
   }

   /**
    * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
    * the Item before the action is complete.
    */
   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
      return this.func_219971_r() ? entityLiving.func_213357_a(worldIn, stack) : stack;
   }

   /**
    * Returns the maximum size of the stack for a specific item.
    */
   @Deprecated // Use ItemStack sensitive version.
   public final int getMaxStackSize() {
      return this.maxStackSize;
   }

   /**
    * Returns the maximum damage an item can take.
    */
   @Deprecated // Use ItemStack sensitive version.
   public final int getMaxDamage() {
      return this.maxDamage;
   }

   public boolean isDamageable() {
      return this.maxDamage > 0;
   }

   /**
    * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
    * the damage on the stack.
    */
   public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      return false;
   }

   /**
    * Called when a Block is destroyed using this Item. Return true to trigger the "Use Item" statistic.
    */
   public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
      return false;
   }

   /**
    * Check whether this Item can harvest the given Block
    */
   public boolean canHarvestBlock(BlockState blockIn) {
      return false;
   }

   /**
    * Returns true if the item can be used on the given entity, e.g. shears on sheep.
    */
   public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public ITextComponent getName() {
      return new TranslationTextComponent(this.getTranslationKey());
   }

   protected String getDefaultTranslationKey() {
      if (this.translationKey == null) {
         this.translationKey = Util.makeTranslationKey("item", Registry.field_212630_s.getKey(this));
      }

      return this.translationKey;
   }

   /**
    * Returns the unlocalized name of this item.
    */
   public String getTranslationKey() {
      return this.getDefaultTranslationKey();
   }

   /**
    * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
    * different names based on their damage or NBT.
    */
   public String getTranslationKey(ItemStack stack) {
      return this.getTranslationKey();
   }

   /**
    * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
    */
   public boolean shouldSyncTag() {
      return true;
   }

   @Nullable
   @Deprecated // Use ItemStack sensitive version.
   public final Item getContainerItem() {
      return this.containerItem;
   }

   /**
    * True if this Item has a container item (a.k.a. crafting result)
    */
   @Deprecated // Use ItemStack sensitive version.
   public boolean hasContainerItem() {
      return this.containerItem != null;
   }

   /**
    * Called each tick as long the item is on a player inventory. Uses by maps to check if is on a player hand and
    * update it's contents.
    */
   public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
   }

   /**
    * Called when item is crafted/smelted. Used only by maps so far.
    */
   public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
   }

   /**
    * Returns {@code true} if this is a complex item.
    */
   public boolean isComplex() {
      return false;
   }

   /**
    * returns the action that specifies what animation to play when the items is being used
    */
   public UseAction getUseAction(ItemStack stack) {
      return stack.getItem().func_219971_r() ? UseAction.EAT : UseAction.NONE;
   }

   /**
    * How long it takes to use or consume an item
    */
   public int getUseDuration(ItemStack stack) {
      if (stack.getItem().func_219971_r()) {
         return this.func_219967_s().func_221465_e() ? 16 : 32;
      } else {
         return 0;
      }
   }

   /**
    * Called when the player stops using an Item (stops holding the right mouse button).
    */
   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
   }

   /**
    * allows items to add custom lines of information to the mouseover description
    */
   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
   }

   public ITextComponent getDisplayName(ItemStack stack) {
      return new TranslationTextComponent(this.getTranslationKey(stack));
   }

   /**
    * Returns true if this item has an enchantment glint. By default, this returns <code>stack.isItemEnchanted()</code>,
    * but other items can override it (for instance, written books always return true).
    *  
    * Note that if you override this method, you generally want to also call the super version (on {@link Item}) to get
    * the glint for enchanted items. Of course, that is unnecessary if the overwritten version always returns true.
    */
   @OnlyIn(Dist.CLIENT)
   public boolean hasEffect(ItemStack stack) {
      return stack.isEnchanted();
   }

   /**
    * Return an item rarity from EnumRarity
    */
   public Rarity getRarity(ItemStack stack) {
      if (!stack.isEnchanted()) {
         return this.field_208075_l;
      } else {
         switch(this.field_208075_l) {
         case COMMON:
         case UNCOMMON:
            return Rarity.RARE;
         case RARE:
            return Rarity.EPIC;
         case EPIC:
         default:
            return this.field_208075_l;
         }
      }
   }

   /**
    * Checks isDamagable and if it cannot be stacked
    */
   public boolean isEnchantable(ItemStack stack) {
      return this.getItemStackLimit(stack) == 1 && this.isDamageable();
   }

   protected static RayTraceResult func_219968_a(World p_219968_0_, PlayerEntity p_219968_1_, RayTraceContext.FluidMode p_219968_2_) {
      float f = p_219968_1_.rotationPitch;
      float f1 = p_219968_1_.rotationYaw;
      Vec3d vec3d = p_219968_1_.getEyePosition(1.0F);
      float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
      float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
      float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
      float f6 = f3 * f4;
      float f7 = f2 * f4;
      double d0 = p_219968_1_.getAttribute(PlayerEntity.REACH_DISTANCE).getValue();;
      Vec3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
      return p_219968_0_.func_217299_a(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, p_219968_2_, p_219968_1_));
   }

   /**
    * Return the enchantability factor of the item, most of the time is based on material.
    */
   public int getItemEnchantability() {
      return 0;
   }

   /**
    * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
    */
   public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
      if (this.isInGroup(group)) {
         items.add(new ItemStack(this));
      }

   }

   protected boolean isInGroup(ItemGroup group) {
      if (getCreativeTabs().stream().anyMatch(tab -> tab == group)) return true;
      ItemGroup itemgroup = this.getGroup();
      return itemgroup != null && (group == ItemGroup.SEARCH || group == itemgroup);
   }

   /**
    * gets the CreativeTab this item is displayed on
    */
   @Nullable
   public final ItemGroup getGroup() {
      return this.group;
   }

   /**
    * Return whether this item is repairable in an anvil.
    */
   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
      return false;
   }

   /**
    * Gets a map of item attribute modifiers, used by ItemSword to increase hit damage.
    */
   @Deprecated // Use ItemStack sensitive version.
   public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
      return HashMultimap.create();
   }

   @Nullable
   private final java.util.function.Supplier<net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer> teisr;
   private final java.util.Map<net.minecraftforge.common.ToolType, Integer> toolClasses = Maps.newHashMap();
   private final net.minecraftforge.common.util.ReverseTagWrapper<Item> reverseTags = new net.minecraftforge.common.util.ReverseTagWrapper<>(this, net.minecraft.tags.ItemTags::getGeneration, net.minecraft.tags.ItemTags::getCollection);
   protected final boolean canRepair;

   @Override
   public boolean isRepairable() {
     return canRepair && isDamageable();
   }

   @Override
   public java.util.Set<net.minecraftforge.common.ToolType> getToolTypes(ItemStack stack) {
     return toolClasses.keySet();
   }

   @Override
   public int getHarvestLevel(ItemStack stack, net.minecraftforge.common.ToolType tool, @Nullable PlayerEntity player, @Nullable BlockState blockState) {
     return toolClasses.getOrDefault(tool, -1);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public final net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer getTileEntityItemStackRenderer() {
     net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer renderer = teisr != null ? teisr.get() : null;
     return renderer != null ? renderer : net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer.field_147719_a;
   }

   @Override
   public java.util.Set<ResourceLocation> getTags() {
      return reverseTags.getTagNames();
   }

   public boolean func_219970_i(ItemStack p_219970_1_) {
      return p_219970_1_.getItem() == Items.field_222114_py;
   }

   @OnlyIn(Dist.CLIENT)
   public ItemStack getDefaultInstance() {
      return new ItemStack(this);
   }

   public boolean isIn(Tag<Item> tagIn) {
      return tagIn.contains(this);
   }

   public boolean func_219971_r() {
      return this.field_219974_q != null;
   }

   @Nullable
   public Food func_219967_s() {
      return this.field_219974_q;
   }

   public static class Properties {
      private int maxStackSize = 64;
      private int maxDamage;
      private Item containerItem;
      private ItemGroup group;
      private Rarity field_208104_e = Rarity.COMMON;
      private Food field_221541_f;
      private boolean canRepair = true;
      private java.util.Map<net.minecraftforge.common.ToolType, Integer> toolClasses = Maps.newHashMap();
      private java.util.function.Supplier<java.util.concurrent.Callable<net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer>> teisr;

      public Item.Properties func_221540_a(Food p_221540_1_) {
         this.field_221541_f = p_221540_1_;
         return this;
      }

      public Item.Properties maxStackSize(int maxStackSizeIn) {
         if (this.maxDamage > 0) {
            throw new RuntimeException("Unable to have damage AND stack.");
         } else {
            this.maxStackSize = maxStackSizeIn;
            return this;
         }
      }

      public Item.Properties defaultMaxDamage(int maxDamageIn) {
         return this.maxDamage == 0 ? this.maxDamage(maxDamageIn) : this;
      }

      public Item.Properties maxDamage(int maxDamageIn) {
         this.maxDamage = maxDamageIn;
         this.maxStackSize = 1;
         return this;
      }

      public Item.Properties containerItem(Item containerItemIn) {
         this.containerItem = containerItemIn;
         return this;
      }

      public Item.Properties group(ItemGroup groupIn) {
         this.group = groupIn;
         return this;
      }

      public Item.Properties rarity(Rarity rarityIn) {
         this.field_208104_e = rarityIn;
         return this;
      }

      public Item.Properties setNoRepair() {
         canRepair = false;
         return this;
      }

      public Item.Properties addToolType(net.minecraftforge.common.ToolType type, int level) {
         toolClasses.put(type, level);
         return this;
      }

      public Item.Properties setTEISR(java.util.function.Supplier<java.util.concurrent.Callable<net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer>> teisr) {
         this.teisr = teisr;
         return this;
      }
   }
}