package website.skylorbeck.minecraft.axolotl.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;
import website.skylorbeck.minecraft.axolotl.entities.*;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityAccessor {
    @Shadow
    public abstract Scoreboard getScoreboard();

    @Shadow
    public abstract String getEntityName();


    @Shadow public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    public int axostage = -1;
    public int cooldown = 30;
    private static final LivingEntity[] storedEntity = new LivingEntity[5];

    @Inject(at = @At("RETURN"), method = "<init>")
    public void injectedInit(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci) {
        storedEntity[0]= new BabyAxolotl(Declarar.BABYAXOLOTL, ((PlayerEntity) (Object) this).world);
        storedEntity[1]= new BabyMedAxolotl(Declarar.BABYMEDAXOLOTL, ((PlayerEntity) (Object) this).world);
        storedEntity[2]= new BabyBigAxolotl(Declarar.BABYBIGAXOLOTL, ((PlayerEntity) (Object) this).world);
        storedEntity[3]= new AdolAxolotl(Declarar.ADOLAXOLOTL, ((PlayerEntity) (Object) this).world);
        storedEntity[4]= new ChadAxolotl(Declarar.CHADXOLOTL, ((PlayerEntity) (Object) this).world);

    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectedTick(CallbackInfo ci) {
        LivingEntity entity = getStoredEntity();
        entity.setPos(((PlayerEntity) (Object) this).getX(), ((PlayerEntity) (Object) this).getY(), ((PlayerEntity) (Object) this).getZ());
//        storedEntity.setHeadYaw(((PlayerEntity) (Object) this).getHeadYaw());
        entity.setJumping(((LivingEntityAccessor) this).getJumping());
        entity.setSprinting(((PlayerEntity) (Object) this).isSprinting());
        entity.setStuckArrowCount(((PlayerEntity) (Object) this).getStuckArrowCount());
        entity.setInvulnerable(true);
        entity.setNoGravity(true);
        entity.setSneaking(((PlayerEntity) (Object) this).isSneaking());
        entity.setSwimming(((PlayerEntity) (Object) this).isSwimming());
//        storedEntity.setCurrentHand(((PlayerEntity) (Object) this).getActiveHand());
        entity.setOnGround(((PlayerEntity) (Object) this).isOnGround());
        entity.setVelocity(((PlayerEntity) (Object) this).getVelocity());
        entity.setPose(((PlayerEntity) (Object) this).getPose());
        if (this.cooldown<30){
            this.cooldown++;
        }
        if (this.cooldown>30){
            this.cooldown = 30;
        }
    }
    @Override
    public int getAxostage(){
        return this.axostage;
    }
    @Override
    public LivingEntity getStoredEntity(){
        if (axostage>=0&&axostage<=4) {
            return storedEntity[axostage];
        }
        return storedEntity[0];
    }
   @Override
   public void useAbility() {
       if (cooldown == 30) {
           ((PlayerEntity) (Object) this).swingHand(Hand.MAIN_HAND);
           ((AxoBaseEntity) this.getStoredEntity()).useAbility();
           cooldown = 0;
       }
   }
    @Override
    public void setAxostage(int i) {
        PlayerEntity playerEntity = ((PlayerEntity) (Object) this);
        playerEntity.addEnchantedHitParticles(playerEntity);
        this.axostage =i;
        if (axostage==2){
            ItemStack helm = Items.LEATHER_HELMET.getDefaultStack();
            helm.addEnchantment(Enchantments.RESPIRATION,2);
            equipStack(EquipmentSlot.HEAD, helm);
        } else {
            equipStack(EquipmentSlot.HEAD,ItemStack.EMPTY);
        }
        switch (this.axostage) {
            case 0:
                if (playerEntity.world.isClient) {
                    playerEntity.sendMessage(Text.of("Kill three Cod to evolve"), false);
                    playerEntity.sendMessage(Text.of("You suffocate instantly outside water"), false);
                }
                break;
            case 1:
                if (playerEntity.world.isClient) {
                    playerEntity.sendMessage(Text.of("Kill two Drowned to evolve"), false);
                    playerEntity.sendMessage(Text.of("Push Z to shoot water"), false);
                }
                playerEntity.stepHeight = 1;
                break;
            case 2:
                if (playerEntity.world.isClient) {
                    playerEntity.sendMessage(Text.of("Kill 1 Zombie, 1 Spider and 1 Skeleton to evolve"), false);
                    playerEntity.sendMessage(Text.of("Push Z to shoot a fireball"), false);
                }
                playerEntity.stepHeight = 1;

                break;
            case 3:
                if (playerEntity.world.isClient) {
                    playerEntity.sendMessage(Text.of("Kill 1 Piglin, 1 Wither Skeleton, 1 Enderman and 1 Blaze to evolve"), false);
                    playerEntity.sendMessage(Text.of("Push Z to explosive punch"), false);
                }
                playerEntity.stepHeight = 2;

                break;
            case 4:
                if (playerEntity.world.isClient) {
                    playerEntity.sendMessage(Text.of("You are the ultimate Axolotl!"), false);
                    playerEntity.sendMessage(Text.of("Push Z to Grand Slam!"), false);
                }
                playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST,9999,9));
                playerEntity.stepHeight = 2;
                break;
        }
    }

    @Inject(at = @At("RETURN"), method = "getMovementSpeed", cancellable = true)
    public void injectedMovementSpeed(CallbackInfoReturnable<Float> cir){
        switch (axostage){
            case 0 -> cir.setReturnValue(0.1f);
            case 1 -> cir.setReturnValue(0.11f);
            case 2 -> cir.setReturnValue(0.115f);
            case 3 -> cir.setReturnValue(0.125f);
            case 4 -> cir.setReturnValue(0.135f);
        }
    }
    @Inject(at = @At("RETURN"), method = "getActiveEyeHeight", cancellable = true)
    protected void getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        switch (axostage){
            case 0 ->  cir.setReturnValue(0.5f);
            case 1 ->  cir.setReturnValue(1f);
            case 2 ->  cir.setReturnValue(1.5f);
            case 3 ->  cir.setReturnValue(2.5f);
            case 4 ->  cir.setReturnValue(3.5f);
        }
    }
    @Inject(at = @At("RETURN"), method = "isInvulnerableTo", cancellable = true)
    public void injectedInvuln(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir){
        if (axostage==4 && (damageSource == DamageSource.LAVA ||damageSource.isFromFalling() || damageSource.isFire())){
            cir.setReturnValue(true);
        }
    }
}
