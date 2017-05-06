package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    @Transactional
    @Override
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Override
    Meal findOne(Integer id);

    @Modifying
    @Query(name = Meal.ALL_SORTED)
    List<Meal> findAllOfUser(@Param("userId")int userId);

    @Modifying
    @Query(name = Meal.GET_BETWEEN)
    List<Meal> findAllMealsOfUserBetween(@Param("userId")int userId, @Param("startDate")LocalDateTime startDate, @Param("endDate")LocalDateTime endDate);
}
