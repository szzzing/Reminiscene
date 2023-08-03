package com.szzzing.app.service;

import com.szzzing.app.domain.User;
import com.szzzing.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User insertOne(User user) {
        return userRepository.insertOne(user);
    }

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
}
