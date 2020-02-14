package ru.bigdatasolutions.denisrylow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class DataProcessorTreeSetImpl implements DataProcessor {
    private Map<String, SortedSet<Long>> trades = new HashMap<>();
    private SortedMap<String, Integer> maxTradeCount = new TreeMap<>();

    private static final Long oneMinute =  60 * 1000L;

    public void processData(DataParser.ParsedData data) {
        SortedSet<Long> setOfTrades = trades.get(data.getExchange());
        if (setOfTrades == null) {
            setOfTrades = new TreeSet<>();
            trades.put(data.getExchange(), setOfTrades);
        }
        setOfTrades.add(data.getTimestamp());
        SortedSet<Long> subset = setOfTrades.tailSet(data.getTimestamp() - oneMinute + 1);
        Integer n = subset.size();
        Integer count = maxTradeCount.get(data.getExchange());
        if (count == null || n > count) {
            maxTradeCount.put(data.getExchange(), n);
        }
    }

    public List<Integer> makeRank() {
        return new ArrayList<>(maxTradeCount.values());
    }
}
