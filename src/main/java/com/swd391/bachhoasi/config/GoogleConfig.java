package com.swd391.bachhoasi.config;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;



@Configuration
public class GoogleConfig {
    @Value("${google.firebase.bucket}")
    private String bucket;
    @Value("${google.credentials.config-name}")
    private String configName;
    private String configJson;
    @Bean
    GoogleCredentials googleCloudCredentialConfig() throws IOException {
        if(configJson == null || configJson.isEmpty()) {
            return ServiceAccountCredentials.fromStream(new ClassPathResource(configName).getInputStream());
        }
        InputStream in = new ByteArrayInputStream(configJson.getBytes());
        return ServiceAccountCredentials.fromStream(in);
    }
    @EventListener
    void initFirebaseConnection(ApplicationReadyEvent event) {
        try {
            FirebaseOptions firebaseOptions = FirebaseOptions.builder()
            .setStorageBucket(bucket)
            .setCredentials(googleCloudCredentialConfig())
            .build();
            FirebaseApp.initializeApp(firebaseOptions);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
