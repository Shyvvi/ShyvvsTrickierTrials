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

        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        return registeredItem;
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

//    public static final RegistryKey<ItemGroup> ROS_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), new Identifier(Noxequitem.MOD_ID, "item_group"));
//    public static final ItemGroup ROS_ITEM_GROUP = FabricItemGroup.builder()
//            .icon(() -> new ItemStack(ModItems.VITRIC_NILSTONE_SHARDS))
//            .displayName(Text.translatable("itemGroup.shyvvtrials"))
//            .build();

    public static void initialize() {
//        Registry.register(Registries.ITEM_GROUP, ROS_ITEM_GROUP_KEY, ROS_ITEM_GROUP);
//        ItemGroupEvents.modifyEntriesEvent(ROS_ITEM_GROUP_KEY)
//                .register((itemGroup) -> {
//                    //Gear
//                    itemGroup.add(ModItems.MIRROR);
//                    itemGroup.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.TWIN.get(), 1)));
//                    itemGroup.add(EnchantedBookItem.forEnchantment(new EnchantmentLevelEntry(ModEnchantments.IMBUEMENT.get(), 1)));
//                    //itemGroup.add(ModItems.NOXITE_LONGSWORD);
//                    itemGroup.add(ModItems.NOXITE_HELMET);
//                    itemGroup.add(ModItems.NOXITE_CHESTPLATE);
//                    itemGroup.add(ModItems.NOXITE_LEGGINGS);
//                    itemGroup.add(ModItems.NOXITE_BOOTS);
//                    //Resources
//                    itemGroup.add(ModBlocks.VITRIC_NILSTONE.asItem());
//                    itemGroup.add(ModItems.VITRIC_NILSTONE_SHARDS);
//                    itemGroup.add(ModItems.NOXITE_COMPOUND);
//                    itemGroup.add(ModItems.NOXITE_INGOT);
//                    itemGroup.add(ModBlocks.NOXITE_BLOCK.asItem());
//                    //Decoration
//                    itemGroup.add(ModBlocks.NILSTONE.asItem());
//                    itemGroup.add(ModBlocks.NILSTONE_STAIRS.asItem());
//                    itemGroup.add(ModBlocks.NILSTONE_SLAB.asItem());
//
//                    itemGroup.add(ModBlocks.NILSTONE_BRICKS.asItem());
//                    itemGroup.add(ModBlocks.NILSTONE_BRICK_STAIRS.asItem());
//                    itemGroup.add(ModBlocks.NILSTONE_BRICK_SLAB.asItem());
//                    itemGroup.add(ModBlocks.NILSTONE_BRICK_WALL.asItem());
//
//                    itemGroup.add(ModBlocks.ADORNED_NILSTONE_BRICKS.asItem());
//                    itemGroup.add(ModBlocks.ADORNED_NILSTONE_BRICK_STAIRS.asItem());
//                    itemGroup.add(ModBlocks.ADORNED_NILSTONE_BRICK_SLAB.asItem());
//                    itemGroup.add(ModBlocks.ADORNED_NILSTONE_BRICK_WALL.asItem());
//                });
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register((itemGroup) -> {
//            itemGroup.addAfter(Items.SHIELD, ModItems.MIRROR);
//            itemGroup.addAfter(Items.NETHERITE_BOOTS, ModItems.NOXITE_BOOTS);
//            itemGroup.addAfter(Items.NETHERITE_BOOTS, ModItems.NOXITE_LEGGINGS);
//            itemGroup.addAfter(Items.NETHERITE_BOOTS, ModItems.NOXITE_CHESTPLATE);
//            itemGroup.addAfter(Items.NETHERITE_BOOTS, ModItems.NOXITE_HELMET);
//        });
    }
}
