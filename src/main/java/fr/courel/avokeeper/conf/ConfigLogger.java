/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.courel.avokeeper.conf;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConfigLogger implements CommandLineRunner {

    @Autowired
    private Environment environment;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Active Profiles: " + String.join(", ", environment.getActiveProfiles()));
        System.out.println("Datasource URL: " + environment.getProperty("spring.datasource.url"));
        System.out.println("Config Locations: " + environment.getProperty("spring.config.location"));
    }
}