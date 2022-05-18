package main;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class ReadWriteJason {
    private ReadWriteJason() {
    }

    static Topology read(String path) throws IOException {

        // convert JSON file to map
        String json = new String(Files.readAllBytes(Paths.get(path)));

        Map<String, Object> jsonTopology = getDataFromJson(json);
        String topologyID = (String) jsonTopology.get("id");
        ArrayList<LinkedHashMap<String, Object>> components = (ArrayList<LinkedHashMap<String, Object>>) jsonTopology.get("components");

        ArrayList<Device> Deviceslist = new ArrayList<>();

        for (var jsonDevice: components) {
            String id = (String) jsonDevice.get("id");
            String type = (String) jsonDevice.get("type");
            Constrain c = new Constrain();
            String c_type = null;
            for(String key : jsonDevice.keySet()){
                if( !key.equals("id") && !key.equals("type") && !key.equals("netlist")){
                    c_type = key;
                }
            }
            c.setType(c_type);


            var constrain = (LinkedHashMap<String, Object>) jsonDevice.get(c_type);

            double df = Double.valueOf(constrain.get("default").toString());
            double min = Double.valueOf(constrain.get("min").toString());
            double max = Double.valueOf(constrain.get("max").toString());
            c.setDefaultValue(df);
            c.setMinValue(min);
            c.setMaxValue(max);

            HashMap<String, String> n = (HashMap<String, String>) jsonDevice.get("netlist");

            Deviceslist.add(new Device(type, id, c, n));
        }

        return new Topology(topologyID, Deviceslist);
    }

    static void write(Topology topology, String path) throws IOException {
        // create object mapper
        ObjectMapper mapper=new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        // convert topology to json string
        LinkedHashMap<String, Object> topology_prepared = prepare_topology(topology);
        String jsonString = mapper.writeValueAsString(topology_prepared);
        System.out.println(jsonString);
        new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(jsonString);
        writer.close();
    }
    private static Map<String, Object> getDataFromJson(String json){
        // create object mapper instance
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        Map<String, Object> jsonTopology = new HashMap<String,Object>();
        try {

            jsonTopology = mapper.readValue(json,
                    new TypeReference<HashMap<String,Object>>(){});
            System.out.println(jsonTopology);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonTopology;
    }
    private static LinkedHashMap<String, Object> prepare_topology(Topology t){
        LinkedHashMap<String, Object> t_Map = new LinkedHashMap<>();
        t_Map.put("id", t.getID());
        t_Map.put("components", t.getComponentsForWrite());
        return  t_Map;
    }

}
