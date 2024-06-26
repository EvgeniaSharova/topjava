package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.meals;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repositoryUsersMeals = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        meals.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2015, Month.JANUARY, 1, 14, 0),
                "Админ обед", 1000), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2015, Month.JANUARY, 1, 21, 0),
                "Админ ужин", 1000), ADMIN_ID);

    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repositoryMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repositoryMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return repositoryMeals != null && repositoryMeals.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return CollectionUtils.isEmpty(repositoryMeals) ?
                null : repositoryMeals.get(mealId);

    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenHalfOpen(meal.getDateTime(), startDate, endDate));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return CollectionUtils.isEmpty(repositoryMeals) ?
                Collections.emptyList() :
                repositoryMeals.values()
                        .stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

