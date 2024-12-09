package org.diegosneves.infrastructure.address;

import org.diegosneves.infrastructure.address.persistence.AddressJpaEntity;
import org.diegosneves.infrastructure.address.persistence.AddressRepository;
import org.diegosneves.domain.client.valueobject.Address;
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

    public Long count() {
        return this.addressRepository.count();
    }

    public void deleteAddress(Address addressToDelete) {
        if (addressToDelete == null) return;
        Optional<AddressJpaEntity> foundAddress = this.addressRepository
                .findAddressJpaEntitiesByNumberAndZip(addressToDelete.getNumber(), addressToDelete.getZip());
        foundAddress.ifPresent(addressJpaEntity -> this.addressRepository.deleteById(addressJpaEntity.getId()));
        this.addressRepository.flush();
    }

}
