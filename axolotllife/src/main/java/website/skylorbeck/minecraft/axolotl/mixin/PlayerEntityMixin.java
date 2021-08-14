package website.skylorbeck.minecraft.axolotl.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.EntityRetainer;
import website.skylorbeck.minecraft.axolotl.entities.*;

import java.util.Map;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow
    public abstract Scoreboard getScoreboard();

    @Shadow
    public abstract String getEntityName();

    public int axostage = 0;

    @Inject(at = @At("RETURN"), method = "<init>")
    public void injectedInit(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci) {
        EntityRetainer.setEntity(new BabyAxolotl(Declarar.BABYAXOLOTL, world));
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectedTick(CallbackInfo ci) {
        Scoreboard scoreboard = getScoreboard();
        String playername = getEntityName();
        if (this.axostage == 0 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("fish")).getScore() >= 3 ) {
            this.axostage = 1;
            EntityRetainer.setEntity(new BabyMedAxolotl(Declarar.BABYMEDAXOLOTL, ((PlayerEntity) (Object) this).world));
        }
        if (this.axostage == 1 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("drowned")).getScore() >= 2) {
            this.axostage = 2;
            EntityRetainer.setEntity(new BabyBigAxolotl(Declarar.BABYBIGAXOLOTL, ((PlayerEntity) (Object) this).world));
        }
        if (this.axostage == 2 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("zombie")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("spider")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("skeleton")).getScore() >= 1 ) {
            this.axostage = 3;
            EntityRetainer.setEntity(new AdolAxolotl(Declarar.ADOLAXOLOTL, ((PlayerEntity) (Object) this).world));
        }
        if (this.axostage == 3 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("enderman")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("piglin")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("blaze")).getScore() >= 1
                && scoreboard.getPlayerScore(playername, scoreboard.getObjective("witherskel")).getScore() >= 1 ) {
            this.axostage = 4;
            EntityRetainer.setEntity(new ChadAxolotl(Declarar.CHADXOLOTL, ((PlayerEntity) (Object) this).world));
        }
        if (scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore() >= 1){
            EntityRetainer.setEntity(new ChadAxolotl(Declarar.CHADXOLOTL, ((PlayerEntity) (Object) this).world));
        }

    }
}
