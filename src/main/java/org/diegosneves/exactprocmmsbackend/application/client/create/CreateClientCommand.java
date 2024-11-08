package org.diegosneves.exactprocmmsbackend.application.client.create;

import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.Address;
import org.diegosneves.exactprocmmsbackend.domain.client.valueobject.ClientContact;

public record CreateClientCommand(
        String cnpj,
        Address address,
        ClientContact contact,
        String companyName,
        String companyBrach,
        String companySector) {

    public static CreateClientCommand with(final String cnpj,
                                           final Address address,
                                           final ClientContact contact,
                                           final String companyName,
                                           final String companyBrach,
                                           final String companySector) {
        return new CreateClientCommand(cnpj, address, contact, companyName, companyBrach, companySector);
    }

}
