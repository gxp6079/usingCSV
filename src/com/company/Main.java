package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    // CONFIGURATIONS:
    // Ex2.csv "Renda Fixa" "Renda Variável" 4
    // Ex2.csv "Ativos" "Rentabilidade" 2

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        TemplateReader reader = new TemplateReader();
        System.out.println("Enter csv file name:");
        String filename = scan.nextLine();
        System.out.println("Enter the template type:");
        String templateName = scan.nextLine();
        reader.readTemplate(filename, templateName);
    }


}
