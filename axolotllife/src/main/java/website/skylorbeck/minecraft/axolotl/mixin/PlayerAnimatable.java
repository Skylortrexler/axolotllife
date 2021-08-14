package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;

@Mixin(PlayerEntityRenderer.class)
public class PlayerAnimatable {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void injectedRender(AbstractClientPlayerEntity acpe, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        LivingEntity entity = new BabyAxolotl(Declarar.BABYAXOLOTL,acpe.world);
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