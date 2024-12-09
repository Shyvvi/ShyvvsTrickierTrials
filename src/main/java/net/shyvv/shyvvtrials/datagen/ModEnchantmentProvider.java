package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelBasedValue;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.shyvv.shyvvtrials.enchantments.EmptyEnchantment;
import net.shyvv.shyvvtrials.registry.ModEnchantments;
import net.shyvv.shyvvtrials.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentProvider extends FabricDynamicRegistryProvider {
    public ModEnchantmentProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        RegistryWrapper<Item> itemLookup = registries.getWrapperOrThrow(RegistryKeys.ITEM);

        register(entries, ModEnchantments.PENETRATION_KEY, Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModItemTagProvider.LANCE_REGISTRYKEY),
                        2,
                        5,
                        Enchantment.leveledCost(15, 9),
                        Enchantment.leveledCost(65, 9),
                        2,
                        AttributeModifierSlot.MAINHAND
                )
        ));

        register(entries, ModEnchantments.JOUSTING_KEY, Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModItemTagProvider.LANCE_REGISTRYKEY),
                        2,
                        3,
                        Enchantment.leveledCost(15, 9),
                        Enchantment.leveledCost(65, 9),
                        2,
                        AttributeModifierSlot.MAINHAND
                )));

        register(entries, ModEnchantments.CONSERVATION_KEY, Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ModItemTagProvider.CHARGE_CHAMBER_REGISTRYKEY),
                        2,
                        1,
                        Enchantment.leveledCost(15, 9),
                        Enchantment.leveledCost(65, 9),
                        2,
                        AttributeModifierSlot.MAINHAND
                )));
    }

    private static void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions) {
        entries.add(key, builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "Enchantment Gen";
    }
}
