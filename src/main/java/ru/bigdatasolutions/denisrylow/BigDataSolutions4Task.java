package ru.bigdatasolutions.denisrylow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class BigDataSolutions4Task {

    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(System.in);
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
        DataProcessor dataProcessor = new DataProcessorListImpl();
        DataParser dataParser = new DataParser();
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                DataParser.ParsedData data = dataParser.parseData(line);
                dataProcessor.processData(data);
            }
        } catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException(e);
        }
        List<Integer> list = dataProcessor.makeRank() ;
        for (Integer el : list) {
            System.out.println(el);
        }
    }
}
