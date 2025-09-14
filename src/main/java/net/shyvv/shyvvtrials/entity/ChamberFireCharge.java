package net.shyvv.shyvvtrials.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class ChamberFireCharge extends AbstractFireballEntity {
    public static final ExplosionBehavior EXPLOSION_BEHAVIOR;
    private float explosionPower = 1;
    private int deflectCooldown = 5;

    public ChamberFireCharge(EntityType<? extends AbstractFireballEntity> entityType, World world) {
        super(entityType, world);
    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return this.deflectCooldown <= 0 && super.deflect(deflection, deflector, owner, fromAttack);
    }

    public void setArgs(float explosionPower, LivingEntity owner) {
        this.explosionPower = explosionPower;
        this.setOwner(owner);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, (DamageSource)null, EXPLOSION_BEHAVIOR, this.getX(), this.getY(), this.getZ(), explosionPower, true, World.ExplosionSourceType.TRIGGER, ParticleTypes.EXPLOSION, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE);
            if(this.getWorld() instanceof ServerWorld serverWorld){
                double delta = explosionPower/1.5;
                serverWorld.spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 2*(Math.round(explosionPower)), delta, delta, delta, 0.75);
            }
            this.discard();
        }
    }

    static {
        EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(false, true, Optional.empty(), Optional.empty());
    }
}
