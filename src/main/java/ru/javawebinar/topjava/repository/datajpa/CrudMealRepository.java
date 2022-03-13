package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.Hibernate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserId(Sort sort, int userId);

    List<Meal> getAllByUserIdAndDateTimeIsGreaterThanEqualAndDateTimeIsLessThan(Sort sort, int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=?1 AND m.user.id=?2")
    Meal getWithUser(int id, int userId);
}
