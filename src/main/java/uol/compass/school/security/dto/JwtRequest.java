package uol.compass.school.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
