package jal.dev.common.utils;


public class TimeUtil {
    public static String formatDuration(long duration) {
        long seconds = duration / 1000;
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%02d:%02d:%02d",
                absSeconds / 3600,
                (absSeconds % 3600) / 60,
                absSeconds % 60);
        return seconds < 0 ? "-" + positive : positive;
    }
}
