package com.krylov.renderfarm.mockconsoleclient;

public class CommandFactory {

    public static CommandType createEnterCommand(String s) {
        int intCommandValue = -1;
        try {
            intCommandValue = Integer.parseInt(s);
        } catch (Exception e) {
            return CommandType.UNKNOWN;
        }
        if (intCommandValue >= 1 && intCommandValue <= 3) {
            switch (intCommandValue) {
                case 1:
                    return CommandType.SIGN_IN;
                case 2:
                    return CommandType.LOG_IN;
                case 3:
                    return CommandType.EXIT;
            }
        }
        return CommandType.UNKNOWN;
    }

    public static CommandType createSessionCommand(String s) {
        int intCommandValue = -1;
        try {
            intCommandValue = Integer.parseInt(s);
        } catch (Exception e) {
            return CommandType.UNKNOWN;
        }
        if (intCommandValue >= 1 && intCommandValue <= 4) {
            switch (intCommandValue) {
                case 1:
                    return CommandType.CREATE_TASK;
                case 2:
                    return CommandType.SHOW_ALL_TASKS;
                case 3:
                    return CommandType.LOG_OUT;
                case 4:
                    return CommandType.EXIT;
            }
        }
        return CommandType.UNKNOWN;
    }
}
