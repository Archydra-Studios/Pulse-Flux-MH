package azzy.fabric.pulseflux.util;

public class Material {

    public final double maxTemperature, heatConductivity;
    public final double maxInductance, maxFrequency;
    public final double maxPressure;

    public Material(double maxTemperature, double heatConductivity, double maxInductance, double maxFrequency, double maxPressure) {
        this.maxTemperature = maxTemperature;
        this.heatConductivity = heatConductivity;
        this.maxInductance = maxInductance;
        this.maxFrequency = maxFrequency;
        this.maxPressure = maxPressure;
    }
}
