package ru.jafix.sec.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jafix.sec.entity.Role;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
}
