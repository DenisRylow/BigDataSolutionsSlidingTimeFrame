package ru.bigdatasolutions.denisrylow;

public class DataParser {
    public static class ParsedData {
        private Long timestamp;
        private String exchange;

        public ParsedData(Long timestamp, String exchange) {
            this.timestamp = timestamp;
            this.exchange = exchange;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public String getExchange() {
            return exchange;
        }

        public void setExchange(String exchange) {
            this.exchange = exchange;
        }
    }

    public ParsedData parseData(String str) {
        Long hour = Long.parseLong(str.substring(0,2));
        Long min = Long.parseLong(str.substring(3,5));
        Long sec = Long.parseLong(str.substring(6,8));
        Long millis = Long.parseLong(str.substring(9,12));
        Long timestamp = millis + 1000 * (sec + 60 * (min + 60 * hour));
        int exchangePos = str.lastIndexOf(",") + 1;
        String exchange = str.substring(exchangePos);
        return new ParsedData(timestamp, exchange);
    }
}
