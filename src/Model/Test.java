package Model;




import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Test {
    public static void main(String[] args) throws IOException {
        URL requestURL = new URL("http://localhost:4567/PDFreader?hello=world");
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");

        InputStreamReader inputReader = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(inputReader);


        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            System.out.println(line);
            line = reader.readLine();
        }
    }
}
