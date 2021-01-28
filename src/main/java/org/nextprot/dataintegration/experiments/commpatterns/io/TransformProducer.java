package org.nextprot.dataintegration.experiments.commpatterns.io;

import org.nextprot.commons.statements.Statement;
import org.nextprot.commons.statements.reader.BufferedJsonStatementReader;
import org.nextprot.dataintegration.experiments.commpatterns.org.nextprot.dataintegration.utils.BufferedJsonStatementWriter;

import java.io.FileReader;
import java.io.IOException;

public class TransformProducer {

    public static void main(String args[]) {

        try {
            long startTime = System.currentTimeMillis();
            String inputFile = TransformProducer.class.getClassLoader().getResource("result_partition_100.json").getPath();
            BufferedJsonStatementReader bufferedJsonStatementReader = new BufferedJsonStatementReader(new FileReader(inputFile), 100);
            int count = 0;

            BufferedJsonStatementWriter writer = new BufferedJsonStatementWriter();
            writer.startWritingArray();
            while(bufferedJsonStatementReader.hasStatement()) {
                Statement statement = bufferedJsonStatementReader.nextStatement();

                // Write back the statements to an output file
                writer.writeStatement(statement);
                count++;
            }
            writer.endWritingArray();
            System.out.println(count + " Statements are written to output file");
            long endTime = System.currentTimeMillis();
            System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TransformFunction {

    public void compute(Statement statement) {

    }
}
