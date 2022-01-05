package uol.compass.school.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {

    private String street;

    private Integer number;

    private String district;

    private String cep;

    private String city;

    private String uf;
}
