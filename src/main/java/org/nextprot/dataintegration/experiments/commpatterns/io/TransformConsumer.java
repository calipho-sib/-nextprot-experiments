package org.nextprot.dataintegration.experiments.commpatterns.io;

import org.nextprot.commons.statements.reader.BufferedJsonStatementReader;

import java.io.FileReader;
import java.io.IOException;

public class TransformConsumer {

    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        String inputFile = "output.json";

        try {
            BufferedJsonStatementReader bufferedJsonStatementReader = new BufferedJsonStatementReader(new FileReader(inputFile), 100);
            int count = 0;
            while(bufferedJsonStatementReader.hasStatement()) {
                System.out.println("Loading statement " + ++count);
                bufferedJsonStatementReader.nextStatement();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed time " + (endTime - startTime) + " milliseconds");

    }
}
