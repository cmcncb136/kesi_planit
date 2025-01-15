package com.kesi.planit.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init(){
        try{
            //Json 파일 가져오기
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/kesi-b027c-firebase-adminsdk-h67u3-018671b961.json");

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

    //외부에 객체를 빈으로 등록하고 싶은 경우 사용
    @Bean
    public FirebaseAuth initFirebaseAuth(){
        return FirebaseAuth.getInstance();
    }

    @Bean
    public FirebaseMessaging initFirebaseMessaging(){
        return FirebaseMessaging.getInstance();
    }
}
