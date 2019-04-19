package com.company;


import spark.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setRequestMethod("GET");



        System.out.println(connection.getResponseCode());

    }
}
