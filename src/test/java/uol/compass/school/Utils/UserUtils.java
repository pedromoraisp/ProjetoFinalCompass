package uol.compass.school.Utils;

import uol.compass.school.dto.request.UserRequestDTO;
import uol.compass.school.entity.User;
import uol.compass.school.enums.Role;

public class UserUtils {

    public static UserRequestDTO createUserRequestDTO() {
        return UserRequestDTO.builder()
                .id(1L)
                .name("Jennifer")
                .email("jennifer@email.com")
                .username("jenniferuser")
                .password("123456")
                .role(Role.ADMIN)
                .build();
    }

    public static User createUser() {
        return User.builder()
                .id(1L)
                .name("Jennifer")
                .email("jennifer@email.com")
                .username("jenniferuser")
                .password("123456")
                .role(Role.ADMIN)
                .build();
    }
}
