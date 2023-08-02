package com.szzzing.app.security.auth;

import com.szzzing.app.domain.User;
import com.szzzing.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 유저 정보를 불러와 UserDetails로 리턴
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.selectOneById(id);
        if(user == null) {
            throw new UsernameNotFoundException(id);
        }
        return new PrincipalDetails(user);
    }
}
