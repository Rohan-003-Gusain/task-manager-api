package com.taskmanager.security;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    
    public UserDetailsServiceImpl(UserRepository userRepository) {
    		this.userRepository = userRepository;
    }

 // ========== LOAD USER FOR SPRING SECURITY ==========
    @Override
    public UserDetails loadUserByUsername(String username) 
    			throws UsernameNotFoundException {
    	
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        	);
    };
}
