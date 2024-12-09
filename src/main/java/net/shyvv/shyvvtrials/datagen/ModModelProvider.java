package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.shyvv.shyvvtrials.registry.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.VITRIC_NILSTONE);
//
//        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.NOXITE_BLOCK);
//
//        BlockStateModelGenerator.BlockTexturePool nilstonePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.NILSTONE);
//        BlockStateModelGenerator.BlockTexturePool nilstoneBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.NILSTONE_BRICKS);
//        BlockStateModelGenerator.BlockTexturePool adornedNilstoneBricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.ADORNED_NILSTONE_BRICKS);
//
//        nilstonePool.stairs(ModBlocks.NILSTONE_STAIRS);
//        nilstonePool.slab(ModBlocks.NILSTONE_SLAB);
//
//        nilstoneBricksPool.stairs(ModBlocks.NILSTONE_BRICK_STAIRS);
//        nilstoneBricksPool.slab(ModBlocks.NILSTONE_BRICK_SLAB);
//        nilstoneBricksPool.wall(ModBlocks.NILSTONE_BRICK_WALL);
//
//        adornedNilstoneBricksPool.stairs(ModBlocks.ADORNED_NILSTONE_BRICK_STAIRS);
//        adornedNilstoneBricksPool.slab(ModBlocks.ADORNED_NILSTONE_BRICK_SLAB);
//        adornedNilstoneBricksPool.wall(ModBlocks.ADORNED_NILSTONE_BRICK_WALL);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.DENSE_INGOT, Models.GENERATED);
    }
}
