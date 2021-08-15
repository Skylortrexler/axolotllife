package website.skylorbeck.minecraft.axolotl.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Hand;
import website.skylorbeck.minecraft.axolotl.Axolotl;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.renderers.*;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AxolotlClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYAXOLOTL,BabyRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYMEDAXOLOTL, BabyMedRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYBIGAXOLOTL, BabyBigRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.ADOLAXOLOTL, AdolRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.CHADXOLOTL, ChadRenderer::new);
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Declarar.specialability.wasPressed()) {
                MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
                ClientSidePacketRegistryImpl.INSTANCE.sendToServer(Axolotl.useabilitypacket,new PacketByteBuf(Unpooled.buffer()));
            }
        });
    }
}
