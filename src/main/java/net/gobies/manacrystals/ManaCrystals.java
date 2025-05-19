package net.gobies.manacrystals;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.registries.CreativeTabRegistry;
import net.gobies.manacrystals.init.ManaCrystalSounds;
import net.gobies.manacrystals.item.ModItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import static net.gobies.manacrystals.ManaCrystals.MOD_ID;

@Mod(MOD_ID)
public class ManaCrystals {

    public static final String MOD_ID = "manacrystals";
    private static final Logger LOGGER = LogUtils.getLogger();

    public ManaCrystals() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        ModItems.register(modBus);

        ManaCrystalSounds.REGISTRY.register(modBus);

        modBus.addListener(ManaCrystals::BuildContents);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public static void BuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == (CreativeTabRegistry.MATERIALS_TAB.getKey())) {
            event.accept(ModItems.ManaDust.get());
            event.accept(ModItems.ManaCrystalShard.get());
            event.accept(ModItems.ManaCrystal.get());
        }
    }
}