package net.minecraft.entity.monster;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ElderGuardianEntity extends GuardianEntity {
   public static final float field_213629_b = EntityType.ELDER_GUARDIAN.func_220333_h() / EntityType.GUARDIAN.func_220333_h();

   public ElderGuardianEntity(EntityType<? extends ElderGuardianEntity> p_i50211_1_, World p_i50211_2_) {
      super(p_i50211_1_, p_i50211_2_);
      this.enablePersistence();
      if (this.field_175481_bq != null) {
         this.field_175481_bq.setExecutionChance(400);
      }

   }

   protected void registerAttributes() {
      super.registerAttributes();
      this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)0.3F);
      this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
      this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
   }

   public int getAttackDuration() {
      return 60;
   }

   @OnlyIn(Dist.CLIENT)
   public void setGhost() {
      this.clientSideSpikesAnimation = 1.0F;
      this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
   }

   protected SoundEvent getAmbientSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT : SoundEvents.ENTITY_ELDER_GUARDIAN_AMBIENT_LAND;
   }

   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_HURT : SoundEvents.ENTITY_ELDER_GUARDIAN_HURT_LAND;
   }

   protected SoundEvent getDeathSound() {
      return this.isInWaterOrBubbleColumn() ? SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH : SoundEvents.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
   }

   protected SoundEvent getFlopSound() {
      return SoundEvents.ENTITY_ELDER_GUARDIAN_FLOP;
   }

   protected void updateAITasks() {
      super.updateAITasks();
      int i = 1200;
      if ((this.ticksExisted + this.getEntityId()) % 1200 == 0) {
         Effect effect = Effects.field_76419_f;
         List<ServerPlayerEntity> list = ((ServerWorld)this.world).getPlayers((p_210138_1_) -> {
            return this.getDistanceSq(p_210138_1_) < 2500.0D && p_210138_1_.interactionManager.survivalOrAdventure();
         });
         int j = 2;
         int k = 6000;
         int l = 1200;

         for(ServerPlayerEntity serverplayerentity : list) {
            if (!serverplayerentity.isPotionActive(effect) || serverplayerentity.getActivePotionEffect(effect).getAmplifier() < 2 || serverplayerentity.getActivePotionEffect(effect).getDuration() < 1200) {
               serverplayerentity.connection.sendPacket(new SChangeGameStatePacket(10, 0.0F));
               serverplayerentity.addPotionEffect(new EffectInstance(effect, 6000, 2));
            }
         }
      }

      if (!this.func_213394_dL()) {
         this.func_213390_a(new BlockPos(this), 16);
      }

   }
}