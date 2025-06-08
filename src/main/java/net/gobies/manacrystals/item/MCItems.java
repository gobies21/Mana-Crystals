package net.gobies.manacrystals.item;

import net.gobies.manacrystals.ManaCrystals;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MCItems {
    public static final DeferredRegister<Item> ITEMS;
    public static final RegistryObject<Item> ManaCrystal;
    public static final RegistryObject<Item> ManaCrystalShard;
    public static final RegistryObject<Item> ManaDust;

    public MCItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    static {
        ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ManaCrystals.MOD_ID);
        ManaCrystal = ITEMS.register("mana_crystal", () -> new ManaCrystalItem(new Item.Properties().rarity(Rarity.EPIC)));
        ManaCrystalShard = ITEMS.register("mana_shard", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));
        ManaDust = ITEMS.register("mana_dust", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    }
}