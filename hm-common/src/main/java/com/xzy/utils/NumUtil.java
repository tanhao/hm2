package com.xzy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NumUtil {
    public static String[] carship = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};

    public static List<String> hmpk10Number() {
        List<String> retuRes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            List<String> result = Arrays.asList(carship);
            result.sort((o1, o2) -> random.nextInt());
            retuRes.add(String.join(",", result));
        }
        return retuRes;
    }

    public static List<String> hmsscNumber() {
        List<String> retuRes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> result = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Random random = new Random();
                int idx = random.nextInt(10);
                result.add(idx + "");
            }
            retuRes.add(String.join(",", result));
        }
        return retuRes;
    }

    public static void main(String[] args) {
        System.out.println(NumUtil.hmpk10Number());
        System.out.println(NumUtil.hmsscNumber());
    }
}
