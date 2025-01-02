package org.diegosneves.infrastructure.address.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.diegosneves.domain.client.valueobject.Address;

public record CreateAddressApiInput(
        @JsonProperty("street") String street,
        @JsonProperty("number") String number,
        @JsonProperty("neighborhood") String neighborhood,
        @JsonProperty("city") String city,
        @JsonProperty("state") String state,
        @JsonProperty("zip") String zip
) {

    public Address toAddress() {
        return new Address(street, number, neighborhood, city, state, zip);
    }

    public static CreateAddressApiInput fromAddress(Address address) {
        return new CreateAddressApiInput(
                address.getStreet(),
                address.getNumber(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZip()
        );
    }

}
