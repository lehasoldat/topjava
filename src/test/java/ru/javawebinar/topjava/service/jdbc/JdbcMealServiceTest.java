package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @Override
    public void createWithException() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Assert.notEmpty(validator.validate(service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID)), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 1), "Description", 9), USER_ID)), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new Meal(null, of(2015, Month.JUNE, 1, 18, 2), "Description", 5001), USER_ID)), "Validation failed");
    }
}