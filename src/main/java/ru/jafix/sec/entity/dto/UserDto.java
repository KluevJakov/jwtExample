package ru.jafix.sec.entity.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.jafix.sec.entity.Role;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UserDto {
    private UUID id;
    private String email;
    private Role role;
}
