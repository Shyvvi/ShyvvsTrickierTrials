package net.shyvv.shyvvtrials.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.component.ComponentMap;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.shyvv.shyvvtrials.registry.ModDataComponents;
import net.shyvv.shyvvtrials.registry.ModItems;

import java.util.Objects;

public class ModCallbacks {
    public static void tooltipCallback() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            ComponentMap cMap = stack.getComponents();
            if(cMap.contains(ModDataComponents.LEVELED_KEY)) {
                int level = Objects.requireNonNull(cMap.get(ModDataComponents.LEVELED_KEY)).getLevel();
                //new Object[]{String.format(Locale.ROOT, "#%06X", this.rgb)} ": "+level
                lines.add(1, Text.literal("Level: "+level).formatted(Formatting.GRAY));
            }
        });
    }
}
