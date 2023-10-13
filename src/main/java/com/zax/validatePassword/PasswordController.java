package com.zax.validatePassword;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class PasswordController {

    @GetMapping("/checkpassword")
    public ResponseEntity<Map<String, String>> checkPassword(@RequestParam String password) {
        if (isValidPassword(password)) {
            return ResponseEntity.ok(Collections.singletonMap("status", "valid"));
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(Collections.singletonMap("status", "invalid"));
        }
    }

    private boolean isValidPassword(String password) {
        // Rule 1: At least 12 characters long.
        if (password.length() < 12) {
            return false;
        }

        // Rule 2 & 3: Contains both uppercase, lowercase and at least one numerical digit.
        if (password.chars().noneMatch(Character::isUpperCase)) {
            return false;
        }
        if (password.chars().noneMatch(Character::isLowerCase)) {
            return false;
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            return false;
        }

        // Rule 4: Contains at least one special character.
        String specialChars = "!@#$%^&*()_+[]{}|;:,.<>?";
        if (password.chars().noneMatch(ch -> specialChars.indexOf(ch) >= 0)) {
            return false;
        }

        // Rule 5: Doesn't contain easily guessable words.
        String[] forbiddenWords = {"password", "1234", "admin", "user"};
        for (String word : forbiddenWords) {
            if (password.toLowerCase().contains(word)) {
                return false;
            }
        }

        return true;
    }
}
