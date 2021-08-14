package website.skylorbeck.minecraft.axolotl;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import software.bernie.example.registry.EntityRegistryBuilder;
import software.bernie.geckolib3.GeckoLib;
import website.skylorbeck.minecraft.axolotl.entities.*;

public class Declarar {
    public static final EntityType<BabyAxolotl> BABYAXOLOTL = buildEntity(BabyAxolotl::new,BabyAxolotl.class,0.5f,0.5f, SpawnGroup.CREATURE);
    public static final EntityType<BabyMedAxolotl> BABYMEDAXOLOTL = buildEntity(BabyMedAxolotl::new,BabyMedAxolotl.class,0.5f,0.5f, SpawnGroup.CREATURE);
    public static final EntityType<BabyBigAxolotl> BABYBIGAXOLOTL = buildEntity(BabyBigAxolotl::new,BabyBigAxolotl.class,1f,1f, SpawnGroup.CREATURE);
    public static final EntityType<AdolAxolotl> ADOLAXOLOTL = buildEntity(AdolAxolotl::new,AdolAxolotl.class,1f,1f, SpawnGroup.CREATURE);
    public static final EntityType<ChadAxolotl> CHADXOLOTL = buildEntity(ChadAxolotl::new,ChadAxolotl.class,1f,1f, SpawnGroup.CREATURE);


    public static <T extends Entity> EntityType<T> buildEntity(EntityType.EntityFactory<T> entity, Class<T> entityClass,
                                                               float width, float height, SpawnGroup group) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            String name = entityClass.getSimpleName().toLowerCase();
            return EntityRegistryBuilder.<T>createBuilder(new Identifier(GeckoLib.ModID, name)).entity(entity)
                    .category(group).dimensions(EntityDimensions.changing(width, height)).build();
        }
        return null;
    }
}
