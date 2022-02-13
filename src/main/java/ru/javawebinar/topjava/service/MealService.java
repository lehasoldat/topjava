package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public Meal create(Meal meal, int userId) {
        checkNew(meal);
        return checkNotFound(repository.save(meal, userId), "Invalid userId");
    }

    public void update(Meal meal, int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<Meal> getAll(int userId) {
        return checkNotFound(repository.getAll(userId), "Invalid userId");
    }

    public List<Meal> getAll(int userId, LocalDate startDate, LocalDate endDate) {
        return checkNotFound(repository.getAll(userId, startDate, endDate), "Invalid userId");
    }

}