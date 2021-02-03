package org.nextprot.dataintegration.experiments.commpatterns.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;


public class StatementDeserializer implements Deserializer {


    public void configure(Map map, boolean b) {

    }

    public Object deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        OutputStatement obj = null;
        try {
            obj = mapper.readValue(bytes, OutputStatement.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public void close() {

    }
}