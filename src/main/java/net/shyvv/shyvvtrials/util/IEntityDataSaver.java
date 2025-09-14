package net.shyvv.shyvvtrials.util;

import net.minecraft.nbt.NbtCompound;

public interface IEntityDataSaver {
    public static String persistentDataKey = "shyvvtrials.shyvvdata";
    NbtCompound getPersistentData();
}
