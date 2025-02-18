package db.coursework.utils;

public class WaitUtils {

    public static void waitIfNeed() {
        try {
            if (PropUtils.get("test.is-slow").equals("true")) {
                Thread.sleep(Long.parseLong(PropUtils.get("test.delay")));
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

