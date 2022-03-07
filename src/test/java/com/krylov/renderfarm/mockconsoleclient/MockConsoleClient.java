package com.krylov.renderfarm.mockconsoleclient;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MockConsoleClient {

    // 1. Run Server: run test runServer()
    @Test
    public void runServer() {
        while (true) {
        }
    }


    private long serverPort = 8075;
    private boolean isExit = false;
    private Client client;
    private CommandExecutionService commandExecutionService;

    // 2. Run Console Client: run main()
    public static void main(String[] args) {
        MockConsoleClient mockConsoleClient = new MockConsoleClient();
        mockConsoleClient.runMockConsoleClient();
    }

    public void runMockConsoleClient() {
        CommandType currentCommand;
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader consoleReader = new BufferedReader(inputStreamReader);

        client = new Client(null, null, false);
        commandExecutionService = new CommandExecutionService(serverPort, this, consoleReader);

        try {
            while (!isExit) {
                if (!client.isAuthenticated() || client.getUserName() == null || client.getPassword() == null) {
                    ConsoleWriteUtils.printEnterCommandList();
                    currentCommand = CommandFactory.createEnterCommand(consoleReader.readLine());
                } else {
                    ConsoleWriteUtils.printSessionCommandList();
                    currentCommand = CommandFactory.createSessionCommand(consoleReader.readLine());
                }
                commandExecutionService.executeCommand(currentCommand);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong while command execution!");
        } finally {
            try {
                consoleReader.close();
                inputStreamReader.close();
            } catch (IOException e) {
                System.out.println("Something went wrong while closing resources!");
            }
        }
    }

    public Client getClient() {
        return client;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }


}
