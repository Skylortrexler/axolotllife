package website.skylorbeck.minecraft.axolotl.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.tag.FluidTags;
import net.minecraft.world.GameRules;
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

import java.util.Objects;

public class ChadAxolotl extends AxoBaseEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public ChadAxolotl(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }


    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        Animation animation = event.getController().getCurrentAnimation();
        if (animation != null && Objects.equals(animation.animationName, "animation.irongolem.attack")) {
            if (event.getController().getAnimationState() == AnimationState.Stopped) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.static", false));
            }
            return PlayState.CONTINUE;
        }
        if (this.handSwinging) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.attack", false));
        } else if (this.isSubmergedInWater() || this.isInsideWaterOrBubbleColumn() || this.isTouchingWater() || this.isSwimming() || this.isSubmergedIn(FluidTags.WATER)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.swin", true));
        } else if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.irongolem.static", true));
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
            Explosion.DestructionType destructionType = this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE;
            this.world.createExplosion(this.world.getClosestPlayer(this,5), this.getX(), this.getY(), this.getZ(), 5, destructionType);
        }
    }
    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }
}
