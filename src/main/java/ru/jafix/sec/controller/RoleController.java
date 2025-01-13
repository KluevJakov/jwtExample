package ru.jafix.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.jafix.sec.entity.Role;
import ru.jafix.sec.service.RoleService;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    protected RoleService roleService;

    @PostMapping
    public ResponseEntity<?> createRole(Role role) {
        try {
            Role roleIfPresent = roleService.createRole(role);
            return ResponseEntity.ok(roleIfPresent);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeRole(UUID id) {
        roleService.removeRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRole(UUID id) {
        try {
            Role roleIfPresent = roleService.getRole(id);
            return ResponseEntity.ok(roleIfPresent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
