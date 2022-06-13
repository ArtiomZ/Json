package org.example;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Convert convert = new Convert();
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        List<Employee> list = convert.parseCSV(columnMapping, fileName);
        String str = convert.listToJson(list);
        convert.writeString(str);
    }
}