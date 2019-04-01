package com.company;

import java.util.ArrayList;
import java.util.List;

public class Template {
    private List<TableAttributes> tables;

    public Template() {
        this.tables = new ArrayList<>();
    }

    public void addTable(TableAttributes tableAttributes) {
        this.tables.add(tableAttributes);
    }

    public List<TableAttributes> getTables() {
        return tables;
    }
}
