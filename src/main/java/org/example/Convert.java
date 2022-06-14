package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

    public List<Employee> parseXML(String path) {
        List<Employee> list = new ArrayList();
        File file = new File(path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            doc = dbf.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            System.out.println("Open parsing error " + e.toString());
        }

        assert doc != null;
        Node rootNode = doc.getDocumentElement();
        NodeList rootChilds = rootNode.getChildNodes();

        for (int i = 0; i < rootChilds.getLength(); i++) {
            Employee employee = new Employee();
            if (rootChilds.item(i).getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            Node u = rootChilds.item(i);
            NodeList o = u.getChildNodes();
            for (int w = 0; w < o.getLength(); w++) {
                if (o.item(w).getNodeType() != Node.ELEMENT_NODE) {
                    continue;
                }
                switch (o.item(w).getNodeName()) {
                    case "id":
                        String str = o.item(w).getTextContent();
                        long id = Long.parseLong(str);
                        employee.setId(id);
                        break;
                    case "firstName":
                        String firstName = o.item(w).getTextContent();
                        employee.setFirstName(firstName);
                        break;
                    case "lastName":
                        String lastName = o.item(w).getTextContent();
                        employee.setLastName(lastName);
                        break;
                    case "country":
                        String country = o.item(w).getTextContent();
                        employee.setCountry(country);
                        break;
                    case "age":
                        String age = o.item(w).getTextContent();
                        Integer age1 = new Integer(age);
                        employee.setAge(age1);
                        break;
                }

            }
            list.add(employee);
        }
        return list;

    }

}

