package net.shyvv.shyvvtrials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.EquipmentTable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.shyvv.shyvvtrials.block.TrialSpawnerChanges;
import net.shyvv.shyvvtrials.config.ModConfigs;
import net.shyvv.shyvvtrials.item.components.LeveledKeyComponent;
import net.shyvv.shyvvtrials.registry.ModDataComponents;
import net.shyvv.shyvvtrials.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.*;

@Mixin(TrialSpawnerLogic.class)
public abstract class TrialSpawnerLogicMixin {

    @Unique
    TrialSpawnerLogic thisObject = (TrialSpawnerLogic)(Object)this;
    @Unique
    private int level;
    @Unique
    private boolean hasModifiedLoottable;

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
            int newAmp = 0;
            if(statusInstance != null) {
                newAmp = statusInstance.getAmplifier()+1;
            }
            m.addStatusEffect(new StatusEffectInstance(newStatus, 99999999, newAmp));
        }
        ItemStack newWeapon = m.getWeaponStack();
        if(newWeapon.isOf(Items.BOW)) {
            RegistryEntry<Enchantment> registryEntry = m.getWorld().getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.POWER);
            StatusEffectInstance strength = m.getStatusEffect(StatusEffects.STRENGTH);
            if(strength != null) {
                newWeapon.addEnchantment(registryEntry, strength.getAmplifier() * 3);
                m.equipStack(EquipmentSlot.MAINHAND, newWeapon);
            }
        }
    }

    @Inject(method = "trySpawnMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setPersistent()V", shift = At.Shift.AFTER))
    private void shyvvtrials$trialSpawnChange(ServerWorld world, BlockPos pos, CallbackInfoReturnable<Optional<UUID>> cir, @Local(ordinal = 0) MobEntity mobEntity) {
        hasModifiedLoottable = false;
        List<UUID> list = thisObject.getData().players.stream().toList();
        if (thisObject.isOminous() && !list.isEmpty()) {
            level = TrialSpawnerChanges.getLevel(world, list) + 1;
            if(level > 0) {
                scalingEffects(mobEntity, level + ModConfigs.OMINOUS_SPAWNER_DIFFICULTY_SCALE);
            }
        }
    }

    @Inject(method = "ejectLootTable", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;isEmpty()Z", shift = At.Shift.AFTER))
    private void shyvvtrials$trialSpawnerLootScaling(ServerWorld world, BlockPos pos, RegistryKey<LootTable> lootTable, CallbackInfo ci, @Local(ordinal = 0) ObjectArrayList<ItemStack> objectArrayList, @Local(ordinal = 0) LootTable lootTable2) {
        if(level > 0 && !hasModifiedLoottable) {
            for (int i = 0; i < level; i++) {
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder(world)).build(LootContextTypes.EMPTY);
                objectArrayList.add(lootTable2.generateLoot(lootContextParameterSet).getFirst());
            }
            hasModifiedLoottable = true;
        }
    }
}
