package net.shyvv.shyvvtrials.registry;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.shyvv.shyvvtrials.item.ChargeChamberItem;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.item.ItemUtils;
import net.shyvv.shyvvtrials.item.LanceItem;

public class ModItems {
    public static Item register(Item item, String id) {

        Identifier itemID = Identifier.of(Shyvvtrials.MOD_ID, id);

        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static final Item DENSE_INGOT = register(
            new Item(new Item.Settings()),
            "dense_ingot"
    );

    public static final Item LANCE = register(
            new LanceItem(new Item.Settings().rarity(Rarity.EPIC).maxDamage(500).component(DataComponentTypes.TOOL, ItemUtils.createToolComponent()).attributeModifiers(LanceItem.createAttributeModifiers())),
            "lance"
    );

    public static final Item CHARGE_CHAMBER = register(
            new ChargeChamberItem(new Item.Settings().rarity(Rarity.EPIC).maxDamage(250).component(DataComponentTypes.TOOL, ItemUtils.createToolComponent())),
            "charge_chamber"
    );

    public static void initialize() {
    }
}
