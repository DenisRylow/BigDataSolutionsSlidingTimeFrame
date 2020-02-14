package ru.bigdatasolutions.denisrylow;

import java.util.List;

public interface DataProcessor {
    void processData(DataParser.ParsedData str);
    List<Integer> makeRank();
}
