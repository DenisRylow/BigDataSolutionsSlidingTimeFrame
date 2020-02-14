package ru.bigdatasolutions.denisrylow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DataProcessorListImpl implements DataProcessor {
    private Map<String, LinkedList<Long>> tradesV2 = new HashMap<>();
    private SortedMap<String, Integer> maxTradeCountV2 = new TreeMap<>();

    private static final Long oneMinute =  60 * 1000L;

    public List<Integer> makeRank() {
        return new ArrayList<>(maxTradeCountV2.values());
    }

    public void processData(DataParser.ParsedData data) {
        LinkedList<Long> listOfTrades = tradesV2.get(data.getExchange());
        if (listOfTrades == null) {
            listOfTrades = new LinkedList<>();
            tradesV2.put(data.getExchange(), listOfTrades);
        }
        listOfTrades.add(data.getTimestamp());
        Iterator<Long> iter = listOfTrades.iterator();
        boolean inFrame = false;
        while (iter.hasNext() && !inFrame) {
            Long val = iter.next();
            if (val < (data.getTimestamp() - oneMinute + 1)) {
                iter.remove();
            } else {
                inFrame = true;
            }
        }
        Integer n = listOfTrades.size();
        Integer count = maxTradeCountV2.get(data.getExchange());
        if (count == null || n > count) {
            maxTradeCountV2.put(data.getExchange(), n);
        }
    }
}
