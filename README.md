# TopologyAPI
Providing the functionality to access, manage and store device topologies, given different json files each includes one topology, storing them in desk and applying different queries.

## Description
- A topology is a set of electronic and electerical components that are connected together to form a device with some functionality.
- The API supports the following operations:
    1. Read topology from JSON file and store it in memory.
    2. Write topology into a JSON file and store it in disk.
    3. Query the list of topologies stored in memory.
    4. Query the components of a certain topology.
    5. Query the components that are conneted to each other through a given node in a certain topology.
    6. Delete topology from memory.

## Why JAVA is used as the programming language
- Java is **Class based, Object-Oriented Language**. So it's a good choice to be used to model the API in an appropriate way.
- Java is supported by powerful **Serialization and Deserialization** JSON parsers like [GSON](https://sites.google.com/site/gson/gson-user-guide), [Jackson](https://github.com/FasterXML/jackson) and [JSON.simple](https://github.com/fangyidong/json-simple).

## Used Technologies
- [IntelliJ](https://www.jetbrains.com/idea/) as an IDE.
- [Maven](https://maven.apache.org/) as a building tool.
- [Jackson](https://github.com/FasterXML/jackson) to parse JSON files.
- [Junit5](https://junit.org/junit5/) to perform unit tests.
- [Qodana](https://www.jetbrains.com/qodana/), the built-in code analysis tool for [IntelliJ](https://www.jetbrains.com/idea/).

## TopologyAPI Documentation
**TopologyAPI is modeled by three classes as follows:**

- ***API Class:*** Provides the user with the main functionality to manipulate the topologies through its methods.
- ***ReadWriteJson Class:*** Provides `API` with helper methods to manipulate JSON files, its visibility modifier is `default` as it's only needed by `API` to handle JSON files.
- ***Memory Class:*** Represents the memory source that the `API` uses to store the read topologies, its visibilty modifier is `default` to make it visible only for the classes in **Main** package, so it's granteed that the user of this package **cannot** access it.

**Notes:** If the API supports access to Memory, `Memory` may contain the methods used to store, retrieve and manipulate topologies from Memory.

**The UML Diagram of API and ReadWriteJson Class:**

![picture alt](https://github.com/Snossy123/Topology-API/blob/main/UML/UML%20API%20Topology2.jpeg "TopologyManager UML")

**The UML Diagram of Memory Class:**

![picture alt](https://github.com/Snossy123/Topology-API/blob/main/UML/memory.jpeg "DataBase UML")

## API Documentation:
**readJSON(String jsonFilePath):**
- Description: read topology from the given JSON file.
- Parameters:
    1. `jsonFilePath`: the path of the given JSON file.
- Return: `void`.
- Throw: 
    1. `IOException` if the path is wrong or no such a file in this path.

**writeJSON(String topologyID, String filePath):**
- Description: writes the given topology in a JSON file.
- Parameters: 
    1. `topologyID`: the ID of the topology that wanted to be written into disk as a JSON file.
    2. `filePath`: the path of the file that the topology will be written into.
- Return: `void`.
- Throw:
    1. `IOException` if the path is in wrong.
    2. `TopologyIDNotFoundException` if there's no topology with the given ID in memory.

**queryTopologies():**
- Description: gives the user a copy of the list of topologies currently stored in memory.
- Parameters: `void`.
- Return: `ArrayList<Topology>`.

**queryDevices(String topologyID):**
- Description: gives the user a copy of the list of the components of the given device.
- Parameters: 
    1. `topologyID`: the ID of the topology to query its components.
- Return: `ArrayList<Device>`.
- Throw:
    1. `TopologyIDNotFoundException` if there's no topology with the given ID in memory.

**queryDevicesWithNetListNode(String topologyID, String node):**
- Description: gives the user a copy of the list of components that are conneted to the given node.
- Parameters: 
    1. `topologyID`: the ID of the topology.
    2. `node`: the given node to query components connected to it.
- Return: `ArrayList<Device>`.
- Throw:
    1. `TopologyIDNotFoundException` if there's no topology with the given ID in memory.

**deleteTopology(String topologyID):**
- Description: delete the given topology from the memory.
- Parameters: 
    1. `topologyID`: the ID of the topology that will be deleted.
- Return: `void`.
- Throw:
    1. `TopologyIDNotFoundException` if there's no topology with the given ID in memory.


## Classes Documentation
**The topologies stored in memory is modeled with the following classes:**

- ***Topology Class:*** It models the topology as an ID and an array of devices, each element in this array is of type `Device`.
- ***Device Class:*** It models the device as an ID, type, characteristics and net list.
- ***Constain Class:*** It models the characteristics of each device as a default, minimum, maximum value of (resistance, voltage, etc...).
- ***TopologyIDNotFoundException Class:*** Defines a **user-defined-exception** to be thrown if the user tried to manipulate a topology in memory while it's not actually stored yet.  

**The UML Diagram of Topology, Device, and Constrain Class:**
![picture alt]( https://github.com/Snossy123/Topology-API/blob/main/UML/UML%20API%20Topology.jpeg "Topology UML")

**Note:** The UML Diagram of these classes shows the **composition** relation between them.

## Usage
- Install the [Used Technologies](#Used-Technologies).
- Import `TopologyAPI` package in your program, ex:
```
import TopologyAPI.*;
```
- Use the methods provided in `TopologyManager` according to the [TopologyManager Documentation](#TopologyManager-Documentation).

## Testing
- Unit tests is provided by `TestTopologyAPI` class which is defined [here](https://github.com/Eslam-Walid/TopologyAPI/blob/master/src/test/java/TestTopologyAPI/TestTopologyAPI.java).
- `TestTopologyAPI` contains test methods for all `TopologyManager` methods.
- You can run the whole `TestTopologyAPI` class or run individual methods.
- Output of [Junit5](https://junit.org/junit5/) testing process from [IntelliJ](https://www.jetbrains.com/idea/):

![picture alt]( https://github.com/Snossy123/Topology-API/blob/main/Test_API_Topology.jpg "Testing Output")
