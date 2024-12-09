package net.shyvv.shyvvtrials.registry;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.shyvv.shyvvtrials.Shyvvtrials;
import net.shyvv.shyvvtrials.entity.ChamberFireCharge;
import net.shyvv.shyvvtrials.entity.ChamberWindCharge;

public class ModEntities {
    public static final EntityType<ChamberFireCharge> CHAMBER_FIRE_CHARGE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Shyvvtrials.MOD_ID, "chamber_fire_charge"),
            EntityType.Builder.create(ChamberFireCharge::new, SpawnGroup.MISC).dimensions(1f, 1f).build("chamber_fire_charge"));

    public static final EntityType<ChamberWindCharge> CHAMBER_WIND_CHARGE = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Shyvvtrials.MOD_ID, "chamber_wind_charge"),
            EntityType.Builder.create(ChamberWindCharge::new, SpawnGroup.MISC).dimensions(0.5f, 0.5f).build("chamber_wind_charge"));

    public static void initialize() {}
}
