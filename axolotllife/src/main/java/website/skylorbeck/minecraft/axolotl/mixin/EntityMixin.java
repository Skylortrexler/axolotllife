package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import website.skylorbeck.minecraft.axolotl.EntityRetainer;
import website.skylorbeck.minecraft.axolotl.entities.*;

@Mixin(Entity.class)
public class EntityMixin {
    /**
     * @author skylorbeck
     * @reason if I had more time I would do it better
     */
    @Overwrite
    public final float getStandingEyeHeight() {
        LivingEntity living = EntityRetainer.getEntity();
        if (living instanceof BabyAxolotl){
            return 0.5f;
        } else if (living instanceof BabyMedAxolotl || living instanceof BabyBigAxolotl){
            return 1f;
        } else if (living instanceof AdolAxolotl){
            return 3f;
        } else if (living instanceof ChadAxolotl){
            return 4f;
        }
        return 0.5f;
    }
}
