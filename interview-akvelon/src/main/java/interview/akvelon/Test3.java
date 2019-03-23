package interview.akvelon;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Test3 {
    public static List<Integer> getMinimumDifference(List<String> a, List<String> b) {
        // Write your code here
        int size = a.size();
        List<Integer> result = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            String wordA = a.get(i);
            String wordB = b.get(i);

            if (wordA.length() != wordB.length()) {
                result.add(-1);
            } else {
                Map<Integer, List<Integer>> wordAEx = wordA.chars().boxed().collect(Collectors.groupingBy(Function.identity()));
                Map<Integer, List<Integer>> wordBEx = wordB.chars().boxed().collect(Collectors.groupingBy(Function.identity()));

                int diff = 0;

                Set<Integer> keys = new HashSet<>();
                keys.addAll(wordAEx.keySet());
                keys.addAll(wordBEx.keySet());

                for (Integer key : keys) {
                    int sizeA = 0;
                    if (wordAEx.containsKey(key)) {
                        sizeA = wordAEx.get(key).size();
                    }

                    int sizeB = 0;
                    if (wordBEx.containsKey(key)) {
                        sizeB = wordBEx.get(key).size();
                    }

                    diff += Math.abs(sizeA - sizeB);
                }


                result.add(diff/2);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getMinimumDifference(Arrays.asList("a",
                "jk",
                "abb",
                "mn",
                "abc"), Arrays.asList("bb",
                "kj",
                "bbc",
                "op",
                "def")));
    }
}
