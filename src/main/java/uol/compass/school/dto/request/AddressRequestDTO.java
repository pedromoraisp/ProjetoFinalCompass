package uol.compass.school.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank
    private String street;

    @NotNull
    private Integer number;

    @NotBlank
    private String district;

    @NotBlank
    private String cep;

    @NotBlank
    private String city;

    @NotBlank
    private String uf;
}
