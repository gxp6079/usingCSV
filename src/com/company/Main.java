package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    // CONFIGURATIONS:
    // Ex2.csv "Renda Fixa" "Renda Variável" 4
    // Ex2.csv "Ativos" "Rentabilidade" 2

    public static void main(String[] args) {
        if(args.length < 4) {
            System.out.println("Format of the arguments: filename start end");
            return;
        }
        String filename = args[0];
        String start = args[1];
        String end = args[2];
        int page = Integer.parseInt(args[3]);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            TableFactory tableFactory = new TableFactory(readAll(br));
            tableFactory.initialize(start, end, page);
            Table table = tableFactory.makeTable();
            System.out.println(table.getDataAt("Ago/18"));
            //Table table = makeTables(readAll(br), start, end, page);
            //System.out.println(table);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static List<String[]> readAll(Reader reader) throws Exception {
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> list = csvReader.readAll();
        reader.close();
        csvReader.close();
        return list;
    }

}
