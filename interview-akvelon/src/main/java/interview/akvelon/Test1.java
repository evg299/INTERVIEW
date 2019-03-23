package interview.akvelon;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Test1 {
    public static String arrange(String sentence) {
        if (sentence.endsWith(".")) {
            sentence = sentence.substring(0, sentence.length() - 1);
        }

        sentence = sentence.toLowerCase();
        String[] words = sentence.split("\\s+");

        String resultSentence = Arrays.asList(words).stream().sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.joining(" "));

        return resultSentence.substring(0, 1).toUpperCase() + resultSentence.substring(1) + ".";
    }

    public static void main(String[] args) {
        String input = "The lines are printed in reverse order.";
        System.out.println(arrange(input));
    }
}
