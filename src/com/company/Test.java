package com.company;



import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("GET");

//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
//        outputStreamWriter.write("Ex2.csv");
//        outputStreamWriter.flush();
        Scanner scanner = new Scanner(System.in);

        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inputReader);

        OutputStreamWriter outputWriter = new OutputStreamWriter(connection.getOutputStream());
        BufferedWriter writer = new BufferedWriter(outputWriter);


        String line = reader.readLine();
        System.out.println(line);
        writer.write(scanner.nextLine());
//        while (line != null && !line.equals("")) {
//            System.out.println(line);
//            writer.write(scanner.nextLine());
//            line = reader.readLine();
//        }
    }
}
