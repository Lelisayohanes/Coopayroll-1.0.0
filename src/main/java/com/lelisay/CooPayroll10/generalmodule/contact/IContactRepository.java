package com.lelisay.CooPayroll10.generalmodule.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IContactRepository extends JpaRepository<Contact,Long> {
    Optional<Contact> findByEmail(String email);

    Optional<Contact> findByPhone(String phone);
}
