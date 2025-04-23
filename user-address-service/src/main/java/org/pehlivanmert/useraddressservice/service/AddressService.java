package org.pehlivanmert.useraddressservice.service;

import lombok.RequiredArgsConstructor;
import org.pehlivanmert.useraddressservice.entity.Address;
import org.pehlivanmert.useraddressservice.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public Address getAddressByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }
}
