package net.shyvv.shyvvtrials.client.item;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.registry.ModItems;

public class ModModelPredicateProviders {
    public static void registerModelPredicateProviders() {
        // For versions before 1.21, replace 'Identifier.ofVanilla' with 'new Identifier'.
        ModelPredicateProviderRegistry.register(ModItems.CHARGE_CHAMBER, Identifier.ofVanilla("use"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.getActiveItem() != itemStack ? 0.0F : (itemStack.getMaxUseTime(livingEntity) - livingEntity.getItemUseTimeLeft()) / 60.0F;
        });

        ModelPredicateProviderRegistry.register(ModItems.CHARGE_CHAMBER,  Identifier.ofVanilla("using"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
        });
    }
}
