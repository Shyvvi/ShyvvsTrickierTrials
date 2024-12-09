package net.shyvv.shyvvtrials.mixin;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.shyvv.shyvvtrials.client.PlayerArmPoses;
import net.shyvv.shyvvtrials.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {

    @SuppressWarnings("rawtypes")
    @Unique
    BipedEntityModel thisObject = (BipedEntityModel)(Object)this;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void shyvvtrials$armPoses(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack item = livingEntity.getMainHandStack();
        if(item.isOf(ModItems.LANCE)) {
            PlayerArmPoses.lanceHold(thisObject.rightArm, thisObject.leftArm, livingEntity.getMainArm()== Arm.RIGHT);
        }
    }
}
