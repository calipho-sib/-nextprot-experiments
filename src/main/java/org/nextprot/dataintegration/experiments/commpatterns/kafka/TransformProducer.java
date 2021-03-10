package org.nextprot.dataintegration.experiments.commpatterns.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.nextprot.commons.statements.Statement;
import org.nextprot.commons.statements.reader.BufferedJsonStatementReader;
import org.nextprot.commons.statements.specs.CoreStatementField;
import org.nextprot.dataintegration.experiments.commpatterns.org.nextprot.dataintegration.utils.BufferedJsonStatementWriter;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TransformProducer {

    //private Producer<String, OutputStatement> producer;
    private Producer<String, String> producer;

    private String topic;

    public TransformProducer(String topic) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, KafkaConfig.CLIENT_ID);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StatementSerializer.class.getName());
        //properties.put("schema.registry.url", "http://127.0.0.1:8081");
        producer = new KafkaProducer<String, String>(properties);

        // Topic
        this.topic = topic;
    }

    public void produce(String outputStatement) {

        //ProducerRecord<String, OutputStatement> record = new ProducerRecord<String, OutputStatement>(topic, "k",outputStatement);
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "k",outputStatement);
        producer.send(record);
        //producer.close();
    }

    public static void main(String args[]) {
        TransformProducer transformProducer = new TransformProducer("testtopic");

        // Read from the input file and prepare a statement to send to kafka broker
        long startTime = System.currentTimeMillis();
        String inputFile = TransformProducer.class.getClassLoader().getResource("data.json").getPath();
        BufferedJsonStatementReader bufferedJsonStatementReader = null;
        try {
            bufferedJsonStatementReader = new BufferedJsonStatementReader(new FileReader(inputFile), 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int count = 0;

        BufferedJsonStatementWriter writer = new BufferedJsonStatementWriter();
        writer.startWritingArray();
        int batchSize = 0;
        String outputStatment = "";
        while(bufferedJsonStatementReader.hasStatement()) {
            try {
                Statement statement = bufferedJsonStatementReader.nextStatement();

                String entryAccession = statement.getValue(CoreStatementField.EVIDENCE_CODE);
                String annotationCategory = statement.getAnnotationCategory();
                String annotationId = statement.getValue(CoreStatementField.BIOLOGICAL_OBJECT_ACCESSION);

                //OutputStatement outputStatement = new OutputStatement(entryAccession, annotationCategory, annotationId);

                if(batchSize < 200 ){
                    batchSize++;
                    outputStatment += "accession:" + entryAccession + ",category: " + annotationCategory + ", id: " + annotationId;
                } else {
                    transformProducer.produce(outputStatment);
                    outputStatment = "";
                    batchSize = 0;
                }

                count++;
                //if(count > 10 ) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        System.out.println(count + " Statements are written to output file");
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");


    }
}

class OutputStatement {

    private String key;

    public String entry;

    public String annotationCategory;

    public String annotationId;

    public OutputStatement() { }

    public OutputStatement(String entry, String annotationCategory, String annotationId) {
        this.entry = entry;
        this.annotationCategory = annotationCategory;
        this.annotationId = annotationId;
    }

    public String getKey() {
        return new Integer(this.hashCode()).toString();
    }

    public String toString() {
        return this.entry + "," + this.annotationCategory + "," + this.annotationId;
    }

}


