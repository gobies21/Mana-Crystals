package net.gobies.manacrystals.util;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.gobies.manacrystals.item.ManaCrystalItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;
import java.util.UUID;

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
        int globalUseCount = modData.getInt(ManaCrystalItem.GLOBAL_USE_COUNT_TAG);

        Player player = event.getEntity();

        for (int i = 1; i <= globalUseCount; i++) {
            UUID uniqueUUID = UUID.nameUUIDFromBytes(("ManaCrystalManaIncrease" + i).getBytes());
            modData.getInt(String.valueOf(ManaCrystalItem.getManaIncrease()));
            AttributeModifier modifier = new AttributeModifier(uniqueUUID, "ManaCrystalManaIncrease", ManaCrystalItem.getManaIncrease(), AttributeModifier.Operation.ADDITION);
            Objects.requireNonNull(player.getAttribute(AttributeRegistry.MAX_MANA.get())).addPermanentModifier(modifier);
        }
    }
}