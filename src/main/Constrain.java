package main;

public class Constrain {
    private String type;
    private double defaultValue;
    private double minValue;
    private double maxValue;

    public Constrain(){}
    public Constrain(String type, double defaultValue, double minValue, double maxValue) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Constrain constrain = (Constrain) obj;
        return constrain.getDefaultValue() == defaultValue &&
                constrain.getMinValue() == minValue &&
                constrain.getMaxValue() == maxValue &&
                constrain.getType().equals(type);
    }
}
