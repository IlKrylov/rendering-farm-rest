package com.krylov.renderfarm.service;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestScenario {

    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public TestScenario(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @Test
    public void runTestScenario() {

        RegistrationRequestDto requestDto = new RegistrationRequestDto();
        requestDto.setUserName("TestUser");
        requestDto.setPassword("TestPassword".toCharArray());
        UserDto user = userService.register(requestDto);
        System.out.println(user);

        TaskDto taskDto1 = new TaskDto();
        taskDto1.setTitle("Test title1");
        taskDto1.setDescription("Test description1");
        taskService.createAndRun(1l, taskDto1);

        TaskDto taskDto2 = new TaskDto();
        taskDto2.setTitle("Test title2");
        taskDto2.setDescription("Test description2");
        taskService.createAndRun(1l, taskDto2);

        List<TaskDto> taskDtoList = taskService.findAllTasksByUserId(1l);
        System.out.println();
        taskDtoList.forEach((e) -> System.out.println(e));

        TaskDto user1Task1 = taskService.findTaskById(1l,1l);
        System.out.println(user1Task1);

    }
}
