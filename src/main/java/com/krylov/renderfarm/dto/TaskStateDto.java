package com.krylov.renderfarm.dto;

import com.krylov.renderfarm.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {

    private Date date;

    private TaskStatus taskStatus;
}
