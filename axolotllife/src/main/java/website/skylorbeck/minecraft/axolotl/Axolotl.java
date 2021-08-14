package website.skylorbeck.minecraft.axolotl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import software.bernie.example.EntityUtils;
import software.bernie.geckolib3.GeckoLib;

public class Axolotl implements ModInitializer {
    @Override
    public void onInitialize() {
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
    }

}
