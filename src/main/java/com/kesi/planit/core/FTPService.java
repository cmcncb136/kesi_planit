package com.kesi.planit.core;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class FTPService {
    private static FTPClient ftp = null;
    private static final Logger log = LoggerFactory.getLogger(FTPService.class);
    public static final String DEFAULT_PATH = "planIt";

    @Value("${ftp.server}")
    private String server;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    /* FTP 서버 연결*/
    public FTPClient connectFTP(){
        if(ftp != null) //이미 만들어진 객체가 있다면 원래 객체를 리턴
            return ftp;

        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("utf-8");


        try{
            ftp.connect(server, port); //연결시도

            int replyCode = ftp.getReplyCode(); //연결 후 응답 값
            log.info("replyCode : {}", replyCode); //응답 값 확인

            if(!FTPReply.isPositiveCompletion(replyCode))
                throw new RuntimeException("FTP server refused connection");

            if(!ftp.login(username, password))
                throw  new RuntimeException("FTP server login failed");

            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();//매우매우 중요! 패시브 모드
            return this.ftp = ftp;
        }catch (IOException e){
            throw new RuntimeException("FPT Connection Exception!");
        }
    }

    public void init(){
        if(ftp == null) return;

        if(ftp.isConnected()){
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                ftp = null;
            }
        }
    }


    //파일 업로드
    public boolean uploadFileToFTP(MultipartFile file, String uid, String path, String name){
        FTPClient ftp = connectFTP();

        try{
            ftp.changeWorkingDirectory(DEFAULT_PATH); //기본 경로로 이동
            if(!ftp.changeWorkingDirectory(uid)) { //+ "/" + path)){ //해당 경로가 존재하지 않으면
                ftp.makeDirectory(uid); // + "/" + path ); //해당 경로를 생성
                ftp.changeWorkingDirectory(uid); // + "/" + path); //해당 경로로 다시 이동
            }

            if(!ftp.changeWorkingDirectory(path)) { //Todo. 추후 하위폴더까지 만들어 코드 수정해야 됨
                ftp.makeDirectory(path);
                ftp.changeWorkingDirectory(path);
            }

            log.info("WorkingDirectory : {}", ftp.printWorkingDirectory()); //이동한 디렉토리 출력


            //log.info("fileBytes : {}", fileBytes.length);
            byte[] fileData = file.getBytes(); //받은 값을 Byte로 변경

            return ftp.storeFile(name, new ByteArrayInputStream(fileData)); //파일 저장(자동으로 덮어씀)
        } catch (IOException e) {
            log.warn(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            init();
        }
    }
}

