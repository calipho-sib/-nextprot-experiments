package org.nextprot.dataintegration.experiments.commpatterns.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class StatementSerializer implements Serializer {


    public byte[] serialize(String s, Object o) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsBytes(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    public void close() {

    }
}