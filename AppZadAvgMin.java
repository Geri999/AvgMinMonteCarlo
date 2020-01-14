package ex;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class AppZadAvgMin {

    // generator tablicy wartosci int od 0 do max w ilość size.
    public static ArrayList<Integer> arrayGenerator(int size, int max) {
        ArrayList<Integer> array = new ArrayList<>();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < size; i++) {
            array.add(secureRandom.nextInt(max));
        }
        return array;
    }

    // wyświetlanie tablic(ale ....jak są długie to jest nudno)
    public static void displayArray(ArrayList array) {
        System.out.println(array.toString());
    }

    //wyliczenie statystyk i zwrócenie ich w postaci hashMapy (strumienie)
    public static HashMap statisticsGeneratorModern(List<Integer> array) {
        HashMap<String, Double> statisticsHashMap = new HashMap<>();
        statisticsHashMap.put("Minimum", array.stream().mapToDouble(Integer::intValue).min().getAsDouble());
        statisticsHashMap.put("AVG", array.stream().mapToDouble(Integer::intValue).average().getAsDouble());

        return statisticsHashMap;
    }

    //wyliczenie statystyk i zwrócenie ich w postaci hashMapy (for)
    public static HashMap statisticsGeneratorClassic(List<Integer> array) {
        HashMap<String, Double> statisticsHashMap = new HashMap<>();
        int minimum = Integer.MAX_VALUE;
        double avg = 0;
        for (Integer integer : array) {
            if (minimum > integer) minimum = integer;
            avg += integer;
        }
        avg = avg / array.size();
        statisticsHashMap.put("Minimum", (double) minimum);
        statisticsHashMap.put("AVG", avg);

        return statisticsHashMap;
    }

    //MonteCarlo szukacz minimum
    public static double mcMinimumSeeker(List<Integer> array, int attemps) {
        SecureRandom secureRandom = new SecureRandom();
        int minimum = Integer.MAX_VALUE;
        for (int i = 0; i < attemps; i++) {
            int position = secureRandom.nextInt(array.size());
            int los = array.get(position);
            if (los < minimum) minimum = los;
        }
        return minimum;
    }

    //MonteCarlo szukacz średniej
    public static double mcAverageSeeker(List<Integer> array, int attemps) {
        SecureRandom secureRandom = new SecureRandom();
        long sum = 0;
        for (int i = 0; i < attemps; i++) {
            int position = secureRandom.nextInt(array.size());
            int los = array.get(position);
            sum += los;
        }
        return sum / attemps;
    }

    public static void main(String[] args) {
        int size = 1000, max = 1_000_000;

        ArrayList<Integer> tablica = arrayGenerator(size, max);
        // displayArray(tablica);
        System.out.printf(Locale.forLanguageTag("PL"),
                "Rozmar tablicy to: %,d elementów,%na największy z nich może mieć " +
                        "wartość: %,d.%n%n", size, max);

        System.out.println("Wartości obliczone:");
        System.out.println("(strumienie): " + statisticsGeneratorModern(tablica).toString());
        System.out.println("(klasycznie): " + statisticsGeneratorClassic(tablica).toString());

        System.out.println("\nOszacowanie wartości minimum:");
        System.out.printf("%-15s %10.0f %n", "10 prób:", mcMinimumSeeker(tablica, 10));
        System.out.printf("%-15s %10.0f %n", "1 000 prób:", mcMinimumSeeker(tablica, 1_000));
        System.out.printf("%-15s %10.0f %n", "100 000 prób:", mcMinimumSeeker(tablica, 100_000));

        System.out.println("\nOszacowanie wartości średniej:");
        System.out.printf("%-15s %.3f %n", "10 prób:", mcAverageSeeker(tablica, 10));
        System.out.printf("%-15s %.3f %n", "1 000 prób:", mcAverageSeeker(tablica, 1_000));
        System.out.printf("%-15s %.3f %n", "10 mln prób:", mcAverageSeeker(tablica, 10_000_000));
    }

}
