package com.szzzing.api.util;

import com.szzzing.api.dto.FileDto;
import error.FileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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

    // 파일 객체 생성
    public static FileDto getFileDto(MultipartFile multipartFile) {
        if(multipartFile==null) return null;

        FileDto fileDto = new FileDto();
        fileDto.setOriginalName(multipartFile.getOriginalFilename());
        fileDto.setRenameName(renameFile(multipartFile));
        fileDto.setSize(multipartFile.getSize());
        log.info(fileDto.toString());

        return fileDto;
    }

    // 파일 업로드
    public static void uploadFile(MultipartFile multipartFile, FileDto fileDto) {
        // File.seperator 는 OS종속적
        Path location = Paths.get(dir + File.separator + fileDto.getRenameName());
        try {
            // inputstream을 가져와 파일 저장
            // 파일명이 기존에 존재하면 덮어씀
            Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileException("file upload error : " + multipartFile.getOriginalFilename());
        }
    }

    // 파일 삭제
    public static void deleteFile(String filename) {
        File file = new File(dir + filename);
        if(file.exists()) {
            file.delete();
        }
    }

    // 파일명 변경
    public static String renameFile(MultipartFile multipartFile) {
        String original = multipartFile.getOriginalFilename();
        String suffix = original.substring(original.lastIndexOf("."));
        return UUID.randomUUID().toString() + suffix;
    }
}
