package ru.netology;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RobotThread extends Thread {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void startRobot() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                String route = generateRoute("RLRFR", 100);
                System.out.println("Route = " + route);
                int numb = (int) route.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(numb)) {
                        sizeToFreq.put(numb, sizeToFreq.get(numb) + 1);
                    } else {
                        sizeToFreq.put(numb, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> max = sizeToFreq.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений " + max.getKey() +
                " (встретилось " + max.getValue() + "  раз)");

        System.out.println("Другие размеры: ");
        sizeToFreq.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" - " + e.getKey() + " (встретилось " + e.getValue() + "  раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
