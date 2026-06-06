package net.gobies.manacrystals.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MCSounds {

    public static final DeferredRegister<SoundEvent> REGISTRY;
    public static final DeferredHolder<SoundEvent, SoundEvent> ManaCrystalUse;

    public MCSounds() {
    }

    static {
        REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, "manacrystals");
        ManaCrystalUse = REGISTRY.register("mana_crystal_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.parse("manacrystals:mana_crystal_use")));
    }
}
