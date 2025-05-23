package com.healthinsurance.healthproject.personaldetailserviceimpl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.healthinsurance.healthproject.personaldetailsrepository.UserRepository;
import com.healthinsurance.healthproject.personalentities.Users;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService{
	
	
	
	@Autowired
    private UserRepository userRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(user.getUsername(), user.getPassword(), new ArrayList<>()); 
    } 
}
