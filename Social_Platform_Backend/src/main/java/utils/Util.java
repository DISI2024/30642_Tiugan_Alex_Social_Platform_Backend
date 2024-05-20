package utils;

public class Util {


    private Util() {

    }
    public static Integer getInteger(Object number) {

        if (number == null) {
            return null;
        }

        return ((Number) number).intValue();
    }

    public static Long getLong(Object number) {

        if (number == null) {
            return null;
        }

        return ((Number) number).longValue();
    }
}
