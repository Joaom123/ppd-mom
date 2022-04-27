package ifce.ppd.mom.ppdmom.models;

public class Sensor {
    private final String name;
    private final String type;
    private final int minValue;
    private final int maxValue;
    private int value;

    public Sensor(String name, String type, int minValue, int maxValue) {
        this.name = name;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
