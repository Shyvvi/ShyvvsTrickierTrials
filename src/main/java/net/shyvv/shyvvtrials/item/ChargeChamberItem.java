package net.shyvv.shyvvtrials.item;

import com.mojang.brigadier.Command;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.shyvv.shyvvtrials.config.ModConfigs;
import net.shyvv.shyvvtrials.datagen.ModItemTagProvider;
import net.shyvv.shyvvtrials.registry.ModEnchantments;
import net.shyvv.shyvvtrials.registry.ModEntities;
import net.shyvv.shyvvtrials.registry.ModSounds;
import net.shyvv.shyvvtrials.entity.ChamberFireCharge;
import net.shyvv.shyvvtrials.entity.ChamberWindCharge;
import org.spongepowered.asm.mixin.Dynamic;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;

public class ChargeChamberItem extends Item {
    public ChargeChamberItem(Settings settings) {
        super(settings);
    }

    public static final Predicate<ItemStack> CHARGE_CHAMBER_PROJECTILES = (stack) -> stack.isIn(ModItemTagProvider.CHARGE_CHAMBER_PROJECTILES);

    //----------------------------------------------------------------------------------------------------------
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity && !world.isClient()) {
            ItemStack itemStack = getProjectileType(playerEntity);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
                float f = getChargeProgress(i, itemStack.getCount(), playerEntity.isInCreativeMode());
                if (!((double)f < 0.5)) {
                    if (world instanceof ServerWorld) {
                        shoot(playerEntity, itemStack, f, f, f);
                        if(f > 1 && itemStack.getItem() instanceof WindChargeItem) {
                            for(int ii = Math.round(f); ii>0; ii--) {
                                shoot(playerEntity, itemStack, f, f, 1.5f);
                            }
                        }
                    }

                    if(!user.isInCreativeMode()) {
                        itemStack.decrement((int) Math.ceil(f));
                        stack.damage(1, user, EquipmentSlot.MAINHAND);
                    }
                    world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), ModSounds.CHARGE_CHAMBER_SHOOT, SoundCategory.PLAYERS, 1.0F, 0.8F+(f/3));
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    protected void shoot(LivingEntity shooter, ItemStack itemStack, float charge, float speed, float divergence) {
        ProjectileEntity projectile = null;
        if(itemStack.getItem() instanceof WindChargeItem) {
            projectile = createWindChargeEntity(shooter.getWorld(), shooter, charge);
        } else if (itemStack.getItem() instanceof FireChargeItem) {
            projectile = createFireballEntity(shooter.getWorld(), shooter, charge);
        }
        if(projectile != null) {
            projectile.setOwner(shooter);
            projectile.setVelocity(shooter, shooter.getPitch(), shooter.getYaw(), 0.0F, 1.25f*speed, 3.0f/divergence);
            shooter.getWorld().spawnEntity(projectile);
        }
    }

    public ProjectileEntity createFireballEntity(World world, LivingEntity owner, float power) {
        ChamberFireCharge fireballEntity = new ChamberFireCharge(ModEntities.CHAMBER_FIRE_CHARGE, world);
        fireballEntity.setPos(owner.getPos().getX(), owner.getEyePos().getY(), owner.getPos().getZ());
        fireballEntity.setArgs((power*1.25f)* (float)ModConfigs.CHARGE_CHAMBER_FIRE_CHARGE_STRENGTH, owner);
        return fireballEntity;
    }
    public ProjectileEntity createWindChargeEntity(World world, LivingEntity owner, float power) {
        ChamberWindCharge windchargeEntity = new ChamberWindCharge(ModEntities.CHAMBER_WIND_CHARGE, world);
        windchargeEntity.setPos(owner.getPos().getX(), owner.getEyePos().getY(), owner.getPos().getZ());
        windchargeEntity.setArgs((power*4f)* (float)ModConfigs.CHARGE_CHAMBER_WIND_CHARGE_STRENGTH, owner);
        return windchargeEntity;
    }

    //----------------------------------------------------------------------------------------------------------

    public static float getChargeProgress(int useTicks, int stackAmount, boolean isCreative) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 4F) {
            f = 4F;
        }
        if(!isCreative && f > stackAmount) {
            f = stackAmount;
        }
        return f;
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    private ItemStack randomProjectile() {
        Random rand = new Random();
        return rand.nextBoolean() ? new ItemStack(Items.WIND_CHARGE) : new ItemStack(Items.FIRE_CHARGE);
    }

    public Predicate<ItemStack> getProjectiles() {
        return CHARGE_CHAMBER_PROJECTILES;
    }

    //----------------------------------------------------------------------------------------------------------

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if(user instanceof PlayerEntity p) {
            ItemStack itemStack = getProjectileType(p);
            if (!itemStack.isEmpty()) {
                int i = this.getMaxUseTime(stack, p) - remainingUseTicks;
                switch(i) {
                    case 20, 30:
                        world.playSound(user, user.getBlockPos(), ModSounds.CHARGE_CHAMBER_CHARGE, SoundCategory.PLAYERS, 1.5f, 1.0f);
                        break;
                    case 40:
                        world.playSound(user, user.getBlockPos(), ModSounds.CHARGE_CHAMBER_FULL, SoundCategory.PLAYERS, 1.5f, 1.0f);
                        break;
                    case 50:
                        world.playSound(user, user.getBlockPos(), ModSounds.CHARGE_CHAMBER_MAX, SoundCategory.PLAYERS, 1.5f, 1.0f);
                        break;
                }
            }
        }
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        boolean bl = !getProjectileType(user).isEmpty();
        if (!bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.pass(itemStack);
        }
    }

    public ItemStack getProjectileType(PlayerEntity user) {
        Predicate<ItemStack> predicate = getProjectiles();

        for(int i = 0; i < user.getInventory().size(); ++i) {
            ItemStack itemStack2 = user.getInventory().getStack(i);
            if (predicate.test(itemStack2)) {
                return itemStack2;
            }
        }

        return user.isInCreativeMode() ? randomProjectile() : ItemStack.EMPTY;
    }
}
