package com.marko.kladionicajava.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.marko.kladionicajava.entitiy.Settings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;



@Service
@RequiredArgsConstructor
public class JsonService {
    public void writeJsonFileSettings(Settings settings) {
       // settings = setSettingsInJson();
        Gson gson = new Gson();
        String json = gson.toJson(settings);
        try (FileWriter fileWriter = new FileWriter("src/main/resources/json/settings.json")) {
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Settings readJsonFileSettings() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(new File("src/main/resources/json/settings.json"), Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new Settings();
        }
    }


    public Settings setSettingsInJson(){
        Settings settings = new Settings();
        settings.setTimeReviewMaxbet(12);
        settings.setTimeReviewMozzart("3");
        settings.setStakeForCalculation(100F);
        settings.setTimeRefreshMatches(360);
        settings.setTimeRefreshQuotas(12);
        settings.setMinimumQuota(1.5F);
        settings.setMinimumPayment(50F);
        settings.setMinimumProfit(3.5F);
        return settings;
    }


}
