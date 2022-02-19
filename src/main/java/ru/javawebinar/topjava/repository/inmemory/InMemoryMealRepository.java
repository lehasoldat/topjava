package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, InMemoryBaseRepository<Meal>> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.meals.forEach(meal -> save(meal, MealsUtil.User_ID));
        Arrays.asList(
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410))
                .forEach(meal -> save(meal, MealsUtil.Admin_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal == null) return null;
        InMemoryBaseRepository<Meal> meals = repository.computeIfAbsent(userId, k -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return getAllFilteredByPredicate(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFilteredByPredicate(int userId, Predicate<Meal> predicate) {
        InMemoryBaseRepository<Meal> meals = repository.get(userId);
        return meals.getCollection().stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

