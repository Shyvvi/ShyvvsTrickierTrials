package net.shyvv.shyvvtrials.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.VaultBlockEntity;
import net.minecraft.block.enums.TrialSpawnerState;
import net.minecraft.block.spawner.TrialSpawnerConfig;
import net.minecraft.block.spawner.TrialSpawnerData;
import net.minecraft.block.spawner.TrialSpawnerLogic;
import net.minecraft.block.vault.VaultConfig;
import net.minecraft.block.vault.VaultServerData;
import net.minecraft.block.vault.VaultSharedData;
import net.minecraft.component.ComponentMap;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.shyvv.shyvvtrials.block.TrialSpawnerChanges;
import net.shyvv.shyvvtrials.registry.ModDataComponents;
import net.shyvv.shyvvtrials.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Mixin(VaultBlockEntity.Server.class)
public class VaultBlockEntityMixin {

    @Unique
    private static int level = 0;

    @Inject(method = "isValidKey", at = @At("HEAD"), cancellable = true)
    private static void shyvvtrials$leveledKeyComponentChanges(VaultConfig config, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        level = 0;
        ComponentMap map = stack.getComponents();
        if(map.contains(ModDataComponents.LEVELED_KEY) && stack.isOf(config.keyItem().getItem()) && stack.getCount() >= config.keyItem().getCount()) {
            level = map.get(ModDataComponents.LEVELED_KEY).getLevel()+1;
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tryUnlock", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", shift = At.Shift.AFTER))
    private static void shyvvtrials$leveledKeyLootTables(ServerWorld world, BlockPos pos, BlockState state, VaultConfig config, VaultServerData serverData, VaultSharedData sharedData, PlayerEntity player, ItemStack stack, CallbackInfo ci, @Local(ordinal=0) List<ItemStack> list) {
        if(level > 0) {
            for (int i = 0; i < level; i++) {
                LootTable lootTable = world.getServer().getReloadableRegistries().getLootTable(config.lootTable());
                LootContextParameterSet lootContextParameterSet = (new LootContextParameterSet.Builder(world)).build(LootContextTypes.EMPTY);
                list.add(lootTable.generateLoot(lootContextParameterSet).getFirst());
            }
        }
        for (ItemStack item : list) {
            if(item.isOf(Items.HEAVY_CORE)) {
                ItemStack denseIngotReplacement = ModItems.DENSE_INGOT.getDefaultStack();
                denseIngotReplacement.setCount(2+world.getRandom().nextInt(4));
                list.remove(item);
                list.add(denseIngotReplacement);
            }
        }
    }
}
