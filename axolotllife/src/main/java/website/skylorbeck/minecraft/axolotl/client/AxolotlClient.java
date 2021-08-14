package website.skylorbeck.minecraft.axolotl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;
import software.bernie.example.registry.EntityRegistry;
import website.skylorbeck.minecraft.axolotl.Declarar;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;
import website.skylorbeck.minecraft.axolotl.renderers.BabyRenderer;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class AxolotlClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Declarar.BABYAXOLOTL,BabyRenderer::new);

    }
}
