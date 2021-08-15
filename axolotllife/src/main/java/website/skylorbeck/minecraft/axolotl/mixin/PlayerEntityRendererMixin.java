package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void injectedRender(AbstractClientPlayerEntity acpe, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        if (acpe!=null) {
            LivingEntity entity = ((PlayerEntityAccessor) acpe).getStoredEntity();
            entity.setPos(acpe.getX(), acpe.getY(), acpe.getZ());
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
            MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity, f, g, matrixStack, vertexConsumerProvider, i);
            ci.cancel();
        }
    }

}