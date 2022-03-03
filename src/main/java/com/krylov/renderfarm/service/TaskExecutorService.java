package com.krylov.renderfarm.service;

import com.krylov.renderfarm.entity.Task;

public interface TaskExecutorService {

    void assign(Task task);

}
