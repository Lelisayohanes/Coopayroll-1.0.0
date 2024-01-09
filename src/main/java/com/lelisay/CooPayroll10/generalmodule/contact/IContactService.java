package com.lelisay.CooPayroll10.generalmodule.contact;

import java.util.Optional;

public interface IContactService {

    Contact addContactOfCompany(Contact contact);

    Optional<Contact> findByEmail(String email);

    Optional<Contact> findByPhone(String phone);
}
