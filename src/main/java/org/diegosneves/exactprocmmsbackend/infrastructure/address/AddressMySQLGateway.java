package org.diegosneves.exactprocmmsbackend.infrastructure.address;

import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressMySQLGateway {

    private final AddressRepository addressRepository;

    public AddressMySQLGateway(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressJpaEntity create(Address anAddress) {
        AddressJpaEntity newAddress = null;
        if (anAddress != null) {
            newAddress = this.createOrUpdateAddress(anAddress);
        }
        return newAddress;
    }

    private AddressJpaEntity createOrUpdateAddress(Address anAddress) {
        Optional<AddressJpaEntity> addressSearchResult = this.addressRepository.findAddressJpaEntitiesByNumberAndZip(anAddress.getNumber(), anAddress.getZip());
        return addressSearchResult.orElseGet(() -> this.addressRepository.saveAndFlush(AddressJpaEntity.from(anAddress)));
    }


}
