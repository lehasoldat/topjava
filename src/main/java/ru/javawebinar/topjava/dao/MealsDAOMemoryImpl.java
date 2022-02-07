package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.Main.MEALS;
import static ru.javawebinar.topjava.Main.ID_COUNTER;

public class MealsDAOMemoryImpl implements MealsDAO {

    @Override
    public List<Meal> getAll() {
        return MEALS;
    }

    @Override
    public Meal getById(int id) {
        int i = 0;
        while (i < MEALS.size() && !MEALS.get(i).getId().equals(id)) {
            i++;
        }
        return MEALS.get(i);
    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == null)
            create(meal);
        else update(meal);
    }

    @Override
    public void delete(int id) {
        Meal meal = getById(id);
        MEALS.remove(meal);
    }

    private void create(Meal meal) {
        meal.setId(ID_COUNTER.getAndIncrement());
        MEALS.add(meal);
    }

    private void update(Meal meal) {
        Meal oldMeal = getById(meal.getId());
        MEALS.set(MEALS.indexOf(oldMeal), meal);
    }
}
