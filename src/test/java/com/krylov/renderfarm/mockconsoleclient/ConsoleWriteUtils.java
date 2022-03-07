package com.krylov.renderfarm.mockconsoleclient;

public class ConsoleWriteUtils {

    public static void printEnterCommandList() {
        colorPrint("Select command (print number 1-3):");
        colorPrint("1. " + CommandType.SIGN_IN.getDescription());
        colorPrint("2. " + CommandType.LOG_IN.getDescription());
        colorPrint("3. " + CommandType.EXIT.getDescription());
    }

    public static void printSessionCommandList() {
        colorPrint("Select command (print number 1-4):");
        colorPrint("1. "+ CommandType.CREATE_TASK.getDescription());
        colorPrint("2. "+ CommandType.SHOW_ALL_TASKS.getDescription());
        colorPrint("3. " + CommandType.LOG_OUT.getDescription());
        colorPrint("4. " + CommandType.EXIT.getDescription());
    }

    public static void colorPrint(String s){
        System.out.println("\u001B[31m" + s + "\u001B[0m");
    }
}
