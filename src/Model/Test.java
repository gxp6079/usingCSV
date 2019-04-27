package Model;



import spark.Request;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader?hello=world");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");

//        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connection.getOutputStream());
//        outputStreamWriter.write("Ex2.csv");
//        outputStreamWriter.flush();
        Scanner scanner = new Scanner(System.in);

        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inputReader);

//        OutputStreamWriter outputWriter = new OutputStreamWriter(connection.getOutputStream());
//        BufferedWriter writer = new BufferedWriter(outputWriter);


        String line = reader.readLine();
//        writer.write(scanner.nextLine());
        while (line != null && !line.equals("")) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
