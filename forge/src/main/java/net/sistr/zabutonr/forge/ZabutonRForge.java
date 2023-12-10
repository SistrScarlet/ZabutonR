package net.sistr.zabutonr.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sistr.zabutonr.ZabutonR;
import net.sistr.zabutonr.ZabutonRClient;
import net.sistr.zabutonr.client.model.ZabutonEntityModel;
import net.sistr.zabutonr.client.model.ZabutonEntityModelLayers;
import net.sistr.zabutonr.client.renderer.ZabutonREntityRenderer;
import net.sistr.zabutonr.setup.Registration;

@Mod(ZabutonR.MOD_ID)
public class ZabutonRForge {
    public ZabutonRForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(ZabutonR.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        ZabutonR.init();
    }

    @Mod.EventBusSubscriber(modid = ZabutonR.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ZabutonRClient.init();
        }

        @SubscribeEvent
        public static void registerEntityRenderer(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(Registration.ZABUTON_ENTITY.get(), ZabutonREntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerRenderLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ZabutonEntityModelLayers.ZABUTON, ZabutonEntityModel::getTexturedModelData);
        }
    }

}