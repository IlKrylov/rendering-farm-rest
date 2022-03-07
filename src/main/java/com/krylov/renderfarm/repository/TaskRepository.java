package com.krylov.renderfarm.repository;

import com.krylov.renderfarm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("FROM Task WHERE user.id = ?1")
    List<Task> findAllByUserId(Long userId);

}
