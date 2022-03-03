package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.entity.Task;
import com.krylov.renderfarm.entity.TaskState;
import com.krylov.renderfarm.entity.User;
import com.krylov.renderfarm.entity.enums.TaskStatus;
import com.krylov.renderfarm.exception.DataBaseUpdateException;
import com.krylov.renderfarm.exception.EntityNotFoundException;
import com.krylov.renderfarm.repository.TaskRepository;
import com.krylov.renderfarm.repository.TaskStateRepository;
import com.krylov.renderfarm.repository.UserRepository;
import com.krylov.renderfarm.service.TaskExecutorService;
import com.krylov.renderfarm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public TaskDto createAndRun(Long userId, TaskDto taskDto) {
        TaskDto result;
        Task task = convertToEntity(userId, taskDto);
        try {
            task = taskRepository.save(task);
            result = convertToDto(task);
        } catch (Exception e) {
            throw new DataBaseUpdateException("Unable to save task");
        }

        //TODO:
        // taskExecutorService.assign(task);

        return result;
    }

    @Override
    @Transactional
    public List<TaskDto> findAllTasksByUserId(Long userId) {
        //TODO
        return null;
    }

    @Override
    @Transactional
    public TaskDto findTaskById(Long userId, Long taskId) {
        //TODO
        return null;
    }

    @Transactional
    public Task convertToEntity(Long userId, TaskDto taskDto) {
        Task result = new Task();
        result.setId(taskDto.getId());
        result.setTitle(taskDto.getTitle());
        result.setDescription(taskDto.getDescription());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Current user is not found"));
        result.setUser(user);

        TaskState taskState = new TaskState();
        taskState.setCreated(new Date());
        taskState.setTaskStatus(TaskStatus.NEW);
        result.addTaskState(taskState);

        return result;
    }

    public TaskDto convertToDto(Task task) {
        TaskDto result = new TaskDto();
        result.setId(task.getId());
        result.setTitle(task.getTitle());
        result.setDescription(task.getDescription());

        Map<Date, TaskStatus> statusLog = new LinkedHashMap<>();
        task.getTaskStates().stream()
                .sorted((ts1, ts2) -> ts1.getCreated().compareTo(ts2.getCreated()))
                .forEach(ts -> statusLog.put(ts.getCreated(), ts.getTaskStatus()));
        return result;
    }


}
