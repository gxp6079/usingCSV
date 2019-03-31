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
     * mapping subHeader column to the sub header
     */
    private Map<Integer, Header> subHeaders;

    public Table(int page, int id){
        this.headerList = new TreeMap<Integer, Header>();
        this.subHeaders =  new TreeMap<Integer, Header>();
        this.page = page;
        this.id = id;
        this.table = new ArrayList<List<String>>();
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
        table.add(row);
    }

    public int getId(){
        return this.id;
    }

    public int getPage(){
        return this.page;
    }

    /**
    public String toString(){

    }
     */

}
