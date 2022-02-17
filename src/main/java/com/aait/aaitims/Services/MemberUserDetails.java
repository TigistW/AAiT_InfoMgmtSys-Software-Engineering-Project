package com.aait.aaitims.Services;

import java.util.Collection;

import com.aait.aaitims.Entity.User;
import com.aait.aaitims.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
// @Component
public class MemberUserDetails implements UserDetails {

    private User user;

    @Autowired
    private UserRepository Repo;

    // @Override
    public void save(User user) throws Exception {

        // Let's check if user already registered with us
        if (checkIfUserExist(user.getEmail())) {
            throw new Exception("User already exists for this email");
        }
    }

    // @Override
    public boolean checkIfUserExist(String email) {
        return Repo.findByEmail(email) != null ? true : false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

}
