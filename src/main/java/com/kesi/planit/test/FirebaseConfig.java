package com.kesi.planit.test;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init(){
        try{
            //Json 파일 가져오기
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/calendar-api-test-37954-firebase-adminsdk-5aj00-5c0a1a9544.json");

            //json 파일로 옵션 생성
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            //초기화
            FirebaseApp.initializeApp(options);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
