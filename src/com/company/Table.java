package com.company;

import java.util.*;

public class Table {
    private int page;
    private int id;
    private List<List<String>> table;

    /**
     * maps header column to header
     */
    private Map<Integer, Header> headerList;

    /**
     * mapping header column to number of children
     */
    private HashMap<Integer, Integer> headers;

    /**
     * mapping subHeader column to the sub header
     */
    private Map<Integer, Header> subHeaders;
    private ArrayList<Integer> headIndexes;

    public Table(int page, int id){
        this.headerList = new TreeMap<Integer, Header>();
        this.subHeaders =  new TreeMap<Integer, Header>();
        this.page = page;
        this.id = id;
        this.table = new ArrayList<List<String>>();
        this.headIndexes = new ArrayList<>();
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

    public void initHeaders(HashMap<Integer, Integer> headers){
        this.headers = headers;
        for(int i : headers.keySet()){
            headIndexes.add(i);
        }
        headIndexes.sort(Integer::compareTo);
    }

    public void updateHeader(Integer header){
        if(headers.keySet().contains(header)) {
            headers.replace(header, headers.get(header) + 1);
        }
        else{
            for(int idx = 0 ; idx < headIndexes.size(); idx++){
                if(idx == headIndexes.size() - 1){
                    int h = headIndexes.get(headIndexes.size() - 1);
                    headers.replace(h, headers.get(h) + 1);
                }
                else if(header > headIndexes.get(idx) && header < headIndexes.get(idx + 1)){
                    int h = headIndexes.get(idx);
                    headers.replace(h, headers.get(h) + 1);
                    break;
                }
            }
        }
    }

    public void addRow(List<String> row){
        table.add(row);
    }

    public int getId(){
        return this.id;
    }

    public int getPage(){
        return this.page;
    }

}
