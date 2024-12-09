package net.shyvv.shyvvtrials.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;

public class ModSounds {

    public static SoundEvent register(String id) {

        Identifier soundID = Identifier.of(Shyvvtrials.MOD_ID, id);

        SoundEvent registeredSound = Registry.register(Registries.SOUND_EVENT, soundID, SoundEvent.of(soundID));

        return registeredSound;
    }

    public static final SoundEvent CHARGE_CHAMBER_CHARGE = register("charge_chamber_charge");
    public static final SoundEvent CHARGE_CHAMBER_FULL = register("charge_chamber_full");
    public static final SoundEvent CHARGE_CHAMBER_MAX = register("charge_chamber_max");
    public static final SoundEvent CHARGE_CHAMBER_SHOOT = register("charge_chamber_shoot");
    public static final SoundEvent LANCE_IMPACT = register("lance_impact");
    public static final SoundEvent METAL_PIPE = register("metal_pipe");

    public static void initialize() {}
}
