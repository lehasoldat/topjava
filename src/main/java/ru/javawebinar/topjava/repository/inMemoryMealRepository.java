package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.Main.MEALS;
import static ru.javawebinar.topjava.Main.ID_COUNTER;

public class inMemoryMealRepository implements mealsRepository {

    @Override
    public Collection<Meal> getAll() {
        return MEALS.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public Meal getMeal(int id) {
        return MEALS.get(id);
    }

    @Override
    public void save(Meal meal) {
        if (meal.isNew())
            meal.setId(ID_COUNTER.getAndIncrement());
        MEALS.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        MEALS.remove(id);
    }

}
