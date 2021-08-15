package website.skylorbeck.minecraft.axolotl.renderers;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import website.skylorbeck.minecraft.axolotl.entities.BabyMedAxolotl;
import website.skylorbeck.minecraft.axolotl.models.BabyMedModel;

public class BabyMedRenderer extends GeoEntityRenderer<BabyMedAxolotl> {
    public BabyMedRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BabyMedModel());
        this.shadowRadius = 1F; //change 0.7 to the desired shadow size.
    }

    @Override
    public RenderLayer getRenderType(BabyMedAxolotl animatable, float partialTicks, MatrixStack stack,
                                      VertexConsumerProvider renderTypeBuffer,  VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}