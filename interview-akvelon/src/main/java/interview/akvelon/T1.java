package interview.akvelon;

import java.util.ArrayList;
import java.util.List;

public class T1 {
    public static void main(String[] args) {
        List<Integer> simpleNums = new ArrayList<>();

        for (int i = 2; i < 100; i++) {
            boolean isSimple = true;
            for (Integer simpleNum : simpleNums) {
                if (i == simpleNum) {
                    continue;
                }

                int remind = i % simpleNum;
                if (0 == remind) {
                    isSimple = false;
                    break;
                }
            }

            if(isSimple)
                simpleNums.add(i);
        }


        System.out.println(simpleNums);
    }
}
