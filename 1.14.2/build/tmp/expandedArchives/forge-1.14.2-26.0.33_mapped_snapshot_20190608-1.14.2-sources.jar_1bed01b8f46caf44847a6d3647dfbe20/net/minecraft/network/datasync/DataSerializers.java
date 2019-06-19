package net.minecraft.network.datasync;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Pose;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.IntIdentityHashBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;

public class DataSerializers {
   private static final IntIdentityHashBiMap<IDataSerializer<?>> REGISTRY = new IntIdentityHashBiMap<>(16);
   public static final IDataSerializer<Byte> field_187191_a = new IDataSerializer<Byte>() {
      public void write(PacketBuffer buf, Byte value) {
         buf.writeByte(value);
      }

      public Byte read(PacketBuffer buf) {
         return buf.readByte();
      }

      public Byte copyValue(Byte value) {
         return value;
      }
   };
   public static final IDataSerializer<Integer> field_187192_b = new IDataSerializer<Integer>() {
      public void write(PacketBuffer buf, Integer value) {
         buf.writeVarInt(value);
      }

      public Integer read(PacketBuffer buf) {
         return buf.readVarInt();
      }

      public Integer copyValue(Integer value) {
         return value;
      }
   };
   public static final IDataSerializer<Float> field_187193_c = new IDataSerializer<Float>() {
      public void write(PacketBuffer buf, Float value) {
         buf.writeFloat(value);
      }

      public Float read(PacketBuffer buf) {
         return buf.readFloat();
      }

      public Float copyValue(Float value) {
         return value;
      }
   };
   public static final IDataSerializer<String> field_187194_d = new IDataSerializer<String>() {
      public void write(PacketBuffer buf, String value) {
         buf.writeString(value);
      }

      public String read(PacketBuffer buf) {
         return buf.readString(32767);
      }

      public String copyValue(String value) {
         return value;
      }
   };
   public static final IDataSerializer<ITextComponent> field_187195_e = new IDataSerializer<ITextComponent>() {
      public void write(PacketBuffer buf, ITextComponent value) {
         buf.writeTextComponent(value);
      }

      public ITextComponent read(PacketBuffer buf) {
         return buf.readTextComponent();
      }

      public ITextComponent copyValue(ITextComponent value) {
         return value.deepCopy();
      }
   };
   public static final IDataSerializer<Optional<ITextComponent>> field_200544_f = new IDataSerializer<Optional<ITextComponent>>() {
      public void write(PacketBuffer buf, Optional<ITextComponent> value) {
         if (value.isPresent()) {
            buf.writeBoolean(true);
            buf.writeTextComponent(value.get());
         } else {
            buf.writeBoolean(false);
         }

      }

      public Optional<ITextComponent> read(PacketBuffer buf) {
         return buf.readBoolean() ? Optional.of(buf.readTextComponent()) : Optional.empty();
      }

      public Optional<ITextComponent> copyValue(Optional<ITextComponent> value) {
         return value.isPresent() ? Optional.of(value.get().deepCopy()) : Optional.empty();
      }
   };
   public static final IDataSerializer<ItemStack> field_187196_f = new IDataSerializer<ItemStack>() {
      public void write(PacketBuffer buf, ItemStack value) {
         buf.writeItemStack(value);
      }

      public ItemStack read(PacketBuffer buf) {
         return buf.readItemStack();
      }

      public ItemStack copyValue(ItemStack value) {
         return value.copy();
      }
   };
   public static final IDataSerializer<Optional<BlockState>> field_187197_g = new IDataSerializer<Optional<BlockState>>() {
      public void write(PacketBuffer buf, Optional<BlockState> value) {
         if (value.isPresent()) {
            buf.writeVarInt(Block.getStateId(value.get()));
         } else {
            buf.writeVarInt(0);
         }

      }

      public Optional<BlockState> read(PacketBuffer buf) {
         int i = buf.readVarInt();
         return i == 0 ? Optional.empty() : Optional.of(Block.getStateById(i));
      }

      public Optional<BlockState> copyValue(Optional<BlockState> value) {
         return value;
      }
   };
   public static final IDataSerializer<Boolean> field_187198_h = new IDataSerializer<Boolean>() {
      public void write(PacketBuffer buf, Boolean value) {
         buf.writeBoolean(value);
      }

      public Boolean read(PacketBuffer buf) {
         return buf.readBoolean();
      }

      public Boolean copyValue(Boolean value) {
         return value;
      }
   };
   public static final IDataSerializer<IParticleData> field_198166_i = new IDataSerializer<IParticleData>() {
      public void write(PacketBuffer buf, IParticleData value) {
         buf.writeVarInt(Registry.field_212632_u.getId(value.getType()));
         value.write(buf);
      }

      public IParticleData read(PacketBuffer buf) {
         return this.read(buf, Registry.field_212632_u.getByValue(buf.readVarInt()));
      }

      private <T extends IParticleData> T read(PacketBuffer p_200543_1_, ParticleType<T> p_200543_2_) {
         return p_200543_2_.getDeserializer().read(p_200543_2_, p_200543_1_);
      }

      public IParticleData copyValue(IParticleData value) {
         return value;
      }
   };
   public static final IDataSerializer<Rotations> field_187199_i = new IDataSerializer<Rotations>() {
      public void write(PacketBuffer buf, Rotations value) {
         buf.writeFloat(value.getX());
         buf.writeFloat(value.getY());
         buf.writeFloat(value.getZ());
      }

      public Rotations read(PacketBuffer buf) {
         return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
      }

      public Rotations copyValue(Rotations value) {
         return value;
      }
   };
   public static final IDataSerializer<BlockPos> field_187200_j = new IDataSerializer<BlockPos>() {
      public void write(PacketBuffer buf, BlockPos value) {
         buf.writeBlockPos(value);
      }

      public BlockPos read(PacketBuffer buf) {
         return buf.readBlockPos();
      }

      public BlockPos copyValue(BlockPos value) {
         return value;
      }
   };
   public static final IDataSerializer<Optional<BlockPos>> field_187201_k = new IDataSerializer<Optional<BlockPos>>() {
      public void write(PacketBuffer buf, Optional<BlockPos> value) {
         buf.writeBoolean(value.isPresent());
         if (value.isPresent()) {
            buf.writeBlockPos(value.get());
         }

      }

      public Optional<BlockPos> read(PacketBuffer buf) {
         return !buf.readBoolean() ? Optional.empty() : Optional.of(buf.readBlockPos());
      }

      public Optional<BlockPos> copyValue(Optional<BlockPos> value) {
         return value;
      }
   };
   public static final IDataSerializer<Direction> field_187202_l = new IDataSerializer<Direction>() {
      public void write(PacketBuffer buf, Direction value) {
         buf.writeEnumValue(value);
      }

      public Direction read(PacketBuffer buf) {
         return buf.readEnumValue(Direction.class);
      }

      public Direction copyValue(Direction value) {
         return value;
      }
   };
   public static final IDataSerializer<Optional<UUID>> field_187203_m = new IDataSerializer<Optional<UUID>>() {
      public void write(PacketBuffer buf, Optional<UUID> value) {
         buf.writeBoolean(value.isPresent());
         if (value.isPresent()) {
            buf.writeUniqueId(value.get());
         }

      }

      public Optional<UUID> read(PacketBuffer buf) {
         return !buf.readBoolean() ? Optional.empty() : Optional.of(buf.readUniqueId());
      }

      public Optional<UUID> copyValue(Optional<UUID> value) {
         return value;
      }
   };
   public static final IDataSerializer<CompoundNBT> field_192734_n = new IDataSerializer<CompoundNBT>() {
      public void write(PacketBuffer buf, CompoundNBT value) {
         buf.writeCompoundTag(value);
      }

      public CompoundNBT read(PacketBuffer buf) {
         return buf.readCompoundTag();
      }

      public CompoundNBT copyValue(CompoundNBT value) {
         return value.copy();
      }
   };
   public static final IDataSerializer<VillagerData> field_218813_q = new IDataSerializer<VillagerData>() {
      public void write(PacketBuffer buf, VillagerData value) {
         buf.writeVarInt(Registry.field_218369_K.getId(value.getType()));
         buf.writeVarInt(Registry.field_218370_L.getId(value.getProfession()));
         buf.writeVarInt(value.getLevel());
      }

      public VillagerData read(PacketBuffer buf) {
         return new VillagerData(Registry.field_218369_K.getByValue(buf.readVarInt()), Registry.field_218370_L.getByValue(buf.readVarInt()), buf.readVarInt());
      }

      public VillagerData copyValue(VillagerData value) {
         return value;
      }
   };
   public static final IDataSerializer<OptionalInt> field_218814_r = new IDataSerializer<OptionalInt>() {
      public void write(PacketBuffer buf, OptionalInt value) {
         buf.writeVarInt(value.orElse(-1) + 1);
      }

      public OptionalInt read(PacketBuffer buf) {
         int i = buf.readVarInt();
         return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
      }

      public OptionalInt copyValue(OptionalInt value) {
         return value;
      }
   };
   public static final IDataSerializer<Pose> field_218815_s = new IDataSerializer<Pose>() {
      public void write(PacketBuffer buf, Pose value) {
         buf.writeEnumValue(value);
      }

      public Pose read(PacketBuffer buf) {
         return buf.readEnumValue(Pose.class);
      }

      public Pose copyValue(Pose value) {
         return value;
      }
   };

