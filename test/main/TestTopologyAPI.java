package main;

import main.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class TestTopologyAPI {
    @Test
    public void testreadJSON() throws IOException, TopologyIDNotFoundException {
        // Construct the expected Topology
        Topology expectedTopology = buildExpectedTopology();
        // Read the expected topology from the reference json file
        API.readJSON("resources/refTopology.json");
        // Get the actual topology the that the API have read
        Topology actualTopology = API.queryTopologies().get(0);
        API.deleteTopology("top1");
        // Compare the actual topology with the expected topology
        Assert.assertTrue(actualTopology.equals(expectedTopology));
    }

    @Test
    public void testWrite_JSON() throws IOException, TopologyIDNotFoundException {
        Topology expectedTopology, actualTopology;

        // Read the expected topology from refTopology.json
        API.readJSON("resources/refTopology.json");
        expectedTopology = API.queryTopologies().get(0);

        // Write the expected topology from memory to genTopology.json file
        API.writeJSON("top1", "resources/genTopology.json");

        // Remove the expected topology from topologies list
        API.deleteTopology(expectedTopology.getID());

        // Read the written topology in genTopology.json to compare it with the expected topology
        API.readJSON("resources/genTopology.json");
        actualTopology = API.queryTopologies().get(0);
        API.deleteTopology("top1");

        // Compare the actual topology with the expected topology
        Assert.assertTrue(actualTopology.equals(expectedTopology));
    }

    @Test
    public void testdeleteTopology() throws IOException, TopologyIDNotFoundException {
        // Read topology and store it in memory
        API.readJSON("src/resources/topology.json");
        // Make sure that the size of topologies list increased by 1
        Assert.assertEquals(API.queryTopologies().size(), 1);
        // Delete the topology
        API.deleteTopology("top1");
        // Make sure that the size of topologies list decreased by 1
        Assert.assertEquals(API.queryTopologies().size(), 0);
    }

    @Test
    public void testqueryTopologies() throws IOException, TopologyIDNotFoundException {
        // Construct the expected topology
        Topology expectedTopology = buildExpectedTopology();
        // Read the topology from topology.json into memory
        API.readJSON("src/resources/topology.json");
        ArrayList<Topology> actualTopologiesList = API.queryTopologies();
        // Make sure the topology in the topologies list is similar to the expected topology
        Assert.assertTrue(expectedTopology.equals(actualTopologiesList.get(0)));
        API.deleteTopology("top1");
    }

    @Test
    public void testQueryDevices() throws IOException, TopologyIDNotFoundException {
        // Construct the expected topology
        Topology expectedTopology = buildExpectedTopology();
        // Read the topology from topology.json into memory
        API.readJSON("src/resources/topology.json");

        // Construct the expected and the actual list
        ArrayList<Device> actualDevicesList = API.queryDevices("top1");
        ArrayList<Device> expectedDevicesList = expectedTopology.getComponents();
        API.deleteTopology("top1");

        // Make sure that the actual and expected list are the same
        Assert.assertEquals(actualDevicesList.size(), expectedDevicesList.size());
        for (int i = 0; i < actualDevicesList.size(); ++i) {
            Assert.assertTrue(actualDevicesList.get(i).equals(expectedDevicesList.get(i)));
        }
    }

    @Test
    public void testQueryDevicesWithNetListNode() throws IOException, TopologyIDNotFoundException {
        // Construct the expected topology
        Topology expectedTopology = buildExpectedTopology();
        // Read the topology from topology.json into memory
        API.readJSON("src/resources/topology.json");

        ArrayList<Device> expectedDevicesList = expectedTopology.getDevicesWithNetListNode("n1");
        ArrayList<Device> actualDevicesList = API.queryDevicesWithNetListNode("top1", "n1");
        API.deleteTopology("top1");

        Assert.assertEquals(expectedDevicesList.size(), actualDevicesList.size());
        for (int i = 0; i < expectedDevicesList.size(); ++i) {
            Assert.assertTrue(expectedDevicesList.get(i).equals(actualDevicesList.get(i)));
        }
    }

    private Topology buildExpectedTopology() {
        // Construction resistor limits
        Constrain resistorLimit = new Constrain("resistance", 100, 10, 1000);
        // Constructing nMos limits
        Constrain nMosLimit = new Constrain("m(l)", 1.5, 1, 2);
        HashMap<String, String> resistorNetList = new HashMap<>();
        HashMap<String, String> nMosNetList = new HashMap<>();
        ArrayList<Device> topologyComponents = new ArrayList<>();

        // Constructing resistor net list
        resistorNetList.put("t1", "vdd");
        resistorNetList.put("t2", "n1");

        // Constructing nMos net list
        nMosNetList.put("drain", "n1");
        nMosNetList.put("gate", "vin");
        nMosNetList.put("source", "vss");

        // Constructing components
        topologyComponents.add(new Device("resistor", "res1", resistorLimit, resistorNetList));
        topologyComponents.add(new Device("nmos", "m1", nMosLimit, nMosNetList));

        return new Topology("top1", topologyComponents);
    }
}
