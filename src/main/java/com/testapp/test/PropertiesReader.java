package com.testapp.test;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class PropertiesReader {

    JSONParser jsonParser = new JSONParser();
    JSONObject jsonObject = null;

    public void readPropertiesFile() {

        try {
            jsonObject = (JSONObject) jsonParser.parse(new FileReader("src/main/resources/environment.json"));
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

    }

    public String getProperty(String key) {
        String value = (String) jsonObject.get(key);
        return value.trim();
    }

}
