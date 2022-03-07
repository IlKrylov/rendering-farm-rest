package com.krylov.renderfarm.service;

import com.krylov.renderfarm.entity.Task;

public interface TaskExecutorService {

    void submit(Task task);

}
