package website.skylorbeck.minecraft.axolotl.models;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyModel extends AnimatedGeoModel
{

    @Override
    public Identifier getModelLocation(Object object) {
        return new Identifier("axolotl", "geo/baby.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        return new Identifier("axolotl", "textures/baby/steve.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        return new Identifier("axolotl", "animations/baby/steve.animation.json");
    }
}