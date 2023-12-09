package net.sistr.zabutonr.client.renderer;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.sistr.zabutonr.ZabutonR;
import net.sistr.zabutonr.client.model.ZabutonEntityModel;
import net.sistr.zabutonr.client.model.ZabutonEntityModelLayers;
import net.sistr.zabutonr.entity.ZabutonEntity;
import net.sistr.zabutonr.util.Color;

public class ZabutonREntityRenderer extends EntityRenderer<ZabutonEntity> {
    private static final Identifier[] TEXTURES = new Identifier[16];
    private final ZabutonEntityModel MODEL;

    public ZabutonREntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        MODEL = new ZabutonEntityModel(ctx.getPart(ZabutonEntityModelLayers.ZABUTON));
    }

    @Override
    public void render(ZabutonEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - yaw));
        matrices.scale(-1.0f, -1.0f, 1.0f);
        matrices.translate(0.0f, -1.501f, 0.0f);
        MODEL.setAngles(entity, tickDelta, 0.0f, -0.1f, 0.0f, 0.0f);
        MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(getTexture(entity))),
                light, OverlayTexture.DEFAULT_UV,
                1, 1, 1, 1);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(ZabutonEntity entity) {
        return TEXTURES[entity.getColor().id];
    }

    static {
        for (Color color : Color.colors) {
            TEXTURES[color.id] = new Identifier(ZabutonR.MOD_ID,
                    "textures/entity/zabuton/" + color.name + "_zabuton.png");
        }
    }
}
