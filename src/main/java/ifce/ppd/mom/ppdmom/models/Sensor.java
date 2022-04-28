package ifce.ppd.mom.ppdmom.models;

import java.io.Serializable;

public class Sensor implements Serializable {
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

    public int getMinValue() {
        return minValue;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean valueIsLessThanMinumum() {
        return value < maxValue;
    }

    public boolean valueIsGreaterThanMaximum() {
        return value > maxValue;
    }
}
