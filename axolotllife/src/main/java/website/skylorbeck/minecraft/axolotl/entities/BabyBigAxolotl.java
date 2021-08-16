package website.skylorbeck.minecraft.axolotl.entities;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TippedArrowItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Objects;

public class BabyBigAxolotl extends AxoBaseEntity implements IAnimatable {
    AnimationFactory factory = new AnimationFactory(this);

    public BabyBigAxolotl(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 5, this::predicate));
    }


    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isSubmergedInWater()||this.isInsideWaterOrBubbleColumn()||this.isTouchingWater()||this.isSwimming()||this.isSubmergedIn(FluidTags.WATER)){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pig.swin",true));
        } else if (event.isMoving()){
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pig.walk", true));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pig.static",true));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void useAbility() {
        if (!world.isClient) {
            HitResult hitResult = world.getClosestPlayer(this,5d).raycast(100D, 1f,false);
            Vec3d vec3d = world.getClosestPlayer(this,5d).getRotationVec(1.0F);
            double x = hitResult.getPos().getX() - (this.getX() + vec3d.x * 4.0D);
            double y = hitResult.getPos().getY() - (0.5D + this.getBodyY(0.5D));
            double z = hitResult.getPos().getZ() - (this.getZ() + vec3d.z * 4.0D);
            FireballEntity fireballEntity = new FireballEntity(world, this, x, y, z, 1);
            fireballEntity.setPosition(world.getClosestPlayer(this,5d).getPos().getX(),world.getClosestPlayer(this,5d).getPos().getY()+1,world.getClosestPlayer(this,5d).getPos().getZ());
            world.spawnEntity(fireballEntity);
        }
    }
}
