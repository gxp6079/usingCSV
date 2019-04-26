package Model;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        String url = "http://8.41.66.214:4567/PDFreader";

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response = client.execute(request);
        System.out.println("Response code: " + response.getStatusLine().getStatusCode());

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        String line = "";

        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }
}
