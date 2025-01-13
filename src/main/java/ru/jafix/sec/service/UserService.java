package ru.jafix.sec.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.jafix.sec.config.Constants;
import ru.jafix.sec.entity.Role;
import ru.jafix.sec.entity.User;
import ru.jafix.sec.entity.dto.UserDto;
import ru.jafix.sec.repo.RoleRepository;
import ru.jafix.sec.repo.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService implements UserDetailsService {
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    public UserDto createUser(User user) {
        Optional<User> userIfPresent = userRepository.findByEmail(user.getEmail());

        if (userIfPresent.isPresent()) {
            throw new RuntimeException("Email занят другим пользователем");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Optional<Role> roleIfPresent = roleRepository.findByName(Constants.Roles.USER_CODE);

        if (roleIfPresent.isEmpty()) {
            log.error("Роли не инициализированы, регистрация отменена.");
            throw new RuntimeException("Ошибка регистрации! Обратитесь к администратору");
        }

        user.setRole(roleIfPresent.get());

        User savedUser = userRepository.save(user);

        return UserDto.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

    public void removeUser(UUID id) {
        userRepository.deleteById(id);
    }

    public UserDto getUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким id найден"));

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("Пользователь с таким email найден"));
    }
}
