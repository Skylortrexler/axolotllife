package website.skylorbeck.minecraft.axolotl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.impl.networking.ServerSidePacketRegistryImpl;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import software.bernie.example.EntityUtils;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;
import website.skylorbeck.minecraft.axolotl.entities.*;

import static net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register;

public class Axolotl implements ModInitializer {
    public static Identifier useabilitypacket = new Identifier("axolotl","useability");
    public static Identifier setmodel = new Identifier("axolotl","setmodel");

    @Override
    public void onInitialize() {
        ServerSidePacketRegistryImpl.INSTANCE.register(useabilitypacket, (packetContext, attachedData) -> {
            packetContext.getTaskQueue().execute(() -> {
                PlayerEntity playerEntity = packetContext.getPlayer();
                ((AxoBaseEntity)((PlayerEntityAccessor)playerEntity).getStoredEntity()).useAbility();
            });
        });
        GeckoLib.initialize();
        register(Declarar.BABYAXOLOTL,EntityUtils.createGenericEntityAttributes());
        register(Declarar.BABYMEDAXOLOTL, EntityUtils.createGenericEntityAttributes());
        register(Declarar.BABYBIGAXOLOTL,EntityUtils.createGenericEntityAttributes());
        register(Declarar.ADOLAXOLOTL, EntityUtils.createGenericEntityAttributes());
        register(Declarar.CHADXOLOTL,EntityUtils.createGenericEntityAttributes());
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