package com.szzzing.app.service;

import com.szzzing.app.domain.User;
import com.szzzing.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User insert() {
        return null;
    }

    public User login() {
        return null;
    }

    public ArrayList<User> selectAll() {
        return userRepository.selectAll();
    }

    public User selectById() {
        return null;
    }
}
