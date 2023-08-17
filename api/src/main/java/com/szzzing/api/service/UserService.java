package com.szzzing.api.service;

import com.szzzing.api.domain.User;
import com.szzzing.api.repository.UserRepository;
import com.szzzing.api.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ArrayList<User> selectAll() {
        return userRepository.selectAll();
    }

    public boolean authCheckId(String id) {
        User user = userRepository.selectOneById(id);
        return user == null;
    }

    public boolean authCheckEmail(String email) {
        User user = userRepository.selectOneByEmail(email);
        return user == null;
    }
    
    // 회원가입
    public int register(User user) {
        String pw = passwordEncoder.encode(user.getPw());
        user.setPw(pw);
        user.setRole("U");
        user.setEnable(true);
        user.setType("N");
        return userRepository.insertOne(user);
    }


    // 내 정보 수정
    public boolean mypageModify(User user, MultipartFile profileImage) {
        int result = userRepository.updateOne(user);
        String original = user.getProfileImage();

        // 업로드한 사진이 있고, 그 사진이 기존 프로필사진과 다를 때
        if(profileImage!=null) {
            log.info("새로운 사진 업로드 : "+profileImage);
            String renameName = FileUtil.renameFile(profileImage);
            FileUtil.uploadFile(profileImage, renameName);
            // 이전에 존재하는 사진 파일 지우기
            if(user.getProfileImage()!=null) {

            }
        // 업로드한 사진이 없는 경우
        } else {

        }
        return result>=0;
    }

    public boolean mypageCheckNickname(String nickname) {
        User user = userRepository.selectOneByNickname(nickname);
        return user == null;
    }

    public boolean mypageCheckEmail(String email) {
        User user = userRepository.selectOneByEmail(email);
        return user == null;
    }
}
