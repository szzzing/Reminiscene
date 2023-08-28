package com.szzzing.api.service;

import com.szzzing.api.dto.common.FileDto;
import com.szzzing.api.dto.user.UserDto;
import com.szzzing.api.dto.user.UserModifyDto;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashMap;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final FileRepository fileRepository;
    private final PasswordEncoder passwordEncoder;
    private final String profieDir = "/upload/profile/";

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

        if(userModifyDto.getOriginalImage()!=null) userModifyDto.setOriginalImage(userModifyDto.getOriginalImage().replace(profieDir, ""));
        if(userModifyDto.getNewImage()!=null) userModifyDto.setNewImage(userModifyDto.getNewImage().replace(profieDir, ""));
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
            userModifyDto.setNewImage(profieDir+fileDto.getRenameName());

        // 3. 프로필 사진 업로드 - 기존사진삭제
        } else if(userModifyDto.getOriginalImage()!=null && fileDto!=null) {
            // 업로드
            FileUtil.uploadFile(userModifyDto.getUploadFile(), fileDto);
            fileRepository.insertOne(fileDto);
            // 삭제
            FileUtil.deleteFile(userModifyDto.getOriginalImage());
            fileRepository.deleteOne(userModifyDto.getOriginalImage());
            // 사용자 정보
            userModifyDto.setNewImage(profieDir+fileDto.getRenameName());
        }
        // 4. 프로필 사진 그대로
        else {
            // 사용자 정보
            userModifyDto.setNewImage(profieDir+userModifyDto.getOriginalImage());
        }
        // 사용자 정보 수정
        int result = userRepository.updateOne(userModifyDto);

        return result>0;
    }

    public boolean check(Principal principal, String id, String email, String nickname) {
        HashMap<String, String> map = new HashMap<>();
        map.put("loginUser", principal==null ? null : principal.getName());
        map.put("id", id);
        map.put("email", email);
        map.put("nickname", nickname);
        log.info(map.toString());

        if(id!=null) return userRepository.selectOneUser(id) == null;
        else if(nickname!=null) return userRepository.selectCountByNickname(map) == 0;
        else return userRepository.selectCountByEmail(map) == 0;
    }

}
