package com.example.readdetectedfiles.API.Modules;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
                    newFile.mkdirs();
                } else {
                    // 상위 디렉토리를 먼저 생성
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
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
        new File(new File(filePath).getParent()).mkdirs();
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {
            byte[] bytesIn = new byte[4096];
            int read;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
        }
    }
}
