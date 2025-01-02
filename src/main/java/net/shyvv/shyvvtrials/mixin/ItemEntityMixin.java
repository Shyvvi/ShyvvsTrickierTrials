package net.shyvv.shyvvtrials.mixin;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.shyvv.shyvvtrials.entity.ChamberFireCharge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "damage", at = @At(value = "HEAD"), cancellable = true)
    private void shyvvtrials$fireballsDontDestroyItemsNowYay(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(source.getAttacker() != null && source.getAttacker() instanceof ChamberFireCharge) {
            cir.setReturnValue(true);
        }
    }
}
