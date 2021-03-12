package azzy.fabric.pulseflux.util;

public class Material {
    public final double maxTemperature, heatConductivity;
    public final double maxInductance, maxFrequency;

    public Material(double maxTemperature, double heatConductivity, double maxInductance, double maxFrequency) {
        this.maxTemperature = maxTemperature;
        this.heatConductivity = heatConductivity;
        this.maxInductance = maxInductance;
        this.maxFrequency = maxFrequency;
    }
}
