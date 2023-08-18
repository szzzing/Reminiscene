package com.szzzing.api.service;

import com.szzzing.api.dto.FileDto;
import com.szzzing.api.dto.UserDto;
import com.szzzing.api.dto.UserModifyDto;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;

    public ArrayList<UserDto> selectAll() {
        return userRepository.selectAll();
    }

    public boolean authCheckId(String id) {
        UserDto userDto = userRepository.selectOneById(id);
        return userDto == null;
    }

    public boolean authCheckEmail(String email) {
        UserDto userDto = userRepository.selectOneByEmail(email);
        return userDto == null;
    }
    
    // 회원가입
    public boolean register(UserDto userDto) {
        String pw = passwordEncoder.encode(userDto.getPw());
        userDto.setPw(pw);
        userDto.setRole("U");
        userDto.setEnable(true);
        userDto.setType("N");
        return userRepository.insertOne(userDto)>0;
    }

    // 내 정보 수정
    public boolean mypageModify(UserModifyDto userModifyDto) throws RuntimeException {

        if(userModifyDto.getOriginalImage()!=null) userModifyDto.setOriginalImage(userModifyDto.getOriginalImage().replace("/upload/", ""));
        if(userModifyDto.getNewImage()!=null) userModifyDto.setNewImage(userModifyDto.getNewImage().replace("/upload/", ""));
        FileDto fileDto = FileUtil.getFileDto(userModifyDto.getUploadFile());

        /*
         * 1. 프로필 사진 완전 삭제 new=null, original!=null, upload=null v
         * 2. 프로필 사진 업로드 - 기본프사였음 new!=null, original=null, upload!=null v
         * 3. 프로필 사진 업로드 - 기존사진삭제 new!=null, original!=null, upload!=null v
         * 4. 프로필 사진 그대로 new!=null, original!=null, upload=null
         */

        // 1. 프로필 사진 완전 삭제
        if (userModifyDto.getNewImage()==null) {
            // 삭제
            FileUtil.deleteFile(userModifyDto.getOriginalImage());
            fileRepository.deleteOne(userModifyDto.getOriginalImage());

        // 2. 프로필 사진 업로드 - 기본프사였음
        } else if(userModifyDto.getOriginalImage()==null) {
            // 업로드
            FileUtil.uploadFile(userModifyDto.getUploadFile(), fileDto);
            fileRepository.insertOne(fileDto);
            // 사용자 정보 수정
            userModifyDto.setNewImage(fileDto.getRenameName());

        // 3. 프로필 사진 업로드 - 기존사진삭제
        } else if(userModifyDto.getOriginalImage()!=null && fileDto!=null) {
            // 업로드
            FileUtil.uploadFile(userModifyDto.getUploadFile(), fileDto);
            fileRepository.insertOne(fileDto);
            // 삭제
            FileUtil.deleteFile(userModifyDto.getOriginalImage());
            fileRepository.deleteOne(userModifyDto.getOriginalImage());
            // 사용자 정보
            userModifyDto.setNewImage(fileDto.getRenameName());
        }
        // 4. 프로필 사진 그대로
        else {
            // 사용자 정보
            userModifyDto.setNewImage(userModifyDto.getOriginalImage());
        }
        // 사용자 정보 수정
        int result = userRepository.updateOne(userModifyDto);

        return result>0;
    }

    public boolean mypageCheckNickname(String nickname) {
        UserDto userDto = userRepository.selectOneByNickname(nickname);
        return userDto == null;
    }

    public boolean mypageCheckEmail(String email) {
        UserDto userDto = userRepository.selectOneByEmail(email);
        return userDto == null;
    }
}
