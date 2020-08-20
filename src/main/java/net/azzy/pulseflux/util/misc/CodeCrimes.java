package net.azzy.pulseflux.util.misc;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

import static net.azzy.pulseflux.PulseFlux.PFLog;

public class CodeCrimes {

    private static Unsafe unsafe = null;

    public static Unsafe bringInTheArtillery(){
        return unsafe;
    }

    static{
        Field ohno = null;
        try {
            ohno = Unsafe.class.getDeclaredField("theUnsafe");
            ohno.setAccessible(true);
            unsafe = (Unsafe) ohno.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            PFLog.error("UwU OwO I did an oopsie");
            e.printStackTrace();
        }
    }
}
