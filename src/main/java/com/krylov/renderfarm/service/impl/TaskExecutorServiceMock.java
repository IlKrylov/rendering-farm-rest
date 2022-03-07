package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.entity.Task;
import com.krylov.renderfarm.entity.TaskState;
import com.krylov.renderfarm.entity.enums.TaskStatus;
import com.krylov.renderfarm.exception.TaskExecutionException;
import com.krylov.renderfarm.repository.TaskStateRepository;
import com.krylov.renderfarm.service.utils.TaskStateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TaskExecutorServiceMock implements com.krylov.renderfarm.service.TaskExecutorService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    private final TaskStateRepository taskStateRepository;
    private final TaskStateFactory taskStateFactory;

    @Value("${rendering.mintime.minute}")
    private Long renderingMinTimeMinute;
    @Value("${rendering.maxtime.minute}")
    private Long renderingMaxTimeMinute;

    @Autowired
    public TaskExecutorServiceMock(TaskStateRepository taskStateRepository,
                                   TaskStateFactory taskStateFactory) {
        this.taskStateRepository = taskStateRepository;
        this.taskStateFactory = taskStateFactory;
    }

    @Override
    public void submit(Task task) {
        Runnable runnableTask = new Runnable() {
            @Override
            public void run() {
                try {
                    TaskState renderingState = taskStateFactory.createTaskState(task, TaskStatus.RENDERING);
                    taskStateRepository.save(renderingState);

                    Long renderingTime = ThreadLocalRandom.current()
                            .nextLong(renderingMinTimeMinute * 60000, renderingMaxTimeMinute * 60000);
                    Thread.sleep(renderingTime);

                    TaskState completeState = taskStateFactory.createTaskState(task, TaskStatus.COMPLETE);
                    taskStateRepository.save(completeState);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    throw new TaskExecutionException("Unable to save task state");
                }

            }
        };
        executorService.execute(runnableTask);
    }
}
