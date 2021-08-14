package website.skylorbeck.minecraft.axolotl;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import software.bernie.example.EntityUtils;
import software.bernie.example.registry.EntityRegistry;
import software.bernie.example.registry.EntityRegistryBuilder;
import software.bernie.geckolib3.GeckoLib;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;

public class Axolotl implements ModInitializer {
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        FabricDefaultAttributeRegistry.register(Declarar.BABYAXOLOTL,
                EntityUtils.createGenericEntityAttributes());
    }

}
