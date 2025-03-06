package com.example.readdetectedfiles.API.Modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class pathDetected {

    private unzip unzip = new unzip();

    public void path() throws IOException, InterruptedException {
        // 감시할 디렉토리 경로 설정
        Path dir = Paths.get("/home/airflow/programs");
//        Path dir = Paths.get("D://opt//n_sight//files");

        // WatchService 생성
        WatchService watcher = FileSystems.getDefault().newWatchService();

        // 디렉토리에 감시 이벤트 등록 (파일 등록)
        dir.register(watcher, ENTRY_CREATE);

        System.out.println("파일경로 감시: " + dir);

        // 감시 루프
        while (true) {
            WatchKey key = null;
            try {
                key = watcher.take(); // 이벤트가 발생할 때까지 대기
            } catch (InterruptedException ex) {
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // 이벤트의 경로를 가져옴
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                // 파일 생성 이벤트 감지
                if (kind == ENTRY_CREATE) {
                    System.out.println("New file created: " + fileName);
                    if(fileName.toString().endsWith(".zip")) {
                        File file = new File(dir.toFile().getPath()+File.separator+fileName.toString());
                        Thread.sleep(1000);
                        unzip.unzip(file);
                    }
                }
            }

            // 키 재설정 (필수)
            boolean valid = key.reset();
            if (!valid) {
                break;
            }
        }
    }

    public static boolean isZipFile(String filePath) {
        // 파일 경로를 Path 객체로 변환
        Path path = Paths.get(filePath);

        // 파일 이름 추출
        String fileName = path.getFileName().toString();

        // 파일 이름이 .zip으로 끝나는지 확인
        return fileName.toLowerCase().endsWith(".zip");
    }
}

