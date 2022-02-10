package ru.javawebinar.topjava;

import ru.javawebinar.topjava.repository.mealsRepository;
import ru.javawebinar.topjava.repository.inMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {

    public static final Map<Integer, Meal> MEALS = new ConcurrentHashMap<>();
    public static final mealsRepository MEALS_REPOSITORY = new inMemoryMealRepository();
    public static final int CALORIES_PER_DAY = 2000;
    public static final AtomicInteger ID_COUNTER = new AtomicInteger(0);

    static {
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        MEALS_REPOSITORY.save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public static void main(String[] args) {
        System.out.format("Hello TopJava Enterprise!");
    }
}
