package net.gobies.manacrystals.util;

import net.gobies.manacrystals.item.ManaCrystalItem;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber
public class MCEventHandler {

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (!(event.getEntity() instanceof ServerPlayer newPlayer)) return;
        ServerPlayer oldPlayer = (ServerPlayer) event.getOriginal();
        int crystalUses = ManaCrystalItem.getManaCrystalUses(oldPlayer);
        newPlayer.getPersistentData().putInt(ManaCrystalItem.MANA_CRYSTAL_USES, crystalUses);

        if (!event.isWasDeath()) {
            ManaCrystalItem.applyManaBonus(newPlayer);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaCrystalItem.applyManaBonus(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ManaCrystalItem.applyManaBonus(player);
        }
    }
}