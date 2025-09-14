package net.shyvv.shyvvtrials.config;

import com.mojang.datafixers.util.Pair;
import net.shyvv.shyvvtrials.Shyvvtrials;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static double CHARGE_CHAMBER_WIND_CHARGE_STRENGTH;
    public static double CHARGE_CHAMBER_FIRE_CHARGE_STRENGTH;
    public static int OMINOUS_SPAWNER_DIFFICULTY_SCALE;

    public static void initialize() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Shyvvtrials.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("charge_chamber.fire_charge_strength_modifier", 1.0), "0.5 would be half of its current strength and 2 would be double");
        configs.addKeyValuePair(new Pair<>("charge_chamber.wind_charge_strength_modifier", 1.0), "Same thing but wind charge so it's weird");
        configs.addKeyValuePair(new Pair<>("ominous_spawner.difficulty_modifier", 0), "Negative values = easier, positive values = harder (only takes whole numbers so no 0.3141)");
    }

    private static void assignConfigs() {
        CHARGE_CHAMBER_FIRE_CHARGE_STRENGTH = CONFIG.getOrDefault("charge_chamber.fire_charge_strength_modifier", 1.0);
        CHARGE_CHAMBER_WIND_CHARGE_STRENGTH = CONFIG.getOrDefault("charge_chamber.wind_charge_strength_modifier", 1.0);
        OMINOUS_SPAWNER_DIFFICULTY_SCALE = CONFIG.getOrDefault("ominous_spawner.difficulty_modifier", 0);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
