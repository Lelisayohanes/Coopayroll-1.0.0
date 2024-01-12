package com.lelisay.CooPayroll10.generalmodule.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService implements IContactService{

    private final IContactRepository contactRepository;
    @Override
    public Contact addContactOfCompany(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public Optional<Contact> findByPhone(String phone) {
        return contactRepository.findByPhone(phone);
    }

    @Override
    public Contact saveEmployeeContact(Contact activeContact) {
        return contactRepository.save(activeContact);
    }


}
