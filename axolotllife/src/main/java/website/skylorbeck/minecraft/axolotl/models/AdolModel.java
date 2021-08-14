package website.skylorbeck.minecraft.axolotl.models;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AdolModel extends AnimatedGeoModel
{

    @Override
    public Identifier getModelLocation(Object object) {
        return new Identifier("axolotl", "geo/humanoid_axolotl.geo.json");
    }

    @Override
    public Identifier getTextureLocation(Object object) {
        return new Identifier("axolotl", "textures/adolescent/iron_golem.png");
    }

    @Override
    public Identifier getAnimationFileLocation(Object animatable) {
        return new Identifier("axolotl", "animations/adolescent/irongolem.animation.json");
    }
}