package net.shyvv.shyvvtrials.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.ComponentMap;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.item.components.LeveledKeyComponent;

public class ModItemGroups {

    public static final RegistryKey<ItemGroup> SHYVVTRIALS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Shyvvtrials.MOD_ID, "item_group"));
    public static final ItemGroup SHYVVTRIALS_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.LANCE))
            .displayName(Text.translatable("itemGroup.shyvvtrials"))
            .build();

    private static ItemStack leveledKeyComponent(ItemStack stack, int level) {
        ComponentMap map = ComponentMap.builder().add(ModDataComponents.LEVELED_KEY, new LeveledKeyComponent(level, true)).build();
        stack.applyComponentsFrom(map);
        return stack;
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, SHYVVTRIALS_ITEM_GROUP_KEY, SHYVVTRIALS_ITEM_GROUP);
        ItemGroupEvents.modifyEntriesEvent(SHYVVTRIALS_ITEM_GROUP_KEY)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.DENSE_INGOT);
                    itemGroup.add(ModItems.LANCE);
                    itemGroup.add(ModItems.CHARGE_CHAMBER);
                    itemGroup.getContext().lookup().getOptionalWrapper(RegistryKeys.ENCHANTMENT).ifPresent(registryWrapper -> {
                        itemGroup.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(registryWrapper.getOrThrow(ModEnchantments.JOUSTING_KEY), 3)));
                        itemGroup.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(registryWrapper.getOrThrow(ModEnchantments.PENETRATION_KEY), 5)));
                    });
                    itemGroup.add(leveledKeyComponent(Items.OMINOUS_TRIAL_KEY.getDefaultStack(), 1));
                    itemGroup.add(leveledKeyComponent(Items.OMINOUS_TRIAL_KEY.getDefaultStack(), 2));
                    itemGroup.add(leveledKeyComponent(Items.OMINOUS_TRIAL_KEY.getDefaultStack(), 3));
                    itemGroup.add(leveledKeyComponent(Items.OMINOUS_TRIAL_KEY.getDefaultStack(), 4));
                    itemGroup.add(leveledKeyComponent(Items.OMINOUS_TRIAL_KEY.getDefaultStack(), 5));
                });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
            itemGroup.addAfter(Items.MACE, ModItems.LANCE);
            itemGroup.addAfter(Items.MACE, ModItems.CHARGE_CHAMBER);
        });
    }
}
