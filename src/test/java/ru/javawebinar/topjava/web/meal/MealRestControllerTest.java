package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.*;

class MealRestControllerTest extends AbstractControllerTest {

    private static final String MEAL_REST_URL = MealRestController.MEAL_REST_URL + "/";

    @Autowired
    private MealService mealService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MEAL_REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getTos(meals, DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(MEAL_REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void createWithLocation() throws Exception {
        Meal newMeal = MealTestData.getNew();
        ResultActions actions = perform(MockMvcRequestBuilders.post(MEAL_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());
        Meal created = MEAL_MATCHER.readFromJson(actions);
        int id = created.getId();
        newMeal.setId(id);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(id, USER_ID), newMeal);
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(MEAL_REST_URL + MEAL1_ID))
                .andExpect(status().isNoContent());
        Assertions.assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        Meal updated = MealTestData.getUpdated();
        int id = updated.getId();
        perform(MockMvcRequestBuilders.put(MEAL_REST_URL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealService.get(id, USER_ID), updated);
    }

    @Test
    void getBetween() throws Exception {
        LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 31);
        LocalTime startTime = LocalTime.of(10, 0);
        LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 31);
        LocalTime endTime = LocalTime.of(14, 0);
        List<MealTo> expected = getFilteredTos(mealService.getBetweenInclusive(startDate, endDate, USER_ID), DEFAULT_CALORIES_PER_DAY, startTime, endTime);
        perform(MockMvcRequestBuilders.get(MEAL_REST_URL + "filter?startDate={?}&startTime={?}&endDate={?}&endTime={?}", startDate, startTime, endDate, endTime))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }

    @Test
    void getBetweenWithNoTime() throws Exception {
        LocalDate startDate = LocalDate.of(2020, Month.JANUARY, 31);
        LocalDate endDate = LocalDate.of(2020, Month.JANUARY, 31);
        List<MealTo> expected = getFilteredTos(mealService.getBetweenInclusive(startDate, endDate, USER_ID), DEFAULT_CALORIES_PER_DAY, null, null);
        perform(MockMvcRequestBuilders.get(MEAL_REST_URL + "filter?startDate={?}&startTime={?}&endDate={?}&endTime={?}", startDate, null, endDate, null))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }

    @Test
    void getBetweenWithNoDate() throws Exception {
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(14, 0);
        List<MealTo> expected = getFilteredTos(mealService.getBetweenInclusive(null, null, USER_ID), DEFAULT_CALORIES_PER_DAY, startTime, endTime);
        perform(MockMvcRequestBuilders.get(MEAL_REST_URL + "filter?startDate={?}&startTime={?}&endDate={?}&endTime={?}", null, startTime, null, endTime))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(expected));
    }


}