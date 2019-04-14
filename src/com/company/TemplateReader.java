package com.company;

import com.opencsv.CSVReader;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TemplateReader {


    public static void readTemplate(String filename, String templateName) {
        Template template = null;
        //try {
        //    template = readFromDB(templateName);
        //} catch (SQLException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        List<String[]> list = readAllLines(filename);
        if (template != null) {
            Map<Integer, Table> tables = new HashMap<>();

            TableFactory tableFactory = new TableFactory(list);
            for (TableAttributes ta : template.getTables()) {
                tableFactory.initialize(ta.START, ta.END);
                Table table = tableFactory.makeTable(ta.getOccurrence());
                if (table != null) tables.put(table.hashCode(), table);
            }

            /**
             * name of field to value
             */
            Map<String, List<String>> values = new HashMap<>();

            for (Field field : template.getFields().values()) {
                List<String> value = field.getValue(tables.get(field.TABLE_ID));
                values.put(field.NAME, value);
                System.out.println(value);
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
            System.out.println("Enter the start and end to tables that you would like");
            String attString = scan.nextLine();
            while(!attString.equals("")){
                String[] allAtributes = attString.split(",");
                String start = allAtributes[0];
                String end = allAtributes[1];
                attributes = new TableAttributes(start, end);


                tableFactory.initialize(start, end);
                int location = 1;
                if (tableFactory.getNumLocations() > 1) {
                    System.out.println(start + " was found " + tableFactory.getNumLocations() + " times, which instance do you want.");
                    location = Integer.parseInt(scan.nextLine().trim());
                }
                attributes.setOccurrence(location);
                template.addTable(attributes);

                Table table = tableFactory.makeTable(location);
                tables.put(table.hashCode(), table);
                attString = scan.nextLine();
            }

            for(Integer id : tables.keySet()){
                System.out.println(id);
                System.out.println(tables.get(id));
                System.out.println("\n");
            }

            for(String fieldName : template.getFields().keySet()){
                System.out.println("Input the location of " + fieldName + "(format : id,header)");
                String[] loc = scan.nextLine().split(",");
                int idTarget = Integer.valueOf(loc[0]);
                String header = loc[1];
                template.addField(new Field(fieldName, idTarget, header));
            }

            try {
                template.saveDB();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

    private static Template readFromDB(String type) throws SQLException, IOException {
        Connection connection = DataBaseConnection.makeConnection();
        try {
            return (Template) DataBaseConnection.deSerializeJavaObjectFromDB(
                    connection, type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
