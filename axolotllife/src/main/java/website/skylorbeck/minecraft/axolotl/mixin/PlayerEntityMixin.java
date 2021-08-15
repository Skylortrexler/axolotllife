package website.skylorbeck.minecraft.axolotl.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.Scoreboard;
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

    public int axostage = 0;
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
        this.axostage =i;
        if (axostage==2){
            ItemStack helm = Items.LEATHER_HELMET.getDefaultStack();
            helm.addEnchantment(Enchantments.RESPIRATION,5);
            equipStack(EquipmentSlot.HEAD, helm);
        } else {
            equipStack(EquipmentSlot.HEAD,ItemStack.EMPTY);
        }
        switch (this.axostage) {
            case 0, 1, 2 -> ((PlayerEntity) (Object) this).stepHeight = 1;
            case 3, 4 -> ((PlayerEntity) (Object) this).stepHeight = 2;
        }
    }

    @Inject(at = @At("RETURN"), method = "getActiveEyeHeight", cancellable = true)
    protected void getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir){
        LivingEntity living = storedEntity[axostage];
        if (living instanceof BabyAxolotl){
            cir.setReturnValue(0.5f);
        } else if (living instanceof BabyMedAxolotl|| living instanceof BabyBigAxolotl){
            cir.setReturnValue(1f);
        } else if (living instanceof AdolAxolotl){
            cir.setReturnValue(2.5f);
        } else if (living instanceof ChadAxolotl){
            cir.setReturnValue(3.5f);
        }
    }

}
