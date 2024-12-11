package net.shyvv.shyvvtrials;

import net.fabricmc.api.ModInitializer;
import net.shyvv.shyvvtrials.item.ModCallbacks;
import net.shyvv.shyvvtrials.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Shyvvtrials implements ModInitializer {
    public static final String MOD_ID = "shyvvtrials";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEntities.initialize();
        ModItems.initialize();
        ModSounds.initialize();
        ModEnchantments.initialize();
        ModParticles.initialize();
        ModDataComponents.initialize();
    }
}
