package net.sistr.zabutonr.setup;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKeys;
import net.sistr.zabutonr.entity.ZabutonEntity;
import net.sistr.zabutonr.item.ZabutonItem;
import net.sistr.zabutonr.util.Color;

import static net.sistr.zabutonr.ZabutonR.MOD_ID;

public class Registration {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(MOD_ID, RegistryKeys.ITEM);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(MOD_ID, RegistryKeys.ENTITY_TYPE);

    public static void init() {
        ITEMS.register();
        ENTITY_TYPES.register();

        CreativeTabRegistry.append(ItemGroups.FUNCTIONAL, ZABUTON_ITEMS);
    }

    private static final RegistrySupplier<ZabutonItem>[] ZABUTON_ITEMS = registerZabutons();

    public static RegistrySupplier<ZabutonItem>[] registerZabutons() {
        //<ZabutonItem>つけるとエラーが出る。なんでや？
        RegistrySupplier<ZabutonItem>[] zabutons = new RegistrySupplier[16];
        for (Color color : Color.values()) {
            zabutons[color.id] = ITEMS.register(color.name + "_zabuton",
                    () -> new ZabutonItem(new Item.Settings(), color));
        }
        return zabutons;
    }

    public static final RegistrySupplier<EntityType<ZabutonEntity>> ZABUTON_ENTITY
            = ENTITY_TYPES.register("zabuton", () ->
            EntityType.Builder.<ZabutonEntity>create(ZabutonEntity::new, SpawnGroup.MISC)
                    .setDimensions(12 / 16f, 3 / 16f)
                    .build("zabuton"));


    public static RegistrySupplier<ZabutonItem> getZabutonRegistry(Color color) {
        return ZABUTON_ITEMS[color.id];
    }

    public static ZabutonItem getZabuton(Color color) {
        return getZabutonRegistry(color).get();
    }
}
