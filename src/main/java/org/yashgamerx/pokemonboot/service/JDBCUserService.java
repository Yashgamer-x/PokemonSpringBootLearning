package org.yashgamerx.pokemonboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JDBCUserService {

    private final UserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    public boolean userExists(String username){
        return userDetailsManager.userExists(username);
    }

    public void addUser(String username, String password){
        if(userExists(username)){
            return;
        }
        var encryptedPassword = passwordEncoder.encode(password);
        var user = User.builder()
                .username(username)
                .password(encryptedPassword)
                .roles("USER")
                .build();
        userDetailsManager.createUser(user);
    }

}
