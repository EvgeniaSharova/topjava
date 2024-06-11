package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to user");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("mealList", MealsUtil.getFilteredMeal(storage.getAll(), 2000));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        switch (action) {
            case "delete" : {
                String id = request.getParameter("id");
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            }
            case "add" : {
                storage.save(new Meal());
                response.sendRedirect("/mealsEdit.jsp");
                return;
            }
        }
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
