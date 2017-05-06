package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    @Autowired
    private CrudMealRepository mealRepository;

    @Autowired
    private CrudUserRepository userRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(userRepository.findOne(userId));
        return mealRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return get(id, userId) != null && mealRepository.delete(id) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = mealRepository.findOne(id);
        if (meal.getUser().getId() != userId) return null;
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealRepository.findAllOfUser(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return mealRepository.findAllMealsOfUserBetween(userId, startDate, endDate);
    }
}