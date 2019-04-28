package Model;

import com.opencsv.CSVReader;

import javax.servlet.ServletOutputStream;
import javax.xml.crypto.Data;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class TemplateReader {

    public static boolean checkIfExists(String templateName) throws SQLException {
        Connection connection = DataBaseConnection.makeConnection();
        return DataBaseConnection.checkItObjExists(connection, templateName);
    }

    public static void readExistingTemplate(String filename, String templateName, ServletOutputStream out) throws IOException {
        Template template = null;
        try {
            template = readFromDB(templateName);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String[]> list = readAllLines(filename);

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
            out.println(String.valueOf(value));
        }

    }

    public static void getTables(Template template, TableFactory tableFactory, ServletOutputStream out) throws IOException {

        HashMap<Integer, Table> tables = new HashMap<>();

        for(TableAttributes attributes : template.getTables()){
            tableFactory.initialize(attributes.START, attributes.END);
            Table table = tableFactory.makeTable(attributes.getOccurrence());
            tables.put(table.hashCode(), table);
        }

        for(Integer id : tables.keySet()){
            out.println(id);
            out.println(String.valueOf(tables.get(id)));
            out.println("\n");
        }
    }

    public static void createTable(Template template, String start, String end, int instance){

        TableAttributes attributes = new TableAttributes(start, end);
        attributes.setOccurrence(instance);
        template.addTable(attributes);

    }


    public static Template readFromDB(String type) throws SQLException, IOException {
        Connection connection = DataBaseConnection.makeConnection();
        try {
            return (Template) DataBaseConnection.deSerializeJavaObjectFromDB(
                    connection, type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<String[]> readAllLines(String filename) {
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
