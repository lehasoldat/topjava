package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {

    public static List<MealTo> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return filteredByPredicate(meals, meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime), caloriesPerDay);
    }

    public static List<MealTo> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return filteredByPredicate(meals, meal -> true, caloriesPerDay);
    }


    public static List<MealTo> filteredByPredicate(Collection<Meal> meals, Predicate<Meal> predicate, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(predicate)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
