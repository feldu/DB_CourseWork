package test.coursework.utils;

import lombok.experimental.UtilityClass;

import java.util.Random;
import java.util.UUID;

@UtilityClass
public class RandomUtils {

    private static final Random random = new Random();

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static int randomInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
