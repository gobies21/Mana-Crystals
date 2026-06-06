package net.gobies.manacrystals.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.event.LootTableLoadEvent;

public class LootTableHandler {

    public static void addLootWithLooting(LootTableLoadEvent event, String location, Item item, int minCount, int maxCount, float baseDropChance, float lootingBoostChance) {
        ResourceLocation lootTable = event.getName();
        ResourceLocation entityLocation = ResourceLocation.parse(location);

        if (lootTable.equals(entityLocation)) {
            HolderLookup.Provider registries = event.getRegistries();
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .add(LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                            .when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, baseDropChance, lootingBoostChance)));

            event.getTable().addPool(poolBuilder.build());
        }
    }

    public static void addLoot(LootTableLoadEvent event, String location, Item item, int minCount, int maxCount, float baseDropChance) {
        ResourceLocation lootTable = event.getName();
        ResourceLocation entityLocation = ResourceLocation.parse(location);

        if (lootTable.equals(entityLocation)) {
            LootPool.Builder poolBuilder = LootPool.lootPool()
                    .add(LootItem.lootTableItem(item)
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)))
                            .when(LootItemRandomChanceCondition.randomChance(baseDropChance)));

            event.getTable().addPool(poolBuilder.build());
        }
    }
}