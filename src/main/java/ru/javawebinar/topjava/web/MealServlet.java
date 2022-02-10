package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealsDAO;
import ru.javawebinar.topjava.dao.MealsDAOMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.Main.CALORIES_PER_DAY;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private final MealsDAO mealsDAO = new MealsDAOMemoryImpl();
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        //READ_ALL
        if (action == null) {
            log.info("Get all meals");
            List<Meal> meals = mealsDAO.getAll();
            List<MealTo> mealsTo = MealsUtil.getWithExceeded(meals, CALORIES_PER_DAY);
            request.setAttribute("mealsTo", mealsTo);
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
        //CREATE or UPDATE
        else if (action.equals("edit")) {
            String idParam = request.getParameter("id");
            Meal meal;
            if (idParam == null) {
                log.info("Create meal");
                meal = new Meal(LocalDateTime.now(), "", 0);
            } else {
                log.info("Update meal");
                int id = Integer.parseInt(idParam);
                meal = mealsDAO.getById(id);
            }
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("edit_meal.jsp").forward(request, response);
        }
        //DELETE
        else if (action.equals("delete")) {
            log.info("Delete meal");
            int id = Integer.parseInt(request.getParameter("id"));
            mealsDAO.delete(id);
            response.sendRedirect("meals");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Save meal");
        request.setCharacterEncoding("UTF-8");
        try {
            String id = request.getParameter("id");
            LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
            String description = request.getParameter("description");
            int calories = Integer.parseInt(request.getParameter("calories"));
            Meal meal = new Meal(localDateTime, description, calories);
            if (!id.isEmpty()) meal.setId(Integer.parseInt(id));
            mealsDAO.save(meal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        response.sendRedirect("meals");
    }
}
