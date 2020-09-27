package com.http.server;

import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocalStrings",new Locale("ja"));
        String name = resourceBundle.getString("requestStream.readline.error");
        System.out.println(name);
    }
}
