package main;

import java.io.IOException;
import java.util.ArrayList;

public final class  API {
    // Private Constructor to prevent the user from instantiating this class
    private API() {

    }

    public static void readJSON(String path) throws IOException {
        Memory.topologies.add(ReadWriteJason.read(path));
    }

    public static void writeJSON(String topologyID, String path) throws TopologyIDNotFoundException, IOException {
        Topology topology = searchTopologyID(topologyID);
        if (topology != null) {
            ReadWriteJason.write(topology, path);
        } else {
            throw new TopologyIDNotFoundException("No such topology with this ID");
        }
    }

    public static void deleteTopology(String topologyID) throws TopologyIDNotFoundException {
        Topology topology = searchTopologyID(topologyID);
        if (topology != null) {
            Memory.topologies.remove(topology);
        } else {
            throw new TopologyIDNotFoundException("No such topology with this ID");
        }
    }

    public static ArrayList<Topology> queryTopologies() {
        return new ArrayList<>(Memory.topologies);
    }

    public static ArrayList<Device> queryDevices(String topologyID) throws TopologyIDNotFoundException {
        Topology topology = searchTopologyID(topologyID);
        if (topology != null) {
            return topology.getComponents();
        } else {
            throw new TopologyIDNotFoundException("No such topology with this ID");
        }
    }

    public static ArrayList<Device> queryDevicesWithNetListNode(String topologyID, String node) throws TopologyIDNotFoundException {
        // search for the topology with its ID in topologies list
        Topology topology = searchTopologyID(topologyID);
        if (topology != null) {
            return topology.getDevicesWithNetListNode(node);
        } else {
            throw new TopologyIDNotFoundException("No such topology with this ID");
        }
    }

    private static Topology searchTopologyID(String topologyID) {
        for (var topology: Memory.topologies) {
            if (topology.getID().equals(topologyID)) {
                return topology;
            }
        }
        return null;
    }
}