   public static void registerSerializer(IDataSerializer<?> serializer) {
      if (REGISTRY.add(serializer) >= 256) throw new RuntimeException("Vanilla DataSerializer ID limit exceeded");
   }

   @Nullable
   public static IDataSerializer<?> getSerializer(int id) {
      return net.minecraftforge.common.ForgeHooks.getSerializer(id, REGISTRY);
   }

   public static int getSerializerId(IDataSerializer<?> serializer) {
      return net.minecraftforge.common.ForgeHooks.getSerializerId(serializer, REGISTRY);
   }

   static {
      registerSerializer(field_187191_a);
      registerSerializer(field_187192_b);
      registerSerializer(field_187193_c);
      registerSerializer(field_187194_d);
      registerSerializer(field_187195_e);
      registerSerializer(field_200544_f);
      registerSerializer(field_187196_f);
      registerSerializer(field_187198_h);
      registerSerializer(field_187199_i);
      registerSerializer(field_187200_j);
      registerSerializer(field_187201_k);
      registerSerializer(field_187202_l);
      registerSerializer(field_187203_m);
      registerSerializer(field_187197_g);
      registerSerializer(field_192734_n);
      registerSerializer(field_198166_i);
      registerSerializer(field_218813_q);
      registerSerializer(field_218814_r);
      registerSerializer(field_218815_s);
   }
}