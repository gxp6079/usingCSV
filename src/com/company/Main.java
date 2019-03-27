package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

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
            makeTables(readAll(br), start, end, page);
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

    private static Table makeTables(List<String[]> list, String start, String end, int page){

        int currPage = 1;
        int row = 0;
        while(currPage != page){
            if(list.get(row).length == 1 && list.get(row)[0].equals("")){
                currPage ++;
            }
            row++;
        }

        int leftCol = 0;
        while(!list.get(row)[leftCol].equals(start)){
            if(leftCol == list.get(row).length - 1){
                leftCol = 0;
                row ++;
            }
            else{
                leftCol++;
            }
        }

        Table table =  new Table(page, 0);

        //maps the index of the header to the amount of columns it contains
        HashMap<Integer, Integer> headers = new HashMap<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        ArrayList<String> tableRow = new ArrayList<>();


        for(int col = leftCol ; col < list.get(row).length ; col++){
            if(!list.get(row)[col].equals("")){
                indexes.add(col);
                headers.put(col, 1);
                tableRow.add(list.get(row)[col]);
            }
        }

        table.addRow(tableRow);
        table.initHeaders(headers);
        tableRow = new ArrayList<String>();

        row++;
        int col = leftCol;
        while(!list.get(row)[col].equals(end)){
            if(col >= leftCol){
                if(!list.get(row)[col].equals("")){
                    if(!indexes.contains(col)){
                        if((indexes.contains(col - 1))){
                            if(list.get(row)[col-1].equals("")) {
                                tableRow.remove(tableRow.size() - 1);
                            }
                            else if(indexes.contains(col - 2) && (list.get(row)[col-2]).equals("")){
                                tableRow.remove(tableRow.size() - 2);
                            }
                        }
                        else {
                            for (int idx = 0; idx < indexes.size(); idx++) {
                                if (idx == indexes.size() - 1) {
                                    table.updateHeader(indexes.get(idx));
                                } else if (col > indexes.get(idx) && col < indexes.get(idx + 1)) {
                                    table.updateHeader(indexes.get(idx));
                                    break;
                                }
                            }
                            indexes.add(col);
                        }
                    }
                    tableRow.add(list.get(row)[col]);
                }
                else if(indexes.contains(col)){
                    tableRow.add("");
                }
            }
            if(col == list.get(row).length - 1){
                col = 0;
                table.addRow(tableRow);
                tableRow = new ArrayList<>();
                row ++;
            }
            else{
                col++;
            }
        }
        return table;

    }

}
