package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import javax.validation.*;
import java.util.Date;
import java.util.Set;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Override
    public void createWithException() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Assert.notEmpty(validator.validate(service.create(new User(null, "  ", "mail1@yandex.ru", "password", Role.USER))), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new User(null, "User", "  ", "password", Role.USER))), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new User(null, "User", "mail2@yandex.ru", "  ", Role.USER))), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new User(null, "User", "mail3@yandex.ru", "password", 9, true, new Date(), Set.of()))), "Validation failed");
        Assert.notEmpty(validator.validate(service.create(new User(null, "User", "mail4@yandex.ru", "password", 100001, true, new Date(), Set.of()))), "Validation failed");
    }
}