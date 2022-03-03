package com.krylov.renderfarm.service.impl;

import com.krylov.renderfarm.entity.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorServiceMock implements com.krylov.renderfarm.service.TaskExecutorService {

    @Override
    public void assign(Task task) {
        //TODO: create task, put in threadpool ...
    }
}
