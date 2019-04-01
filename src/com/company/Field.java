package com.company;

import java.io.Serializable;
import java.util.ArrayList;

public class Field implements Serializable {
    public final String NAME;
    public final int TABLE_ID;
    public final String HEADER;

    public Field(String name, int table, String header){
        this.NAME = name;
        this.TABLE_ID = table;
        this.HEADER = header;
    }

    public ArrayList<String> getValue(Table table){
        return table.getDataAt(HEADER);
    }

}
