package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow private Vec3d pos;

    @Shadow public abstract Vec3d getEyePos();

    /**
     * @author skylorbeck
     * @reason if I had more time I would do it better
     */
    @Overwrite
    public final float getStandingEyeHeight() {
        if (MinecraftClient.getInstance().player != null && ((Entity)(Object)this).isPlayer()) {
            int living = ((PlayerEntityAccessor)this).getAxostage();
            switch (living){
                case 0 -> {
                    return 0.5f;
                }
                case 1, 2 -> {
                    return 1f;
                }
                case 3 -> {
                    return 2.5f;
                }
                case 4 -> {
                    return 3.5f;
                }
            }
        }
        return 1.85f;
    }
    @Inject(at = @At("RETURN"), method = "getEyeY", cancellable = true)
    public void getPEyeY(CallbackInfoReturnable<Double> cir){
        if (((Entity)(Object)this).isPlayer()){
            cir.setReturnValue(((Entity) (Object) this).getPos().y+((PlayerEntity)(Object)this).getStandingEyeHeight());
        }
    }
}