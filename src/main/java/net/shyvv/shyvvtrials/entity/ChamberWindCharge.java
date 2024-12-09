package net.shyvv.shyvvtrials.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class ChamberWindCharge extends AbstractWindChargeEntity {
    public static final ExplosionBehavior EXPLOSION_BEHAVIOR;
    private int explosionPower = 1;
    private int deflectCooldown = 5;

    public ChamberWindCharge(EntityType<? extends AbstractWindChargeEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return this.deflectCooldown <= 0 && super.deflect(deflection, deflector, owner, fromAttack);
    }

    public void setArgs(int explosionPower, LivingEntity owner) {
        this.explosionPower = explosionPower;
        this.setOwner(owner);
    }

    @Override
    protected void createExplosion(Vec3d pos) {
        this.getWorld().createExplosion(this, (DamageSource)null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), explosionPower, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_BREEZE_WIND_BURST);
    }

    static {
        EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(true, false, Optional.empty(), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    }
}
