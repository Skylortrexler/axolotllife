package website.skylorbeck.minecraft.axolotl.models;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BabyBigModel extends AnimatedGeoModel
{

    @Override
    public Identifier getModelLocation(Object object) {
        return new Identifier("axolotl", "geo/beeg_axolotl.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        return new Identifier("axolotl", "textures/babymed/pig.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        return new Identifier("axolotl", "animations/babymed/pig.animation.json");
    }
}