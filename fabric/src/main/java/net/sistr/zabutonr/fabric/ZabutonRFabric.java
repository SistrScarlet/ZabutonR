package net.sistr.zabutonr.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.sistr.zabutonr.ZabutonR;
import net.fabricmc.api.ModInitializer;
import net.sistr.zabutonr.ZabutonRClient;

public class ZabutonRFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        ZabutonR.init();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        ZabutonRClient.init();
    }
}