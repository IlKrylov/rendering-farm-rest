package com.krylov.renderfarm.repository;

import com.krylov.renderfarm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
