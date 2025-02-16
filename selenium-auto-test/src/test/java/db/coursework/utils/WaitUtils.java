package db.coursework.utils;

public class WaitUtils {

    public static void waitIfNeed() {
        try {
            if (PropUtils.get("test.is-slow").equals("true")) {
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

