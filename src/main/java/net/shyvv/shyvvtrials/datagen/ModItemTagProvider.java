package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider<Item>{
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.ITEM, registriesFuture);
    }

    public static final TagKey<Item> CHARGE_CHAMBER_PROJECTILES = TagKey.of(RegistryKeys.ITEM, Identifier.of(Shyvvtrials.MOD_ID, "charge_chamber_projectiles"));
    public static final TagKey<Item> LANCE_REGISTRYKEY = TagKey.of(RegistryKeys.ITEM, Identifier.of(Shyvvtrials.MOD_ID, "lance_registrykey"));
    public static final TagKey<Item> CHARGE_CHAMBER_REGISTRYKEY = TagKey.of(RegistryKeys.ITEM, Identifier.of(Shyvvtrials.MOD_ID, "charge_chamber_registrykey"));

    @Override
    public void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(CHARGE_CHAMBER_PROJECTILES)
                .add(Items.FIRE_CHARGE)
                .add(Items.WIND_CHARGE);
        getOrCreateTagBuilder(LANCE_REGISTRYKEY)
                .add(ModItems.LANCE);
        getOrCreateTagBuilder(CHARGE_CHAMBER_REGISTRYKEY)
                .add(ModItems.CHARGE_CHAMBER);
        getOrCreateTagBuilder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.LANCE)
                .add(ModItems.CHARGE_CHAMBER);
    }
}
