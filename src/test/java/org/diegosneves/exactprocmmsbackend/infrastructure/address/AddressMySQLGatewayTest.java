package org.diegosneves.exactprocmmsbackend.infrastructure.address;

import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.infrastructure.MySQLGatewayTest;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressJpaEntity;
import org.diegosneves.exactprocmmsbackend.infrastructure.address.persistence.AddressRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@MySQLGatewayTest
class AddressMySQLGatewayTest {

    @Autowired
    private AddressMySQLGateway gateway;

    @Autowired
    private AddressRepository repository;

    @Test
    void givenAValidAddressWhenCallsCreateShouldReturnANewAddress() {
        final var expectedStreet = "Rua";
        final var expectedNumber = "333";
        final var expectedNeighborhood = "Bairro";
        final var expectedCity = "Cidade";
        final var expectedState = "Estado";
        final var expectedZip = "93000000";
        final var anAddress = new Address(expectedStreet, expectedNumber, expectedNeighborhood, expectedCity, expectedState, expectedZip);

        assertEquals(0, repository.count());

        final var actualAddress = this.gateway.create(anAddress);

        assertEquals(1, repository.count());

        assertNotNull(actualAddress);
        assertEquals(expectedStreet, actualAddress.getStreet());
        assertEquals(expectedNumber, actualAddress.getNumber());
        assertEquals(expectedNeighborhood, actualAddress.getNeighborhood());
        assertEquals(expectedCity, actualAddress.getCity());
        assertEquals(expectedState, actualAddress.getState());
        assertEquals(expectedZip, actualAddress.getZip());

        final var addressJpaEntity = this.repository.findAddressJpaEntitiesByNumberAndZip(anAddress.getNumber(), anAddress.getZip()).orElse(null);

        assertNotNull(addressJpaEntity);
        assertNotNull(addressJpaEntity.getId());
        assertEquals(expectedStreet, addressJpaEntity.getStreet());
        assertEquals(expectedNumber, addressJpaEntity.getNumber());
        assertEquals(expectedNeighborhood, addressJpaEntity.getNeighborhood());
        assertEquals(expectedCity, addressJpaEntity.getCity());
        assertEquals(expectedState, addressJpaEntity.getState());
        assertEquals(expectedZip, addressJpaEntity.getZip());
    }

    @Test
    void givenAnExistingAddressWhenCallsCreateShouldReturnAExistingAddress() {
        final var expectedStreet = "Rua";
        final var expectedNumber = "333";
        final var expectedNeighborhood = "Bairro";
        final var expectedCity = "Cidade";
        final var expectedState = "Estado";
        final var expectedZip = "93000000";
        final var anAddress = new Address(expectedStreet, expectedNumber, expectedNeighborhood, expectedCity, expectedState, expectedZip);

        this.repository.save(AddressJpaEntity.from(anAddress));

        assertEquals(1, repository.count());

        final var actualAddress = this.gateway.create(anAddress);

        assertEquals(1, repository.count());

        assertNotNull(actualAddress);
        assertEquals(expectedStreet, actualAddress.getStreet());
        assertEquals(expectedNumber, actualAddress.getNumber());
        assertEquals(expectedNeighborhood, actualAddress.getNeighborhood());
        assertEquals(expectedCity, actualAddress.getCity());
        assertEquals(expectedState, actualAddress.getState());
        assertEquals(expectedZip, actualAddress.getZip());

        final var addressJpaEntity = this.repository.findAddressJpaEntitiesByNumberAndZip(anAddress.getNumber(), anAddress.getZip()).orElse(null);

        assertNotNull(addressJpaEntity);
        assertNotNull(addressJpaEntity.getId());
        assertEquals(expectedStreet, addressJpaEntity.getStreet());
        assertEquals(expectedNumber, addressJpaEntity.getNumber());
        assertEquals(expectedNeighborhood, addressJpaEntity.getNeighborhood());
        assertEquals(expectedCity, addressJpaEntity.getCity());
        assertEquals(expectedState, addressJpaEntity.getState());
        assertEquals(expectedZip, addressJpaEntity.getZip());
    }

    @Test
    void givenANullAddressWhenCallsCreateShouldReturnANullAddress() {

        assertEquals(0, repository.count());

        final var actualAddress = this.gateway.create(null);

        assertEquals(0, repository.count());
        assertNull(actualAddress);
    }

}
