package website.skylorbeck.minecraft.axolotl.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Objects;

public class AdolAxolotl extends AxoBaseEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public AdolAxolotl(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }


    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        Animation animation = event.getController().getCurrentAnimation();
        if (animation!=null &&  Objects.equals(animation.animationName, "animation.irongolem.attack")){
            if (event.getController().getAnimationState() == AnimationState.Stopped){
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.static",true));
            }
            return PlayState.CONTINUE;
        }
        if (this.handSwinging) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.attack",false));
        } else if (this.isSubmergedInWater()||this.isInsideWaterOrBubbleColumn()||this.isTouchingWater()||this.isSwimming()||this.isSubmergedIn(FluidTags.WATER)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.swin",true));
        } else if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.static",true));
        }

        return PlayState.CONTINUE;
    }
    private <P extends IAnimatable> PlayState predicate2(AnimationEvent<P> event) {
        if (this.handSwinging) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.attack",false));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void useAbility() {
        if (!this.world.isClient) {
            this.world.createExplosion(this.world.getClosestPlayer(this,5), this.getX(), this.getY(), this.getZ(), 2,true, Explosion.DestructionType.NONE);
            List<Entity> list = this.world.getOtherEntities(this, this.world.getClosestPlayer(this,5).getBoundingBox().expand(3D));
            if (!list.isEmpty()) {
                for (Entity value : list) {
                    if (value instanceof LivingEntity && !(value instanceof PlayerEntity)) {
                        value.damage(DamageSource.GENERIC,5);
                        ((LivingEntity) value).takeKnockback(1D, (double) MathHelper.sin(this.world.getClosestPlayer(this,5).getYaw() * 0.017453292F), (double)(-MathHelper.cos(this.world.getClosestPlayer(this,5).getYaw() * 0.017453292F)));
                    }
                }
            }
        }
    }
}
