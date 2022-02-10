package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.repository.mealsRepository;
import ru.javawebinar.topjava.repository.inMemoryMealRepository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.Main.CALORIES_PER_DAY;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final mealsRepository repository = new inMemoryMealRepository();
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        //READ_ALL
        if (action == null) {
            log.info("Get all meals");
            List<MealTo> mealsTo = MealsUtil.getWithExceeded(repository.getAll(), CALORIES_PER_DAY);
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        //UPDATE
        else if (action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.info("Update meal id = " + id);
            Meal meal = repository.getMeal(id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("edit_meal.jsp").forward(request, response);
        }
        //CREATE
        else if (action.equals("create")) {
            Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("edit_meal.jsp").forward(request, response);
        }
        //DELETE
        else if (action.equals("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.info("Delete meal id = " + id);
            repository.delete(id);
            response.sendRedirect("meals");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        String id = request.getParameter("id");
        if (!id.isEmpty()) meal.setId(Integer.parseInt(id));
        if (meal.isNew()) {
            log.info("Create meal: " + meal);
        }
        repository.save(meal);
        response.sendRedirect("meals");
    }
}
