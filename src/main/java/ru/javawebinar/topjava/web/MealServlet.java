package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final int CALORIES_PER_DAY = 2000;
    private MealStorage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MapMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to user");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("mealList", MealsUtil.getMealsTo(storage.getAll(), CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else {
            String id = request.getParameter("id");
            switch (action) {
                case "delete": {
                    storage.delete(Integer.parseInt(id));
                    response.sendRedirect("meals");
                    return;
                }
                case "add": {
                    Meal meal = new Meal(LocalDateTime.now(), "", 0);
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                }
                case "edit": {
                    Meal meal = storage.get(Integer.parseInt(id));
                    request.setAttribute("meal", meal);
                    request.getRequestDispatcher("mealsEdit.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        storage.save(meal);
        response.sendRedirect("meals");
    }
}
