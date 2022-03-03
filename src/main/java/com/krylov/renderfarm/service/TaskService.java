package com.krylov.renderfarm.service;

import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.entity.Task;

import java.util.List;

public interface TaskService {

    TaskDto createAndRun(Long userId, TaskDto taskDto);

    List<TaskDto> findAllTasksByUserId(Long userId);

    TaskDto findTaskById(Long userId, Long taskId);

}
