package net.shyvv.shyvvtrials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentTable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.EnchantCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.shyvv.shyvvtrials.item.ItemUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(TrialSpawnerLogic.class)
public abstract class TrialSpawnerLogicMixin {

    @Unique
    TrialSpawnerLogic thisObject = (TrialSpawnerLogic)(Object)this;
    @Unique
    public int level = 0;

    @Unique
    private List<RegistryEntry<StatusEffect>> getBuffsList() {
        List<RegistryEntry<StatusEffect>> list = new ArrayList<>();
        list.add(StatusEffects.STRENGTH);
        list.add(StatusEffects.SPEED);
        list.add(StatusEffects.ABSORPTION);
        return list;
    }

    @Unique
    private void scalingEffects(MobEntity m, int scale) {
        List<RegistryEntry<StatusEffect>> list = getBuffsList();
        Random rand = new Random();
        for(int i = 0; i<=scale; i++) {
            RegistryEntry<StatusEffect> newStatus = list.get(rand.nextInt(0, list.size()));
            StatusEffectInstance statusInstance = m.getStatusEffect(newStatus);
            int newAmp = 1;
            if(statusInstance != null) {
                newAmp = statusInstance.getAmplifier()+1;
            }
            m.addStatusEffect(new StatusEffectInstance(newStatus, 99999999, newAmp));
        }

        //m.setEquipmentFromTable(new EquipmentTable(.));
    }

    @Inject(method = "trySpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setPersistent()V", shift = At.Shift.AFTER))
    private void shyvvtrials$trialSpawnChange(ServerWorld world, BlockPos pos, CallbackInfoReturnable<Optional<UUID>> cir, @Local(ordinal = 0) MobEntity mobEntity) {
        List<UUID> list = thisObject.getEntityDetector().detect(world, thisObject.getEntitySelector(), pos, (double)50, true);
        if (thisObject.isOminous() && !list.isEmpty()) {
            Optional<Pair<PlayerEntity, RegistryEntry<StatusEffect>>> optional = TrialSpawnerData.findPlayerWithOmen(world, list);
            optional.ifPresent((pair) -> {
                PlayerEntity playerEntity = pair.getFirst();
                StatusEffectInstance statusEffectInstance = playerEntity.getStatusEffect(StatusEffects.TRIAL_OMEN);
                if(statusEffectInstance != null) {
                    this.level = statusEffectInstance.getAmplifier();
                }
            });

            if(level > 0) {
                scalingEffects(mobEntity, level);
            }
        }
    }
}
