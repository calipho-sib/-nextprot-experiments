package org.nextprot.dataintegration.experiments.commpatterns.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class TransformConsumer implements Runnable{

    private Consumer<String, OutputStatement> consumer;

    public TransformConsumer(String topic) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.KAFKA_BROKERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KafkaConfig.GROUP_ID_CONFIG);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StatementDeserializer.class.getName());

        // Create the consumer using props.
        consumer = new KafkaConsumer<String, OutputStatement>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void run() {
        System.out.println("Starting the consumer and subscribed to topic ");
        long startTime = System.currentTimeMillis();
        final int giveUp = 100;   int noRecordsCount = 0;

        while (true) {
            final ConsumerRecords<String, OutputStatement> consumerRecords = consumer.poll(1000);

            if (consumerRecords.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            for(ConsumerRecord record: consumerRecords) {
               System.out.println("Loading " + record.value().toString());
            }

            consumer.commitAsync();
        }
        consumer.close();
        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");
    }

    public static void main(String args[]) {
        TransformConsumer transformConsumer = new TransformConsumer("testtopic");
        Thread thread = new Thread(transformConsumer);
        thread.start();

    }
}
