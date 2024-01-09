package com.lelisay.CooPayroll10.generalmodule.address;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final IAddressRepository addressRepository;

    @Override
    public Address addAddressOfCompany(Address address) {
        return addressRepository.save(address);
    }
}
