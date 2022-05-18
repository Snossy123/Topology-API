package main;

import java.util.HashMap;

public class Device {
    private String type;
    private String ID;
    private Constrain constrain;
    private HashMap<String, String> netList;

    public Device(){ }
    public Device(String type, String ID, Constrain constrain, HashMap<String, String> netList) {
        this.type = type;
        this.ID = ID;
        this.constrain = constrain;
        this.netList = new HashMap<>(netList);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Constrain getLimit() {
        return constrain;
    }

    public void setLimit(Constrain constrain) {
        this.constrain = constrain;
    }

    public HashMap<String, String> getNetList() {
        return new HashMap<>(netList);
    }

    public void setNetList(HashMap<String, String> netList) {
        this.netList = new HashMap<>(netList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Device device = (Device) obj;
        return type.equals(device.getType()) &&
                ID.equals(device.getID()) &&
                constrain.equals(device.getLimit()) &&
                netList.equals(device.getNetList());
    }
}