package com.swd391.bachhoasi.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;    
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


@Configuration
public class GoogleConfig {
    @Value("${google.firebase.bucket}")
    private String bucket;
    @Value("${google.credentials.client-id}")
    private String clientId;
    @Value("${google.credentials.client-email}")
    private String clientEmail;
    @Value("${google.credentials.private-key-id}")
    private String privateId;
    @Value("${google.credentials.private-key}")
    private String privateKey;

    GoogleCredentials googleCloudCredentialConfig() throws IOException {
        return ServiceAccountCredentials.fromPkcs8(clientId, clientEmail, privateKey, privateId, null);
    }

    void initFirebaseConnection() {
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
