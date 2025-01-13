package ru.jafix.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jafix.sec.entity.Role;
import ru.jafix.sec.repo.RoleRepository;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    protected RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public void removeRole(UUID id) {
        roleRepository.deleteById(id);
    }

    public Role getRole(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Роль не найдена"));
    }
}
