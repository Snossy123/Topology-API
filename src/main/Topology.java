package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Topology {
    private String ID;
    private ArrayList<Device> components;

    public Topology(String ID, ArrayList<Device> components) {
        this.ID = ID;
        this.components = new ArrayList<>(components);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<Device> getComponents() {
        return new ArrayList<>(components);
    }

    public ArrayList<Object> getComponentsForWrite() {
        ArrayList<Object> cMap = new ArrayList<>();

        for (Device d: components) {
            HashMap<String, Object> BoundMap = new HashMap<>();
            HashMap<String, Object> deviceMap = new HashMap<>();
            Constrain B = d.getLimit();
            BoundMap.put("default", B.getDefaultValue());
            BoundMap.put("min", B.getMinValue());
            BoundMap.put("max", B.getMaxValue());
            deviceMap.put("id", d.getID());
            deviceMap.put("type", d.getType());
            deviceMap.put("netlist", d.getNetList());
            deviceMap.put(B.getType(), BoundMap);
            cMap.add(deviceMap);
        }
        return  cMap;
    }

    public void setComponents(ArrayList<Device> components) {
        this.components = new ArrayList<>(components);
    }

    public ArrayList<Device> getDevicesWithNetListNode(String node) {
        ArrayList<Device> devices = new ArrayList<>();

        for (var device: components) {
            if (device.getNetList().containsValue(node)) {
                devices.add(device);
            }
        }
        return devices;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Topology topology = (Topology) obj;
        return ID.equals(topology.getID()) &&
                equalsComponents(topology.getComponents());
    }

    private boolean equalsComponents (ArrayList<Device> components) {
        if (components.size() != this.components.size()) return false;
        for (int i = 0; i < components.size(); ++i) {
            if (!components.get(i).equals(this.components.get(i))) {
                return false;
            }
        }
        return true;
    }
}

