package com.krylov.renderfarm.dto;

import com.krylov.renderfarm.entity.enums.TaskStatus;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
public class TaskDto {

    private Long id;

    private String title;

    private String description;

    private Map<Date, TaskStatus> statusLog;

}
