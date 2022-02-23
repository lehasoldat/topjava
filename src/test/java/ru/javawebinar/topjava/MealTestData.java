package ru.javawebinar.topjava;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MealTestData {

    public static final List<Meal> USER_MEALS = new ArrayList<>(Arrays.asList(
            new Meal(100003, LocalDateTime.parse("2022-02-20T06:20:00"), "Завтрак", 500),
            new Meal(100004, LocalDateTime.parse("2022-02-20T11:50:00"), "Обед", 1100),
            new Meal(100005, LocalDateTime.parse("2022-02-20T19:00:00"), "Ужин", 400),
            new Meal(100006, LocalDateTime.parse("2022-02-20T23:00:00"), "Полуночный перекус", 100),
            new Meal(100007, LocalDateTime.parse("2022-02-21T06:30:00"), "Завтрак", 400),
            new Meal(100008, LocalDateTime.parse("2022-02-21T12:00:00"), "Обед", 1200),
            new Meal(100009, LocalDateTime.parse("2022-02-21T19:30:00"), "Ужин", 400),
            new Meal(100010, LocalDateTime.parse("2022-02-22T06:20:00"), "Завтрак", 300),
            new Meal(100011, LocalDateTime.parse("2022-02-22T11:40:00"), "Обед", 1400),
            new Meal(100012, LocalDateTime.parse("2022-02-22T20:00:00"), "Ужин", 800)
    ));

    public static final List<Meal> ADMIN_MEALS = new ArrayList<>(Arrays.asList(
            new Meal(100013, LocalDateTime.parse("2022-02-21T09:00:00"), "Завтрак", 200),
            new Meal(100014, LocalDateTime.parse("2022-02-21T14:00:00"), "Обед", 1300),
            new Meal(100015, LocalDateTime.parse("2022-02-21T20:30:00"), "Ужин", 400)
    ));


    public static final int MEAL_ID = START_SEQ + 3;
    public static final int DELETE_ID = START_SEQ + 4;
    public static final int NOT_FOUND = START_SEQ + 16;
    public static final Meal MEAL = USER_MEALS.get(0);
    public static final LocalDate START_DATE = LocalDate.parse("2022-02-21");
    public static final LocalDate END_DATE = LocalDate.parse("2022-02-22");

    public static void assertMatch(Meal actual, Meal expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static List<Meal> getMeals(LocalDate startDate, LocalDate endDate) {
        List<Meal> expected = USER_MEALS.stream()
                .filter(meal -> meal.getDate().compareTo(startDate) >= 0
                        && meal.getDate().compareTo(endDate) <= 0)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return expected;
    }

    public static Meal getUpdated() {
        Meal meal = new Meal(MEAL);
        meal.setCalories(400);
        meal.setDescription("Новый завтрак");
        meal.setDateTime(LocalDateTime.parse("2022-02-20T06:20:10"));
        return meal;
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2022, 2, 23, 12, 30, 0), "Еда", 1000);
    }
}
