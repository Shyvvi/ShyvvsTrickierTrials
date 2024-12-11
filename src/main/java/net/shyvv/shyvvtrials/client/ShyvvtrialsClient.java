package net.shyvv.shyvvtrials.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.render.entity.WindChargeEntityRenderer;
import net.minecraft.particle.SimpleParticleType;
import net.shyvv.shyvvtrials.client.particle.LanceAttackParticle;
import net.shyvv.shyvvtrials.item.ModCallbacks;
import net.shyvv.shyvvtrials.registry.ModEntities;
import net.shyvv.shyvvtrials.client.item.ModModelPredicateProviders;
import net.shyvv.shyvvtrials.registry.ModParticles;

public class ShyvvtrialsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.CHAMBER_WIND_CHARGE, WindChargeEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.CHAMBER_FIRE_CHARGE, (context) -> {
            return new FlyingItemEntityRenderer<>(context, 3.0F, true);
        });
        ModModelPredicateProviders.registerModelPredicateProviders();
        ModParticles.clientInitialize();
        ModCallbacks.tooltipCallback();
    }
}
