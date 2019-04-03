package com.company;

import java.io.Serializable;

public class TableAttributes implements Serializable {
    public final String START;
    public final String END;

    public TableAttributes(String start, String end) {
        this.START = start;
        this.END = end;
    }
}
