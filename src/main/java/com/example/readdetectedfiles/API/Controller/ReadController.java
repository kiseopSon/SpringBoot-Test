package com.example.readdetectedfiles.API.Controller;


import com.example.readdetectedfiles.API.Modules.dagCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReadController {

//    @PostMapping("create")
//    public void setCreate(@RequestBody String params){
//        dagCreate dagCreate = new dagCreate(params, "D:\\opt\\n_sight\\files");
//    }
//
//    @PostMapping("creates")
//    public void setCreates(@RequestBody List<String> params){
//        dagCreate dagCreate = new dagCreate(params, "테스트", "D:\\opt\\n_sight\\files");
//    }

    @GetMapping("/")
    public String index() {
        return "Hello World";
    }


    @GetMapping("logs")
    public void getPath() {

        // 로그 파일 리스트 읽어오기
        List<File> logFiles = listLogFiles("/opt/nobrandetl/logs");
        System.out.println(logFiles);

        // 가장 최근 파일 찾기
        File latestFile = findLatestFile(logFiles);
        if (latestFile != null) {
            System.out.println("Latest log file: " + latestFile.getName());

            // 가장 최근 파일 내용 읽어오기
            String fileContent = readFileContent(latestFile);
            System.out.println("Content of the latest log file:\n" + fileContent);
        } else {
            System.out.println("Could not determine the latest log file.");
        }
    }

    // 지정된 디렉토리에서 .logs 파일 리스트 읽어오기
    private static List<File> listLogFiles(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified path is not a valid directory.");
            return new ArrayList<>();
        }

        File[] files = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".logs"));
        return files != null ? Arrays.asList(files) : new ArrayList<>();
    }

    // 가장 최근 파일 찾기
    private static File findLatestFile(List<File> files) {
        return files.stream()
                .max(Comparator.comparingLong(File::lastModified))
                .orElse(null);
    }

    // 파일 내용 읽어오기
    private static String readFileContent(File file) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }

        return content.toString();
    }
}
