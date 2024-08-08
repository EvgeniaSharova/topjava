package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 2;

    public static final int NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(MEAL_ID + 1, LocalDateTime.of(2024, Month.JULY, 29, 9, 0), "завтрак юзер", 500);
    public static final Meal userMeal2 = new Meal(MEAL_ID + 2, LocalDateTime.of(2024, Month.JULY, 29, 12, 30), "обед юзер", 600);
    public static final Meal userMeal3 = new Meal(MEAL_ID + 3, LocalDateTime.of(2024, Month.JULY, 29, 15, 0), "ланч юзер", 700);
    public static final Meal userMeal4 = new Meal(MEAL_ID + 4, LocalDateTime.of(2024, Month.JULY, 29, 19, 40), "ужин юзер", 800);
    public static final Meal userMeal5 = new Meal(MEAL_ID + 5, LocalDateTime.of(2024, Month.JUNE, 20, 8, 0), "завтрак юзер", 500);
    public static final Meal userMeal6 = new Meal(MEAL_ID + 6, LocalDateTime.of(2024, Month.JUNE, 20, 13, 50), "обед юзер", 500);
    public static final Meal userMeal7 = new Meal(MEAL_ID + 7, LocalDateTime.of(2024, Month.JUNE, 20, 17, 0), "ланч юзер", 500);
    public static final Meal userMeal8 = new Meal(MEAL_ID + 8, LocalDateTime.of(2024, Month.JUNE, 20, 21, 10), "ужин юзер", 500);


    public static final Meal adminMeal1 = new Meal(MEAL_ID + 9, LocalDateTime.of(2024, Month.JULY, 29, 10, 35), "завтрак админ", 500);
    public static final Meal adminMeal2 = new Meal(MEAL_ID + 10, LocalDateTime.of(2024, Month.JULY, 29, 11, 30), "обед админ", 600);
    public static final Meal adminMeal3 = new Meal(MEAL_ID + 11, LocalDateTime.of(2024, Month.JULY, 29, 16, 20), "ланч админ", 700);
    public static final Meal adminMeal4 = new Meal(MEAL_ID + 12, LocalDateTime.of(2024, Month.JULY, 29, 22, 10), "ужин админ", 800);
    public static final Meal adminMeal5 = new Meal(MEAL_ID + 13, LocalDateTime.of(2024, Month.MAY, 10, 7, 34), "завтрак админ", 100);
    public static final Meal adminMeal6 = new Meal(MEAL_ID + 14, LocalDateTime.of(2024, Month.MAY, 10, 14, 29), "обед админ", 500);
    public static final Meal adminMeal7 = new Meal(MEAL_ID + 15, LocalDateTime.of(2024, Month.MAY, 10, 18, 48), "ланч админ", 500);
    public static final Meal adminMeal8 = new Meal(MEAL_ID + 16, LocalDateTime.of(2024, Month.MAY, 10, 23, 59), "ужин админ", 500);


    public static final List<Meal> USER_MEALS_LIST = Arrays.asList(userMeal4, userMeal3, userMeal2, userMeal1, userMeal8, userMeal7, userMeal6, userMeal5);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, Month.AUGUST, 7, 10, 0), "новая еда", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(adminMeal1);
        updated.setDateTime(LocalDateTime.of(2024, Month.AUGUST, 7, 12, 12));
        updated.setDescription("обновленная еда");
        updated.setCalories(1212);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }

}
