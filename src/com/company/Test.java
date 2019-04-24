package com.company;


import com.google.gson.Gson;
import org.mortbay.util.ajax.AjaxFilter;
import spark.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        //connection.setDoOutput(true);
        connection.setRequestMethod("GET");

//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
//        outputStreamWriter.write("Ex2.csv");
//        outputStreamWriter.flush();
        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inputReader);
        String line = reader.readLine();
        while (!line.equals("")) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
