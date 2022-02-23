package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF8"))
public class MealServiceTest {

    @Autowired
    private MealService mealService;

    static {
        SLF4JBridgeHandler.install();
    }

    @Test
    public void get() {
        assertMatch(mealService.get(MEAL_ID, USER_ID), MEAL);
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> {
            mealService.get(MealTestData.NOT_FOUND, USER_ID);
        });
    }

    @Test
    public void getWithWrongUserId() {
        Assert.assertThrows(NotFoundException.class, () -> {
            mealService.get(MEAL_ID, ADMIN_ID);
        });
    }

    @Test
    public void delete() {
        mealService.delete(DELETE_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> mealService.get(DELETE_ID, USER_ID));
    }

    @Test
    public void deleteWithWrongUserId() {
        assertThrows(NotFoundException.class, () -> mealService.get(DELETE_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = getMeals(START_DATE, END_DATE);
        List<Meal> actual = mealService.getBetweenInclusive(START_DATE, END_DATE, USER_ID);
        assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expected = getMeals(LocalDate.MIN, LocalDate.MAX);
        List<Meal> actual = mealService.getAll(USER_ID);
        assertMatch(actual, expected);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(updated.getId(), USER_ID), updated);
    }

    @Test
    public void updateWithWrongUserId() {
        Meal updated = MealTestData.getUpdated();
        assertThrows(NotFoundException.class, () -> mealService.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = MealTestData.getNew();
        int newId = mealService.create(created, USER_ID).getId();
        created.setId(newId);
        assertMatch(mealService.get(newId, USER_ID), created);
    }

    @Test
    public void duplicateDateTimeCreate() {
        Meal duplicate = new Meal(MEAL);
        duplicate.setId(null);
//        mealService.create(duplicate, USER_ID);
        assertThrows(DuplicateKeyException.class, () -> mealService.create(duplicate, USER_ID));
    }

}