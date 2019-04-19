package com.company;

import java.util.Scanner;

import java.sql.*;
import static spark.Spark.get;

import com.mysql.cj.jdbc.ConnectionImpl;
import spark.Request;
import spark.Response;
import spark.Route;

public class Main {

    // CONFIGURATIONS:
    // Ex2.csv "Renda Fixa" "Renda Variável" 4
    // Ex2.csv "Ativos" "Rentabilidade" 2

    public static void main(String[] args) {
        get("/PDFreader", new Route() {
            public Object handle(Request request, Response response) {
                return run_with_csv();
                // return null;
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
    public static int run_with_csv() {
        Scanner scan = new Scanner(System.in);
        TemplateReader reader = new TemplateReader();
        System.out.println("Enter csv file name:");
        String filename = scan.nextLine();
        System.out.println("Enter the template type:");
        String templateName = scan.nextLine();
        reader.readTemplate(filename, templateName);
        return 1;
    }


}
