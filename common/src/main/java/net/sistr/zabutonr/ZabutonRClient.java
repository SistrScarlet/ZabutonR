package net.sistr.zabutonr;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sistr.zabutonr.client.model.ZabutonEntityModel;
import net.sistr.zabutonr.client.model.ZabutonEntityModelLayers;
import net.sistr.zabutonr.client.renderer.ZabutonREntityRenderer;
import net.sistr.zabutonr.setup.Registration;

@Environment(EnvType.CLIENT)
public class ZabutonRClient {

    public static void init() {
        EntityRendererRegistry.register(Registration.ZABUTON_ENTITY, ZabutonREntityRenderer::new);
        EntityModelLayerRegistry.register(ZabutonEntityModelLayers.ZABUTON, ZabutonEntityModel::getTexturedModelData);
    }

}
