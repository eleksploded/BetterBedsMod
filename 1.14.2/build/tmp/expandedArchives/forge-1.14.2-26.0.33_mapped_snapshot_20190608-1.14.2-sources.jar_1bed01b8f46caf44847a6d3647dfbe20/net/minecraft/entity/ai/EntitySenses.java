package net.minecraft.entity.ai;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;

public class EntitySenses {
   private final MobEntity field_75526_a;
   private final List<Entity> seenEntities = Lists.newArrayList();
   private final List<Entity> unseenEntities = Lists.newArrayList();

   public EntitySenses(MobEntity entityIn) {
      this.field_75526_a = entityIn;
   }

   /**
    * Clears canSeeCachePositive and canSeeCacheNegative.
    */
   public void tick() {
      this.seenEntities.clear();
      this.unseenEntities.clear();
   }

   /**
    * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
    */
   public boolean canSee(Entity entityIn) {
      if (this.seenEntities.contains(entityIn)) {
         return true;
      } else if (this.unseenEntities.contains(entityIn)) {
         return false;
      } else {
         this.field_75526_a.world.getProfiler().startSection("canSee");
         boolean flag = this.field_75526_a.canEntityBeSeen(entityIn);
         this.field_75526_a.world.getProfiler().endSection();
         if (flag) {
            this.seenEntities.add(entityIn);
         } else {
            this.unseenEntities.add(entityIn);
         }

         return flag;
      }
   }
}