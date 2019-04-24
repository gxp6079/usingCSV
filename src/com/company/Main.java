package com.company;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import java.sql.*;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.ConnectionImpl;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.ServletOutputStream;

public class Main {

    // CONFIGURATIONS:
    // Ex2.csv "Renda Fixa" "Renda Variável" 4
    // Ex2.csv "Ativos" "Rentabilidade" 2

    public static void main(String[] args) {

        Gson gson = new Gson();
        get("/PDFreader", new Route() {
            public Object handle(Request request, Response response) throws IOException {
                run_with_csv(response.raw().getOutputStream());
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
        String databaseUrl = "jdbc:mysql://localhost/PDFreader?serverTimezone=EST";

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
    public static int run_with_csv(ServletOutputStream out) throws IOException {
        Scanner scan = new Scanner(System.in);
        TemplateReader reader = new TemplateReader();
        //outputStream.write("Enter csv file name:".getBytes());
        out.println("Enter csv file name:");
        out.flush();
        String filename = scan.nextLine();
        out.println("Enter the template type:");
        out.flush();
        String templateName = scan.nextLine();
        reader.readTemplate(filename, templateName, out);
        out.flush();
        return 1;
    }



}
