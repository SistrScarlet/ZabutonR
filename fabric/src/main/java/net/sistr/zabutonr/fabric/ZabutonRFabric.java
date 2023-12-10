package net.sistr.zabutonr.fabric;

import dev.architectury.registry.client.level.entity.EntityModelLayerRegistry;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sistr.zabutonr.ZabutonR;
import net.fabricmc.api.ModInitializer;
import net.sistr.zabutonr.ZabutonRClient;
import net.sistr.zabutonr.client.model.ZabutonEntityModel;
import net.sistr.zabutonr.client.model.ZabutonEntityModelLayers;
import net.sistr.zabutonr.client.renderer.ZabutonREntityRenderer;
import net.sistr.zabutonr.setup.Registration;

public class ZabutonRFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        ZabutonR.init();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        //Forge側で動作しないため分けて書く
        EntityRendererRegistry.register(Registration.ZABUTON_ENTITY, ZabutonREntityRenderer::new);
        EntityModelLayerRegistry.register(ZabutonEntityModelLayers.ZABUTON, ZabutonEntityModel::getTexturedModelData);
        ZabutonRClient.init();
    }
}