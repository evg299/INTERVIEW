package interview.akvelon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test2 {
    public static List<Integer> getOneBits(int n) {
        List<Integer> result = new ArrayList<>();
        List<Integer> tmp = new ArrayList<>();

        int oneCounter = 0;
        while (0 != n) {
            int bit = n % 2;
            oneCounter += bit;
            tmp.add(bit);
            n = n / 2;
        }

        result.add(oneCounter);

        Collections.reverse(tmp);
        for (int i = 0; i < tmp.size(); i++) {
            if (1 == tmp.get(i)) {
                result.add(i + 1);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getOneBits(161));
    }
}
