package net.minecraft.world.chunk;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.entity.player.ServerPlayerEntity;

public final class PlayerGenerationTracker {
   private final Set<ServerPlayerEntity> field_219449_a = Sets.newHashSet();
   private final Set<ServerPlayerEntity> field_219450_b = Sets.newHashSet();

   public Stream<ServerPlayerEntity> func_219444_a(long p_219444_1_) {
      return this.field_219449_a.stream();
   }

   public void func_219442_a(long p_219442_1_, ServerPlayerEntity p_219442_3_, boolean p_219442_4_) {
      (p_219442_4_ ? this.field_219450_b : this.field_219449_a).add(p_219442_3_);
   }

   public void func_219443_a(long p_219443_1_, ServerPlayerEntity p_219443_3_) {
      this.field_219449_a.remove(p_219443_3_);
      this.field_219450_b.remove(p_219443_3_);
   }

   public void func_219446_a(ServerPlayerEntity p_219446_1_) {
      this.field_219450_b.add(p_219446_1_);
      this.field_219449_a.remove(p_219446_1_);
   }

   public void func_219447_b(ServerPlayerEntity p_219447_1_) {
      this.field_219450_b.remove(p_219447_1_);
      this.field_219449_a.add(p_219447_1_);
   }

   public boolean func_219448_c(ServerPlayerEntity p_219448_1_) {
      return !this.field_219449_a.contains(p_219448_1_);
   }

   public void func_219445_a(long p_219445_1_, long p_219445_3_, ServerPlayerEntity p_219445_5_) {
   }
}