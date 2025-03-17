package com.example.readdetectedfiles;

import com.example.readdetectedfiles.API.Modules.deepdeepPathDetected;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReadDetectedFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadDetectedFilesApplication.class, args);

        try {
            deepdeepPathDetected path = new deepdeepPathDetected();
            path.path();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
