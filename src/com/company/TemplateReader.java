package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.util.*;

public class TemplateReader {


    public static void readTemplate(String filename, String templateName) {
        Template template = readTemplate(templateName);
        List<String[]> list = readAllLines(filename);
        if (template != null) {
            Map<Integer, Table> tables = new HashMap<>();

            TableFactory tableFactory = new TableFactory(list);
            for (TableAttributes ta : template.getTables()) {
                tableFactory.initialize(ta.START, ta.END, ta.PAGE);
                Table table = tableFactory.makeTable();
                if (table != null) tables.put(table.hashCode(), table);
            }

            /**
             * name of field to value
             */
            Map<String, List<String>> values = new HashMap<>();

            for (Field field : template.getFields().values()) {
                values.put(field.NAME, field.getValue(tables.get(field.TABLE_ID)));
            }




            // make list of tables
            // read tables from template
            // create tables based off start and end from template
            //
            // create list for requested data
            // call each field in the template for what data is can give us with the correct tables
        } else {
            Scanner scan = new Scanner(System.in);
            template = new Template(templateName);
            Map<Integer, Table> tables = new HashMap<>();
            TableFactory tableFactory = new TableFactory(list);

            TableAttributes attributes;
            while(scan.hasNextLine()){
                String[] allAtributes = scan.nextLine().split(" ");
                String start = allAtributes[0];
                String end = allAtributes[1];
                int page = Integer.valueOf(allAtributes[2]);
                attributes = new TableAttributes(start, end, page);

                template.addTable(attributes);

                tableFactory.initialize(start, end, page);
                Table table = tableFactory.makeTable();
                tables.put(table.hashCode(), table);
            }

            for(Integer id : tables.keySet()){
                System.out.println(id);
                System.out.println(tables.get(id));
            }

            for(String fieldName : template.getFields().keySet()){
                System.out.println("Input the location of " + fieldName + "(format : id header)");
                String[] loc = scan.nextLine().split(" ");
                int idTarget = Integer.valueOf(loc[0]);
                String header = loc[1];
                template.addField(new Field(fieldName, idTarget, header));
            }

            template.save();

        }
    }


    private static Template readTemplate(String type) {
        try {
            FileInputStream fis = new FileInputStream(new File(type + ".ser"));
            ObjectInputStream ois = new ObjectInputStream(fis);

            Template temp = (Template) ois.readObject();

            return temp;
        } catch (Exception e) {
            return null;
        }
    }


    private static List<String[]> readAllLines(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> list = csvReader.readAll();
            reader.close();
            csvReader.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
