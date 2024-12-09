package net.shyvv.shyvvtrials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.block.spawner.TrialSpawnerConfig;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mixin(TrialSpawnerState.class)
public abstract class TrialSpawnerStateMixin {

    @Unique
    TrialSpawnerState thisObject = (TrialSpawnerState)(Object)this;

    @Unique
    private int level = 0;

    @Unique
    private void setlevel(ServerWorld world, List<UUID> list) {
        Optional<Pair<PlayerEntity, RegistryEntry<StatusEffect>>> optional = TrialSpawnerData.findPlayerWithOmen(world, list);
        optional.ifPresent((pair) -> {
            PlayerEntity playerEntity = pair.getFirst();
            RegistryEntry<StatusEffect> registryEntry = StatusEffects.TRIAL_OMEN;
            StatusEffectInstance statusEffectInstance = playerEntity.getStatusEffect(registryEntry);
            if(statusEffectInstance != null) {
                this.level = statusEffectInstance.getAmplifier();
            }
        });
    }
//net.minecraft.block.spawner.TrialSpawnerData
//    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/spawner/TrialSpawnerLogic;ejectLo", shift = At.Shift.AFTER))
//    private void shyvvtrials$trialSpawnChange(ServerWorld world, BlockPos pos, CallbackInfoReturnable<Optional<UUID>> cir, @Local(ordinal = 0) MobEntity mobEntity) {
//        List<UUID> list = thisObject.getEntityDetector().detect(world, thisObject.getEntitySelector(), pos, (double)50, true);
//        if (thisObject.isOminous() && !list.isEmpty()) {
//            Optional<Pair<PlayerEntity, RegistryEntry<StatusEffect>>> optional = TrialSpawnerData.findPlayerWithOmen(world, list);
//            optional.ifPresent((pair) -> {
//                PlayerEntity playerEntity = pair.getFirst();
//                StatusEffectInstance statusEffectInstance = playerEntity.getStatusEffect(StatusEffects.TRIAL_OMEN);
//                if(statusEffectInstance != null) {
//                    this.level = statusEffectInstance.getAmplifier();
//                }
//            });
//
//            if(level > 0) {
//                scalingEffects(mobEntity, level);
//            }
//        }
//    }
}
