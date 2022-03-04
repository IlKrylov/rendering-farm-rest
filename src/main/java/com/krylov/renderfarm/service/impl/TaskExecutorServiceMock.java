package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.entity.Task;
import com.krylov.renderfarm.entity.TaskState;
import com.krylov.renderfarm.entity.enums.TaskStatus;
import com.krylov.renderfarm.exception.TaskExecutionException;
import com.krylov.renderfarm.repository.TaskRepository;
import com.krylov.renderfarm.service.utils.TaskStateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class TaskExecutorServiceMock implements com.krylov.renderfarm.service.TaskExecutorService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final TaskRepository taskRepository;
    private final TaskStateFactory taskStateFactory;

    @Autowired
    public TaskExecutorServiceMock(TaskRepository taskRepository,
                                   TaskStateFactory taskStateFactory) {
        this.taskRepository = taskRepository;
        this.taskStateFactory = taskStateFactory;
    }

    @Override
    public void submit(Task task) {
        Runnable runnableTask = new Runnable() {
            @Override
            @Transactional
            public void run() {
                try{
                    TaskState renderingState = taskStateFactory.createTaskState(task, TaskStatus.RENDERING);
                    task.addTaskState(renderingState);
                    taskRepository.save(task);

                    Thread.sleep(10);

                    TaskState completeState = taskStateFactory.createTaskState(task, TaskStatus.COMPLETE);
                    task.addTaskState(completeState);
                    taskRepository.save(task);

                } catch (Exception e){
                    throw new TaskExecutionException("TaskExecutionException");
                }

            }
        };
        executorService.submit(runnableTask);
    }
}
