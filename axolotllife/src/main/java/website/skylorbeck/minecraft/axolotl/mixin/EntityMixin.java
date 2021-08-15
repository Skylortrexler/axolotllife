package website.skylorbeck.minecraft.axolotl.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;
import website.skylorbeck.minecraft.axolotl.entities.*;

@Mixin(Entity.class)
public class EntityMixin {
    /**
     * @author skylorbeck
     * @reason if I had more time I would do it better
     */
    @Overwrite
    public final float getStandingEyeHeight() {
        if (MinecraftClient.getInstance().player != null) {
            LivingEntity living = ((PlayerEntityAccessor) MinecraftClient.getInstance().player).getStoredEntity();
            if (living instanceof BabyAxolotl) {
                return 0.5f;
            } else if (living instanceof BabyMedAxolotl || living instanceof BabyBigAxolotl) {
                return 1f;
            } else if (living instanceof AdolAxolotl) {
                return 2.5f;
            } else if (living instanceof ChadAxolotl) {
                return 3.5f;
            }
        }
        return 0.5f;
    }
}