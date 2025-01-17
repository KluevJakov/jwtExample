package ru.jafix.sec;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.jafix.sec.entity.Role;
import ru.jafix.sec.entity.User;
import ru.jafix.sec.entity.dto.UserDto;
import ru.jafix.sec.repo.RoleRepository;
import ru.jafix.sec.repo.UserRepository;
import ru.jafix.sec.service.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    protected UserService userService;

    @MockitoBean
    protected UserRepository mockUserRepo;

    @MockitoBean
    protected RoleRepository mockRoleRepo;

    //@MockitoBean
    //protected PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setup() {
        when(mockUserRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(mockUserRepo.save(any())).thenReturn(getUserFromRepo());
        when(mockRoleRepo.findByName("USER")).thenReturn(Optional.of(getUserRole()));
        //when(mockPasswordEncoder.encode(anyString())).thenReturn("encoded_password");
    }

    @Test
    void createUserTest() {
        UserDto userDto = userService.createUser(getUserFromClient());

        assertEquals(getExpectedUserDto().toString(), userDto.toString());
    }

    @Test
    void createUserFailureByEmailTest() {
        when(mockUserRepo.findByEmail(anyString())).thenReturn(Optional.of(getUserFromRepo()));
        assertThrows(RuntimeException.class, () -> {
            UserDto userDto = userService.createUser(getUserFromClient());
        });
    }

    @Test
    void createUserFailureByRoleTest() {
        when(mockRoleRepo.findByName("USER")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> {
            UserDto userDto = userService.createUser(getUserFromClient());
        });
    }

    private static User getUserFromClient() {
        User testUserFromClient = new User();
        testUserFromClient.setEmail("test@test.test");
        testUserFromClient.setPassword("raw_password");
        return testUserFromClient;
    }

    private static User getUserFromRepo() {
        User testUser = new User();
        testUser.setId(UUID.fromString("acd9db8f-c429-4d34-99db-8fc4299d3498"));
        testUser.setEmail("test@test.test");
        testUser.setRole(getUserRole());
        return testUser;
    }

    private static Role getUserRole() {
        Role role = new Role();
        role.setName("USER");
        role.setId(UUID.fromString("1dc83c68-26ca-4c88-883c-6826ca1c88a6"));
        return role;
    }

    private static UserDto getExpectedUserDto() {
        return UserDto.builder()
                .id(UUID.fromString("acd9db8f-c429-4d34-99db-8fc4299d3498"))
                .email("test@test.test")
                .role(getUserRole())
                .build();
    }
}
