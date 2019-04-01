package com.company;

import java.io.Serializable;

public class TableAttributes implements Serializable {
    public final String START;
    public final String END;
    public final int PAGE;
    public final int ID;

    public TableAttributes(String start, String end, int page, int id) {
        this.START = start;
        this.END = end;
        this.PAGE = page;
        this.ID = id;
    }
}
