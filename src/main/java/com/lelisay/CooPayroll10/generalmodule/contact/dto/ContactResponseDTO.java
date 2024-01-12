package com.lelisay.CooPayroll10.generalmodule.contact.dto;

import com.lelisay.CooPayroll10.generalmodule.contact.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponseDTO {
        private String phone;
        private String phone2;
        private String email;
        private String email2;
        private String fax;

        // Constructors, getters, setters, and other methods as needed

        public static ContactResponseDTO fromEntity(Contact contact) {
                ContactResponseDTO dto = new ContactResponseDTO();
                if (contact != null) {
                        dto.setPhone(contact.getPhone());
                        dto.setPhone2(contact.getPhone2());
                        dto.setEmail(contact.getEmail());
                        dto.setEmail2(contact.getEmail2());
                        dto.setFax(contact.getFax());
                        // Set other fields as needed
                }
                return dto;
        }

}
