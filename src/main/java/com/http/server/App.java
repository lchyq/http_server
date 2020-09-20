package com.http.server;

import org.omg.CORBA.portable.IDLEntity;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
