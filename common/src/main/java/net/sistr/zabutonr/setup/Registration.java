package net.sistr.zabutonr.setup;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.sistr.zabutonr.entity.ZabutonEntity;
import net.sistr.zabutonr.item.ZabutonItem;

import static net.sistr.zabutonr.ZabutonR.MOD_ID;

public class Registration {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static void init() {
        ITEMS.register();
        ENTITY_TYPES.register();
    }

    public static final RegistrySupplier<Item> WHITE_ZABUTON_ITEM = ITEMS.register("white_zabuton",
            () -> new ZabutonItem(new Item.Settings()));

    public static final RegistrySupplier<EntityType<ZabutonEntity>> WHITE_ZABUTON_ENTITY
            = ENTITY_TYPES.register("white_zabuton", () ->
            EntityType.Builder.<ZabutonEntity>create(ZabutonEntity::new, SpawnGroup.MISC)
                    .setDimensions(12 / 16f, 3 / 16f)
                    .build("white_zabuton"));
}
