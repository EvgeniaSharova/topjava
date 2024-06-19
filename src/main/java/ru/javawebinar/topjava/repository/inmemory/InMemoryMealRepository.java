package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repositoryUsersMeals = new ConcurrentHashMap<>();
    private final AtomicInteger mealId = new AtomicInteger(0);


    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(mealId.incrementAndGet());
            repositoryMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repositoryMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return repositoryMeals.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return CollectionUtils.isEmpty(repositoryMeals) ?
                null : repositoryMeals.get(mealId);

    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> repositoryMeals = repositoryUsersMeals.get(userId);
        return CollectionUtils.isEmpty(repositoryMeals) ?
                Collections.emptyList() :
                repositoryMeals.values()
                        .stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}

