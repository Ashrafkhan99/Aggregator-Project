package com.example.Aggregator.Controller;

import com.example.Aggregator.DTO.PreferenceDto;
import com.example.Aggregator.Entity.Preference;
import com.example.Aggregator.Services.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/preferences")
public class PreferenceController {
    @Autowired
    private PreferenceService preferenceService;

    @GetMapping("/fetch")
    public Set<Preference> getPreferences(@RequestHeader("Authorization") String token) {
        return preferenceService.getPreferences(token.substring(7));
    }

    @PutMapping("/update")
    public Set<Preference> updatePreferences(@RequestHeader("Authorization") String token, @RequestBody PreferenceDto preferenceDto) {
        return preferenceService.updatePreferences(token.substring(7), preferenceDto);
    }
}
