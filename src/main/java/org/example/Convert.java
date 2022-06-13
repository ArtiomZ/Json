package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Convert {
    public List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> list;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setColumnMapping(columnMapping);
            strategy.setType(Employee.class);
            CsvToBean<Employee> myBuild = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            list = myBuild.parse();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public String listToJson(List list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        String json = gson.toJson(list, listType);
        return json;
    }

    public void writeString(String str) {
        File file = new File("file.json");
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
