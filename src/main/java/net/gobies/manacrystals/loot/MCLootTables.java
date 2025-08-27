package net.gobies.manacrystals.loot;

import net.gobies.manacrystals.CommonConfig;
import net.gobies.manacrystals.ManaCrystals;
import net.gobies.manacrystals.item.MCItems;
import net.gobies.manacrystals.util.LootTableHandler;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ManaCrystals.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MCLootTables {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (CommonConfig.ENABLE_ADDITIONAL_LOOT.get()) {
            LootTableHandler.addLootWithLooting(event, "irons_spellbooks:entities/necromancer", MCItems.ManaDust.get(), 3, 8, 0.25F, 0.01F);
            LootTableHandler.addLootWithLooting(event, "irons_spellbooks:entities/necromancer", MCItems.ManaCrystalShard.get(), 1, 2, 0.10F, 0.01F);

            LootTableHandler.addLootWithLooting(event, "irons_spellbooks:entities/dead_king", MCItems.ManaCrystalShard.get(), 3, 9, 0.75F, 0.01F);
            LootTableHandler.addLootWithLooting(event, "irons_spellbooks:entities/dead_king", MCItems.ManaCrystal.get(), 1, 2, 0.50F, 0.01F);

            LootTableHandler.addLoot(event, "irons_spellbooks:chests/generic_magic_treasure", MCItems.ManaDust.get(), 6, 12, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_generic_loot", MCItems.ManaDust.get(), 6, 12, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_good_loot", MCItems.ManaDust.get(), 6, 12, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_treasure_loot", MCItems.ManaDust.get(), 6, 12, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_ancient_city_loot", MCItems.ManaDust.get(), 3, 9, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_nether_loot", MCItems.ManaDust.get(), 3, 9, 0.25F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/catacombs/crypt_loot", MCItems.ManaDust.get(), 3, 9, 0.65F);

            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_treasure_loot", MCItems.ManaCrystalShard.get(), 1, 8, 0.05F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_ancient_city_loot", MCItems.ManaCrystalShard.get(), 1, 4, 0.03F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/additional_nether_loot", MCItems.ManaCrystalShard.get(), 1, 4, 0.05F);
            LootTableHandler.addLoot(event, "irons_spellbooks:chests/catacombs/crypt_loot", MCItems.ManaCrystalShard.get(), 2, 8, 0.55F);
        }
    }
}