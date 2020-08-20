package net.azzy.pulseflux.util.gui;

import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;

public interface ExtendedPropertyDelegate extends PropertyDelegate {

    default String getString(int index){
        return null;
    }

    default void setString(int index, String value){}

    default Double getDouble(int index){
        return 0.0;
    }

    default void setDouble(int index, double value){}

    default boolean getBoolean(int index){
        return false;
    }

    default void setBoolean(int index, boolean value){}

    default long getLong(int index){
        return 0;
    }

    default void setLong(int index, long value){}

    @Override
    default int get(int index){
        return 0;
    }

    @Override
    default void set(int index, int value){}

    default BlockPos getpos() {
        return null;
    }

    @Override
    default int size(){
        return 0;
    }
}
