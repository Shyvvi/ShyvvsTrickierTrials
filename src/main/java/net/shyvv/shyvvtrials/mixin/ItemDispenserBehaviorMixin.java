package net.shyvv.shyvvtrials.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TrialSpawnerBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.TrialSpawnerBlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.shyvv.shyvvtrials.block.TrialSpawnerChanges;
import net.shyvv.shyvvtrials.item.components.LeveledKeyComponent;
import net.shyvv.shyvvtrials.registry.ModDataComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.UUID;

@Mixin(ItemDispenserBehavior.class)
public abstract class ItemDispenserBehaviorMixin {

    @Inject(method = "spawnItem", at = @At(value = "TAIL"))
    private static void shyvvtrials$trialEjectLoot(World world, ItemStack stack, int speed, Direction side, Position pos, CallbackInfo cir) {
        BlockEntity spawnerState = world.getBlockEntity(new BlockPos((int) pos.getX(), (int) pos.getY()-2, (int) pos.getZ()));
        if(spawnerState instanceof TrialSpawnerBlockEntity b && stack.getItem() == Items.OMINOUS_TRIAL_KEY) {
            List<UUID> list = b.getSpawner().getData().players.stream().toList();
            int level = TrialSpawnerChanges.getLevel((ServerWorld) world, list)+1;
            if(level > 0) {
                ComponentMap map = ComponentMap.builder().add(ModDataComponents.LEVELED_KEY, new LeveledKeyComponent(level, true)).build();
                stack.applyComponentsFrom(map);
            }
        }
    }
}
