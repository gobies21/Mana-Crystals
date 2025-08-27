package net.gobies.manacrystals;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.registries.CreativeTabRegistry;
import net.gobies.manacrystals.init.MCSounds;
import net.gobies.manacrystals.item.MCItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ManaCrystals.MOD_ID)
public class ManaCrystals {

    public static final String MOD_ID = "manacrystals";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ManaCrystals() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        MCItems.register(modBus);

        MCSounds.REGISTRY.register(modBus);

        modBus.addListener(ManaCrystals::BuildContents);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);
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