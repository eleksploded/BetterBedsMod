package net.minecraft.command.arguments;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.command.arguments.serializers.BrigadierSerializers;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentTypes {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Map<Class<?>, ArgumentTypes.Entry<?>> CLASS_TYPE_MAP = Maps.newHashMap();
   private static final Map<ResourceLocation, ArgumentTypes.Entry<?>> ID_TYPE_MAP = Maps.newHashMap();

   public static <T extends ArgumentType<?>> void func_218136_a(String p_218136_0_, Class<T> p_218136_1_, IArgumentSerializer<T> p_218136_2_) {
      ResourceLocation resourcelocation = new ResourceLocation(p_218136_0_);
      if (CLASS_TYPE_MAP.containsKey(p_218136_1_)) {
         throw new IllegalArgumentException("Class " + p_218136_1_.getName() + " already has a serializer!");
      } else if (ID_TYPE_MAP.containsKey(resourcelocation)) {
         throw new IllegalArgumentException("'" + resourcelocation + "' is already a registered serializer!");
      } else {
         ArgumentTypes.Entry<T> entry = new ArgumentTypes.Entry<>(p_218136_1_, p_218136_2_, resourcelocation);
         CLASS_TYPE_MAP.put(p_218136_1_, entry);
         ID_TYPE_MAP.put(resourcelocation, entry);
      }
   }

   public static void registerArgumentTypes() {
      BrigadierSerializers.registerArgumentTypes();
      func_218136_a("entity", EntityArgument.class, new EntityArgument.Serializer());
      func_218136_a("game_profile", GameProfileArgument.class, new ArgumentSerializer<>(GameProfileArgument::gameProfile));
      func_218136_a("block_pos", BlockPosArgument.class, new ArgumentSerializer<>(BlockPosArgument::blockPos));
      func_218136_a("column_pos", ColumnPosArgument.class, new ArgumentSerializer<>(ColumnPosArgument::columnPos));
      func_218136_a("vec3", Vec3Argument.class, new ArgumentSerializer<>(Vec3Argument::vec3));
      func_218136_a("vec2", Vec2Argument.class, new ArgumentSerializer<>(Vec2Argument::vec2));
      func_218136_a("block_state", BlockStateArgument.class, new ArgumentSerializer<>(BlockStateArgument::blockState));
      func_218136_a("block_predicate", BlockPredicateArgument.class, new ArgumentSerializer<>(BlockPredicateArgument::blockPredicate));
      func_218136_a("item_stack", ItemArgument.class, new ArgumentSerializer<>(ItemArgument::item));
      func_218136_a("item_predicate", ItemPredicateArgument.class, new ArgumentSerializer<>(ItemPredicateArgument::itemPredicate));
      func_218136_a("color", ColorArgument.class, new ArgumentSerializer<>(ColorArgument::color));
      func_218136_a("component", ComponentArgument.class, new ArgumentSerializer<>(ComponentArgument::component));
      func_218136_a("message", MessageArgument.class, new ArgumentSerializer<>(MessageArgument::message));
      func_218136_a("nbt_compound_tag", NBTCompoundTagArgument.class, new ArgumentSerializer<>(NBTCompoundTagArgument::func_218043_a));
      func_218136_a("nbt_tag", NBTTagArgument.class, new ArgumentSerializer<>(NBTTagArgument::func_218085_a));
      func_218136_a("nbt_path", NBTPathArgument.class, new ArgumentSerializer<>(NBTPathArgument::nbtPath));
      func_218136_a("objective", ObjectiveArgument.class, new ArgumentSerializer<>(ObjectiveArgument::objective));
      func_218136_a("objective_criteria", ObjectiveCriteriaArgument.class, new ArgumentSerializer<>(ObjectiveCriteriaArgument::objectiveCriteria));
      func_218136_a("operation", OperationArgument.class, new ArgumentSerializer<>(OperationArgument::operation));
      func_218136_a("particle", ParticleArgument.class, new ArgumentSerializer<>(ParticleArgument::particle));
      func_218136_a("rotation", RotationArgument.class, new ArgumentSerializer<>(RotationArgument::rotation));
      func_218136_a("scoreboard_slot", ScoreboardSlotArgument.class, new ArgumentSerializer<>(ScoreboardSlotArgument::scoreboardSlot));
      func_218136_a("score_holder", ScoreHolderArgument.class, new ScoreHolderArgument.Serializer());
      func_218136_a("swizzle", SwizzleArgument.class, new ArgumentSerializer<>(SwizzleArgument::swizzle));
      func_218136_a("team", TeamArgument.class, new ArgumentSerializer<>(TeamArgument::team));
      func_218136_a("item_slot", SlotArgument.class, new ArgumentSerializer<>(SlotArgument::slot));
      func_218136_a("resource_location", ResourceLocationArgument.class, new ArgumentSerializer<>(ResourceLocationArgument::resourceLocation));
      func_218136_a("mob_effect", PotionArgument.class, new ArgumentSerializer<>(PotionArgument::mobEffect));
      func_218136_a("function", FunctionArgument.class, new ArgumentSerializer<>(FunctionArgument::function));
      func_218136_a("entity_anchor", EntityAnchorArgument.class, new ArgumentSerializer<>(EntityAnchorArgument::entityAnchor));
      func_218136_a("int_range", IRangeArgument.IntRange.class, new IRangeArgument.IntRange.Serializer());
      func_218136_a("float_range", IRangeArgument.FloatRange.class, new IRangeArgument.FloatRange.Serializer());
      func_218136_a("item_enchantment", EnchantmentArgument.class, new ArgumentSerializer<>(EnchantmentArgument::enchantment));
      func_218136_a("entity_summon", EntitySummonArgument.class, new ArgumentSerializer<>(EntitySummonArgument::entitySummon));
      func_218136_a("dimension", DimensionArgument.class, new ArgumentSerializer<>(DimensionArgument::getDimension));
      func_218136_a("time", TimeArgument.class, new ArgumentSerializer<>(TimeArgument::func_218091_a));
   }

   @Nullable
   private static ArgumentTypes.Entry<?> get(ResourceLocation id) {
      return ID_TYPE_MAP.get(id);
   }

   @Nullable
   private static ArgumentTypes.Entry<?> get(ArgumentType<?> type) {
      return CLASS_TYPE_MAP.get(type.getClass());
   }

   public static <T extends ArgumentType<?>> void serialize(PacketBuffer buffer, T type) {
      ArgumentTypes.Entry<T> entry = (ArgumentTypes.Entry<T>)get(type);
      if (entry == null) {
         LOGGER.error("Could not serialize {} ({}) - will not be sent to client!", type, type.getClass());
         buffer.writeResourceLocation(new ResourceLocation(""));
      } else {
         buffer.writeResourceLocation(entry.id);
         entry.serializer.write(type, buffer);
      }
   }

   @Nullable
   public static ArgumentType<?> deserialize(PacketBuffer buffer) {
      ResourceLocation resourcelocation = buffer.readResourceLocation();
      ArgumentTypes.Entry<?> entry = get(resourcelocation);
      if (entry == null) {
         LOGGER.error("Could not deserialize {}", (Object)resourcelocation);
         return null;
      } else {
         return entry.serializer.read(buffer);
      }
   }

   private static <T extends ArgumentType<?>> void serialize(JsonObject json, T type) {
      ArgumentTypes.Entry<T> entry = (ArgumentTypes.Entry<T>)get(type);
      if (entry == null) {
         LOGGER.error("Could not serialize argument {} ({})!", type, type.getClass());
         json.addProperty("type", "unknown");
      } else {
         json.addProperty("type", "argument");
         json.addProperty("parser", entry.id.toString());
         JsonObject jsonobject = new JsonObject();
         entry.serializer.write(type, jsonobject);
         if (jsonobject.size() > 0) {
            json.add("properties", jsonobject);
         }
      }

   }

   public static <S> JsonObject serialize(CommandDispatcher<S> dispatcher, CommandNode<S> node) {
      JsonObject jsonobject = new JsonObject();
      if (node instanceof RootCommandNode) {
         jsonobject.addProperty("type", "root");
      } else if (node instanceof LiteralCommandNode) {
         jsonobject.addProperty("type", "literal");
      } else if (node instanceof ArgumentCommandNode) {
         serialize(jsonobject, ((ArgumentCommandNode)node).getType());
      } else {
         LOGGER.error("Could not serialize node {} ({})!", node, node.getClass());
         jsonobject.addProperty("type", "unknown");
      }

      JsonObject jsonobject1 = new JsonObject();

      for(CommandNode<S> commandnode : node.getChildren()) {
         jsonobject1.add(commandnode.getName(), serialize(dispatcher, commandnode));
      }

      if (jsonobject1.size() > 0) {
         jsonobject.add("children", jsonobject1);
      }

      if (node.getCommand() != null) {
         jsonobject.addProperty("executable", true);
      }

      if (node.getRedirect() != null) {
         Collection<String> collection = dispatcher.getPath(node.getRedirect());
         if (!collection.isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(String s : collection) {
               jsonarray.add(s);
            }

            jsonobject.add("redirect", jsonarray);
         }
      }

      return jsonobject;
   }

   static class Entry<T extends ArgumentType<?>> {
      public final Class<T> argumentClass;
      public final IArgumentSerializer<T> serializer;
      public final ResourceLocation id;

      private Entry(Class<T> argumentClassIn, IArgumentSerializer<T> serializerIn, ResourceLocation idIn) {
         this.argumentClass = argumentClassIn;
         this.serializer = serializerIn;
         this.id = idIn;
      }
   }
}