package ru.bigdatasolutions.denisrylow;

import java.util.Objects;
import java.util.TreeSet;


public class BigDataSolutions3Task {

    static class Letter {
        private Integer index;

        public Letter(Integer index, String letter) {
            this.index = index;
            this.letter = letter;
        }

        private String letter;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Letter letter = (Letter) o;
            return Objects.equals(index, letter.index);
        }

        @Override
        public int hashCode() {
            return Objects.hash(index);
        }
    }

   static void swap(String[] input, int a, int b) {
        String tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }

    static String toStr(String[] arr) {
        StringBuilder builder = new StringBuilder();
        for (String str : arr) {
            builder.append(str);
        }
        return builder.toString();
    }

    static public void main(String[] args) {
        TreeSet<String> set = new TreeSet<>();

        String[] elements = {"M", "A", "R", "G", "A", "R", "I", "T", "A"};
        set.add(toStr(elements));

        int n = elements.length;
        int[] indexes = new int[n];
        for (int i = 0; i < n; i++) {
            indexes[i] = 0;
        }
        int i = 0;
        while (i < n) {
            if (indexes[i] < i) {
                swap(elements, i % 2 == 0 ?  0: indexes[i], i);
                set.add(toStr(elements));
                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }
        System.out.println(set.size());
    }
}
