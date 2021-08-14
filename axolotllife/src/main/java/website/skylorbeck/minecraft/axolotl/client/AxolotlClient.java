package website.skylorbeck.minecraft.axolotl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
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

    }
}
