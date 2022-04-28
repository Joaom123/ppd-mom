package ifce.ppd.mom.ppdmom.models;

import java.io.Serializable;

public class Sensor implements Serializable {
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

    public int getMinValue() {
        return minValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean valueIsLessThanMinimum() {
        return value < minValue;
    }

    public boolean valueIsGreaterThanMaximum() {
        return value > maxValue;
    }
}
