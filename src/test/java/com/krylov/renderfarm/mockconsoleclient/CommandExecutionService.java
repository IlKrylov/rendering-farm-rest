package com.krylov.renderfarm.mockconsoleclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krylov.renderfarm.dto.RegistrationRequestDto;
import com.krylov.renderfarm.dto.TaskDto;
import com.krylov.renderfarm.dto.TaskStateDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static com.krylov.renderfarm.mockconsoleclient.ConsoleWriteUtils.colorPrint;

public class CommandExecutionService {

    private static final String LOCALHOST_URL = "http://localhost:";
    private static final String REGISTRATION_ENDPOINT = "/api/v1/user/signup";
    private static final String LOGIN_ENDPOINT = "/api/v1/user/login";
    private static final String TASKS_ENDPOINT = "/api/v1/user/tasks";

    private Long serverPort;
    private MockConsoleClient mockConsoleClient;
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();
    private BufferedReader reader;

    public CommandExecutionService(Long serverPort,
                                   MockConsoleClient mockConsoleClient,
                                   BufferedReader reader) {
        this.serverPort = serverPort;
        this.mockConsoleClient = mockConsoleClient;
        this.reader = reader;
    }

    public void executeCommand(CommandType command) {
        System.out.println("Selected command : " + command.getDescription());
        switch (command) {
            case SIGN_IN: {
                signIn();
                break;
            }
            case LOG_IN: {
                logIn();
                break;
            }
            case CREATE_TASK: {
                createTask();
                break;
            }
            case SHOW_ALL_TASKS: {
                showAllTasks();
                break;
            }
            case LOG_OUT: {
                logOut();
                break;
            }
            case EXIT: {
                logOut();
                exit();
                break;
            }
            case UNKNOWN: {
                unknown();
                break;
            }
        }
    }

    private void signIn() {
        String username;
        String password;
        try {
            colorPrint("Enter username:");
            username = reader.readLine();
            colorPrint("Enter password:");
            password = reader.readLine();

            RegistrationRequestDto registrationRequestDto = new RegistrationRequestDto();
            registrationRequestDto.setUserName(username);
            registrationRequestDto.setPassword(password.toCharArray());

            String url = LOCALHOST_URL + serverPort + REGISTRATION_ENDPOINT;
            HttpEntity<RegistrationRequestDto> request = new HttpEntity<>(registrationRequestDto, new HttpHeaders());
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Client client = mockConsoleClient.getClient();
                client.setUserName(username);
                client.setPassword(password);
                client.setAuthenticated(true);
                colorPrint("User '" + username + "' was registered!");
            }

        } catch (IOException e) {
            colorPrint("Incorrect entered data!");
        } catch (ResourceAccessException e) {
            colorPrint("Server is not available, start Server!");
            mockConsoleClient.setExit(true);
        } catch (Exception e) {
            colorPrint("Failed to save user! Cause: " + e.getMessage());
        }
    }

    private void logIn() {
        String username;
        String password;
        try {
            colorPrint("Enter username:");
            username = reader.readLine();
            colorPrint("Enter password:");
            password = reader.readLine();

            String url = LOCALHOST_URL + serverPort + LOGIN_ENDPOINT;
            HttpHeaders httpHeaders = createBasicAuthHeaders(username, password);
            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                colorPrint("Correct username and password! Log in successful!");
                Client client = mockConsoleClient.getClient();
                client.setUserName(username);
                client.setPassword(password);
                client.setAuthenticated(true);
            }

        } catch (IOException e) {
            colorPrint("Incorrect entered data!");
        } catch (ResourceAccessException e) {
            colorPrint("Server is not available, start Server!");
            mockConsoleClient.setExit(true);
        } catch (Exception e) {
            colorPrint("Failed to login! Check username and password!");
        }
    }

    private void createTask() {

        try {
            colorPrint("Enter task title:");
            String taskTitle = reader.readLine();
            colorPrint("Enter task description:");
            String taskDescription = reader.readLine();
            TaskDto taskDto = new TaskDto();
            taskDto.setTitle(taskTitle);
            taskDto.setDescription(taskDescription);

            String url = LOCALHOST_URL + serverPort + TASKS_ENDPOINT;
            HttpHeaders httpHeaders = createBasicAuthHeaders(
                    mockConsoleClient.getClient().getUserName(), mockConsoleClient.getClient().getPassword());

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, new HttpEntity<>(taskDto, httpHeaders), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                colorPrint("Task created! Title: " + taskTitle);
            }

        } catch (IOException e) {
            colorPrint("Incorrect entered data!");
        } catch (ResourceAccessException e) {
            colorPrint("Server is not available, start Server!");
            mockConsoleClient.setExit(true);
        } catch (Exception e) {
            colorPrint("Failed to create new task! Cause: " + e.getMessage());
        }
    }

    private void showAllTasks() {
        try {
            String url = LOCALHOST_URL + serverPort + TASKS_ENDPOINT;
            HttpHeaders httpHeaders = createBasicAuthHeaders(
                    mockConsoleClient.getClient().getUserName(), mockConsoleClient.getClient().getPassword());

            ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                List<TaskDto> taskDtoList = objectMapper.readValue(response.getBody(), new TypeReference<List<TaskDto>>() {
                });
                if (taskDtoList.isEmpty()) colorPrint("No tasks created!");
                else {
                    colorPrint("Your tasks:");
                    for (TaskDto taskDto : taskDtoList) {
                        System.out.println("\t Title: " + taskDto.getTitle());
                        System.out.println("\t Description: " + taskDto.getDescription());
                        for (TaskStateDto taskStateDto : taskDto.getStatusLog()) {
                            System.out.println("\t\t Date: " + taskStateDto.getDate() + ", Status: " + taskStateDto.getTaskStatus());
                        }
                    }
                }

            }

        } catch (ResourceAccessException e) {
            colorPrint("Server is not available, start Server!");
            mockConsoleClient.setExit(true);
        } catch (Exception e) {
            colorPrint("Failed to get all tasks! Cause: " + e.getMessage());
        }
    }

    private void logOut() {
        Client client = mockConsoleClient.getClient();
        client.setAuthenticated(false);
        client.setUserName(null);
        client.setPassword(null);
        System.out.println("Log out: successful");
    }

    private void exit() {
        mockConsoleClient.setExit(true);
    }

    private void unknown() {

    }

    private HttpHeaders createBasicAuthHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }


}
