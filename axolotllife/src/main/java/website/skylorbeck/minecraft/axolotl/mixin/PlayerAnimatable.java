package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
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
public class PlayerAnimatable {

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void injectedRender(AbstractClientPlayerEntity acpe, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci){
        LivingEntity entity =((PlayerEntityAccessor)acpe).getStoredEntity();
        entity.bodyYaw = ((PlayerEntity) (Object) acpe).bodyYaw;
        entity.prevBodyYaw = ((PlayerEntity) (Object) acpe).prevBodyYaw;
        entity.headYaw = ((PlayerEntity) (Object) acpe).headYaw;
        entity.prevHeadYaw = ((PlayerEntity) (Object) acpe).prevHeadYaw;
        MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(entity).render(entity,f,g,matrixStack,vertexConsumerProvider,i);
        ci.cancel();
    }

}