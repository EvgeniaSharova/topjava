package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.inMemoryMealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new inMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("mealList", MealsUtil.getMealsTo(storage.getAll(), CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            String id = request.getParameter("id");
            switch (action) {
                case "delete": {
                    log.debug("delete meal with id {}", id);
                    storage.delete(Integer.parseInt(id));
                    response.sendRedirect("meals");
                    return;
                }
                case "add": {
                    log.debug("add new meal");
                    Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0);
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                }
                case "edit": {
                    log.debug("edit meal with id {}", id);
                    Meal meal = storage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                }
                default: {
                    request.setAttribute("mealList", MealsUtil.getMealsTo(storage.getAll(), CALORIES_PER_DAY));
                    request.getRequestDispatcher("/meals.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")).truncatedTo(ChronoUnit.MINUTES),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        log.debug("correct {}", (id.isEmpty() ? "add new meal" : "edit meal " + meal.getId()));
        storage.save(meal);
        response.sendRedirect("meals");
    }
}
