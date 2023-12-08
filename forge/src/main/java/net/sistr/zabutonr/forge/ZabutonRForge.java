package net.sistr.zabutonr.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.sistr.zabutonr.ZabutonR;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sistr.zabutonr.ZabutonRClient;

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
    }

}