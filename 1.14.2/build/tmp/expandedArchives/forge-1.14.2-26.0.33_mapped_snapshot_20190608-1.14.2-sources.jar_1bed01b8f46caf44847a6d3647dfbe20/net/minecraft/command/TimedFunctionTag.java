package net.minecraft.command;

import net.minecraft.advancements.FunctionManager;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class TimedFunctionTag implements ITimerCallback<MinecraftServer> {
   private final ResourceLocation field_216315_a;

   public TimedFunctionTag(ResourceLocation p_i51189_1_) {
      this.field_216315_a = p_i51189_1_;
   }

   public void func_212869_a_(MinecraftServer p_212869_1_, TimerCallbackManager<MinecraftServer> p_212869_2_, long p_212869_3_) {
      FunctionManager functionmanager = p_212869_1_.getFunctionManager();
      Tag<FunctionObject> tag = functionmanager.getTagCollection().getOrCreate(this.field_216315_a);

      for(FunctionObject functionobject : tag.getAllElements()) {
         functionmanager.execute(functionobject, functionmanager.getCommandSource());
      }

   }

   public static class Serializer extends ITimerCallback.Serializer<MinecraftServer, TimedFunctionTag> {
      public Serializer() {
         super(new ResourceLocation("function_tag"), TimedFunctionTag.class);
      }

      public void func_212847_a_(CompoundNBT p_212847_1_, TimedFunctionTag p_212847_2_) {
         p_212847_1_.putString("Name", p_212847_2_.field_216315_a.toString());
      }

      public TimedFunctionTag func_212846_b_(CompoundNBT p_212846_1_) {
         ResourceLocation resourcelocation = new ResourceLocation(p_212846_1_.getString("Name"));
         return new TimedFunctionTag(resourcelocation);
      }
   }
}