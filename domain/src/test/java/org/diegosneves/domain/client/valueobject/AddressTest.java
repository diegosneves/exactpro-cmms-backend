package org.diegosneves.domain.client.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {


    @Test
    void shouldReturnANonNullAddress() {

        Address address = buildAddress();

        assertNotNull(address);
        assertEquals("Rua", address.getStreet());
        assertEquals("333", address.getNumber());
        assertEquals("Bairro", address.getNeighborhood());
        assertEquals("Cidade", address.getCity());
        assertEquals("Estado", address.getState());
        assertEquals("93000000", address.getZip());


    }

    private Address buildAddress() {
        return new Address("Rua", "333", "Bairro", "Cidade", "Estado", "93000000");
    }


    @Test
    void addressSetStreetTest() {
        String expectedStreet = "Street";

        Address address = buildAddress();
        address.setStreet(expectedStreet);

        assertEquals(expectedStreet, address.getStreet());
    }


    @Test
    void shouldSetNumber() {
        String expectedNumber = "123";

        Address address = buildAddress();
        address.setNumber(expectedNumber);

        assertEquals(expectedNumber, address.getNumber());
    }


    @Test
    void testSetNeighborhood_ShouldSetNeighborhood() {
        String expectedNeighborhood = "Centro";

        Address address = buildAddress();
        address.setNeighborhood(expectedNeighborhood);

        assertEquals(expectedNeighborhood, address.getNeighborhood());
    }


    @Test
    void testSetCity_setsCityCorrectly() {
        String expectedCity = "New York";

        Address address = buildAddress();
        address.setCity(expectedCity);

        assertEquals(expectedCity, address.getCity());
    }


    @Test
    void testAddressStateChange() {
        String expectedState = "New York";

        Address address = buildAddress();
        address.setState(expectedState);

        assertEquals(expectedState, address.getState());
    }


    @Test
    void testSetZipReturnsCorrectZip() {
        String expectedZip = "93222111";

        Address address = buildAddress();
        address.setZip(expectedZip);

        assertEquals(expectedZip, address.getZip());
    }
}
