package com.blog.services.impl;

import com.blog.entities.UserEntity;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collections;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder bCryptPasswordEncoder;
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole()))
        );
    }

    @Override
    public boolean register(UserEntity user) {
        try {
            user.setRole("USER");
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public UserEntity getAuthUserEntity(Principal principal) throws UsernameNotFoundException {
        if (principal == null) {
            throw new UsernameNotFoundException("User not authenticated");
        }
        return userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }

    @Override
    public UserEntity addImg(String pathImg, Principal principal) throws UsernameNotFoundException {
        var userEntity = getAuthUserEntity(principal);
        userEntity.setImg(pathImg);
        return userRepository.save(userEntity);
    }
}
