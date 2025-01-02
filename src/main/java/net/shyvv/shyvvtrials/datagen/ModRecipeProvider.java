package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Items.HEAVY_CORE)
                .input('D', ModItems.DENSE_INGOT)
                .input('I', Items.IRON_BLOCK)
                .pattern(" D ")
                .pattern("DID")
                .pattern(" D ")
                .criterion(hasItem(Items.IRON_BLOCK), conditionsFromItem(Items.IRON_BLOCK))
                .criterion(hasItem(ModItems.DENSE_INGOT), conditionsFromItem(ModItems.DENSE_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.LANCE)
                .input('D', ModItems.DENSE_INGOT)
                .input('B', Items.BREEZE_ROD)
                .pattern("  D")
                .pattern("DD ")
                .pattern("BD ")
                .criterion(hasItem(Items.BREEZE_ROD), conditionsFromItem(Items.BREEZE_ROD))
                .criterion(hasItem(ModItems.DENSE_INGOT), conditionsFromItem(ModItems.DENSE_INGOT))
                .offerTo(recipeExporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CHARGE_CHAMBER)
                .input('D', ModItems.DENSE_INGOT)
                .input('S', Items.SKELETON_SKULL)
                .pattern(" D ")
                .pattern("DSD")
                .pattern(" D ")
                .criterion(hasItem(Items.SKELETON_SKULL), conditionsFromItem(Items.SKELETON_SKULL))
                .criterion(hasItem(ModItems.DENSE_INGOT), conditionsFromItem(ModItems.DENSE_INGOT))
                .offerTo(recipeExporter);
    }
}
