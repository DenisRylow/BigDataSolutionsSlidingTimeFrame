package ru.bigdatasolutions.denisrylow;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Test4Task {

    @org.junit.Test
    public void testDataParser() {
        DataParser dataParser = new DataParser();
        DataParser.ParsedData data =
                dataParser.parseData("00:30:00.010,36.99,100,V");
        Assert.assertEquals("V", data.getExchange());
        Assert.assertEquals(Long.valueOf(1800010L), data.getTimestamp());

        DataParser.ParsedData data2 =
                dataParser.parseData("01:00:00.000,36.99,100,V");
        Assert.assertEquals("V", data2.getExchange());
        Assert.assertEquals(Long.valueOf(3600000L), data2.getTimestamp());
    }

    @org.junit.Test
    public void testListDataProcessor() {
        DataProcessor dataProcessor = new DataProcessorListImpl();
        DataParser dataParser = new DataParser();
        List<String> ar = Arrays.asList(
                "09:30:01.034,36.99,100,V",
                "09:30:55.000,37.08,205,V",
                "09:30:55.554,36.90,54,V",
                "09:30:55.556,36.91,99,D",
                "09:31:01.033,36.94,100,D",
                "09:31:01.034,36.95,900,V"
        );
        for (String el : ar) {
            dataProcessor.processData(dataParser.parseData(el));
        }
        List<Integer> result = dataProcessor.makeRank();
        Assert.assertEquals(Arrays.asList(2, 3), result);
    }

    @org.junit.Test
    public void testTreeSetDataProcessor() {
        DataProcessor dataProcessor = new DataProcessorTreeSetImpl();
        DataParser dataParser = new DataParser();
        List<String> ar = Arrays.asList(
                "09:30:01.034,36.99,100,V",
                "09:30:55.000,37.08,205,V",
                "09:30:55.554,36.90,54,V",
                "09:30:55.556,36.91,99,D",
                "09:31:01.033,36.94,100,D",
                "09:31:01.034,36.95,900,V"
        );
        for (String el : ar) {
            dataProcessor.processData(dataParser.parseData(el));
        }
        List<Integer> result = dataProcessor.makeRank();
        Assert.assertEquals(Arrays.asList(2, 3), result);
    }

    @org.junit.Test
    public void testMain() throws Exception {
        // Load trades. Prepare input stream.
        URL url = Test4Task.class.getResource("trades.csv");
        File file = new File(url.getPath());
        Objects.requireNonNull(file);
        System.setIn(new FileInputStream(file));

        // Prepare output stream.
        ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outBuffer);
        System.setOut(out);

        // Execute program.
        String[] args = new String[]{};
        BigDataSolutions4Task.main(args);

        // Load correct answers.
        URL url2 = Test4Task.class.getResource("correctAnswers.txt");
        File file2 = new File(url2.getPath());
        Objects.requireNonNull(file2);
        BufferedReader correctAsnws = new BufferedReader(new InputStreamReader(new FileInputStream(file2)));

        // Read output from system.out.
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outBuffer.toByteArray());
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        // Compare correct answers to the produced values.
        String correctAnsw;
        String producedAnsw;
        boolean correct;
        do {
            correctAnsw = correctAsnws.readLine();
            producedAnsw = outputReader.readLine();
            correct = Objects.equals(correctAnsw, producedAnsw);
        } while (correct && (correctAnsw != null));
        Assert.assertTrue(correct);
    }
}
