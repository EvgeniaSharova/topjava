package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;

public class Util {
    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T startTime, @Nullable T endTime) {
        return (startTime == null || value.compareTo(startTime) >= 0) && (endTime == null || value.compareTo(endTime) < 0);
    }

}
