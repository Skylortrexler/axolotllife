package website.skylorbeck.minecraft.axolotl.client;

import io.netty.buffer.Unpooled;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.impl.networking.ClientSidePacketRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.lwjgl.glfw.GLFW;
import website.skylorbeck.minecraft.axolotl.Axolotl;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.PlayerEntityAccessor;
import website.skylorbeck.minecraft.axolotl.entities.AxoBaseEntity;
import website.skylorbeck.minecraft.axolotl.renderers.*;

import java.util.UUID;

import static website.skylorbeck.minecraft.axolotl.Axolotl.setmodel;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AxolotlClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Declarar.specialability =  KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.axolotl.ability", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Z, // The keycode of the key
                "category.axolotl" // The translation key of the keybinding's category.
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (Declarar.specialability.wasPressed()) {
                MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
                PacketByteBuf packetByteBuf = new PacketByteBuf(Unpooled.buffer());
                packetByteBuf.writeUuid(MinecraftClient.getInstance().player.getUuid());
                ClientSidePacketRegistryImpl.INSTANCE.sendToServer(Axolotl.useabilitypacket,packetByteBuf);
            }
        });
        ClientSidePacketRegistryImpl.INSTANCE.register(setmodel, (packetContext, attachedData) -> {
            String string = attachedData.readString();
            int i = attachedData.readInt();
            packetContext.getTaskQueue().execute(() -> {
                PlayerEntity playerEntity = packetContext.getPlayer().world.getPlayerByUuid(UUID.fromString(string));
                ((PlayerEntityAccessor)playerEntity).setAxostage(i);
            });
        });
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYAXOLOTL,BabyRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYMEDAXOLOTL, BabyMedRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYBIGAXOLOTL, BabyBigRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.ADOLAXOLOTL, AdolRenderer::new);
        EntityRendererRegistry.INSTANCE.register(Declarar.CHADXOLOTL, ChadRenderer::new);


    }
}
