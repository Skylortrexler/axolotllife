package website.skylorbeck.minecraft.axolotl.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;
import website.skylorbeck.minecraft.axolotl.entities.BabyBigAxolotl;
import website.skylorbeck.minecraft.axolotl.entities.BabyMedAxolotl;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isSubmergedIn(Lnet/minecraft/tag/Tag;)Z"), method = "baseTick")
    public boolean injected(LivingEntity livingEntity, Tag<Fluid> fluidTag) {
        BlockPos blockPos = new BlockPos(((LivingEntity) (Object) this).getX(), ((LivingEntity) (Object) this).getEyeY(), ((LivingEntity) (Object) this).getZ());
        if (livingEntity instanceof PlayerEntity &&((PlayerEntityAccessor)livingEntity).getAxostage()>=0){
            int living = ((PlayerEntityAccessor)livingEntity).getAxostage();
            return (((LivingEntity) (Object) this).world.getBlockState(blockPos).isOf(Blocks.AIR) && (living<3));
        }
        return livingEntity.isSubmergedIn(fluidTag);
    }

}
