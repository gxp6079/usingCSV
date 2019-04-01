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
            Table table = makeTables(readAll(br), start, end, page);
            System.out.println(table);
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

        /*
         * places you have seen data before
         */
        ArrayList<Integer> indexes = new ArrayList<>();

        ArrayList<String> tableRow = new ArrayList<>();


        for(int col = leftCol ; col < list.get(row).length ; col++){
            if(!list.get(row)[col].equals("")){
                Header header = new Header(row, col, list.get(row)[col]);
                table.addHeader(header);
                tableRow.add(list.get(row)[col]);

                indexes.add(col);

            }
        }

        table.addRow(tableRow);
        tableRow = new ArrayList<String>();

        row++;
        int col = leftCol;
        while(!list.get(row)[col].equals(end)){
            if(col >= leftCol){
                if(!list.get(row)[col].equals("")){ // if the column we are in has something in it
                    String value = list.get(row)[col];

                    // if there is nothing in the left most column of the table
                    // this means that there is no label, so it is still a header at this point
                    if (list.get(row)[leftCol].equals("")) {
                        // we determined that this should be a header
                        // check for sub header before it
                        // if there is a sub header give it the same parent as said sub header
                        // otherwise just make it a normal header

                        // getting the last index before this one where we saw data
                        int lastData = 0;
                        for (int idx : indexes) {
                            if (idx < col) lastData = idx;
                            else break;
                        }
                        Header lastSubHeader = table.getSubHeader(lastData);
                        if (table.getHeader(col) != null) {
                            Header parent = table.getHeader(col);
                            Header child = new Header(row, col, value, parent);
                            table.addSubHeader(child);
                            table.updateHeader(parent.getCol(), child);
                        } else if (lastSubHeader != null && lastSubHeader.getRow() == row) {
                            Header sub = table.getSubHeader(lastData);
                            Header child = new Header(row, col, value, sub.getParent());
                            table.addSubHeader(child);
                            table.updateHeader(sub.getParent().getCol(), child);
                        } else {
                            Header header = new Header(row, col, value);
                            table.addHeader(header);
                        }
                        indexes.add(col);
                        indexes.sort(Integer::compareTo);
                    } else {
                        if (!indexes.contains(col)) { // if we have not seen data in this column yet
                            if ((indexes.contains(col - 1) || indexes.contains(col - 2))) { // check if we have seen data at the index before this one
                                if (indexes.contains(col - 1) && list.get(row)[col - 1].equals("")) { // and if the data in the column directly behind you is empty
                                    // remove the last element in this table row (would be "")
                                    tableRow.remove(tableRow.size() - 1);
                                }
                                // if the data 2 spaces behind you is empty then remove the last element in the table row ("")
                                else if (indexes.contains(col - 2) && (list.get(row)[col - 2]).equals("")) {
                                    tableRow.remove(tableRow.size() - 1);
                                }
                            } else {
                                for (int idx = 0; idx < indexes.size(); idx++) {
                                    if (idx == indexes.size() - 1) {
                                        value = list.get(row)[col];
                                        Header parent = null;
                                        if (table.getHeader(col) != null) {
                                            parent = table.getHeader(col);
                                        }
                                        // getting header for current row
                                        Header child;
                                        // check if there is a header in my column
                                        //      give me set my parent to that header
                                        // if there is not a parent above me, check if the other header in my row has a parent
                                        //      if it has parent, give me that header as a parent
                                        // else
                                        //      make me header with no parent and add me to tables header list
                                        if (parent != null && indexes.get(idx) == col) {
                                            child = new Header(row, col, value, parent);
                                        } else if (table.getSubHeader(indexes.get(idx)) != null) {
                                            Header prevSubHeader = table.getSubHeader(indexes.get(idx));
                                            child = new Header(row, col, value, prevSubHeader.getParent());
                                        } else {
                                            child = new Header(row, col, value);
                                        }

                                        if (child.hasParent()) {
                                            table.addSubHeader(child);
                                        } else {
                                            table.addHeader(child);
                                        }

                                        table.updateHeader(parent.getCol(), child);
                                    }
                                    // checking if you are between two indexes that youve seen data before
                                    else if (col > indexes.get(idx) && col < indexes.get(idx + 1)) {
                                        // determined that the column has not had data in it until now and its not in the list of indexes
                                        // make child with parent of header at idx
                                        // add child to subHeaders
                                        Header parent = table.getHeader(indexes.get(idx));
                                        Header child = new Header(row, col, list.get(row)[col], parent);

                                        if (child.hasParent()) {
                                            table.addSubHeader(child);
                                        } else {
                                            table.addHeader(child);
                                        }

                                        table.updateHeader(parent.getCol(), child);
                                        break;
                                    }
                                }
                                indexes.add(col); // add that we have seen data at this index
                                indexes.sort(Integer::compareTo);
                            }
                        }
                    }
                    tableRow.add(list.get(row)[col]); // add the data at this column to the row
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
