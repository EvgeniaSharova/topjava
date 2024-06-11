package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MealStorage implements Storage {
    final private Map<Integer, Meal> storage = new HashMap<>();
    final private AtomicInteger id = new AtomicInteger(0);

    {
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500));
        save(new Meal(id.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410));
    }

    @Override
    public Meal save(Meal meal) {
        return storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return storage.values();
    }
}
