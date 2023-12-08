package net.sistr.zabutonr.client.model;

import com.google.common.collect.Sets;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.sistr.zabutonr.ZabutonR;


public class ZabutonEntityModelLayers {
    private static final String MAIN = "main";
    public static final EntityModelLayer ZABUTON = registerMain("zabuton");

    private static EntityModelLayer registerMain(String id) {
        return register(id, MAIN);
    }

    private static EntityModelLayer register(String id, String layer) {
        return create(id, layer);
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(new Identifier(ZabutonR.MOD_ID, id), layer);
    }
}
