package ru.javawebinar.topjava.web;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class MealServlet extends HttpServlet {

    private MealRestController mealRestController;

    public ConfigurableApplicationContext appCtx;

    @Override
    public void init() throws ServletException {
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = appCtx.getBean(MealRestController.class);
        super.init();
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")),
                authUserId());

        if (meal.getId() == null) mealRestController.create(meal);
        else mealRestController.update(meal, Integer.parseInt(id));
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        LocalDate startDate = getDate(request, "startDate");
        LocalDate endDate = getDate(request, "endDate");
        LocalTime startTime = getTime(request, "startTime");
        LocalTime endTime = getTime(request, "endTime");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000, authUserId()) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                boolean filterByDate = false;
                boolean filterByTime = false;
                if (startDate != null || endDate != null) {
                    startDate = startDate == null ? LocalDate.MIN : startDate;
                    endDate = endDate == null ? LocalDate.MAX : endDate;
                    filterByDate = true;
                }
                if (startTime != null || endTime != null) {
                    startTime = startTime == null ? LocalTime.MIN : startTime;
                    endTime = endTime == null ? LocalTime.MAX : endTime;
                    filterByTime = true;
                }

                if (filterByDate && filterByTime) {
                    request.setAttribute("meals", mealRestController.getAll(startDate, endDate, startTime, endTime));
                } else if (filterByDate) {
                    request.setAttribute("meals", mealRestController.getAll(startDate, endDate));
                } else if (filterByTime) {
                    request.setAttribute("meals", mealRestController.getAll(startTime, endTime));
                } else {
                    request.setAttribute("meals", mealRestController.getAll());
                }
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private LocalDate getDate(HttpServletRequest request, String paramName) {
        LocalDate date = null;
        try {
            date = LocalDate.parse(request.getParameter(paramName));
        } catch (Exception ignore) {
        }
        return date;
    }

    private LocalTime getTime(HttpServletRequest request, String paramName) {
        LocalTime time = null;
        try {
            time = LocalTime.parse(request.getParameter(paramName));
        } catch (Exception ignore) {
        }
        return time;
    }


}
