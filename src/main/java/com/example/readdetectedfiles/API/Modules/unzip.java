package com.example.readdetectedfiles.API.Modules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class unzip {

    // 파일 압축 해제 메서드
    public void unzip(File file) throws IOException {
        ZipEntry entry;
        ZipInputStream zipIn = null;
        try {
            if (!file.exists()) {
                System.out.println("파일이 존재하지 않습니다.");
                return;
            }
            FileInputStream fis = new FileInputStream(file);
            zipIn = new ZipInputStream(fis);
            System.out.println("스트림 가용: " + fis.available() + " bytes");
            entry = zipIn.getNextEntry();

            // 모든 엔트리를 읽어서 처리합니다.
            while (entry != null) {
                // 파일 경로를 압축 파일의 루트 디렉토리에 상대적으로 계산
                String filePath = new File(file.getParent(), entry.getName()).getPath();

                if (!entry.isDirectory()) {
                    // 파일이면 파일을 추출합니다.
                    extractFile(zipIn, filePath);
                } else {
                    // 디렉토리이면 디렉토리를 생성합니다.
                    File dir = new File(filePath);

                    dir.mkdirs();

                    try {
                        Path path = dir.toPath();
                        Set<PosixFilePermission> permissions = new HashSet<>();

                        // 소유자 권한
                        permissions.add(PosixFilePermission.OWNER_READ);
                        permissions.add(PosixFilePermission.OWNER_WRITE);
                        permissions.add(PosixFilePermission.OWNER_EXECUTE);

                        // 그룹 권한
                        permissions.add(PosixFilePermission.GROUP_READ);
                        permissions.add(PosixFilePermission.GROUP_WRITE);
                        permissions.add(PosixFilePermission.GROUP_EXECUTE);

                        // 기타 사용자 권한
                        permissions.add(PosixFilePermission.OTHERS_READ);
                        permissions.add(PosixFilePermission.OTHERS_WRITE);
                        permissions.add(PosixFilePermission.OTHERS_EXECUTE);

                        // 권한 적용 (rwxrwxrwx와 동일)
                        Files.setPosixFilePermissions(path, permissions);

                        log.info("POSIX 권한이 성공적으로 설정되었습니다: " + dir.getAbsolutePath());
                    } catch (UnsupportedOperationException e) {
                        // Windows 등 POSIX를 지원하지 않는 시스템에서 실행 시
                        log.warn("이 시스템은 POSIX 권한을 지원하지 않습니다. 기본 방법으로 권한을 설정합니다.");

                        // 기본 방법으로 권한 설정
                        dir.setReadable(true, false);
                        dir.setWritable(true, false);
                        dir.setExecutable(true, false);
                    } catch (IOException e) {
                        log.error("권한 설정 중 오류 발생: " + e.getMessage());
                    }
                }
                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
        }
    }

    // 파일 압축 해제 메서드 (MultipartFile 버전)
    public void unzip(MultipartFile zipFile, File destDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());

                if (zipEntry.isDirectory()) {
                    try {
                        Path path = newFile.toPath();
                        Set<PosixFilePermission> permissions = new HashSet<>();

                        // 소유자 권한
                        permissions.add(PosixFilePermission.OWNER_READ);
                        permissions.add(PosixFilePermission.OWNER_WRITE);
                        permissions.add(PosixFilePermission.OWNER_EXECUTE);

                        // 그룹 권한
                        permissions.add(PosixFilePermission.GROUP_READ);
                        permissions.add(PosixFilePermission.GROUP_WRITE);
                        permissions.add(PosixFilePermission.GROUP_EXECUTE);

                        // 기타 사용자 권한
                        permissions.add(PosixFilePermission.OTHERS_READ);
                        permissions.add(PosixFilePermission.OTHERS_WRITE);
                        permissions.add(PosixFilePermission.OTHERS_EXECUTE);

                        // 권한 적용 (rwxrwxrwx와 동일)
                        Files.setPosixFilePermissions(path, permissions);

                        log.info("POSIX 권한이 성공적으로 설정되었습니다: " + newFile.getAbsolutePath());
                    } catch (UnsupportedOperationException e) {
                        // Windows 등 POSIX를 지원하지 않는 시스템에서 실행 시
                        log.warn("이 시스템은 POSIX 권한을 지원하지 않습니다. 기본 방법으로 권한을 설정합니다.");

                        // 기본 방법으로 권한 설정
                        newFile.setReadable(true, false);
                        newFile.setWritable(true, false);
                        newFile.setExecutable(true, false);
                    } catch (IOException e) {
                        log.error("권한 설정 중 오류 발생: " + e.getMessage());
                    }
                    newFile.mkdirs();
                } else {
                    // 상위 디렉토리를 먼저 생성
                    File topDirectory = new File(newFile.getParent());

                    topDirectory.mkdirs();

                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }

                    try {
                        Path path = topDirectory.toPath();
                        Set<PosixFilePermission> permissions = new HashSet<>();

                        // 소유자 권한
                        permissions.add(PosixFilePermission.OWNER_READ);
                        permissions.add(PosixFilePermission.OWNER_WRITE);
                        permissions.add(PosixFilePermission.OWNER_EXECUTE);

                        // 그룹 권한
                        permissions.add(PosixFilePermission.GROUP_READ);
                        permissions.add(PosixFilePermission.GROUP_WRITE);
                        permissions.add(PosixFilePermission.GROUP_EXECUTE);

                        // 기타 사용자 권한
                        permissions.add(PosixFilePermission.OTHERS_READ);
                        permissions.add(PosixFilePermission.OTHERS_WRITE);
                        permissions.add(PosixFilePermission.OTHERS_EXECUTE);

                        // 권한 적용 (rwxrwxrwx와 동일)
                        Files.setPosixFilePermissions(path, permissions);

                        log.info("POSIX 권한이 성공적으로 설정되었습니다: " + topDirectory.getAbsolutePath());
                    } catch (UnsupportedOperationException e) {
                        // Windows 등 POSIX를 지원하지 않는 시스템에서 실행 시
                        log.warn("이 시스템은 POSIX 권한을 지원하지 않습니다. 기본 방법으로 권한을 설정합니다.");

                        // 기본 방법으로 권한 설정
                        topDirectory.setReadable(true, false);
                        topDirectory.setWritable(true, false);
                        topDirectory.setExecutable(true, false);
                    } catch (IOException e) {
                        log.error("권한 설정 중 오류 발생: " + e.getMessage());
                    }
                }
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        // 상위 디렉토리를 먼저 생성
        File topDirectory = new File(new File(filePath).getParent());

        topDirectory.mkdirs();

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }

        try {
            Path path = topDirectory.toPath();
            Set<PosixFilePermission> permissions = new HashSet<>();

            // 소유자 권한
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.OWNER_EXECUTE);

            // 그룹 권한
            permissions.add(PosixFilePermission.GROUP_READ);
            permissions.add(PosixFilePermission.GROUP_WRITE);
            permissions.add(PosixFilePermission.GROUP_EXECUTE);

            // 기타 사용자 권한
            permissions.add(PosixFilePermission.OTHERS_READ);
            permissions.add(PosixFilePermission.OTHERS_WRITE);
            permissions.add(PosixFilePermission.OTHERS_EXECUTE);

            // 권한 적용 (rwxrwxrwx와 동일)
            Files.setPosixFilePermissions(path, permissions);

            log.info("POSIX 권한이 성공적으로 설정되었습니다: " + topDirectory.getAbsolutePath());
        } catch (UnsupportedOperationException e) {
            // Windows 등 POSIX를 지원하지 않는 시스템에서 실행 시
            log.warn("이 시스템은 POSIX 권한을 지원하지 않습니다. 기본 방법으로 권한을 설정합니다.");

            // 기본 방법으로 권한 설정
            topDirectory.setReadable(true, false);
            topDirectory.setWritable(true, false);
            topDirectory.setExecutable(true, false);
        } catch (IOException e) {
            log.error("권한 설정 중 오류 발생: " + e.getMessage());
        }
    }
}
