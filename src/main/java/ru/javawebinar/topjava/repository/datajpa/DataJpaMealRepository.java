package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    public static final Sort SORT_BY_DATETIME_DESC = Sort.by(Sort.Direction.DESC, "dateTime");
    private final CrudMealRepository crudRepository;

    public DataJpaMealRepository(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(em.getReference(User.class, userId));
        return !meal.isNew() && get(meal.id(), userId) == null ? null : crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(SORT_BY_DATETIME_DESC, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getAllByUserIdAndDateTimeIsGreaterThanEqualAndDateTimeIsLessThan(
                SORT_BY_DATETIME_DESC, userId, startDateTime, endDateTime);
    }
    public Meal getWithUser(int id, int userId) {
        return crudRepository.getWithUser(id, userId);
    }
}
