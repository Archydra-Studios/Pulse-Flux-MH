package net.azzy.pulseflux.util.interaction;

public interface HeatHolder {

    double getHeat();

    void moveHeat(double change);

    double getArea();

    default boolean forceArea(){
        return false;
    }

    HeatTransferHelper.HeatMaterial getMaterial();
}
