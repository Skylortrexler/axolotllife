package website.skylorbeck.minecraft.axolotl.renderers;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import website.skylorbeck.minecraft.axolotl.entities.BabyBigAxolotl;
import website.skylorbeck.minecraft.axolotl.models.BabyBigModel;

public class BabyBigRenderer extends GeoEntityRenderer<BabyBigAxolotl> {
    public BabyBigRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BabyBigModel());
        this.shadowRadius = 1F; //change 0.7 to the desired shadow size.
    }

    @Override
    public RenderLayer getRenderType(BabyBigAxolotl animatable, float partialTicks, MatrixStack stack,
                                      VertexConsumerProvider renderTypeBuffer,  VertexConsumer vertexBuilder,
                                     int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(this.getTextureLocation(animatable));
    }
}