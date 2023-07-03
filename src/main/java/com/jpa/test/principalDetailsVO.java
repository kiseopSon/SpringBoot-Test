package com.jpa.test;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class principalDetailsVO implements UserDetails{
	private final testVO vo;
	
	public testVO getUser() {
		return vo;
	}
	
	@Override
	public String getPassword() {
        return vo.getPassword();
    }
	@Override
    public String getUsername() {
        return vo.getUsername();
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
        return true;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		vo.getRoleList().forEach(r -> {
			authorities.add(() -> {return r;});
		});
		return authorities;
	}

}
