package com.krylov.renderfarm.mockconsoleclient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {

    private String userName;
    private String password;
    private boolean isAuthenticated;

}
