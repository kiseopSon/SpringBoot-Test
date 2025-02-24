package com.example.readdetectedfiles;

import com.example.readdetectedfiles.API.Modules.deepPathDetected;
import com.example.readdetectedfiles.API.Modules.pathDetected;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadDetectedFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadDetectedFilesApplication.class, args);

        try {
            deepPathDetected path = new deepPathDetected();
            path.path();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
