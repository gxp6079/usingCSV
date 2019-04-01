package com.company;

import org.apache.commons.lang3.StringUtils;

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
                subs += StringUtils.repeat(' ', parent.getLength());
                subs += "| ";
            }
            else{
                int toAdd = parent.getLength();
                Header child = null;
                for (int i = 0 ; i < parent.getChildren().size() ; i++){
                    child = parent.getChildren().get(i);
                    subs += child.toString();
                    toAdd -= child.getLength();
                    if(i != parent.getChildren().size() - 1){
                        subs += "| ";
                    }
                }
                if(toAdd > 0){
                    subs += StringUtils.repeat(' ', toAdd);
                    if(child != null){
                        child.updateLength(child.getLength() + toAdd);
                    }
                }
                subs += "| ";
            }
        }
        subs += "\n";
        s += subs;
        for (List<String> row : table){
            String rowString = "";
            int idx = 0;
            for(String value : row){
                rowString += value;
                while(idx < headerList.keySet().size() && (!headerList.keySet().contains(idx)) && !subHeaders.keySet().contains(idx)){
                    idx++;
                }
                int toAdd = 0;
                if(headerList.keySet().contains(idx)){
                    toAdd = headerList.get(idx).getLength() - value.length();
                }
                else if(subHeaders.keySet().contains(idx)){
                    toAdd = subHeaders.get(idx).getLength() - value.length();
                }
                if(toAdd > 0){
                    rowString += StringUtils.repeat(' ', toAdd);
                }
                rowString += "| ";
            }
            rowString += "\n";
            s += rowString;
        }
        return s;
    }

}
