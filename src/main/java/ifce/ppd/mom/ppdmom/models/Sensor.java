package ifce.ppd.mom.ppdmom.models;

public class Sensor {
    private String name;
    private String type;
    private int minValue;
    private int maxValue;
    private int value;

    public Sensor(String name, String type, int minValue, int maxValue) {
        this.name = name;
        this.type = type;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }
}
