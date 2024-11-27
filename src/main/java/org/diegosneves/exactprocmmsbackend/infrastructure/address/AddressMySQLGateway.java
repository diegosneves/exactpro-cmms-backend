package org.diegosneves.exactprocmmsbackend.infrastructure.address;

import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressMySQLGateway {

    private final AddressRepository addressRepository;

    public AddressMySQLGateway(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


}
