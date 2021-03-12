package azzy.fabric.pulseflux.util;

public enum Material {
    HSLA(1000, 0.2, 1024, 4096);

    public final double maxTemperature, heatConductivity;
    public final double maxInductance, maxFrequency;

    Material(double maxTemperature, double heatConductivity, double maxInductance, double maxFrequency) {
        this.maxTemperature = maxTemperature;
        this.heatConductivity = heatConductivity;
        this.maxInductance = maxInductance;
        this.maxFrequency = maxFrequency;
    }
}
