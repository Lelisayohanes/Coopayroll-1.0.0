package com.lelisay.CooPayroll10.generalmodule.address.dto;
import com.lelisay.CooPayroll10.generalmodule.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;


    public static AddressResponseDTO fromEntity(Address address) {
        AddressResponseDTO dto = new AddressResponseDTO();
        if (address != null) {
            dto.setStreet(address.getStreet());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setPostalCode(address.getPostalCode());
            dto.setCountry(address.getCountry());
            // Set other fields as needed
        }
        return dto;
    }
}
