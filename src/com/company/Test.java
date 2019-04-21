package com.company;


import spark.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setRequestMethod("GET");

        //InputStream inputStream = connection.getInputStream();
        //OutputStream outputStream = connection.getOutputStream();

        //System.out.println(inputStream.read());
        System.out.println(connection.getResponseCode());

    }
}
