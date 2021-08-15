package website.skylorbeck.minecraft.axolotl.mixin;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import website.skylorbeck.minecraft.axolotl.Axolotl;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;

import java.util.List;
import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow @Final
    List<ServerPlayerEntity> players;

    @Shadow public abstract ServerScoreboard getScoreboard();

    @Inject(at = @At("HEAD"), method = "tick")
    public void injectedTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        ServerScoreboard scoreboard = getScoreboard();
        String playername;
        for (PlayerEntity player:this.players) {
            PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
            playername = player.getEntityName();
            if (((PlayerEntityAccessor)player).getAxostage() == 0 ) {
                if (scoreboard.getPlayerScore(playername, scoreboard.getObjective("fish")).getScore() >= 3) {
                    ((PlayerEntityAccessor)player).setAxostage(1);
                    packetByteBuf.writeInt(1);
                    ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player,Axolotl.setmodel,packetByteBuf);
                }
                if (!player.isWet()){
                    player.damage(DamageSource.DRYOUT, 1.0F);
                }
            }
            if (((PlayerEntityAccessor)player).getAxostage() == 1 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("drowned")).getScore() >= 2) {
                ((PlayerEntityAccessor)player).setAxostage(2);
                ItemStack helm = Items.LEATHER_HELMET.getDefaultStack();
                helm.addEnchantment(Enchantments.RESPIRATION,5);
                player.equipStack(EquipmentSlot.HEAD, helm);
                packetByteBuf.writeInt(2);
                ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player,Axolotl.setmodel,packetByteBuf);
            }
            if (((PlayerEntityAccessor)player).getAxostage() == 2 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("zombie")).getScore() >= 1
                    && scoreboard.getPlayerScore(playername, scoreboard.getObjective("spider")).getScore() >= 1
                    && scoreboard.getPlayerScore(playername, scoreboard.getObjective("skeleton")).getScore() >= 1 ) {
                ((PlayerEntityAccessor)player).setAxostage(3);
                player.equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                packetByteBuf.writeInt(3);
                ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player,Axolotl.setmodel,packetByteBuf);
            }
            if (((PlayerEntityAccessor)player).getAxostage() == 3 && scoreboard.getPlayerScore(playername, scoreboard.getObjective("enderman")).getScore() >= 1
                    && scoreboard.getPlayerScore(playername, scoreboard.getObjective("piglin")).getScore() >= 1
                    && scoreboard.getPlayerScore(playername, scoreboard.getObjective("blaze")).getScore() >= 1
                    && scoreboard.getPlayerScore(playername, scoreboard.getObjective("witherskel")).getScore() >= 1 ) {
                ((PlayerEntityAccessor)player).setAxostage(4);
                packetByteBuf.writeInt(4);
                ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player,Axolotl.setmodel,packetByteBuf);
            }
            if (scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore() >= 1) {
                ((PlayerEntityAccessor)player).setAxostage(scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore());
                packetByteBuf.writeInt(scoreboard.getPlayerScore(playername, scoreboard.getObjective("override")).getScore());
                ServerSidePacketRegistryImpl.INSTANCE.sendToPlayer(player,Axolotl.setmodel,packetByteBuf);
            }
        }
    }

}
