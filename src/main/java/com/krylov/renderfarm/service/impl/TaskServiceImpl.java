package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.entity.Task;
import com.krylov.renderfarm.entity.User;
import com.krylov.renderfarm.entity.enums.TaskStatus;
import com.krylov.renderfarm.exception.DataBaseUpdateException;
import com.krylov.renderfarm.exception.EntityNotFoundException;
import com.krylov.renderfarm.exception.InvalidDtoException;
import com.krylov.renderfarm.repository.TaskRepository;
import com.krylov.renderfarm.repository.UserRepository;
import com.krylov.renderfarm.service.TaskExecutorService;
import com.krylov.renderfarm.service.TaskService;
import com.krylov.renderfarm.service.utils.TaskStateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskExecutorService taskExecutorService;
    private final TaskStateFactory taskStateFactory;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           TaskExecutorService taskExecutorService,
                           TaskStateFactory taskStateFactory) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskExecutorService = taskExecutorService;
        this.taskStateFactory = taskStateFactory;
    }

    @Override
    public TaskDto createAndRun(Long userId, TaskDto taskDto) {
        TaskDto result;
        Task task = convertToEntity(userId, taskDto);
        try {
            task = taskRepository.saveAndFlush(task);
        } catch (Exception e) {
            throw new DataBaseUpdateException("Unable to save task");
        }
        taskExecutorService.submit(task);
        result = convertToDto(task);
        return result;
    }

    @Override
    @Transactional
    public List<TaskDto> findAllTasksByUserId(Long userId) {
        List<Task> taskList = taskRepository.findAllByUserId(userId);
        List<TaskDto> taskDtoList = taskList.stream().map(this::convertToDto).collect(Collectors.toList());
        return taskDtoList;
    }

    @Override
    @Transactional
    public TaskDto findTaskById(Long userId, Long taskId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Current user is not found"));
        Task task = user.getTasks().stream().filter(t -> t.getId() == taskId).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Selected task is not found"));
        TaskDto taskDto = convertToDto(task);
        return taskDto;
    }

    @Transactional
    public Task convertToEntity(Long userId, TaskDto taskDto) {
        if (taskDto == null) throw new InvalidDtoException("Invalid TaskDto");
        Task result = new Task();
        result.setId(taskDto.getId());
        result.setTitle(taskDto.getTitle());
        result.setDescription(taskDto.getDescription());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Current user is not found"));
        result.setUser(user);
        result.addTaskState(taskStateFactory.createTaskState(result, TaskStatus.NEW));
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
        result.setStatusLog(statusLog);
        return result;
    }


}
