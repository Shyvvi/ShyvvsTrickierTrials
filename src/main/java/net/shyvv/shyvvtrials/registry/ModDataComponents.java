package net.shyvv.shyvvtrials.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.item.components.LeveledKeyComponent;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final ComponentType<LeveledKeyComponent> LEVELED_KEY = register("leveled_key", (builder) -> {
        return builder.codec(LeveledKeyComponent.CODEC).packetCodec(LeveledKeyComponent.PACKET_CODEC);
    });

    @SuppressWarnings("unchecked")
    public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {

        Identifier compID = Identifier.of(Shyvvtrials.MOD_ID, id);

        return Registry.register(Registries.DATA_COMPONENT_TYPE, compID, ((ComponentType.Builder)builderOperator.apply(ComponentType.builder())).build());
    }

    public static void initialize() {}
}
