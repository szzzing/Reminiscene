package com.szzzing.api.security.auth;

import com.szzzing.api.dto.user.UserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// spring security에서 사용자 정보를 담는 UserDetails 인터페이스 구현
@Data
public class PrincipalDetails implements UserDetails {
    private UserDto userDto;

    public PrincipalDetails(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList();
        authorities.add((new SimpleGrantedAuthority("ROLE_"+ userDto.getRole())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userDto.getPw();
    }

    @Override
    public String getUsername() {
        return userDto.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userDto.isEnable();
    }
}
