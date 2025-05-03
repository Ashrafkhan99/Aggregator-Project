package com.example.Aggregator.Services;

import com.example.Aggregator.DTO.UserDto;
import com.example.Aggregator.Entity.User;
import com.example.Aggregator.Entity.VerificationToken;
import com.example.Aggregator.Repository.UserRepository;
import com.example.Aggregator.Repository.VerificationTokenRepository;
import com.example.Aggregator.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public User registerUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setRole(userDto.getRole());
        user.setPreferences(userDto.getPreferences());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(false);
        return userRepository.save(user);
    }

    public void registerToken(User user, String token) {
        // Implement token registration logic here
        // This is a placeholder for the actual implementation
        System.out.println("Token registered: " + token);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        // Set expiration date if needed

        Date expirationTime = new Date(System.currentTimeMillis() + 60 * 60 * 1000); // 1 hour in milliseconds
        verificationToken.setExpirationDate(expirationTime);
        verificationTokenRepository.save(verificationToken);

    }

    public boolean validateToken(String token) {
        Optional<VerificationToken> verificationTokenOptional = Optional.ofNullable(verificationTokenRepository.findByToken(token));
        if (verificationTokenOptional.isPresent()) {
            VerificationToken verificationToken = verificationTokenOptional.get();
            Date expirationDate = (Date) verificationToken.getExpirationDate();
            return expirationDate != null && expirationDate.after(new Date());
        }
        return false;
    }

    public void enableUser(String token) {
        Optional<VerificationToken> verificationTokenOptional = Optional.ofNullable(verificationTokenRepository.findByToken(token));
        if (verificationTokenOptional.isPresent()) {
            VerificationToken verificationToken = verificationTokenOptional.get();
            User user = verificationToken.getUser();
            user.setActive(true);
            userRepository.save(user);
            verificationTokenRepository.delete(verificationToken);
        }
    }

    @Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findByName(username); // Use the correct method
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), Collections.emptyList());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    public String login(UserDto user) {
        Optional<User> userOptional = userRepository.findByName(user.getName());
        if (userOptional.isPresent() && userOptional.get().isActive()) {
            User user1 = userOptional.get();
            if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
                return JwtUtil.generateToken(user1.getName());
            }
        }
        return null;
    }
}
