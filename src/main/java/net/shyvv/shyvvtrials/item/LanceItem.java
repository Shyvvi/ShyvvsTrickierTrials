package net.shyvv.shyvvtrials.item;

import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.shyvv.shyvvtrials.registry.ModEnchantments;
import net.shyvv.shyvvtrials.registry.ModParticles;
import net.shyvv.shyvvtrials.registry.ModSounds;

import java.util.List;
import java.util.Objects;

public class LanceItem extends Item {
    private static final float ATTACK_DAMAGE_MODIFIER_VALUE = 6.0F;
    private static final float ATTACK_SPEED_MODIFIER_VALUE = -3.6F;
    private static final float PLAYER_ENTITY_INTERACTION_RANGE_VALUE = 1.0F;
    public static final float MINING_SPEED_MULTIPLIER = 1.5F;
    public static final float KNOCKBACK_RANGE = 3.5F;
    private static final float KNOCKBACK_POWER = 0.7F;
    private Vec3d oldPos = new Vec3d(0, 0, 0);
    private Vec3d posDifference = new Vec3d(0,0,0);

    public static final Identifier BASE_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID = Identifier.ofVanilla("base_entity_interaction_range");
    public LanceItem(Settings settings) {
        super(settings);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, ATTACK_DAMAGE_MODIFIER_VALUE, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, ATTACK_SPEED_MODIFIER_VALUE, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(BASE_PLAYER_ENTITY_INTERACTION_RANGE_MODIFIER_ID, PLAYER_ENTITY_INTERACTION_RANGE_VALUE, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    //-----------------------------------------------------------------------


    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof PlayerEntity p) {
            Vec3d pos = p.getPos();
            Vec3d temp = oldPos.subtract(pos);
            if(!temp.equals(new Vec3d(0,0,0))) {
                posDifference = temp;
            }
            oldPos = pos;
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, EquipmentSlot.MAINHAND);
        if(shouldDealAdditionalDamage()) {
            ServerWorld serverWorld = (ServerWorld) target.getWorld();
            serverWorld.spawnParticles(ModParticles.LANCE_IMPACT, target.getX(), target.getBodyY(0.5), target.getZ(), 0, 0, 0, 0, 0);
            if(stack.getName().getLiteralString() != null && stack.getName().getLiteralString().equalsIgnoreCase("metal pipe")) {
                serverWorld.playSound(target, target.getBlockPos(), ModSounds.METAL_PIPE, SoundCategory.PLAYERS, 1.5f, 1.2f - (serverWorld.getRandom().nextFloat() * 0.2f));
            } else {
                serverWorld.playSound(target, target.getBlockPos(), ModSounds.LANCE_IMPACT, SoundCategory.PLAYERS, 1.5f, 1.2f - (serverWorld.getRandom().nextFloat() * 0.2f));
            }
            if(target.getVehicle() != null) {
                target.dismountVehicle();
            }
            target.addVelocity(getKnockback());
        }
        return super.postHit(stack, target, attacker);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public int getEnchantability() {
        return 15;
    }

    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        if(damageSource.getAttacker() instanceof LivingEntity i && !target.getWorld().isClient) {
            ItemStack item = i.getWeaponStack();
            DynamicRegistryManager regManager = target.getWorld().getRegistryManager();
            float bonusDamage = 0;

            if(shouldDealAdditionalDamage()) {
                if(ItemUtils.getEnchantmentLevel(regManager, ModEnchantments.PENETRATION_KEY, item) > 0) {
                    bonusDamage += baseAttackDamage*(getCombinedVelocity() * (float) (1+(0.05 * ItemUtils.getEnchantmentLevel(regManager, ModEnchantments.PENETRATION_KEY, item))));
                }
                if(i.getVehicle() instanceof HorseEntity && ItemUtils.getEnchantmentLevel(regManager, ModEnchantments.JOUSTING_KEY, item) > 0) {
                    bonusDamage *= (1+(0.25f * ItemUtils.getEnchantmentLevel(regManager, ModEnchantments.JOUSTING_KEY, item)));
                }
            }
            return bonusDamage;
        }
        return 0.0f;
    }

    private Vec3d getKnockback() {
        int multiplier = -3;
        return new Vec3d(this.posDifference.x*multiplier, 0.1, this.posDifference.z*multiplier);
    }

    private boolean shouldDealAdditionalDamage() {
        return getCombinedVelocity() > 1.2F;
    }

    public float getCombinedVelocity() {
        return (float) ((Math.abs(this.posDifference.x) + Math.abs(this.posDifference.z))/2)+1;
    }
}
