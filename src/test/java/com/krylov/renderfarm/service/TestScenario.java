package com.krylov.renderfarm.service;

import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.TaskDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

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
        userService.register(requestDto);

        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test title");
        taskDto.setDescription("Test description");

        taskService.createAndRun(1l, taskDto);

    }
}
