package com.szzzing.api.service;

import com.szzzing.api.dto.common.FileDto;
import com.szzzing.api.dto.user.*;
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

public interface UserService {

    // 회원가입
    public boolean register(UserDto userDto);

    // 내 정보 수정
    public boolean modifyUser(UserModifyDto userModifyDto);

    public boolean check(Principal principal, UserCheckDto userCheckDto);

    public UserListDto getUserList(UserSelectDto userSelectDto);

    public UserDto getUser(String id);

    public boolean modifyPw(UserModifyDto userModifyDto);

    public boolean checkUserPw(String id, String pw);

    public boolean withdraw(String id);
}
