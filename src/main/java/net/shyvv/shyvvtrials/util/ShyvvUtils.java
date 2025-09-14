package net.shyvv.shyvvtrials.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3d;

public class ShyvvUtils {
    public static Vec3d getPersistentVec3d(NbtCompound nbt, String key) {
        if(nbt.contains(key+"X") && nbt.contains(key+"Y") && nbt.contains(key+"Z")) {
            double x = nbt.getDouble(key + "X");
            double y = nbt.getDouble(key + "Y");
            double z = nbt.getDouble(key + "Z");
            return new Vec3d(x, y, z);
        } else{
            return new Vec3d(0, 0, 0);
        }
    }

    public static NbtCompound setPersistentVec3d(NbtCompound nbt, String key, Vec3d vector) {
        nbt.putDouble(key+"X", vector.getX());
        nbt.putDouble(key+"Y", vector.getY());
        nbt.putDouble(key+"Z", vector.getZ());
        return nbt;
    }
}
