package net.gobies.manacrystals;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = ManaCrystals.MOD_ID)
public class CommonConfig {
    private static final String FILENAME = "manacrystals-common.toml";

    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SPEC;

    public static ModConfigSpec.ConfigValue<Double> MANA_CRYSTAL_MANA_INCREASE;
    public static float mana_crystal_mana_gain;
    public static ModConfigSpec.ConfigValue<Integer> MANA_CRYSTAL_MAX_USES;
    public static int mana_crystal_max_uses;
    public static ModConfigSpec.ConfigValue<Boolean> ENABLE_ADDITIONAL_LOOT;
    public static boolean enable_additional_loot;

    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        if (configEvent.getConfig().getFileName().equals(FILENAME)) {
            mana_crystal_mana_gain = MANA_CRYSTAL_MANA_INCREASE.get().floatValue();
            mana_crystal_max_uses = MANA_CRYSTAL_MAX_USES.get();
            enable_additional_loot = ENABLE_ADDITIONAL_LOOT.get();
        }
    }

    static {
        BUILDER.push("Mana_Crystal");
        MANA_CRYSTAL_MANA_INCREASE = BUILDER.comment("Mana increase per mana crystal").define("Mana_Increase", 10.0);
        MANA_CRYSTAL_MAX_USES = BUILDER.comment("Maximum amount of times you can use mana crystals").define("Max_Uses", 10);
        BUILDER.pop();

        BUILDER.push("General");
        ENABLE_ADDITIONAL_LOOT = BUILDER.comment("Enable additional loot for mana crystal related items in irons spellbooks loot tables").define("Enable_Additional_Loot", true);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
