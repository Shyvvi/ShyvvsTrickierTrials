package net.shyvv.shyvvtrials.item;

import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.List;

public class ItemUtils {
    public static int getEnchantmentLevel(DynamicRegistryManager regManager, RegistryKey<Enchantment> enchantment, ItemStack i) {
        return EnchantmentHelper.getLevel(regManager.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(enchantment), i);
    }

    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 2);
    }
}
