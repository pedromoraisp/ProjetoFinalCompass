package uol.compass.school.Utils;

import uol.compass.school.dto.request.AddressRequestDTO;
import uol.compass.school.dto.response.AddressDTO;
import uol.compass.school.entity.Address;

public class AddressUtils {

    public static Address createAddress() {
        return Address.builder()
                .street("Rua das Araras")
                .number(10)
                .district("Bairro das Aroeiras")
                .cep("31000000")
                .city("Bela Uberlândia")
                .uf("MG")
                .build();
    }

    public static AddressRequestDTO createAddressRequestDTO() {
        return AddressRequestDTO.builder()
                .street("Rua das Araras")
                .number(10)
                .district("Bairro das Aroeiras")
                .cep("31000000")
                .city("Bela Uberlândia")
                .uf("MG")
                .build();
    }

    public static AddressDTO createAddressDTO() {
        return AddressDTO.builder()
                .street("Rua das Araras")
                .number(10)
                .district("Bairro das Aroeiras")
                .cep("31000000")
                .city("Bela Uberlândia")
                .uf("MG")
                .build();
    }


}
