package com.krylov.renderfarm.service.utils;

import com.krylov.renderfarm.entity.Task;
import com.krylov.renderfarm.entity.TaskState;
import com.krylov.renderfarm.entity.enums.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskStateFactory {

    public TaskState createTaskState(Task task, TaskStatus taskStatus) {
        TaskState result = new TaskState();
        result.setTask(task);
        result.setCreated(new Date());
        result.setTaskStatus(taskStatus);
        return result;
    }

}
