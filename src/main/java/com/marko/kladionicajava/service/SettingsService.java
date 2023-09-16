package com.marko.kladionicajava.service;


import com.marko.kladionicajava.entitiy.Settings;
import com.marko.kladionicajava.tools.JsonService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SettingsService {

    @Autowired
    public Settings settings;
    private final JsonService jsonService;

    public void readJsonSettings() {
        settings = jsonService.readJsonFileSettings();
    }
    public Settings getSettings() {
        return settings;
    }

}
