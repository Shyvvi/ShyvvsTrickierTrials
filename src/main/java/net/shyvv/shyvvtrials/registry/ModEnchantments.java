package net.shyvv.shyvvtrials.registry;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.enchantments.EmptyEnchantment;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> PENETRATION_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Shyvvtrials.MOD_ID, "penetration"));

    public static final RegistryKey<Enchantment> JOUSTING_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Shyvvtrials.MOD_ID, "jousting"));

    public static final RegistryKey<Enchantment> CONSERVATION_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Shyvvtrials.MOD_ID, "conservation"));

    public static final MapCodec<EmptyEnchantment> PENETRATION_EFFECT = register("penetration", EmptyEnchantment.CODEC);

    public static final MapCodec<EmptyEnchantment> JOUSTING_EFFECT = register("jousting", EmptyEnchantment.CODEC);

    public static final MapCodec<EmptyEnchantment> CONSERVATION_EFFECT = register("conservation", EmptyEnchantment.CODEC);

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Shyvvtrials.MOD_ID, name), codec);
    }



    public static void initialize() {

    }
}
