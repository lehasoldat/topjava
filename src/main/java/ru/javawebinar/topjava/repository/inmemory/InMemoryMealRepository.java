package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal == null) return null;

        if (meal.isNew()) {
            if (!isValid(meal, userId)) return null;
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }

        if (!isValid(repository.get(meal.getId()), userId)) return null;

        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        if (!isValid(repository.get(id), userId)) return false;
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (!isValid(repository.get(id), userId)) return null;
        return repository.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllFilteredByPredicate(userId, meal -> meal.getDate().compareTo(startDate) >= 0 && meal.getDate().compareTo(endDate) <= 0);
    }

    private List<Meal> getAllFilteredByPredicate(int userId, Predicate<Meal> predicate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    private boolean isValid(Meal meal, int userId) {
        return meal != null && meal.getUserId() == userId;
    }
}

