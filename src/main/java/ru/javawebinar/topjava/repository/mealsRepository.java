package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface mealsRepository {

    Collection<Meal> getAll();

    Meal getMeal(int id);

    void delete(int id);

    void save(Meal meal);
}
