package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
//        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
//                .add(ModBlocks.VITRIC_NILSTONE)
//
//                .add(ModBlocks.NILSTONE)
//                .add(ModBlocks.NILSTONE_SLAB)
//                .add(ModBlocks.NILSTONE_STAIRS)
//
//                .add(ModBlocks.NILSTONE_BRICKS)
//                .add(ModBlocks.NILSTONE_BRICK_SLAB)
//                .add(ModBlocks.NILSTONE_BRICK_STAIRS)
//                .add(ModBlocks.NILSTONE_BRICK_WALL)
//
//                .add(ModBlocks.ADORNED_NILSTONE_BRICKS)
//                .add(ModBlocks.ADORNED_NILSTONE_BRICK_SLAB)
//                .add(ModBlocks.ADORNED_NILSTONE_BRICK_STAIRS)
//                .add(ModBlocks.ADORNED_NILSTONE_BRICK_WALL);
//
//        getOrCreateTagBuilder(TagKey.of(RegistryKeys.BLOCK, new Identifier("fabric", "needs_tool_level_4")))
//                .add(ModBlocks.VITRIC_NILSTONE);
//
//        getOrCreateTagBuilder(BlockTags.WALLS)
//                .add(ModBlocks.NILSTONE_BRICK_WALL)
//                .add(ModBlocks.ADORNED_NILSTONE_BRICK_WALL);

    }
}
