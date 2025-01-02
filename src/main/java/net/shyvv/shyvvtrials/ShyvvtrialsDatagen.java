package net.shyvv.shyvvtrials;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.shyvv.shyvvtrials.datagen.*;

public class ShyvvtrialsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModEnchantmentProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModEnchantmentTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);

    }
}
