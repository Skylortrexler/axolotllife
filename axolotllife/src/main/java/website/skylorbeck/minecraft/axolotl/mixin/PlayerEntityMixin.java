package website.skylorbeck.minecraft.axolotl.mixin;

import com.mojang.authlib.GameProfile;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.EntityRetainer;
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

    @Inject(at = @At("RETURN"), method = "<init>")
    public void injectedInit(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci) {
        EntityRetainer.setEntity(new BabyAxolotl(Declarar.BABYAXOLOTL, world));
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectedTick(CallbackInfo ci) {
        Scoreboard scoreboard = getScoreboard();
        String playername = getEntityName();
        if (this.axostage == 0 ) {
            if (scoreboard.getPlayerScore(playername, scoreboard.getObjective("fish")).getScore() >= 3) {
                this.axostage = 1;
                EntityRetainer.setEntity(new BabyMedAxolotl(Declarar.BABYMEDAXOLOTL, ((PlayerEntity) (Object) this).world));
                ((EntityAccessor)this).setStandingEyeHeight(1f);
            }
            if (!((PlayerEntity) (Object) this).isWet()){
                ((PlayerEntity) (Object) this).damage(DamageSource.DRYOUT, 1.0F);
            }
        }
        if (this.axostage == 1 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("drowned")).getScore() >= 2) {
            this.axostage = 2;

            EntityRetainer.setEntity(new BabyBigAxolotl(Declarar.BABYBIGAXOLOTL, ((PlayerEntity) (Object) this).world));
            ItemStack helm = Items.LEATHER_HELMET.getDefaultStack();
            helm.addEnchantment(Enchantments.RESPIRATION,5);
            this.equipStack(EquipmentSlot.HEAD, helm);
            ((EntityAccessor)this).setStandingEyeHeight(1.5f);
        }
        if (this.axostage == 2 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("zombie")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("spider")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("skeleton")).getScore() >= 1 ) {
            this.axostage = 3;
            EntityRetainer.setEntity(new AdolAxolotl(Declarar.ADOLAXOLOTL, ((PlayerEntity) (Object) this).world));
            this.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
            ((EntityAccessor)this).setStandingEyeHeight(5f);
        }
        if (this.axostage == 3 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("enderman")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("piglin")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("blaze")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("witherskel")).getScore() >= 1 ) {
            this.axostage = 4;
            EntityRetainer.setEntity(new ChadAxolotl(Declarar.CHADXOLOTL, ((PlayerEntity) (Object) this).world));
            ((EntityAccessor)this).setStandingEyeHeight(6f);
        }


        if (scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore() >= 1){
            this.axostage = scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore();
            switch(this.axostage){
                case 0 -> EntityRetainer.setEntity(new BabyAxolotl(Declarar.BABYAXOLOTL,((PlayerEntity) (Object) this).world));
                case 1 -> EntityRetainer.setEntity(new BabyMedAxolotl(Declarar.BABYMEDAXOLOTL,((PlayerEntity) (Object) this).world));
                case 2 -> EntityRetainer.setEntity(new BabyBigAxolotl(Declarar.BABYBIGAXOLOTL,((PlayerEntity) (Object) this).world));
                case 3 -> EntityRetainer.setEntity(new AdolAxolotl(Declarar.ADOLAXOLOTL,((PlayerEntity) (Object) this).world));
                case 4 -> EntityRetainer.setEntity(new ChadAxolotl(Declarar.CHADXOLOTL,((PlayerEntity) (Object) this).world));
            }
        }


    }
    @Override
    public int getAxostage(){
        return this.axostage;
    }

    @Inject(at = @At("RETURN"), method = "getActiveEyeHeight", cancellable = true)
    protected void getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir){
        LivingEntity living = EntityRetainer.getEntity();
        if (living instanceof BabyAxolotl){
            cir.setReturnValue(0.5f);
        } else if (living instanceof BabyMedAxolotl|| living instanceof BabyBigAxolotl){
            cir.setReturnValue(1f);
        } else if (living instanceof AdolAxolotl){
            cir.setReturnValue(5f);
        } else if (living instanceof ChadAxolotl){
            cir.setReturnValue(6f);
        }
    }

}
