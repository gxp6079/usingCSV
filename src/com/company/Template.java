package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Template implements Serializable {
    private List<TableAttributes> tables;
    private HashMap<String, Field> fields;
    private final String type;

    public Template(String type) {
        this.tables = new ArrayList<>();
        this.fields = new HashMap<>();
        this.type = type;
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

    public void save() {
        String filename = this.type + ".ser";
        try {
            FileOutputStream fos = new FileOutputStream(new File(filename));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);

            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

}
