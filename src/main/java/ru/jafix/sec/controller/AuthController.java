package ru.jafix.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.sec.entity.dto.JwtRequest;
import ru.jafix.sec.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    protected AuthService authService;

    @PostMapping
    public ResponseEntity<?> auth(@RequestBody JwtRequest request) {
        try {
            return ResponseEntity.ok(authService.auth(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
