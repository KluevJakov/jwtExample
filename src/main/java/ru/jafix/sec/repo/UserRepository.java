package ru.jafix.sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jafix.sec.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
