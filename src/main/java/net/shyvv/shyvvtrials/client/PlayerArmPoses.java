package net.shyvv.shyvvtrials.client;

import net.minecraft.client.model.ModelPart;

public class PlayerArmPoses {
    public static void lanceHold(ModelPart holdingArm, ModelPart otherArm, boolean rightArmed) {
        ModelPart modelPart = rightArmed ? holdingArm : otherArm;
        ModelPart modelPart2 = rightArmed ? otherArm : holdingArm;
        modelPart.yaw = (rightArmed ? 0.1F : 0.95F);

        modelPart.roll = (rightArmed ? 0.2F : -0.1F);

        modelPart.pitch = (rightArmed ? 0.1F : 0.75F);
    }
}
