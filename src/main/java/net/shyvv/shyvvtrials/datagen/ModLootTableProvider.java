package net.shyvv.shyvvtrials.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {


    protected ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
//        addDrop(ModBlocks.NOXITE_BLOCK);
//
//        addDrop(ModBlocks.NILSTONE);
//        addDrop(ModBlocks.NILSTONE_SLAB);
//        addDrop(ModBlocks.NILSTONE_STAIRS);
//
//        addDrop(ModBlocks.NILSTONE_BRICKS);
//        addDrop(ModBlocks.NILSTONE_BRICK_SLAB);
//        addDrop(ModBlocks.NILSTONE_BRICK_STAIRS);
//        addDrop(ModBlocks.NILSTONE_BRICK_WALL);
//
//        addDrop(ModBlocks.ADORNED_NILSTONE_BRICKS);
//        addDrop(ModBlocks.ADORNED_NILSTONE_BRICK_SLAB);
//        addDrop(ModBlocks.ADORNED_NILSTONE_BRICK_STAIRS);
//        addDrop(ModBlocks.ADORNED_NILSTONE_BRICK_WALL);
//
//        addDrop(ModBlocks.VITRIC_NILSTONE, multiOreDrops(ModBlocks.VITRIC_NILSTONE, ModItems.VITRIC_NILSTONE_SHARDS));

    }

//    public LootTable.Builder multiOreDrops(Block drop, Item item) {
//        return BlockLootTableGenerator.dropsWithSilkTouch(drop, (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
//                ((LeafEntry.Builder)
//                        ItemEntry.builder(item)
//                                .apply(SetCountLootFunction
//                                        .builder(UniformLootNumberProvider
//                                                .create(1f, 2f))))
//                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
//    }
}

