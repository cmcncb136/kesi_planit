package com.kesi.planit.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @Bean //외부에 객체를 빈으로 등록하고 싶은 경우 사용
    public FirebaseAuth init(){
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

        return FirebaseAuth.getInstance();
    }

}
