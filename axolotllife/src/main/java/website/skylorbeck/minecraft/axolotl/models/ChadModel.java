package website.skylorbeck.minecraft.axolotl.models;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChadModel extends AnimatedGeoModel
{

    @Override
    public Identifier getModelLocation(Object object) {
        return new Identifier("axolotl", "geo/chadxolotl.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        return new Identifier("axolotl", "textures/adult/iron_golem.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        return new Identifier("axolotl", "animations/adult/irongolem.animation.json");
    }
}