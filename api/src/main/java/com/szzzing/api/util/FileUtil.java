package com.szzzing.api.util;

import com.szzzing.api.dto.common.FileDto;
import error.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
public class FileUtil {
    private final static String dir = "../../Reminiscene/upload/";
    private final static String url = "/upload/";

    // 파일 객체 생성
    public static FileDto getFileDto(MultipartFile multipartFile, String type) {
        if(multipartFile==null) return null;

        FileDto fileDto = new FileDto();
        fileDto.setOriginalName(multipartFile.getOriginalFilename());
        fileDto.setRenameName(renameFile(multipartFile));
        fileDto.setDirectory(url+type+"/");
        fileDto.setSize(multipartFile.getSize());

        return fileDto;
    }

    // 파일 업로드
    public static void uploadFile(MultipartFile multipartFile, FileDto fileDto, String type) {
        // File.seperator 는 OS 종속적
        Path location = Paths.get(dir+type+"/" + File.separator + fileDto.getRenameName());
        try {
            // inputstream을 가져와 파일 저장
            // 파일명이 기존에 존재하면 덮어씀
            Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileException("file upload error : " + multipartFile.getOriginalFilename());
        }
    }

    // 파일 삭제
    public static boolean deleteFile(String filename, String type) {
        File file = new File(dir + type + "/" + filename);
        if(file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    // 파일명 변경
    public static String renameFile(MultipartFile multipartFile) {
        String original = multipartFile.getOriginalFilename();
        String suffix = original.substring(original.lastIndexOf("."));
        return UUID.randomUUID().toString() + suffix;
    }
}
