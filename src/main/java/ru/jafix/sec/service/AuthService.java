package ru.jafix.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.jafix.sec.entity.User;
import ru.jafix.sec.entity.dto.JwtRequest;
import ru.jafix.sec.repo.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String auth(JwtRequest request) {
        Optional<User> userIfPresent = userRepository.findByEmail(request.getEmail());

        if (userIfPresent.isEmpty()) {
            throw new NoSuchElementException("Такого пользователя не существует");
        }

        if (!passwordEncoder.matches(request.getPassword(), userIfPresent.get().getPassword())) {
            throw new RuntimeException("Неверный пароль");
        }

        return jwtService.generate(userIfPresent.get());
    }
}
