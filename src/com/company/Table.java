package com.company;

import org.apache.commons.collections.list.LazyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private int page;
    private int id;
    private List<List<String>> table;
    private HashMap<Integer, Integer> headers;
    private ArrayList<Integer> headIndexes;

    public Table(int page, int id){
        this.page = page;
        this.id = id;
        this.table = new ArrayList<List<String>>();
        this.headIndexes = new ArrayList<>();
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
