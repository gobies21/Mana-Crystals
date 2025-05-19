package net.gobies.manacrystals.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ManaCrystalSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY;
    public static final RegistryObject<SoundEvent> ManaCrystalUse;

    public ManaCrystalSounds() {
    }

    static {
        REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "manacrystals");
        ManaCrystalUse = REGISTRY.register("mana_crystal_use", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("manacrystals", "mana_crystal_use")));
    }
}
