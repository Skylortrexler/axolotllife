package website.skylorbeck.minecraft.axolotl;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

public interface PlayerEntityAccessor {
    int getAxostage();
}
