package com.szzzing.api.security.auth;

import com.szzzing.api.dto.UserDto;
import com.szzzing.api.repository.UserRepository;
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
        UserDto userDto = userRepository.selectOneById(id);
        if(userDto == null) {
            throw new UsernameNotFoundException(id);
        }
        return new PrincipalDetails(userDto);
    }
}
