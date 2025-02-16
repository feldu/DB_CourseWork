package db.coursework.utils;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

    public static String randowString() {
        return UUID.randomUUID().toString();
    }

    public static String randomPassport() {
        Random random = new Random();
        StringBuilder passport = new StringBuilder();

        // Генерация 7 случайных цифр
        for (int i = 0; i < 7; i++) {
            passport.append(random.nextInt(10));
        }

        return passport.toString();
    }

    public static Integer randomWeight() {
        Random random = new Random();
        return random.nextInt(81) + 40;
    }

}

