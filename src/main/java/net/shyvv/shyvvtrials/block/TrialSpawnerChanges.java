package net.shyvv.shyvvtrials.block;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrialSpawnerChanges {

    public static int getLevel(ServerWorld world, List<UUID> list) {
        final int[] i = {0};
        Optional<Pair<PlayerEntity, RegistryEntry<StatusEffect>>> optional = TrialSpawnerData.findPlayerWithOmen(world, list);
        optional.ifPresent((pair) -> {
            PlayerEntity playerEntity = pair.getFirst();
            RegistryEntry<StatusEffect> registryEntry = StatusEffects.TRIAL_OMEN;
            StatusEffectInstance statusEffectInstance = playerEntity.getStatusEffect(registryEntry);
            if(statusEffectInstance != null) {
                i[0] = statusEffectInstance.getAmplifier();
            }
        });
        return i[0];
    }
}
