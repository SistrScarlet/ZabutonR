package net.sistr.zabutonr.tag;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sistr.zabutonr.ZabutonR;

public class ZabutonTags {
    public static final TagKey<Item> ZABUTON = of("zabuton");

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, new Identifier(ZabutonR.MOD_ID, id));
    }

}
