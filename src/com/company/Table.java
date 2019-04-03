package com.company;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static java.util.Collections.min;

public class Table {
    private int page;
    private List<List<String>> table;

    private String start;
    private String end;


    /**
     * maps header column to header
     */
    private Map<Integer, Header> headerList;


    /**
     * mapping subHeader column to the sub header
     */
    private Map<Integer, Header> subHeaders;

    public Table(int page, String start, String end){
        this.headerList = new TreeMap<Integer, Header>();
        this.subHeaders =  new TreeMap<Integer, Header>();
        this.page = page;
        this.start = start;
        this.end = end;
        this.table = new ArrayList<List<String>>();
    }

    public List<List<String>> getTable(){
        return this.table;
    }

    public void addHeader(Header header) {
        this.headerList.put(header.getCol(), header);
    }

    public void addSubHeader(Header sub) {
        this.subHeaders.put(sub.getCol(), sub);
    }

    public Header getHeader(int col) {
        return headerList.get(col);
    }

    public Header getSubHeader(int col) {
        return subHeaders.get(col);
    }


    public void updateHeader(Integer parent, Header subheader){
        headerList.get(parent).addChild(subheader);
    }

    public void addRow(List<String> row){
        table.add(new ArrayList<>(row));
    }

    public List<String> getDataAt(String header) {
        boolean found = false;
        int col = 0;
        for (int i : headerList.keySet()) {
            if (headerList.get(i).getValue().equals(header)) break;
            else if (headerList.get(i).hasChildren()) {
                for (Header h : headerList.get(i).getChildren()) {
                    if (h.getValue().equals(header)){
                        found = true;
                        break;
                    }
                    col++;
                }
            } else {
                if (headerList.get(i).getValue().equals(header)){
                    found = true;
                    break;
                }
                col++;
            }
        }

        if (!found){
            for (List<String> row : table){
                if(row.get(0).equals(header)){
                    return row.subList(1, row.size());
                }
            }
        }

        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < table.size(); i++) {
            data.add(table.get(i).get(col));
        }
        return data;
    }


    public int getPage(){
        return this.page;
    }

    public String toString(){
        String s = "";
        for (Header header : headerList.values()){
           s += header.toString();
           s += "| ";
        }
        s += "\n";
        String subs = "";
        for (Header parent :headerList.values()){
            if(!parent.hasChildren()){
                subs += "| ";
            }
            else{
                Header child = null;
                for (int i = 0 ; i < parent.getChildren().size() ; i++){
                    child = parent.getChildren().get(i);
                    subs += child.toString();
                    subs += "| ";
                }
            }
        }
        subs += "\n";
        s += subs;
        for (List<String> row : table){
            String rowString = "";
            int idx = 0;
            for(String value : row){
                rowString += value;
                rowString += "| ";
            }
            rowString += "\n";
            s += rowString;
        }
        return s;
    }


    @Override
    public int hashCode() {
        return start.hashCode() + end.hashCode() + page;
    }
}
