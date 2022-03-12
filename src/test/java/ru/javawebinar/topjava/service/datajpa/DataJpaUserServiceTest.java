package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.UserTestData.GUEST_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(USER_ID);
        List<Meal> actual = user.getMeals();
        MealTestData.MEAL_MATCHER.assertMatch(actual, MealTestData.meals);
    }

    @Test
    public void getWithNoMeals() {
        User user = service.getWithMeals(GUEST_ID);
        List<Meal> actual = user.getMeals();
        MealTestData.MEAL_MATCHER.assertMatch(actual, new ArrayList<>());
    }
}
