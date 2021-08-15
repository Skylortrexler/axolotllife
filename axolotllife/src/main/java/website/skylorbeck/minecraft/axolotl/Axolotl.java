package website.skylorbeck.minecraft.axolotl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.Timer;
import org.lwjgl.glfw.GLFW;
import software.bernie.example.EntityUtils;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import website.skylorbeck.minecraft.axolotl.entities.AxoBaseEntity;

public class Axolotl implements ModInitializer {
    public static Identifier useabilitypacket = new Identifier("axolotl","useability");

    @Override
    public void onInitialize() {
        GeckoLibMod.DISABLE_IN_DEV = true;
        GeckoLib.initialize();
        FabricDefaultAttributeRegistry.register(Declarar.BABYAXOLOTL,
                EntityUtils.createGenericEntityAttributes());
        FabricDefaultAttributeRegistry.register(Declarar.BABYMEDAXOLOTL,
                EntityUtils.createGenericEntityAttributes());
        FabricDefaultAttributeRegistry.register(Declarar.BABYBIGAXOLOTL,
                EntityUtils.createGenericEntityAttributes());
        FabricDefaultAttributeRegistry.register(Declarar.ADOLAXOLOTL,
                EntityUtils.createGenericEntityAttributes());
        FabricDefaultAttributeRegistry.register(Declarar.CHADXOLOTL,
                EntityUtils.createGenericEntityAttributes());
        Declarar.specialability =  KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.axolotl.ability", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Z, // The keycode of the key
                "category.axolotl" // The translation key of the keybinding's category.
        ));
        ServerSidePacketRegistry.INSTANCE.register(useabilitypacket, (packetContext, attachedData) -> {//get blank trigger packet
            packetContext.getTaskQueue().execute(() -> {
                ((AxoBaseEntity)EntityRetainer.getEntity()).useAbility();
            });
        });
    }

}
//Can only survive in water. Instantly takes damage if out of the water
//Must kill 3 small fish before evolving
//
//2nd Stage:
//Can venture out of water for 10 seconds before taking damage
//Must kill a 2 drowned to evolve
//
//3rd Stage:
//Can survive on land for 2 minutes before taking damage
//Must kill 1 Zombie, 1 Skeleton, and 1 Spider to evolve
//
//4th Stage:
//Walks on 2 legs
//Can survive on both land and water always
//Must kill 1 Enderman, 1 Piglin, 1 Wither Skeleton, and 1 Blaze to evolve
//
//
//5th Stage:
//Survives in all climates and can survive in Lava as well