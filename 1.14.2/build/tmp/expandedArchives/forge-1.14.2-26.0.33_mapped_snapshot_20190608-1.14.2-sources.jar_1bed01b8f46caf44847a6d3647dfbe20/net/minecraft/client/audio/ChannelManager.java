package net.minecraft.client.audio;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChannelManager {
   private final Set<ChannelManager.Entry> field_217905_a = Sets.newIdentityHashSet();
   private final SoundSystem field_217906_b;
   private final Executor field_217907_c;

   public ChannelManager(SoundSystem p_i50894_1_, Executor p_i50894_2_) {
      this.field_217906_b = p_i50894_1_;
      this.field_217907_c = p_i50894_2_;
   }

   public ChannelManager.Entry func_217895_a(SoundSystem.Mode p_217895_1_) {
      ChannelManager.Entry channelmanager$entry = new ChannelManager.Entry();
      this.field_217907_c.execute(() -> {
         SoundSource soundsource = this.field_217906_b.func_216403_a(p_217895_1_);
         if (soundsource != null) {
            channelmanager$entry.field_217893_b = soundsource;
            this.field_217905_a.add(channelmanager$entry);
         }

      });
      return channelmanager$entry;
   }

   public void func_217897_a(Consumer<Stream<SoundSource>> p_217897_1_) {
      this.field_217907_c.execute(() -> {
         p_217897_1_.accept(this.field_217905_a.stream().map((p_217896_0_) -> {
            return p_217896_0_.field_217893_b;
         }).filter(Objects::nonNull));
      });
   }

   public void func_217899_a() {
      this.field_217907_c.execute(() -> {
         Iterator<ChannelManager.Entry> iterator = this.field_217905_a.iterator();

         while(iterator.hasNext()) {
            ChannelManager.Entry channelmanager$entry = iterator.next();
            channelmanager$entry.field_217893_b.func_216434_i();
            if (channelmanager$entry.field_217893_b.func_216435_g()) {
               channelmanager$entry.func_217891_b();
               iterator.remove();
            }
         }

      });
   }

   public void func_217903_b() {
      this.field_217905_a.forEach(ChannelManager.Entry::func_217891_b);
      this.field_217905_a.clear();
   }

   @OnlyIn(Dist.CLIENT)
   public class Entry {
      private SoundSource field_217893_b;
      private boolean field_217894_c;

      public boolean func_217889_a() {
         return this.field_217894_c;
      }

      public void func_217888_a(Consumer<SoundSource> p_217888_1_) {
         ChannelManager.this.field_217907_c.execute(() -> {
            if (this.field_217893_b != null) {
               p_217888_1_.accept(this.field_217893_b);
            }

         });
      }

      public void func_217891_b() {
         this.field_217894_c = true;
         ChannelManager.this.field_217906_b.func_216408_a(this.field_217893_b);
         this.field_217893_b = null;
      }
   }
}