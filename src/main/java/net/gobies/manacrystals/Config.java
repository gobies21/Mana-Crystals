package net.gobies.manacrystals;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ManaCrystals.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> MANA_CRYSTAL_MANA_INCREASE;
    public static float mana_crystal_mana_gain;
    public static ForgeConfigSpec.ConfigValue<Integer> MANA_CRYSTAL_MAX_USES;
    public static int mana_crystal_max_uses;

    public Config() {
    }


    @SubscribeEvent
    static void onLoad(ModConfigEvent.Loading configEvent) {
        mana_crystal_mana_gain = MANA_CRYSTAL_MANA_INCREASE.get().floatValue();
        mana_crystal_max_uses = MANA_CRYSTAL_MAX_USES.get();

    }

    static {
        BUILDER.push("Mana_Crystal");
        MANA_CRYSTAL_MANA_INCREASE = BUILDER.comment("Mana increase per mana crystal").define("Mana_Increase", 10.0);
        MANA_CRYSTAL_MAX_USES = BUILDER.comment("Maximum amount of times you can use mana crystals").define("Max_Uses", 10);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
