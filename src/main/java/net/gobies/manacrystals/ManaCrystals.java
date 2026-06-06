package net.gobies.manacrystals;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.registries.CreativeTabRegistry;
import net.gobies.manacrystals.init.MCSounds;
import net.gobies.manacrystals.item.MCItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(ManaCrystals.MOD_ID)
public class ManaCrystals {

    public static final String MOD_ID = "manacrystals";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ManaCrystals(IEventBus modBus, ModContainer container) {
        MCItems.register(modBus);

        MCSounds.REGISTRY.register(modBus);

        modBus.addListener(ManaCrystals::BuildContents);

        container.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
    }

    @SubscribeEvent
    public static void BuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == (CreativeTabRegistry.MATERIALS_TAB.getKey())) {
            event.accept(MCItems.ManaDust.get());
            event.accept(MCItems.ManaCrystalShard.get());
            event.accept(MCItems.ManaCrystal.get());
        }
    }
}