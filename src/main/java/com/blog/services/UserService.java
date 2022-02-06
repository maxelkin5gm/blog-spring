package com.blog.services;

import com.blog.entities.UserEntity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;

public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    boolean register(UserEntity user);

    UserEntity getAuthUserEntity(Principal principal) throws UsernameNotFoundException;

    UserEntity addImg(String pathImg, Principal principal) throws UsernameNotFoundException;
}
