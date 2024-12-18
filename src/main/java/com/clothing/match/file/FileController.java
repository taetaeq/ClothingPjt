package com.clothing.match.file;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {

    // 파일을 서버에서 서빙하기 위한 핸들러
    @GetMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {
        // 확장자가 없는 경우 확장자를 자동으로 결정
        String fileExtension = getFileExtension(filename); // 확장자 가져오기
        Path filePath = Paths.get("C:/clothing/uploads/").resolve(filename + fileExtension); // 확장자 포함하여 경로 생성

        // 로그 출력 - 경로 확인
        System.out.println("Requested file path: " + filePath.toString());

        // 파일 리소스를 불러옴
        Resource resource = new FileSystemResource(filePath);

        // 파일이 존재하는지 확인
        if (resource.exists()) {
            // 확장자에 따라 MIME 타입을 수동으로 설정
            String mimeType = getMimeTypeByExtension(filename);

            // MIME 타입을 설정하고 파일을 응답으로 반환
            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(mimeType))  // 지정된 MIME 타입 사용
                    .body(resource);
        } else {
            // 파일이 없으면 404 Not Found 응답
            System.out.println("File not found: " + filename);
            return ResponseEntity.notFound().build();
        }
    }

    // 확장자에 맞는 MIME 타입을 반환하는 메서드
    private String getMimeTypeByExtension(String filename) {
    	
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (filename.endsWith(".png")) {
            return "image/png";
        } else if (filename.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "application/octet-stream"; // 기본값
        }
        
    }

    // 확장자를 자동으로 결정하는 메서드
    private String getFileExtension(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return ".jpg";  // .jpg 확장자를 자동으로 추가
        } else if (filename.endsWith(".png")) {
            return ".png";  // .png 확장자를 자동으로 추가
        } else {
            return ".jpg";  // 기본값으로 .jpg 확장자를 추가 (필요 시 수정)
        }
    }
    
 // 파일 삭제 핸들러 추가
    @DeleteMapping("/uploads/{filename}")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@PathVariable String filename) {
        Path filePath = Paths.get("C:/clothing/uploads/").resolve(filename);

        // 로그 출력 - 경로 확인
        System.out.println("Deleting file path: " + filePath.toString());

        // 파일 삭제
        FileSystemResource fileResource = new FileSystemResource(filePath);
        if (fileResource.exists() && fileResource.getFile().delete()) {
            return ResponseEntity.ok("File deleted successfully.");
        } else {
            return ResponseEntity.status(500).body("Failed to delete file.");
        }
    }
}
