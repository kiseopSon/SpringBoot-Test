package com.example.readdetectedfiles.API.Modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class deepPathDetected {

    private unzip unzip = new unzip();
    // 각 WatchKey와 디렉토리 경로를 매핑하기 위한 Map
    private Map<WatchKey, Path> keyPathMap = new HashMap<>();

    public void path() throws IOException, InterruptedException {
        // 감시할 기본 디렉토리 경로 설정
        Path baseDir = Paths.get("/nas/programs");
        //Path baseDir = Paths.get("E://opt//files");

        // WatchService 생성
        WatchService watcher = FileSystems.getDefault().newWatchService();

        // 기본 디렉토리 등록
        registerDirectory(baseDir, watcher);

        System.out.println("파일감시 경로: " + baseDir + " 밑으로 종속 검색 시작");

        // 감시 루프
        while (true) {
            WatchKey key = null;
            try {
                key = watcher.take(); // 이벤트가 발생할 때까지 대기
            } catch (InterruptedException ex) {
                return;
            }

            Path parentDir = keyPathMap.get(key);
            if (parentDir == null) {
                System.err.println("WatchKey를 인식하지 못했습니다!");
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                // 이벤트가 발생한 파일/디렉토리의 상대 경로
                Path relativePath = (Path) event.context();
                // 절대 경로 생성
                Path fullPath = parentDir.resolve(relativePath);

                if (kind == ENTRY_CREATE) {
                    System.out.println("새로 생성된 항목: " + fullPath);
                    // 새 디렉토리가 생성된 경우 해당 디렉토리도 감시하도록 등록
                    if (Files.isDirectory(fullPath)) {
                        registerDirectory(fullPath, watcher);
                    } else if (fullPath.toString().endsWith(".zip")) {
                        // .zip 파일이면 unzip 처리 (파일이 완전히 생성될 때까지 잠시 대기)
                        Thread.sleep(1000);
                        unzip.unzip(fullPath.toFile());
                    }
                }
            }

            // WatchKey 재설정
            boolean valid = key.reset();
            if (!valid) {
                keyPathMap.remove(key);
                // 모든 디렉토리 감시가 종료되면 루프 탈출
                if (keyPathMap.isEmpty()) {
                    break;
                }
            }
        }
    }

    // 디렉토리를 WatchService에 등록하는 헬퍼 메서드
    private void registerDirectory(Path dir, WatchService watcher) throws IOException {
        WatchKey key = dir.register(watcher, ENTRY_CREATE);
        keyPathMap.put(key, dir);
        System.out.println("감시 디렉토리 등록됨: " + dir);
    }
}
