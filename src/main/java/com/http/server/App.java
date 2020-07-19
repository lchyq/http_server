package com.http.server;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("LocalStrings",new Locale("ja"));
        String name = resourceBundle.getString("requestStream.readline.error");
        System.out.println(name);
    }
}
