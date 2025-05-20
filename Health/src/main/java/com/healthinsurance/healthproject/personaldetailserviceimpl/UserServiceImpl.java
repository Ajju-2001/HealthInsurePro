package com.healthinsurance.healthproject.personaldetailserviceimpl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.healthinsurance.healthproject.hepler.JwtUtil;
import com.healthinsurance.healthproject.personaldetailsrepository.UserRepository;
import com.healthinsurance.healthproject.personalentities.Users;

@Service
public class UserServiceImpl {
	
	//userRepository to access the user table
	private final UserRepository userRepository;
	
	//jwtUtil to generate JWT tokens
    private final JwtUtil jwtUtil; 
    
    //passwordEncoder to hash passwords using BCrypt
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(Users user) {
    	
    	if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password must not be empty");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            throw new IllegalArgumentException("Role must not be empty");
        }

        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    	
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
        	throw new IllegalArgumentException("Username already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);  

        userRepository.save(user); 
        return "User registered successfully";
    } 

    public String loginUser(Users user) {
    	
    	if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
    	        user.getPassword() == null || user.getPassword().trim().isEmpty()) {
    	        throw new IllegalArgumentException("Username and password must not be empty");
    	    }
    	
        Optional<Users> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            Users foundUser = existingUser.get();  

            if (passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return jwtUtil.generateToken(foundUser);   
            }
        }
        throw new IllegalArgumentException("Invalid username or password");   
    }

} 
