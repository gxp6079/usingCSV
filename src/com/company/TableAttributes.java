package com.company;

import java.io.Serializable;

public class TableAttributes implements Serializable {
    public final String START;
    public final String END;
    public final int PAGE;

    public TableAttributes(String start, String end, int page) {
        this.START = start;
        this.END = end;
        this.PAGE = page;
    }
}
