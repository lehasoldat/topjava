package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> users = new ArrayList<>(Arrays.asList(
            new User(1, "Aleksey", "Aleksey@mail.ru", "AlekseyPass", Role.USER, Role.ADMIN),
            new User(1, "Sergey", "Sergey@mail.ru", "SergeyPass", Role.USER),
            new User(1, "Dmitriy", "Dmitriy@mail.ru", "DmitriyPass", Role.USER),
            new User(1, "Nikolay", "Nikolay@mail.ru", "NikolayPass", Role.ADMIN),
            new User(1, "Aleksandr", "Aleksandr@mail.ru", "AleksandrPass", Role.USER),
            new User(1, "Oleg", "Oleg@mail.ru", "OlegPass", Role.USER),
            new User(1, "Vasiliy", "Vasiliy@mail.ru", "VasiliyPass", Role.USER)
    ));


}
