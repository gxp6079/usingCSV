package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template implements Serializable {
    private List<TableAttributes> tables;
    private HashMap<String, Field> fields;

    public Template() {
        this.tables = new ArrayList<>();
        this.fields = new HashMap<>();
        fields.put("Data", null);
        fields.put("Conta", null);
        fields.put("Descricao", null);
        fields.put("Valor Bruto", null);
    }

    public void addTable(TableAttributes tableAttributes) {
        this.tables.add(tableAttributes);
    }

    public void addField(Field field){
        fields.replace(field.NAME, field);
    }

    public Map<String, Field> getFields() {
        return this.fields;
    }

    public List<TableAttributes> getTables() {
        return tables;
    }

}
