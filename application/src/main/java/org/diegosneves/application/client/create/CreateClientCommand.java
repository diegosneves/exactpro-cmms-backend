package org.diegosneves.application.client.create;

import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;

public record CreateClientCommand(
        String cnpj,
        Address address,
        Contact contact,
        String companyName,
        String companyBrach,
        String companySector) {

    public static CreateClientCommand with(final String cnpj,
                                           final Address address,
                                           final Contact contact,
                                           final String companyName,
                                           final String companyBrach,
                                           final String companySector) {
        return new CreateClientCommand(cnpj, address, contact, companyName, companyBrach, companySector);
    }

}
