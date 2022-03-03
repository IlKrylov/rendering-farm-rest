package com.krylov.renderfarm.repository;

import com.krylov.renderfarm.entity.TaskState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStateRepository extends JpaRepository<TaskState, Long> {
}
