package com.company;

public class Header {
    private String value;
    private int row;
    private int col;
    private Header parent;

    /**
     * constructor where there is no parent
     * @param row
     * @param col
     * @param value
     */
    public Header(int row, int col, String value) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.parent = null;
    }

    /**
     * constructor for sub header with parent
     * @param row
     * @param col
     * @param value
     * @param parent
     */
    public Header(int row, int col, String value, Header parent) {
        this.value = value;
        this.row = row;
        this.col = col;
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Header getParent() {
        return parent;
    }

    public String getValue() {
        return value;
    }


    public boolean hasParent() {
        return this.parent != null;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
