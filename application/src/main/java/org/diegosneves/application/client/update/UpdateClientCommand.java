package org.diegosneves.application.client.update;

import org.diegosneves.domain.client.valueobject.Address;
import org.diegosneves.domain.client.valueobject.Contact;

public record UpdateClientCommand(
        String id,
        String cnpj,
        Address address,
        Contact contact,
        String companyName,
        String companyBrach,
        String companySector) {

    public static UpdateClientCommand with(
            final String id,
            final String cnpj,
            final Address address,
            final Contact contact,
            final String companyName,
            final String companyBrach,
            final String companySector) {

        return new UpdateClientCommand(id, cnpj, address, contact, companyName, companyBrach, companySector);
    }

}
