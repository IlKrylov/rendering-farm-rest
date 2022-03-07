package com.krylov.renderfarm.mockconsoleclient;

public enum CommandType {
    SIGN_IN("Sign in"),
    LOG_IN ("Log in"),
    CREATE_TASK ("Create new task"),
    SHOW_ALL_TASKS ("Show all tasks"),
    LOG_OUT ("Log out"),
    EXIT ("Exit"),
    UNKNOWN ("Unknown");

    private final String description;

    private CommandType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
