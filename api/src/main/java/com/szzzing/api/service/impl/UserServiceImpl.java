package com.szzzing.api.service.impl;

import com.szzzing.api.dto.common.FileDto;
import com.szzzing.api.dto.user.*;
import com.szzzing.api.repository.FileRepository;
import com.szzzing.api.repository.MailRepository;
import com.szzzing.api.repository.TokenRepository;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.service.UserService;
import com.szzzing.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.HashMap;

@Slf4j
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MailRepository mailRepository;
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
    @Transactional
    public boolean modifyUser(UserModifyDto userModifyDto) throws RuntimeException {
        int result = 0;

        if(userModifyDto.getOriginalImage()!=null) userModifyDto.setOriginalImage(userModifyDto.getOriginalImage().replace(profieDir, ""));
        if(userModifyDto.getNewImage()!=null) userModifyDto.setNewImage(userModifyDto.getNewImage().replace(profieDir, ""));
        FileDto fileDto = FileUtil.getFileDto(userModifyDto.getUploadFile(), "profile");

        /*
         * 1. 프로필 사진 완전 삭제
         * 2. 프로필 사진 업로드 - 기본프사였음
         * 3. 프로필 사진 업로드 - 기존사진삭제
         * 4. 프로필 사진 그대로
         */

        // 1. 프로필 사진 완전 삭제
        if (userModifyDto.getNewImage()==null) {
            // 삭제
            fileRepository.deleteFile(userModifyDto.getOriginalImage());
            result = userRepository.updateOne(userModifyDto);

            FileUtil.deleteFile(userModifyDto.getOriginalImage(), "profile");

        // 2. 프로필 사진 업로드 - 기본프사였음
        } else if(userModifyDto.getOriginalImage()==null) {
            fileRepository.insertFile(fileDto);
            userModifyDto.setNewImage(profieDir+fileDto.getRenameName());
            result = userRepository.updateOne(userModifyDto);

            // 업로드
            FileUtil.uploadFile(userModifyDto.getUploadFile(), fileDto, "profile");

        // 3. 프로필 사진 업로드 - 기존사진삭제
        } else if(!userModifyDto.getOriginalImage().equals(userModifyDto.getNewImage())) {
            userModifyDto.setNewImage(profieDir+fileDto.getRenameName());
            result = userRepository.updateOne(userModifyDto);

            fileRepository.insertFile(fileDto);
            fileRepository.deleteFile(userModifyDto.getOriginalImage());

            // 업로드/삭제
            FileUtil.uploadFile(userModifyDto.getUploadFile(), fileDto, "profile");
            FileUtil.deleteFile(userModifyDto.getOriginalImage(), "profile");
        }

        // 4. 프로필 사진 그대로
        else {
            userModifyDto.setNewImage(profieDir+userModifyDto.getOriginalImage());
            result = userRepository.updateOne(userModifyDto);
        }

        return result>0;
    }

    public boolean check(Principal principal, UserCheckDto userCheckDto) {
        userCheckDto.setLoginUser(principal==null ? null : principal.getName());
        
        // 아이디, 이메일 일치 여부
        if(userCheckDto.getId()!=null && userCheckDto.getEmail()!=null) {
            return userRepository.selectOneByIdEmail(userCheckDto) != null;
        }
        // 아이디 존재 여부
        else if(userCheckDto.getId()!=null) {
            return userRepository.selectCountById(userCheckDto) == 0;
        }
        // 닉네임 존재 여부
        else if(userCheckDto.getNickname()!=null) {
            return userRepository.selectCountByNickname(userCheckDto) == 0;
        }
        // 이메일 존재 여부
        else {
            return userRepository.selectCountByEmail(userCheckDto) == 0 && mailRepository.getMailStatus(userCheckDto.getEmail());
        }
    }

    public UserListDto getUserList(UserSelectDto userSelectDto) {
        userSelectDto.setOffset();
        UserListDto userListDto = new UserListDto();
        userListDto.setList(userRepository.selectUserList(userSelectDto));
        userListDto.setPage(userSelectDto.getPage());

        return userListDto;
    }

    public UserDto getUser(String id) {
        UserDto userDto = userRepository.selectOneUser(id);
        if(userDto!=null) userDto.setPw(null);
        return userDto;
    }

    public boolean modifyPw(UserModifyDto userModifyDto) {
        String pw = passwordEncoder.encode(userModifyDto.getPw());
        userModifyDto.setPw(pw);
        return userRepository.updateOnePw(userModifyDto) > 0;
    }

    public boolean checkUserPw(String id, String pw) {
        UserDto user = userRepository.selectOneUser(id);
        return passwordEncoder.matches(pw, user.getPw());
    }

    public boolean withdraw(String id) {
        return userRepository.updateOneEnable(id) > 0;
    }
}
