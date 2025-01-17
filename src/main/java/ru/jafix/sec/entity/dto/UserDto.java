package ru.jafix.sec.entity.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.jafix.sec.entity.Role;

import java.util.UUID;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserDto {
    private UUID id;
    private String email;
    private Role role;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
