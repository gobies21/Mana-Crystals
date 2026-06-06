package net.gobies.manacrystals.item;

import net.gobies.manacrystals.ManaCrystals;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MCItems {
    public static final DeferredRegister<Item> ITEMS;
    public static final DeferredHolder<Item, ManaCrystalItem> ManaCrystal;
    public static final DeferredHolder<Item, Item> ManaCrystalShard;
    public static final DeferredHolder<Item, Item> ManaDust;

    public MCItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        ITEMS = DeferredRegister.create(Registries.ITEM, ManaCrystals.MOD_ID);
        ManaCrystal = ITEMS.register("mana_crystal", () -> new ManaCrystalItem(new Item.Properties().rarity(Rarity.EPIC)));
        ManaCrystalShard = ITEMS.register("mana_shard", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ManaDust = ITEMS.register("mana_dust", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    }
}