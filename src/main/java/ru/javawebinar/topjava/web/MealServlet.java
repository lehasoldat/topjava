package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.Main;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(Main.MEALS, LocalTime.MIN, LocalTime.MAX, Main.CALORIES_PER_DAY);
        request.setAttribute("mealsTo", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }
}
