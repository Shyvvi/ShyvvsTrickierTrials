package net.shyvv.shyvvtrials.registry;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.client.particle.LanceAttackParticle;

public class ModParticles {
    public static void register(SimpleParticleType particle, String id) {
        Identifier particleID = Identifier.of(Shyvvtrials.MOD_ID, id);

        Registry.register(Registries.PARTICLE_TYPE, particleID, particle);
    }

    public static final SimpleParticleType LANCE_IMPACT = FabricParticleTypes.simple();

    public static void clientInitialize() {
        ParticleFactoryRegistry.getInstance().register(LANCE_IMPACT, LanceAttackParticle.Factory::new);
    }

    public static void initialize() {
        register(LANCE_IMPACT, "lance_impact");
    }
}
