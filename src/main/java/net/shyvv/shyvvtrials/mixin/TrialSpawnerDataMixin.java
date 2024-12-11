package net.shyvv.shyvvtrials.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.spawner.TrialSpawnerConfig;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.shyvv.shyvvtrials.block.TrialSpawnerChanges;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mixin(TrialSpawnerData.class)
public abstract class TrialSpawnerDataMixin {

    @Unique
    TrialSpawnerData thisObject = (TrialSpawnerData)(Object)this;

    @Unique
    private int level = 0;

    @Inject(method = "canSpawnMore", at = @At("HEAD"), cancellable = true)
    public void shyvvtrials$spawnAdjust1(ServerWorld world, TrialSpawnerConfig config, int additionalPlayers, CallbackInfoReturnable<Boolean> cir) {
        List<UUID> list = thisObject.players.stream().toList();
        level = TrialSpawnerChanges.getLevel(world, list);
        if(level > 0) {
            cir.setReturnValue(world.getTime() >= thisObject.nextMobSpawnsAt && thisObject.spawnedMobsAlive.size() < config.getSimultaneousMobs(additionalPlayers)+(level/1.4)) ;
        }
    }

    @Inject(method = "hasSpawnedAllMobs", at = @At("HEAD"), cancellable = true)
    public void shyvvtrials$spawnAdjust2(TrialSpawnerConfig config, int additionalPlayers, CallbackInfoReturnable<Boolean> cir) {
        if(level > 0) {
            cir.setReturnValue(thisObject.totalSpawnedMobs >= config.getTotalMobs(additionalPlayers)+(level*2));
        }
    }


    @Inject(method = "applyTrialOmen", at = @At("HEAD"))
    private static void shyvvtrials$trialOmenChange(PlayerEntity player, CallbackInfo ci) {
        StatusEffectInstance statusEffectInstance = player.getStatusEffect(StatusEffects.BAD_OMEN);
        if (statusEffectInstance != null) {
            int i = statusEffectInstance.getAmplifier() + 1;
            int j = 18000 * i;
            player.removeStatusEffect(StatusEffects.BAD_OMEN);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.TRIAL_OMEN, j, statusEffectInstance.getAmplifier()));
        }
    }
}
