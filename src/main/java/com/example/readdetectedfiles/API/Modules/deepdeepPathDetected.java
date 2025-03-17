package com.example.readdetectedfiles.API.Modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class deepdeepPathDetected {

    private unzip unzip = new unzip();
    // 각 WatchKey와 디렉토리 경로를 매핑하기 위한 Map
    private Map<WatchKey, Path> keyPathMap = new HashMap<>();

    public void path() throws IOException, InterruptedException {
        // 감시할 기본 디렉토리 경로 설정
        Path baseDir = Paths.get("/nas/programs");
        //Path baseDir = Paths.get("E://opt//files");

        // WatchService 생성
        WatchService watcher = FileSystems.getDefault().newWatchService();

        // 기본 디렉토리 등록 및 모든 하위 디렉토리도 재귀적으로 등록
        registerDirectoryAndSubdirectories(baseDir, watcher);

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

                // OVERFLOW 이벤트는 무시
                if (kind == OVERFLOW) {
                    continue;
                }

                // 이벤트가 발생한 파일/디렉토리의 상대 경로
                Path relativePath = (Path) event.context();
                // 절대 경로 생성
                Path fullPath = parentDir.resolve(relativePath);

                if (kind == ENTRY_CREATE) {
                    System.out.println("새로 생성된 항목: " + fullPath);

                    // 새 디렉토리가 생성된 경우 해당 디렉토리도 감시하도록 등록
                    if (Files.isDirectory(fullPath)) {
                        try {
                            registerDirectory(fullPath, watcher);

                            // 새로 생성된 디렉토리 내에 이미 존재하는 하위 디렉토리도 등록
                            Files.walkFileTree(fullPath, new SimpleFileVisitor<Path>() {
                                @Override
                                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                                    if (!dir.equals(fullPath)) { // 자기 자신은 이미 등록했으므로 제외
                                        registerDirectory(dir, watcher);
                                    }
                                    return FileVisitResult.CONTINUE;
                                }
                            });
                        } catch (IOException e) {
                            System.err.println("디렉토리 등록 중 오류 발생: " + e.getMessage());
                        }
                    } else if (fullPath.toString().endsWith(".zip")) {
                        // .zip 파일이면 unzip 처리 (파일이 완전히 생성될 때까지 잠시 대기)
                        try {
                            Thread.sleep(1000);
                            System.out.println("ZIP 파일 감지: " + fullPath);
                            unzip.unzip(fullPath.toFile());
                        } catch (Exception e) {
                            System.err.println("ZIP 파일 처리 중 오류 발생: " + e.getMessage());
                        }
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
        if (Files.isDirectory(dir)) {
            WatchKey key = dir.register(watcher, ENTRY_CREATE);
            keyPathMap.put(key, dir);
            System.out.println("감시 디렉토리 등록됨: " + dir);
        }
    }

    // 디렉토리와 모든 하위 디렉토리를 재귀적으로 등록하는 메서드
    private void registerDirectoryAndSubdirectories(Path startDir, WatchService watcher) throws IOException {
        // 시작 디렉토리 등록
        registerDirectory(startDir, watcher);

        // 모든 하위 디렉토리 등록
        Files.walkFileTree(startDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (!dir.equals(startDir)) { // 시작 디렉토리는 이미 등록했으므로 제외
                    registerDirectory(dir, watcher);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                System.err.println("파일 방문 실패: " + file + " - " + exc.getMessage());
                return FileVisitResult.CONTINUE;
            }
        });
    }
}