package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    public static final Sort SORT_BY_DATETIME_DESC = Sort.by(Sort.Direction.DESC, "dateTime");
    private final CrudMealRepository crudMealRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudUserRepository crudUserRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getById(userId));
        return !meal.isNew() && get(meal.id(), userId) == null ? null : crudMealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepository.getAllByUserId(SORT_BY_DATETIME_DESC, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepository.getAllByUserIdAndDateTimeIsGreaterThanEqualAndDateTimeIsLessThan(
                SORT_BY_DATETIME_DESC, userId, startDateTime, endDateTime);
    }

    @Override
    public Meal getWithUser(int id, int userId) {
        return crudMealRepository.getWithUser(id, userId);
    }
}
