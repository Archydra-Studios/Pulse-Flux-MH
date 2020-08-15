package net.azzy.pulseflux.registry.damagetypes;

import net.minecraft.entity.damage.DamageSource;

import static net.azzy.pulseflux.PulseFlux.MOD_ID;

public class DamageIncandescence extends DamageSource {

    public static final DamageIncandescence INCANDESCENCE = (DamageIncandescence) new DamageIncandescence().setBypassesArmor().setExplosive();

    protected DamageIncandescence() {
        super(MOD_ID + ".incandescence");
    }
}
