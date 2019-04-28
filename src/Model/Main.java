package Model;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.util.Scanner;

import java.sql.*;
import static spark.Spark.get;
import static spark.Spark.post;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

public class Main {

    // CONFIGURATIONS:
    // Ex2.csv "Renda Fixa" "Renda Variável" 4
    // Ex2.csv "Ativos" "Rentabilidade" 2

    public static void main(String[] args) {

        Gson gson = new Gson();
        post("/PDFreader", new Route() {
            public Object handle(Request request, Response response) throws IOException {
                System.out.println("request body: " + request.body());
                request.session().attribute("hi", "you");
                String thing = request.queryParams("hello");
                System.out.println(request.queryParams().size());
                System.out.println((String) request.session().attribute("hi"));
                System.out.println(thing);
                response.body("done");
                //run_with_csv(response.raw().getOutputStream(), request.raw().getInputStream());
                return 1;
            }

        });
        get("/postFile", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                request.body();
                return null;
            }
        });
        String databaseUrl = "jdbc:mysql://129.21.117.231:4567/PDFreader?serverTimezone=EST";

        try {
            Connection connectionSource = DriverManager.getConnection(databaseUrl, "brit", "x0EspnYA8JaqCPT9");
            Statement s = connectionSource.createStatement();
            //int Result=s.executeUpdate("CREATE DATABASE PDFreader");
            String table = "CREATE TABLE IF NOT EXISTS `TEMPLATES` (\n" +
                    "`template_type` varchar(50) NOT NULL,\n" +
                    "`template_object` blob,\n" +
                    "PRIMARY KEY (`template_type`)\n" +
                    ")";
            int Result=s.executeUpdate(table);
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    public static int run_with_csv(ServletOutputStream out, ServletInputStream in) throws IOException {
        Scanner scan = new Scanner(System.in);
        TemplateReader reader = new TemplateReader();
        BufferedReader input = new BufferedReader( new InputStreamReader(in));
        //outputStream.write("Enter csv file name:".getBytes());
        out.println("Enter csv file name:");
        out.flush();
        String filename = input.readLine();
        out.println("Enter the template type:");
        out.flush();
        String templateName = input.readLine();
        //reader.readTemplate(filename, templateName, out);
        out.flush();
        return 1;
    }



}
