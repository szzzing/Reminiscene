package com.szzzing.api.service;

import com.szzzing.api.domain.User;
import com.szzzing.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ArrayList<User> selectAll() {
        return userRepository.selectAll();
    }

    public User selectOneById(String id) {
        User user = userRepository.selectOneById(id);
        if(user!=null) user.setPw(null);
        return user;
    }

    public User selectOneByEmail(String email) {
        User user = userRepository.selectOneByEmail(email);
        return user;
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
}
