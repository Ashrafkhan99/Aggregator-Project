package com.example.Aggregator.Services;

import com.example.Aggregator.DTO.PreferenceDto;
import com.example.Aggregator.Entity.Preference;
import com.example.Aggregator.Entity.User;
import com.example.Aggregator.Repository.PreferenceRepository;
import com.example.Aggregator.Repository.UserRepository;
import com.example.Aggregator.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PreferenceService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    public Set<Preference> getPreferences(String token) {
        String username = JwtUtil.extractUsername(token);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return user.getPreferences();
    }

    public Set<Preference> updatePreferences(String token, PreferenceDto preferenceDto) {
        String username = JwtUtil.extractUsername(token);
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (preferenceDto.getKeywords() == null || preferenceDto.getKeywords().isEmpty()) {
            throw new IllegalArgumentException("Preference keywords cannot be empty");
        }

        Set<Preference> preferences = preferenceDto.getKeywords().stream()
                .map(keyword -> preferenceRepository.findByKeyword(keyword)
                        .orElseGet(() -> {
                            Preference pref = new Preference();
                            pref.setKeyword(keyword);
                            return preferenceRepository.save(pref);
                        }))
                .collect(Collectors.toSet());

        user.setPreferences(preferences);
        userRepository.save(user);
        return preferences;
    }
}
