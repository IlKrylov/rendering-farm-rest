package com.krylov.renderfarm.controller;

import com.krylov.renderfarm.conroller.UserRestController;
import com.krylov.renderfarm.exception.DataBaseUpdateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRestController userRestController;

    @BeforeAll
    private static void setUp(@Autowired DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("test-data.sql"));
        } catch (Exception e) {
            throw new DataBaseUpdateException("SetUp error");
        }
    }

    @Test
    public void contextLoads() {
        assertThat(userRestController).isNotNull();
    }

}
