package website.skylorbeck.minecraft.axolotl.renderers;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoReplacedEntityRenderer;
import website.skylorbeck.minecraft.axolotl.entities.BabyAxolotl;
import website.skylorbeck.minecraft.axolotl.models.BabyModel;

public class BabyRenderer extends GeoEntityRenderer<BabyAxolotl> {
    public BabyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BabyModel());
    }
    @Override
    public RenderLayer getRenderType(BabyAxolotl animatable, float partialTicks, MatrixStack stack,
                                     @Nullable VertexConsumerProvider renderTypeBuffer, @Nullable VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}
