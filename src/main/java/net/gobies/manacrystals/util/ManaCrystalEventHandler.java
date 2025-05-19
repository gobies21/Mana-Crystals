package net.gobies.manacrystals.util;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.UUID;

import static net.gobies.manacrystals.item.ManaCrystalItem.GLOBAL_USE_COUNT_TAG;
import static net.gobies.manacrystals.item.ManaCrystalItem.MANA_INCREASE;

@Mod.EventBusSubscriber
public class ManaCrystalEventHandler {

    @SubscribeEvent
    public static void onPlayerDeath(PlayerEvent.Clone event) {
        CompoundTag oldPlayerData = event.getOriginal().getPersistentData();
        CompoundTag modData = oldPlayerData.getCompound("ManaCrystalModData");
        event.getEntity().getPersistentData().put("ManaCrystalModData", modData);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        CompoundTag playerData = event.getEntity().getPersistentData();
        CompoundTag modData = playerData.getCompound("ManaCrystalModData");
        int globalUseCount = modData.getInt(GLOBAL_USE_COUNT_TAG);

        Player player = event.getEntity();

        for (int i = 1; i <= globalUseCount; i++) {
            UUID uniqueUUID = UUID.nameUUIDFromBytes(("ManaCrystalManaIncrease" + i).getBytes());
            modData.getInt(String.valueOf(MANA_INCREASE));
            AttributeModifier modifier = new AttributeModifier(uniqueUUID, "ManaCrystalManaIncrease", MANA_INCREASE, AttributeModifier.Operation.ADDITION);
            Objects.requireNonNull(player.getAttribute(AttributeRegistry.MAX_MANA.get())).addPermanentModifier(modifier);
        }
    }
}