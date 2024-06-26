package ru.javawebinar.topjava.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.getEndInclusive;
import static ru.javawebinar.topjava.util.DateTimeUtil.getStartInclusive;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public void delete(int mealId, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.delete(mealId, userId), mealId);
    }

    public Meal get(int mealId, int userId) {
        return ValidationUtil.checkNotFoundWithId(repository.get(mealId, userId), mealId);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Meal meal, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());

    }

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public List<Meal> getBetweenHalfOpen(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(getStartInclusive(startDate),
                getEndInclusive(endDate), userId);
    }
}