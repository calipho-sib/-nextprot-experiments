package org.nextprot.dataintegration.experiments.commpatterns.redis;

import org.nextprot.commons.statements.Statement;
import org.nextprot.commons.statements.reader.BufferedJsonStatementReader;
import org.nextprot.commons.statements.specs.CoreStatementField;
import redis.clients.jedis.Jedis;

import java.io.FileReader;
import java.io.IOException;

public class TransformProducer {

    public static void main(String args[]) {

        try {
            long startTime = System.currentTimeMillis();
            String inputFile = org.nextprot.dataintegration.experiments.commpatterns.io.TransformProducer.class.getClassLoader().getResource("data.json").getPath();
            BufferedJsonStatementReader bufferedJsonStatementReader = new BufferedJsonStatementReader(new FileReader(inputFile), 100);
            int count = 0;

            Jedis jedis = new Jedis();
            while(bufferedJsonStatementReader.hasStatement()) {
                Statement statement = bufferedJsonStatementReader.nextStatement();
                String entryAccession = statement.getValue(CoreStatementField.EVIDENCE_CODE);
                String annotationCategory = statement.getAnnotationCategory();
                String annotationId = statement.getValue(CoreStatementField.BIOLOGICAL_OBJECT_ACCESSION);

                OutputStatement outputStatement = new OutputStatement(entryAccession, annotationCategory, annotationId);

                // Write back the statements to an output file
                jedis.rpush("1", outputStatement.toString());
                //System.out.println("Writing to redis list " + ++count + " "+ outputStatement.toString());
                count++;
            }

            // send the poison pill
            jedis.lpush("1", "DIE");

            System.out.println(count + " Statements are written to output file");
            long endTime = System.currentTimeMillis();
            System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
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