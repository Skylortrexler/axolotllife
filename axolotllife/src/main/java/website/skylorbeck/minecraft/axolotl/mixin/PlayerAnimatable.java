package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.EntityRetainer;
import website.skylorbeck.minecraft.axolotl.entities.AxoBaseEntity;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;
import website.skylorbeck.minecraft.axolotl.models.BabyModel;
import website.skylorbeck.minecraft.axolotl.renderers.BabyRenderer;

@Mixin(PlayerEntityRenderer.class)
public class PlayerAnimatable {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void injectedRender(AbstractClientPlayerEntity acpe, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        LivingEntity entity = EntityRetainer.getEntity();
        entity.setPos(acpe.getX(), acpe.getY(), acpe.getZ());
        entity.setHeadYaw(acpe.getHeadYaw());
        entity.setJumping(((LivingEntityAccessor) acpe).getJumping());
        entity.setSprinting(acpe.isSprinting());
        entity.setStuckArrowCount(acpe.getStuckArrowCount());
        entity.setInvulnerable(true);
        entity.setNoGravity(true);
        entity.setSneaking(acpe.isSneaking());
        entity.setSwimming(acpe.isSwimming());
//        entity.setCurrentHand(acpe.getActiveHand());
        entity.setPose(acpe.getPose());
        entity.lastLimbDistance = acpe.lastLimbDistance;
        entity.limbDistance = acpe.limbDistance;
        entity.limbAngle = acpe.limbAngle;
        entity.handSwinging = acpe.handSwinging;
        entity.handSwingTicks = acpe.handSwingTicks;
        entity.lastHandSwingProgress = acpe.lastHandSwingProgress;
        entity.handSwingProgress = acpe.handSwingProgress;
        entity.bodyYaw = acpe.bodyYaw;
        entity.prevBodyYaw = acpe.prevBodyYaw;
        entity.headYaw = acpe.headYaw;
        entity.prevHeadYaw = acpe.prevHeadYaw;
        entity.age = acpe.age;
        entity.preferredHand = acpe.preferredHand;
        entity.setOnGround(acpe.isOnGround());
        entity.setVelocity(acpe.getVelocity());
        entity.setPose(acpe.getPose());
        MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity,f,g,matrixStack,vertexConsumerProvider,i);
        ci.cancel();
    }

}