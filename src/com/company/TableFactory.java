package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TableFactory {
    /**
     * left most column of the table
     */
    private int leftCol;

    /**
     * current column that the factory is currently looking at
     */
    private int col;

    /**
     * current row that the factory is currently looking at
     */
    private int row;

    /**
     * list of string arrays, aka csv file text
     */
    private List<String[]> list;

    /**
     * list of indexes we have seen data at
     */
    private ArrayList<Integer> dataIndexes;

    /**
     * list of strings that is constantly updated and eventually sent to the table
     */
    private ArrayList<String> tableRow;

    /**
     * start of word for the table
     */
    private String start;

    /**
     * end word of the table
     */
    private String end;

    private List<Integer[]> locations;


    public TableFactory(List<String[]> list) {
        this.tableRow = new ArrayList<>();
        this.dataIndexes = new ArrayList<>();
        this.list = list;
        this.row = 0;
        this.col = 0;

        this.start = start = "";
        this.end = end = "";
    }


    public void initialize(String start, String end) {
        this.tableRow.clear();
        this.dataIndexes.clear();
        this.row = 0;
        this.col = 0;
        this.start = start;
        this.end = end;
        this.locations = getLocation(start);
    }

    public List<Integer[]> getLocation(String start){
        List<Integer[]> locations = new ArrayList<>();
        int leftCol = 0;
        int row = 0;
        while(row < list.size()){
            if(list.get(row)[leftCol].equals(start)){
                Integer[] loc = new Integer[2];
                loc[0] = row;
                loc[1] = leftCol;
                locations.add(loc);
            }
            if(leftCol == list.get(row).length - 1){
                leftCol = 0;
                row ++;
            }
            else{
                leftCol++;
            }
        }
        return locations;
    }

    public int getNumLocations() {
        return this.locations.size();
    }


    public Table makeTable(int location) {

        boolean finishedHead = false;

        List<Integer[]> locations = getLocation(start);
        if(locations.size() != 1){
            if(locations.size() == 0){
                System.out.println("Start not found");
            }
            else{
                // System.out.println(start + "was found " +Integer.toString(locations.size()) + " times, please enter which #n");
                // Scanner scan = new Scanner(System.in);
                // int n = Integer.valueOf(scan.nextLine());
                this.row = locations.get(location - 1)[0];
                this.leftCol = locations.get(location - 1)[1];
            }
        }
        else{
            this.row = locations.get(0)[0];
            this.leftCol = locations.get(0)[1];
        }
        Table table = new Table(start, end);

        initializeHeaders(table);

        tableRow.clear();

        this.row++;
        this.col = this.leftCol;
        String val = list.get(row)[col];
        while(!val.equals(end)) {
            val = list.get(row)[col];
            if (col >= leftCol) {
                if(col == leftCol && !val.equals("")) finishedHead = true;
                checkEntry(table, finishedHead);
                if (!val.equals("")) tableRow.add(val);
                else if (val.equals("") && dataIndexes.contains(col)) tableRow.add(val);
            }

            if(col == list.get(row).length - 1) {
                if(!list.get(row)[leftCol].equals("")) table.addRow(tableRow);
                col = 0;
                tableRow.clear();
                this.row++;
            } else {
                col++;
            }
        }
        return table;
    }


    private void checkEntry(Table table, boolean finishedHead) {
        String val = list.get(row)[col];
        if (!val.equals("")) {
            if (!finishedHead && list.get(row)[leftCol].equals("")) {
                makeSubHeader(table, val);
            } else {
                if (!dataIndexes.contains(this.col)) {
                    checkPreviousColumns(table);
                }
            }
        }


    }


    private void makeSubHeader(Table table, String value) {
        int lastData = 0;
        for (int idx : dataIndexes) {
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
        if (!dataIndexes.contains(col)) dataIndexes.add(col);
        dataIndexes.sort(Integer::compareTo);
    }


    private void checkPreviousColumns(Table table) {
        int lastData = 0;
        for (int idx : dataIndexes) {
            if (idx < col) lastData = idx;
            else break;
        }

        if (list.get(row)[lastData].equals("")) {
            tableRow.remove(tableRow.size() - 1);
        } else {
            for (int idx = 0; idx < dataIndexes.size(); idx++) {
                if (idx == dataIndexes.size() - 1) {
                    String val = list.get(row)[col];
                    Header parent = table.getHeader(col);

                    Header child;

                    if (parent != null && dataIndexes.get(idx) == col) {
                        child = new Header(row, col, val, parent);
                    } else if (table.getSubHeader(dataIndexes.get(idx)) != null) {
                        Header prevSubHeader = table.getSubHeader(dataIndexes.get(idx));
                        child = new Header(row, col, val, prevSubHeader.getParent());
                    } else {
                        child = new Header(row, col, val);
                    }

                    if (child.hasParent()) table.addSubHeader(child);
                    else table.addHeader(child);

                } else if (col > dataIndexes.get(idx) && col < dataIndexes.get(idx + 1)){
                    Header parent = table.getHeader(dataIndexes.get(idx));
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
            dataIndexes.add(col);
            dataIndexes.sort(Integer::compareTo);


            // for each index in the dataIndexes, check if we are between 2 columns in the list
            //      if we are:
            //          then make a sub header with a parent at the last data index
            //      if we are at the last index:
            //          then make a sub header, check if there is another header directly above it
            //          if there is no header then check if there is a sub header in the last data index with a parent
        }
    }


    private void initializeHeaders(Table table) {
        for (this.col = this.leftCol; this.col < list.get(this.row).length; this.col++) {
            String val = list.get(this.row)[this.col];
            if (!val.equals("")) {
                Header header = new Header(this.row, this.col, val);
                table.addHeader(header);
                this.tableRow.add(val);

                dataIndexes.add(col);
            }
        }
    }

}
