package uol.compass.school.Utils;

import uol.compass.school.security.dto.JwtRequest;
import uol.compass.school.security.dto.JwtResponse;

public class JwtDTOUtils {

    public static JwtRequest createJwtRequest() {
        return JwtRequest.builder()
                .username("jenniferuser")
                .password("123456")
                .build();
    }

    public static JwtResponse createJwtResponse() {
        return JwtResponse.builder()
                .jwtToken("Token")
                .build();
    }
}
