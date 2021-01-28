package org.nextprot.dataintegration.experiments.commpatterns.org.nextprot.dataintegration.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.nextprot.commons.statements.Statement;
import org.nextprot.commons.statements.specs.CoreStatementField;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class BufferedJsonStatementWriter {

    JsonGenerator jGenerator;

    public BufferedJsonStatementWriter() {
        try {
            FileOutputStream stream = new FileOutputStream(new File("output.json"));
            JsonFactory jfactory = new JsonFactory();
            jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startWritingArray() {
        try {
            jGenerator.writeStartArray();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void endWritingArray() {
        try {
            jGenerator.writeEndArray();
            jGenerator.flush();
            jGenerator.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void writeStatement(Statement statement) {
        String entryAccession = statement.getValue(CoreStatementField.EVIDENCE_CODE);
        String annotationCategory = statement.getAnnotationCategory();
        String annotationId = statement.getValue(CoreStatementField.BIOLOGICAL_OBJECT_ACCESSION);

        try {
            jGenerator.writeStartObject();
            jGenerator.writeStringField("entry", entryAccession);
            jGenerator.writeStringField("annotationCategory" , annotationCategory);
            jGenerator.writeStringField("annotationId" , annotationId);
            jGenerator.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
